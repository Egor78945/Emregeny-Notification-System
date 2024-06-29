package org.example.email_sender_service.services.rabbitmq;

import org.example.email_sender_service.models.requestModels.EmailMessage;
import org.example.email_sender_service.services.mail.MailMessageSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    private final MailMessageSender mailMessageSender;

    public EmailConsumer(MailMessageSender mailMessageSender) {
        this.mailMessageSender = mailMessageSender;
    }

    @RabbitListener(queues = {"${spring.rabbitmq.queue.name}"})
    private void consume(EmailMessage emailMessage) {
        mailMessageSender.send(emailMessage);
    }
}
