package com.example.courseworkthree.service.impl;


import com.example.courseworkthree.model.operations.OperationsWithSocks;
import com.example.courseworkthree.model.operations.TypeOperationWithSocks;
import com.example.courseworkthree.model.socks.ColorSocks;
import com.example.courseworkthree.model.socks.SizeSocks;
import com.example.courseworkthree.model.socks.Socks;
import com.example.courseworkthree.service.SocksFileService;
import com.example.courseworkthree.service.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class SocksServiceImpl implements SocksService {

    private static List<Socks> socksLinkedList = new LinkedList<> ();

    private static Map<Socks, OperationsWithSocks> operationsWithSocksSocksMap = new HashMap<> ();

    private final SocksFileService socksFileService;


    public SocksServiceImpl(SocksFileService socksFileService) {
        this.socksFileService = socksFileService;

    }

    @PostConstruct // когда метод отмечен этой аннотацией, он будет вызываться сразу после внедрения зависимости
    private void init() {
    }


    @Override
    public Socks addSocks(Socks socks) {
        socksLinkedList.add ( socks );
        operationsWithSocksSocksMap.put ( socks , createInOperation () );
        saveSocksFile ();
        saveOperationFile ();
        return socks;
    }


    @Override
    public List<Socks> showSocksList() { //выводим весь список товара
        socksLinkedList = readSocksFile ();
        return socksLinkedList;
    }


    @Override
    public Map<Socks,OperationsWithSocks> showOperationsMap(){
        return operationsWithSocksSocksMap;
    }

    @Override
    public boolean editSocksList(Socks socks) { // удаляем необходимое количество носков со склада
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s ) && s.getQuantity () >= socks.getQuantity () && operationsWithSocksSocksMap.containsKey ( socks )) {
                s.setQuantity ( s.getQuantity () - socks.getQuantity () );
                operationsWithSocksSocksMap.put ( socks,createExOperation () );
                saveSocksFile ();
                saveOperationFile ();
            }
        }
        return false;
    }

    @Override
    public Socks addSocksToList(Socks socks) {// пополняем наш склад носков
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s )&&operationsWithSocksSocksMap.containsKey ( socks )) {
                s.setQuantity ( s.getQuantity () + socks.getQuantity () );
                operationsWithSocksSocksMap.put ( socks,createInOperation () );
                saveSocksFile ();
                saveOperationFile ();
                return socks;
            }
        }
        return addSocks ( socks );
    }

    @Override
    public int getSocksAvailability(int min , int max , ColorSocks colorParam , SizeSocks sizeParam) {
        int count = 0;
        for (Socks s : socksLinkedList) {
            if (searchComposition ( min , max , s.getSocksOfComposition () ) && s.getColorSocks ().equals ( colorParam )) {
                count = count + s.getQuantity ();
            }
        }
        return count;
    }


    @Override
    public boolean searchIdenticalSocksList(Socks socksComparable , Socks socksList) {// ищем одинаковые носки в списке без учета их количества
        return socksList.getColorSocks ().equals ( socksComparable.getColorSocks () ) &&
                socksList.getSocksOfComposition () == socksComparable.getSocksOfComposition () &&
                socksList.getSizeSocks () == socksComparable.getSizeSocks ();
    }

    @Override
    public boolean deleteDefectiveSocks(Socks socks) {
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s ) && socks.getQuantity () < s.getQuantity () && socks.getQuantity () > 0&&operationsWithSocksSocksMap.containsKey ( socks )) {
                s.setQuantity ( s.getQuantity () - socks.getQuantity () );
                operationsWithSocksSocksMap.put ( socks,createExOperation () );
                saveSocksFile ();
                saveOperationFile ();
            } else if (s.getQuantity () == socks.getQuantity ()&&operationsWithSocksSocksMap.containsKey ( socks )) {
                operationsWithSocksSocksMap.put ( socks ,createExOperation ());
                socksLinkedList.remove ( s );
                saveSocksFile ();
                saveOperationFile ();
            }
            return true;
        }
        return false;
    }

    public boolean searchComposition(int min , int max , int param) {
        return min < param && param < max;
    }

    private List<Socks> readSocksFile() {
        List<Socks> socksList = new LinkedList<> ();
        try (FileReader reader = new FileReader ( "src/main/resources/socks.json" )) {
            Gson gson = new Gson ();
            Socks[] socks = gson.fromJson ( reader , Socks[].class );
            socksList.addAll ( Arrays.asList ( socks ) );
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return socksList;
    }

    private void saveSocksFile() {
        socksFileService.cleanSocksDataFile ();
        Gson gson = new GsonBuilder ().setPrettyPrinting ().create ();
        String json = gson.toJson ( socksLinkedList );
        try (FileWriter writer = new FileWriter ( "src/main/resources/socks.json" )) {
            writer.write ( json );
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private OperationsWithSocks createExOperation() {
        String date= String.valueOf ( LocalDateTime.now () );
        return new OperationsWithSocks ( TypeOperationWithSocks.EXTRADITION_SOCKS,date);
    }

    private OperationsWithSocks createInOperation() {
        String date= String.valueOf ( LocalDateTime.now () );
        return new OperationsWithSocks ( TypeOperationWithSocks.ACCEPTANCE_SOCKS,date);
    }

    private void saveOperationFile() {
        try {
            String json = new ObjectMapper ().writeValueAsString ( operationsWithSocksSocksMap);
            socksFileService.saveOperationsToFile ( json );
        } catch (JsonProcessingException e) {
            throw new RuntimeException ( e );
        }
    }


}


