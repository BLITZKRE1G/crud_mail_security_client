package com.abhinab.demo.mail.controllers;

import com.abhinab.demo.mail.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users/mail")
public class MailController {

    private static final Logger _logger = LoggerFactory.getLogger(MailController.class);
    private final MailService service;

    MailController(MailService service) {
        this.service = service;
    }

    @PostMapping(value = "/send-basic")
    public void sendTextMail(String toMail, String subject, String message) {
        service.sendText(toMail, subject, message);
    }
}
