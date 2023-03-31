package com.example.courseworkthree.service;

import com.example.courseworkthree.model.ColorSocks;
import com.example.courseworkthree.model.SizeSocks;
import com.example.courseworkthree.model.Socks;

import java.util.List;

public interface SocksService {


    boolean editSocksList(Socks socks);

    Socks addSocksToList(Socks socks);

    Socks addSocks(Socks socks);

    List<Socks> showSocksList();


    int getSocksAvailability(int min , int max , ColorSocks colorParam , SizeSocks sizeParam);

    boolean searchIdenticalSocksList(Socks socksComparable , Socks socksList);

    boolean deleteDefectiveSocks(Socks socks);
}
