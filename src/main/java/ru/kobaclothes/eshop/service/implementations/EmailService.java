package ru.kobaclothes.eshop.service.implementations;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class  EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationEmail(String recipientEmail, String verificationCode) {
        String subject = "Подтверждение email";
        String body = "Пожалуйста, нажмите на следующую ссылку, чтобы подтвердить свой адрес электронной почты: "
                + "localhost:8080//verify/" + verificationCode;

        sendEmail(recipientEmail, subject, body);
    }

    public void sendForgotPassword(String recipientEmail, String verificationCode) {
        String subject = "Восстановление пароля";
        String body = "Пожалуйста, нажмите на следующую ссылку, чтобы восстановить пароль своей учетной записи: "
                + "localhost:8080//verify/" + verificationCode;

        sendEmail(recipientEmail, subject, body);
    }

    private void sendEmail(String recipientEmail, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("materokpro@gmail.com");
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
    }
}