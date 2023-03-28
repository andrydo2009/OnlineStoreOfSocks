package com.example.courseworkthree.model;

public enum SizeSocks {
    S25 ( 25 ), S27 ( 27 ), S30 ( 30 ), S32 ( 32 ), S34 ( 34 ), S36 ( 36 ), S40 ( 40 ), S42 ( 42 ), S44 ( 44 ), S50 ( 50 ), S52 ( 52 );
    final Integer size;

    SizeSocks(Integer size) {
        this.size = size;
    }

}
