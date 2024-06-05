package com.mailSender.springEmailDemo;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;

public class DecryptAttachment {

    public static void main(String[] args) throws Exception {

        String receiverPrivateKeyStr = "*****";

        String senderPublicKeyStr = "*****";

        String encryptedAttachment = "*****";

        PrivateKey receiverPrivateKey = DiffieHellmanUtil.decodePrivateKeyFromString(receiverPrivateKeyStr);

        PublicKey senderPublicKey = DiffieHellmanUtil.decodeKeyFromString(senderPublicKeyStr);
        byte[] sharedSecret = DiffieHellmanUtil.generateSharedSecret(receiverPrivateKey, senderPublicKey);
        SecretKey secretKey = DiffieHellmanUtil.deriveKey(sharedSecret);

        String decryptedMessage = AESUtil.decrypt(encryptedAttachment, secretKey);

        System.out.println("Decrypted Data: " + decryptedMessage);

    }
}
