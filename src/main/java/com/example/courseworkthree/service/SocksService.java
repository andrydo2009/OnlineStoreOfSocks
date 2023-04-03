package com.example.courseworkthree.service;

import com.example.courseworkthree.model.operations.OperationsWithSocks;
import com.example.courseworkthree.model.socks.ColorSocks;
import com.example.courseworkthree.model.socks.SizeSocks;
import com.example.courseworkthree.model.socks.Socks;

import java.util.List;
import java.util.Map;

public interface SocksService {


    Map<OperationsWithSocks, Socks> showOperationsMap();

    boolean editSocksList(Socks socks);

    Socks addSocksToList(Socks socks);

    Socks addSocks(Socks socks);

    List<Socks> showSocksList();


    int getSocksAvailability(int min , int max , ColorSocks colorParam , SizeSocks sizeParam);

    boolean searchIdenticalSocksList(Socks socksComparable , Socks socksList);

    boolean deleteDefectiveSocks(Socks socks);

}
