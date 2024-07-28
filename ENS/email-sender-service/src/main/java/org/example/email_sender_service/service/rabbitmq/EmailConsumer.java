package org.example.email_sender_service.service.rabbitmq;

import org.example.email_sender_service.model.request_model.EmailMessageRequestModel;
import org.example.email_sender_service.service.mail.MailMessageSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    private final MailMessageSender mailMessageSender;

    public EmailConsumer(MailMessageSender mailMessageSender) {
        this.mailMessageSender = mailMessageSender;
    }

    @RabbitListener(queues = {"${spring.rabbitmq.queue.name}"})
    private void consume(EmailMessageRequestModel emailMessageRequestModel) {
        mailMessageSender.send(emailMessageRequestModel);
    }
}
