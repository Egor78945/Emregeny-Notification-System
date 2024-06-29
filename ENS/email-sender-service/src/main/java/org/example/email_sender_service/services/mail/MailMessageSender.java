package org.example.email_sender_service.services.mail;

import org.example.email_sender_service.models.requestModels.EmailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailMessageSender {
    private final String FROM;
    private final String SUBJECT;
    private final JavaMailSender mailSender;

    public MailMessageSender(@Value("${spring.mail.from}") String FROM, @Value("${spring.mail.subject}") String SUBJECT, JavaMailSender mailSender) {
        this.FROM = FROM;
        this.mailSender = mailSender;
        this.SUBJECT = SUBJECT;
    }

    public void send(EmailMessage emailMessage){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(FROM);
        simpleMailMessage.setSubject(SUBJECT);
        simpleMailMessage.setText(emailMessage.getMessage());
        simpleMailMessage.setTo(emailMessage.getTo());

        mailSender.send(simpleMailMessage);
    }
}
