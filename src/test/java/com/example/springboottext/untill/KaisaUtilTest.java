package com.example.springboottext.untill;

import com.mysql.cj.log.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KaisaUtilTest {

    @Test
    void encryptKaisa() {
        String key = "携摍搔搕搖搗搘搙";//密文
        System.out.println("解压的明文:"+ KaisaUtil.decryptKaiser(key));
    }

    @Test
    void decryptKaiser() {
        String key = "Wj123456";
        //加密
        key = KaisaUtil.encryptKaisa(key);
        System.out.println("加密后的 :" + key);
        key = KaisaUtil.decryptKaiser(key);
        System.out.println("解密后的 :" + key);

    }
}