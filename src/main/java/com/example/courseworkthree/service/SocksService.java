package com.example.courseworkthree.service;

import com.example.courseworkthree.model.Socks;

import java.util.LinkedList;

public interface SocksService {



    Socks addSocksToList(Socks socks);

    Socks addSocks(Socks socks);

    LinkedList<Socks> showSocksList();

    Socks editSocksList(Socks socks);

    boolean searchIdenticalSocksList(Socks socksComparable);
}
