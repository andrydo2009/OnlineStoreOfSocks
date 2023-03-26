package com.example.courseworkthree.service.impl;


import com.example.courseworkthree.model.ColorSocks;
import com.example.courseworkthree.model.SizeSocks;
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
    public boolean editSocksList(Socks socks) { // удаляем необходимое количество носков со склада
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s ) && s.getQuantity () >= socks.getQuantity ()) {
                s.setQuantity ( s.getQuantity () - socks.getQuantity () );
                return true;
            }
        }
        return false;
    }

    @Override
    public Socks addSocksToList(Socks socks) {// пополняем наш склад носков
        for (Socks s : socksLinkedList) {
            if (searchIdenticalSocksList ( socks , s )) {
                s.setQuantity ( s.getQuantity () + socks.getQuantity () );
                return socks;
            }
        }
        return addSocks ( socks );
    }
@Override
    public int getSocksAvailability(int min , int max , ColorSocks colorParam , SizeSocks sizeParam) {
        int count = 0;
        for (Socks s : socksLinkedList) {
            if (searchComposition ( min , max , s.getSocksOfComposition () )&&s.getColorSocks ().equals ( colorParam )) {
                count=count+s.getQuantity ();
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

    public boolean searchComposition(int min , int max , int param) {
        return min < param && param < max;
    }


}
