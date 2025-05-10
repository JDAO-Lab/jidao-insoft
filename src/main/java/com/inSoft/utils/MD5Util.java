package com.inSoft.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    /**
     * 对字符串进行MD5加密
     * @param input 要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String input) {
        try {
            // 创建MD5实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将输入字符串转换为字节数组
            byte[] bytes = input.getBytes();
            // 更新摘要
            md.update(bytes);
            // 获取密文
            byte[] digest = md.digest();
            // 将密文转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

