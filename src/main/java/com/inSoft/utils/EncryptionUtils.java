package com.inSoft.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * 自定义加解密工具类
 */
public class EncryptionUtils {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;

    /**
     * 加密方法
     * 逻辑：输入明文及加密密钥即可输出密文（128位的英文及数字的混合字符串）
     */
    public static String encryption(String plainText, String key) {
        try {
            byte[] keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), KEY_SIZE / 8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.toString());
        }
    }

    /**
     * 解密方法
     * 逻辑：输入密文及解密密钥即可输出明文（完整明文信息）
     */
    public static String decryption(String cipherText, String key) {
        try {
            byte[] keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), KEY_SIZE / 8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting: " + e.toString());
        }
    }
}
