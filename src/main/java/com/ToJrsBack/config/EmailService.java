package com.ToJrsBack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ToJrsBack.application.Application;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendApplicationStatusEmail(String to, Application app) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Application Update - ToJrs");

        message.setText(
            "Hi " + app.getJunior().getName() + ",\n\n" +
            "Good news!\n\n" +
            "Your application to \"" + app.getJob().getTitle() + "\" has been " + app.getStatus() + ".\n\n" +
            "Best,\nToJrs Team"
        );

        mailSender.send(message);
    }
}