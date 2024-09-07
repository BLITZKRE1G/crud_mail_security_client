package com.abhinab.demo.mail.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailService {

    private static final Logger _logger = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    @Value("${from.email}")
    private String from;

    MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendText(String toEmail, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(toEmail);
        mail.setSubject(subject);
        mail.setText(message);
        try {
            mailSender.send(mail);
            _logger.info("sent mail to: {}", toEmail);
        } catch (MailException e) {
            _logger.error("mail-exception got triggered: {}", e.getMessage());
        } catch (Exception e) {
            _logger.error("generic-exception was triggered: {}", e.getMessage());
        }
    }

    public void sendWithAttachments(String toEmail, String subject, @Nullable String[] cc, @Nullable String[] bcc, @Nullable String attachment) {
        MimeMessage mail = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true);
            mailHelper.setFrom(from);
            mailHelper.setTo(toEmail);
            mailHelper.setSubject(subject);
            if (cc != null) mailHelper.setCc(cc);
            if (bcc != null) mailHelper.setCc(bcc);
            if (attachment != null) {
                FileSystemResource resource = new FileSystemResource(new File(attachment));
                if (resource.getFilename() != null) mailHelper.addAttachment(resource.getFilename(), resource);
            }
            try {
                mailSender.send(mail);
            } catch (MailAuthenticationException e) {
                _logger.error("could not send mail: {}", e.getMessage());
                _logger.debug(e.getCause().toString());
                _logger.debug(e.getCause().getLocalizedMessage());
            } catch (MailSendException e) {
                _logger.error(e.getMessage());
                _logger.debug(e.getCause().getLocalizedMessage());
            } catch (Exception e) {
                _logger.error("generic-exception: {}", e.getMessage());
                _logger.debug(e.getCause().getLocalizedMessage());
                _logger.debug(e.getLocalizedMessage());
            }
        } catch (MessagingException e) {
            _logger.error("messaging-exception was encountered: {}", e.getMessage());
            _logger.debug(e.getCause().toString());
            _logger.debug(e.getLocalizedMessage());
        }
    }
}
