package org.user_api_service.app.services.rabbitmq.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.user_api_service.app.models.requestModels.MailMessageRequestModel;

@Service
public class MailMessageProducer {
    private final String EXCHANGE_NAME;
    private final String QUEUE_ROUTING_KEY;
    private final RabbitTemplate rabbitTemplate;

    public MailMessageProducer(@Value("${spring.rabbitmq.exchange.name}") String EXCHANGE_NAME, @Value("${spring.rabbitmq.queue.key}") String QUEUE_ROUTING_KEY, RabbitTemplate rabbitTemplate) {
        this.EXCHANGE_NAME = EXCHANGE_NAME;
        this.QUEUE_ROUTING_KEY = QUEUE_ROUTING_KEY;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(MailMessageRequestModel mailMessageProducer) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, QUEUE_ROUTING_KEY, mailMessageProducer);
    }
}
