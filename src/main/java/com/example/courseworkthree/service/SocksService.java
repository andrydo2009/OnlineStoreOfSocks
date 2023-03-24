package com.example.courseworkthree.service;

import com.example.courseworkthree.model.Socks;

import java.util.Map;

public interface SocksService {


    Socks addSocksToMap(Socks socks);

    Map<Socks,Integer> showSocksList();
}
