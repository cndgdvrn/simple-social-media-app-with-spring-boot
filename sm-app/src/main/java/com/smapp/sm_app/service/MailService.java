package com.smapp.sm_app.service;

import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.error.ActivationMailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class MailService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    private final String activationUrlTemplate = "http://localhost:8050/api/auth/activate?token=%s";


    //--------------------------------------------------------------------------------

    public void sendActivationEmail(String username ,String email, String activationToken) {
        String activationUrl = String.format(activationUrlTemplate,activationToken);
        String htmlTemplate = generateActivationHtmlTemplate(username, activationUrl);

        sendEmail(email, htmlTemplate, "account activation email");
    }

    public void sendWelcomeEmail(String username, String email) {
        String htmlTemplate = generateWelcomeHtmlTemplate(username);
        sendEmail(email, htmlTemplate, "welcome email");
    }

    private String generateActivationHtmlTemplate(String username, String activationUrl) {
        return String.format("<html><body style=\"font-family: Arial, sans-serif;\"><h2 style=\"color: #333333;\"> Account activation email </h2><p>Dear %s,</p><a href=\"%s\" style=\"color: #1a73e8; text-decoration: none;\">Click the link</a><p>Or copy and paste this URL into your browser</p><p style=\"word-break: break-all;\">%s</p><p>Thanks :) ,<br> SM App </p></body></html>", username, activationUrl, activationUrl);
    }

    private String generateWelcomeHtmlTemplate(String username) {
        return String.format("<html><body style=\"font-family: Arial, sans-serif;\"><h2 style=\"color: #333333;\"> Welcome to SM App </h2><p>Dear %s,</p><p>Thanks for joining us :) </p><p> SM App </p></body></html>", username);
    }

    private void sendEmail(String email, String htmlTemplate, String about) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("can@can.com");
            helper.setTo(email);
            helper.setSubject(about);
            helper.setText(htmlTemplate, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new ActivationMailException("MessagingException thrown while sending " + about);
        }
    }




}







