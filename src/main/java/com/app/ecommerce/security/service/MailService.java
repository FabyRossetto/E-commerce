package com.app.ecommerce.security.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService implements EmailSenderService{
    @Autowired
    private JavaMailSender mailSender;

        @Value("${spring.mail.username}")
        private String email;

        @Override
        public void sendEmail(String toEmail,
                              String subject,
                              String body) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            try {
                helper.setText(body, true);
                helper.setTo(toEmail);
                helper.setSubject(subject);
                helper.setFrom(email);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            mailSender.send(mimeMessage);
            System.out.println("Mail send...");
        }
}

