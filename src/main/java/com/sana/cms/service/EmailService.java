package com.sana.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetEmail(String toEmail, String link) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Reset Your Password");
            message.setText(
                    "Hello,\n\n" +
                            "Click the link below to reset your password:\n\n" +
                            link +
                            "\n\nThis link will expire in 15 minutes."
            );

            mailSender.send(message);

            System.out.println("✅ EMAIL SENT SUCCESSFULLY");

        } catch (Exception e) {
            System.out.println("❌ EMAIL FAILED");
            e.printStackTrace();   // 🔥 THIS WILL SHOW REAL ERROR
            throw new RuntimeException(e);
        }
    }
}
