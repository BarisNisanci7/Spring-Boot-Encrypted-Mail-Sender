package com.mailSender.springEmailDemo;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtil {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    public static String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        byte[] cipherText = cipher.doFinal(data.getBytes());
        byte[] cipherTextWithIv = new byte[GCM_IV_LENGTH + cipherText.length];
        System.arraycopy(iv, 0, cipherTextWithIv, 0, GCM_IV_LENGTH);
        System.arraycopy(cipherText, 0, cipherTextWithIv, GCM_IV_LENGTH, cipherText.length);
        return Base64.getEncoder().encodeToString(cipherTextWithIv);
    }

    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        byte[] cipherTextWithIv = Base64.getDecoder().decode(encryptedData);
        byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(cipherTextWithIv, 0, iv, 0, iv.length);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        byte[] cipherText = new byte[cipherTextWithIv.length - GCM_IV_LENGTH];
        System.arraycopy(cipherTextWithIv, GCM_IV_LENGTH, cipherText, 0, cipherText.length);
        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText);
    }
}
