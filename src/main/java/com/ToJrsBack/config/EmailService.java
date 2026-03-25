package com.ToJrsBack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ToJrsBack.application.Application;
import com.ToJrsBack.user.User;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
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

    @Async
    public void sendWelcomeEmail(String to, User user) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to ToJrs!!");

        message.setText(
            "Hi " + user.getName() + ",\n\n" +
            "Welcome to ToJrs!\n\n" +
            "We started this project thinking in you and how can you get what you deserved, the place where experiences is shown, not asked for. We invite you to check our jobs and complete the test to improve your posibilities.\n\n" +
            "Best,\nToJrs Team."
        );

        mailSender.send(message);
    }
}