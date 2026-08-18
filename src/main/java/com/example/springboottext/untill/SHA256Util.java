package com.example.springboottext.untill;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class SHA256Util {

    /**
     * 加密
     * 生成盐和加盐后的SHA-256码，并将盐混入到SHA-256码中,对SHA-256密码进行加强
     **/
    public static String encryptPassword(String password) {
        // 生成一个16位的随机数，也就是盐
        String salt = RandomStringUtils.randomAlphanumeric(16);

        // 将盐拼接到明文后，并生成新的sha256码
        String sha256Hex = DigestUtils.sha256Hex(password + salt);

        // 将盐混到新生成的SHA-256码中，之所以这样做是为了后期解密，校验密码
        StringBuilder sb = new StringBuilder(80); // SHA-256是64个字符，加16个字符的盐，总共80个字符
        for (int i = 0; i < 16; i++) {
            sb.append(sha256Hex.charAt(i * 4));
            sb.append(salt.charAt(i));
            sb.append(sha256Hex.charAt(i * 4 + 1));
            sb.append(sha256Hex.charAt(i * 4 + 2));
            sb.append(sha256Hex.charAt(i * 4 + 3));
        }
        return sb.toString();
    }

    /**
     * 解密
     * 从混入盐的SHA-256码中提取盐值和加盐后的SHA-256码
     **/
    public static boolean verifyPassword(String password, String encrypted) {
        // 提取盐值和加盐后的SHA-256码
        StringBuilder sb1 = new StringBuilder(64);
        StringBuilder sb2 = new StringBuilder(16);

        for (int i = 0; i < 16; i++) {
            sb1.append(encrypted.charAt(i * 5));
            sb1.append(encrypted.charAt(i * 5 + 2));
            sb1.append(encrypted.charAt(i * 5 + 3));
            sb1.append(encrypted.charAt(i * 5 + 4));
            sb2.append(encrypted.charAt(i * 5 + 1));
        }

        String sha256Hex = sb1.toString();
        String salt = sb2.toString();

        // 比较二者是否相同
        return  DigestUtils.sha256Hex(password + salt).equals(sha256Hex);
    }
}
