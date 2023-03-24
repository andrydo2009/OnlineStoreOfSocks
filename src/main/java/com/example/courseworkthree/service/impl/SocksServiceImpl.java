package com.example.courseworkthree.service.impl;


import com.example.courseworkthree.model.ColorSocks;
import com.example.courseworkthree.model.SizeSocks;
import com.example.courseworkthree.model.Socks;
import com.example.courseworkthree.service.SocksService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Service
public class SocksServiceImpl implements SocksService {
  
    private static  Map<Socks, Integer> socksMap = new HashMap<> ();

//    private static final int availabilitySocks=0; // количество носков на складе


    @PostConstruct // когда метод отмечен этой аннотацией, он будет вызываться сразу после внедрения зависимости
    private void init() {
        socksMap.put ( new Socks ( ColorSocks.BLACK, SizeSocks.S5,50),100 );
        socksMap.put ( new Socks (ColorSocks.BLACK,SizeSocks.S1,100),80 );
    }

    @Override
    public Integer addSocksToList(Socks socks,int quantity ) { //заполняем карту где Socks key, а value количество с которым мы будем работать
       socksMap.put ( socks,quantity );
       return quantity;
    }

    @Override
    public Map<Socks,Integer> showSocksList () {
        ObjectUtils.isNotEmpty(socksMap);
        return socksMap;
    }



}
