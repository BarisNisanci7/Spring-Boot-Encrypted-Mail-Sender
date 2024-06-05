package com.mailSender.springEmailDemo;

import javax.crypto.SecretKey;
import java.security.KeyPair;

public class TestEncryptionDecryption {
    public static void main(String[] args) throws Exception {

        KeyPair keyPair = DiffieHellmanUtil.generateKeyPair();
        SecretKey secretKey = DiffieHellmanUtil.deriveKey(DiffieHellmanUtil.generateSharedSecret(keyPair.getPrivate(), keyPair.getPublic()));

        String originalData = "This is a test message";

        String encryptedData = AESUtil.encrypt(originalData, secretKey);
        System.out.println("Encrypted Data: " + encryptedData);


        String decryptedData = AESUtil.decrypt(encryptedData, secretKey);
        System.out.println("Decrypted Data: " + decryptedData);
    }
}
