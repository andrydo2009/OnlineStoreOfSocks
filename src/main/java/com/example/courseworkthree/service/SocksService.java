package com.example.courseworkthree.service;

import com.example.courseworkthree.model.Socks;

import java.util.LinkedList;

public interface SocksService {


    boolean editSocksList(Socks socks);

    Socks addSocksToList(Socks socks);

    Socks addSocks(Socks socks);

    LinkedList<Socks> showSocksList();


    boolean searchIdenticalSocksList(Socks socksComparable , Socks socksList);
}
