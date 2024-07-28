package org.example.email_sender_service.service.mail;

import org.example.email_sender_service.model.request_model.EmailMessageRequestModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailMessageSender {
    private final String FROM;
    private final String SUBJECT;
    private final JavaMailSender mailSender;

    public MailMessageSender(@Value("${spring.mail.username}") String FROM, @Value("${spring.mail.subject}") String SUBJECT, JavaMailSender mailSender) {
        this.FROM = FROM;
        this.mailSender = mailSender;
        this.SUBJECT = SUBJECT;
    }

    public void send(EmailMessageRequestModel emailMessageRequestModel){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(FROM);
        simpleMailMessage.setSubject(SUBJECT);
        simpleMailMessage.setText(emailMessageRequestModel.getMessage());
        simpleMailMessage.setTo(emailMessageRequestModel.getTo());

        mailSender.send(simpleMailMessage);
    }
}
