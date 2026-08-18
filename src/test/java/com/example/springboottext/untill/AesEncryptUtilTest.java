package com.example.springboottext.untill;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AesEncryptUtilTest {

    @Test
    void encrypt() {
    }

    @Test
    void decrypt() {
        String key = "123456";
        //加密
       key =  SHA256Util.encryptPassword(key);
        System.out.println(key);
    }
}