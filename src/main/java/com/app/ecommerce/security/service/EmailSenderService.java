package com.app.ecommerce.security.service;

import jakarta.mail.MessagingException;

public interface EmailSenderService {
    void sendEmail(String toEmail,
                   String subject,
                   String body) throws MessagingException;
}
