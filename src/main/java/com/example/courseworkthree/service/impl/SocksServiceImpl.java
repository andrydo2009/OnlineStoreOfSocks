package com.example.courseworkthree.service.impl;

import com.example.courseworkthree.model.operations.OperationsWithSocks;
import com.example.courseworkthree.model.operations.TypeOperationWithSocks;
import com.example.courseworkthree.model.socks.ColorSocks;
import com.example.courseworkthree.model.socks.SizeSocks;
import com.example.courseworkthree.model.socks.Socks;
import com.example.courseworkthree.service.MapDeserialize;
import com.example.courseworkthree.service.SocksFileService;
import com.example.courseworkthree.service.SocksService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {
    private  static final List<Socks> socksLinkedList = new LinkedList<> ();
    private  static Map<OperationsWithSocks, Socks> operationsWithSocksSocksMap = new HashMap<> ();
    private final SocksFileService socksFileService;

    public SocksServiceImpl(SocksFileService socksFileService) {
        this.socksFileService = socksFileService;
    }

    @PostConstruct
    private void init() {
    }

    @Override
    public Socks addSocks(Socks socks) {
        operationsWithSocksSocksMap.put ( createInOperation () ,
                new Socks (socks.getColorSocks (),socks.getSizeSocks (),socks.getSocksOfComposition (),socks.getQuantity ()));
        socksLinkedList.add ( socks);
        saveSocksFile ();
        saveOperationFile ();
        return socks;
    }

    @Override
    public Socks addSocksToList(Socks socks) {
        operationsWithSocksSocksMap.put ( createInOperation () ,
                new Socks (socks.getColorSocks (),socks.getSizeSocks (),socks.getSocksOfComposition (),socks.getQuantity ()));
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s )) {
                s.setQuantity ( s.getQuantity () + socks.getQuantity () );
                saveSocksFile ();
                saveOperationFile ();
            return socks;
            }
        }
        return addSocks ( socks );
    }

    @Override
    public List<Socks> showSocksList() {
        readSocksFile ();
        return socksLinkedList;
    }

    @Override
    public Map<OperationsWithSocks, Socks> showOperationsMap() {
        try {
            operationsWithSocksSocksMap = readOperationFile ();
        } catch (IOException e) {
            throw new RuntimeException ( e );
        }
        return operationsWithSocksSocksMap;
    }

    @Override
    public boolean editSocksList(Socks socks) {
        operationsWithSocksSocksMap.put ( createExOperation () ,
                new Socks (socks.getColorSocks (),socks.getSizeSocks (),socks.getSocksOfComposition (),socks.getQuantity ()));
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s ) && socks.getQuantity () < s.getQuantity () && socks.getQuantity () > 0) {
                s.setQuantity ( s.getQuantity () - socks.getQuantity () );
                saveOperationFile ();
                saveSocksFile ();
            } else if (s.getQuantity () == socks.getQuantity ()) {
                socksLinkedList.remove ( s );
                saveSocksFile ();
                saveOperationFile ();
            }
            return true;
        }
        return false;
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
                socksList.getSizeSocks ().equals ( socksComparable.getSizeSocks () ) &&
                socksList.getSocksOfComposition () == socksComparable.getSocksOfComposition ();
    }

    @Override
    public boolean deleteDefectiveSocks(Socks socks) {
        operationsWithSocksSocksMap.put ( createExOperation () ,new Socks (socks.getColorSocks (),socks.getSizeSocks (),socks.getSocksOfComposition (),socks.getQuantity ()));
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s ) && socks.getQuantity () < s.getQuantity () && socks.getQuantity () > 0) {
                s.setQuantity ( s.getQuantity () - socks.getQuantity () );
                saveOperationFile ();
                saveSocksFile ();
            } else if (s.getQuantity () == socks.getQuantity ()) {
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
        String date = String.valueOf ( new Date () );
        return new OperationsWithSocks ( TypeOperationWithSocks.EXTRADITION_SOCKS , date );
    }

    private OperationsWithSocks createInOperation() {
        String date = String.valueOf ( new Date () );
        return new OperationsWithSocks ( TypeOperationWithSocks.ACCEPTANCE_SOCKS , date );
    }

    private  void saveOperationFile() {
       socksFileService.cleanOperationDataFile ();
        Gson gson = new GsonBuilder ()
                .registerTypeAdapter ( Map.class , new com.example.courseworkthree.service.MapSerializer () )
                .setPrettyPrinting ()
                .create ();
        String json = gson.toJson ( operationsWithSocksSocksMap , Map.class );
        try (FileWriter writer = new FileWriter ( "src/main/resources/operations.json" )) {
            writer.write ( json );
        } catch (IOException e) {
            throw new RuntimeException ( e );
        }
    }

    private Map<OperationsWithSocks, Socks> readOperationFile() throws IOException {
        Map<OperationsWithSocks, Socks> operationsMap = new HashMap<> ();
        try (FileReader reader = new FileReader ( "src/main/resources/operations.json" )) {
            Gson gson = new GsonBuilder ()
                    .registerTypeAdapter ( Map.class , new MapDeserialize () )
                    .create ();
            operationsMap = gson.fromJson ( reader , HashMap.class );
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return operationsMap;
    }
}



