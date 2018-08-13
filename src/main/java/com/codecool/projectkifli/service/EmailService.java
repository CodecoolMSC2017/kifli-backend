package com.codecool.projectkifli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender sender;


    public void simpleMessage(String email, String username) {
        try {
            sendEmail(email, username);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("Error in sending email: " + ex);
        }
    }

    private void sendEmail(String email, String username) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setText("Szija " + username + " én csak azt szeretném mondani, hogy te vagy a legjobb ember a világon és ha Isti lennék akkor irígyelnélek");
        helper.setSubject("A világ legjobb emberének: " + username);

        sender.send(message);
    }

    }
