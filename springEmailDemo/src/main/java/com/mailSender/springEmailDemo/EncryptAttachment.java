package com.mailSender.springEmailDemo;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

public class EncryptAttachment {

    public static void main(String[] args) throws Exception {

        KeyPair senderKeyPair = DiffieHellmanUtil.generateKeyPair();

        String receiverPublicKeyStr = "*****";

        PublicKey receiverPublicKey = DiffieHellmanUtil.decodeKeyFromString(receiverPublicKeyStr);

        byte[] sharedSecret = DiffieHellmanUtil.generateSharedSecret(senderKeyPair.getPrivate(), receiverPublicKey);
        SecretKey secretKey = DiffieHellmanUtil.deriveKey(sharedSecret);

        String message = "This is an encrypted message";

        String encryptedMessage = AESUtil.encrypt(message, secretKey);

        System.out.println("Encrypted Data: " + encryptedMessage);
        System.out.println("Sender Public Key: " + DiffieHellmanUtil.encodeKeyToString(senderKeyPair.getPublic()));
    }
}
