package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    EmailService emailService;

    @Test
    public void sendMailTest() {
        String body = "Java Mail Sender Implemented successfully";
        String subject = "Finally its working fine";
        String to = "omdubey1021@gmail.com";
        emailService.sendMail(to, subject, body);
    }
}
