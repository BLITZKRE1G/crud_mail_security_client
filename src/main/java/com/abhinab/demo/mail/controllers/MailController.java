package com.abhinab.demo.mail.controllers;

import com.abhinab.demo.mail.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users/mail")
public class MailController {

    private static final Logger _logger = LoggerFactory.getLogger(MailController.class);
    private final MailService service;

    MailController(MailService service) {
        this.service = service;
    }

    @PostMapping(value = "/send-basic")
    public void sendTextMail(@RequestParam("toMail") String toMail, @RequestParam("subject") String subject,
            @RequestParam("message") String messageContent) {
        service.sendText(toMail, subject, messageContent);
    }

    @GetMapping(value = "/send-attachment")
    public void sendWithAttachent(
            @RequestParam("to-email") String toMail,
            String subject,
            @Nullable String[] cc,
            @Nullable String[] bcc,
            @RequestParam("message-content") String messageContent) {
        service.sendWithAttachments(toMail, subject, cc, bcc, messageContent);
    }
}
