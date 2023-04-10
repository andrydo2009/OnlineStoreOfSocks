package com.example.courseworkthree.service;

import java.io.File;


public interface SocksFileService {




    boolean cleanSocksDataFile();

    File getDataSocksFile();

    boolean cleanOperationDataFile();

    File getOperationDataSocksFile();
}
