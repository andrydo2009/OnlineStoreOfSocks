package com.example.courseworkthree.service.impl;



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



    @PostConstruct // когда метод отмечен этой аннотацией, он будет вызываться сразу после внедрения зависимости
    private void init() {
    }

    @Override
    public Socks addSocksToMap(Socks socks ) { //заполняем карту где Socks key, а value количество с которым мы будем работать
       socksMap.putIfAbsent ( socks, socks.getQuantity () );
       return socks;
    }

    @Override
    public Map<Socks,Integer> showSocksList () {
        ObjectUtils.isNotEmpty(socksMap);
        return socksMap;
    }



}
