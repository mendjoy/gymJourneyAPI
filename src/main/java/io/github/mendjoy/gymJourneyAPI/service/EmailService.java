package io.github.mendjoy.gymJourneyAPI.service;

import io.github.mendjoy.gymJourneyAPI.config.exception.GymJourneyException;
import io.github.mendjoy.gymJourneyAPI.domain.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    @Value("${spring.mail.url}")
    private  String url;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.roleName.sender}")
    private String nameSender;

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private void sendEmail(String userEmail, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        try {

            helper.setFrom(from, nameSender);
            helper.setTo(userEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw GymJourneyException.internalError("Erro ao enviar email");
        }

    }

    public void sendVerificationEmail(User user) throws MessagingException {
        String verifyUrl = url + "/users/verify?token=" + user.getToken();
        String subject = "Confirme seu cadastro no GymJourney";
        String content = "Olá " + user.getName() + ",<br>"
                + "Por favor clique no link abaixo para verificar sua conta:<br>"
                + "<h3><a href='" + verifyUrl + "' target='_self'>VERIFICAR</a></h3>"
                + "Obrigado,<br>"
                + "GymJourney :).";

        sendEmail(user.getEmail(), subject, content);
    }

}
