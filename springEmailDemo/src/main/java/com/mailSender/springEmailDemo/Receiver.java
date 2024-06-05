package com.mailSender.springEmailDemo;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class Receiver {

    public static void main(String[] args) {
        try {
            KeyPair receiverKeyPair = DiffieHellmanUtil.generateKeyPair();

            String receiverPublicKeyStr = DiffieHellmanUtil.encodeKeyToString(receiverKeyPair.getPublic());
            System.out.println("Receiver Public Key: " + receiverPublicKeyStr);

            String receiverPrivateKeyStr = DiffieHellmanUtil.encodeKeyToString(receiverKeyPair.getPrivate());
            System.out.println("Receiver Private Key: " + receiverPrivateKeyStr);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }
}
