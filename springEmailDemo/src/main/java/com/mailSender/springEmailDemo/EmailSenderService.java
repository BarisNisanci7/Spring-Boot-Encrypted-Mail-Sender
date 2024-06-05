package com.mailSender.springEmailDemo;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.PublicKey;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithAttachment(String toEmail, String subject, String body, String filename, String receiverPublicKeyStr) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filename);
        }
        byte[] attachment = inputStream.readAllBytes();

        KeyPair keyPair = DiffieHellmanUtil.generateKeyPair();
        String senderPublicKeyStr = DiffieHellmanUtil.encodeKeyToString(keyPair.getPublic());

        PublicKey receiverPublicKey = DiffieHellmanUtil.decodeKeyFromString(receiverPublicKeyStr);

        byte[] sharedSecret = DiffieHellmanUtil.generateSharedSecret(keyPair.getPrivate(), receiverPublicKey);
        SecretKey secretKey = DiffieHellmanUtil.deriveKey(sharedSecret);

        String encryptedAttachment = AESUtil.encrypt(new String(attachment), secretKey);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("*****@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body + "\n\nSender Public Key: " + senderPublicKeyStr);

        helper.addAttachment(filename, () -> new ByteArrayInputStream(encryptedAttachment.getBytes()));

        mailSender.send(message);
        System.out.println("Mail sent successfully");
    }
}
