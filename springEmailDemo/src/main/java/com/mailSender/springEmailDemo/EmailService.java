package com.mailSender.springEmailDemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailService implements CommandLineRunner {

    private final EmailSenderService senderService;

    public EmailService(EmailSenderService senderService) {
        this.senderService = senderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmailService.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String toEmail = "*****@gmail.com";
        String subject = "Test subject";
        String body = "Hello, please find the encrypted attachment.";
        String filename = "MyMessage.txt";

        String receiverPublicKeyStr = "*****";

        senderService.sendEmailWithAttachment(toEmail, subject, body, filename, receiverPublicKeyStr);
    }
}
