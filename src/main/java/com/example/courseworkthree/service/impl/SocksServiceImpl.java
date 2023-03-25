package com.example.courseworkthree.service.impl;


import com.example.courseworkthree.model.Socks;
import com.example.courseworkthree.service.SocksService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;


@Service
public class SocksServiceImpl implements SocksService {

    private static LinkedList<Socks> socksLinkedList = new LinkedList<> ();


    @PostConstruct // когда метод отмечен этой аннотацией, он будет вызываться сразу после внедрения зависимости
    private void init() {
    }



    @Override
    public Socks addSocks(Socks socks) {
        socksLinkedList.add ( socks );
        return socks;
    }


    @Override
    public LinkedList<Socks> showSocksList() { //выводим весь список товара
        return socksLinkedList;
    }

    @Override
    public Socks editSocksList(Socks socks) {
        return null;
    }

    @Override
    public Socks addSocksToList(Socks socks) {// пополняем наш склад носков
        for (Socks s : socksLinkedList) {
            if (s.getColorSocks ().equals ( socks.getColorSocks () ) &&
                    s.getSocksOfComposition () == socks.getSocksOfComposition () &&
                    s.getSizeSocks () == socks.getSizeSocks ()) {
                s.setQuantity ( s.getQuantity () + socks.getQuantity () );
                return socks;
            }
        }
        return addSocksToList ( socks );
    }

    @Override
    public boolean searchIdenticalSocksList(Socks socksComparable) {// ищем одинаковые носки в списке без учета их количества
        for (Socks s : socksLinkedList
        ) {
            if (s.getColorSocks ().equals ( socksComparable.getColorSocks () ) &&
                    s.getSocksOfComposition () == socksComparable.getSocksOfComposition () &&
                    s.getSizeSocks () == socksComparable.getSizeSocks ()) {
                return true;
            }
        }
        return false;
    }
}
