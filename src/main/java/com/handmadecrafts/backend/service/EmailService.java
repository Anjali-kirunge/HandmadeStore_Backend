package com.handmadecrafts.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otp, String subject) {
        String body = "Your OTP is: " + otp + "\n\nIt will expire in 5 minutes.";
        log.info("Generating OTP for {}: [ {} ]", toEmail, otp);
        System.out.println("================================================");
        System.out.println("OTP FOR EMAIL " + toEmail + " IS: " + otp);
        System.out.println("================================================");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@handmadecrafts.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("OTP email successfully sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send OTP email to {}. Error: {}", toEmail, e.getMessage());
            log.info("Fallback: Proceeding because OTP [ {} ] is logged above for testing.", otp);
        }
    }
}
