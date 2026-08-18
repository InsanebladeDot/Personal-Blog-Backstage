package com.example.springboottext.untill;

public class KaisaUtil {
    public static final Integer key = 1008611;
    /***
     * 使用凯撒加密方式加密数据
     * @param orignal 原文
     * @return 加密后的字符
     */
    public static String encryptKaisa(String orignal) {
        //将字符串转换为数组
        char[] chars = orignal.toCharArray();
        StringBuffer buffer = new StringBuffer();
        //遍历数组
        for(char aChar : chars) {
            //获取字符的ASCII编码
            int asciiCode = aChar;
            //偏移数据
            asciiCode += key;
            //将偏移后的数据转为字符
            char result = (char)asciiCode;
            //拼接数据
            buffer.append(result);
        }
        return buffer.toString();
    }

    /**
     * 使用凯撒加密方式解密数据
     *
     * @param encryptedData :密文
     * @return : 源数据
     */
    public static String decryptKaiser(String encryptedData) {
        // 将字符串转为字符数组
        char[] chars = encryptedData.toCharArray();
        StringBuilder sb = new StringBuilder();
        // 遍历数组
        for (char aChar : chars) {
            // 获取字符的ASCII编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode -= key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            sb.append(result);
        }

        return sb.toString();
    }
}