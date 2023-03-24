package com.example.courseworkthree.service;

import com.example.courseworkthree.model.Socks;

import java.util.Map;

public interface SocksService {
    Integer addSocksToList(Socks socks,int quantity);

    Map<Socks,Integer> showSocksList();
}
