package com.ToJrsBack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ToJrsBack.application.Application;
import com.ToJrsBack.user.User;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendApplicationStatusEmail(Application application) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(application.getJunior().getEmail());
            helper.setSubject("Application Update - ToJrs");

            String htmlContent = buildHtml(application.getJunior().getName(), application.getJob().getTitle(), application.getStatus().toString());

            helper.setText(htmlContent, true); // 🔥 true = HTML

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error sending email", e);
        }
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

    private String buildHtml(String name, String jobTitle, String status) {

        String color = switch (status) {
            case "ACCEPTED" -> "#28a745";
            case "REJECTED" -> "#dc3545";
            case "REVIEWED" -> "#ffc107";
            default -> "#007bff";
        };

        return """
            <div style="font-family: Arial, sans-serif; background:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:white; border-radius:10px; padding:20px;">
                    
                    <div style="text-align:center;">
                        <img src="https://res.cloudinary.com/dihidgdog/image/upload/v1775491001/NoBGLogo_qhqmld.png" width="120"/>
                    </div>

                    <h2 style="color:#333;">Hi %s 👋</h2>

                    <p style="color:#555;">
                        We have an update regarding your application:
                    </p>

                    <div style="background:#f9f9f9; padding:15px; border-radius:8px;">
                        <p><strong>Position:</strong> %s</p>
                        <p>
                            <strong>Status:</strong> 
                            <span style="color:%s; font-weight:bold;">
                                %s
                            </span>
                        </p>
                    </div>

                    <p style="margin-top:20px;">
                        Keep going — you're doing great 🚀
                    </p>

                    <hr/>

                    <p style="font-size:12px; color:#999;">
                        — ToJrs Team
                    </p>

                </div>
            </div>
            """.formatted(name, jobTitle, color, status);
    }
}