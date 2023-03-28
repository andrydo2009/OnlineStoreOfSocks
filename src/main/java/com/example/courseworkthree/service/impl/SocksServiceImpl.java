package com.example.courseworkthree.service.impl;


import com.example.courseworkthree.model.ColorSocks;
import com.example.courseworkthree.model.SizeSocks;
import com.example.courseworkthree.model.Socks;
import com.example.courseworkthree.service.SocksFileService;
import com.example.courseworkthree.service.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;


@Service
public class SocksServiceImpl implements SocksService {

    private static List<Socks> socksLinkedList = new LinkedList<> ();

    private final SocksFileService socksFileService;

    public SocksServiceImpl(SocksFileService socksFileService) {
        this.socksFileService = socksFileService;
    }

    @PostConstruct // когда метод отмечен этой аннотацией, он будет вызываться сразу после внедрения зависимости
    private void init() {
        //readSocksFromFile ();
    }


    @Override
    public Socks addSocks(Socks socks) {
        socksLinkedList.add ( socks );
        //saveSocksToFile ();
        return socks;
    }


    @Override
    public List<Socks> showSocksList() { //выводим весь список товара
        //readSocksFromFile ();
        return socksLinkedList;
    }

    @Override
    public boolean editSocksList(Socks socks) { // удаляем необходимое количество носков со склада
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s ) && s.getQuantity () >= socks.getQuantity ()) {
                s.setQuantity ( s.getQuantity () - socks.getQuantity () );
                //saveSocksToFile ();
            }
        }
        return false;
    }

    @Override
    public Socks addSocksToList(Socks socks) {// пополняем наш склад носков
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s )) {
                s.setQuantity ( s.getQuantity () + socks.getQuantity () );
                //saveSocksToFile ();
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
            if (searchIdenticalSocksList ( socks , s ) && socks.getQuantity () < s.getQuantity () && socks.getQuantity () > 0) {
                s.setQuantity ( s.getQuantity () - socks.getQuantity () );
                //saveSocksToFile ();
            } else if (s.getQuantity () == socks.getQuantity ()) {
                socksLinkedList.remove ( s );
                //saveSocksToFile
            }
            return true;
        }
        return false;
    }

    public boolean searchComposition(int min , int max , int param) {
        return min < param && param < max;
    }


    private void saveSocksToFile() {
        try {
            String json = new ObjectMapper ().writeValueAsString ( socksLinkedList );
            socksFileService.saveSocksToFile ( json );
        } catch (JsonProcessingException e) {
            throw new RuntimeException ( e );
        }
    }

    private void readSocksFromFile() {
        try {
            String json = socksFileService.readSocksFromFile ();
            socksLinkedList = new ObjectMapper ().readValue ( json , new TypeReference<LinkedList<Socks>> () {
            } );
        } catch (JsonProcessingException e) {
            throw new RuntimeException ( e );
        }
    }


}
