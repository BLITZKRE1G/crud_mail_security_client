package com.abhinab.demo.mail.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private static final Logger _logger = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;

    MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendText(String to, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);
        try {
            mailSender.send(mail);
        } catch (MailException e) {
            _logger.error("mail-exception got triggered: {}", e.getMessage());
        } catch (Exception e) {
            _logger.error("generic-exception was triggered: {}", e.getMessage());
        }
        _logger.error("sent mail to: {}", to);
    }
}
