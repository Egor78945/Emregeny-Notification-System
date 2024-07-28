package org.user_api_service.app.configuration.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    private final String QUEUE_NAME;
    private final String QUEUE_ROUTING_KEY;
    private final String EXCHANGE_NAME;

    public RabbitMQConfiguration(@Value("${spring.rabbitmq.queue.name}") String QUEUE_NAME, @Value("${spring.rabbitmq.queue.key}") String QUEUE_ROUTING_KEY, @Value("${spring.rabbitmq.exchange.name}") String EXCHANGE_NAME) {
        this.QUEUE_NAME = QUEUE_NAME;
        this.QUEUE_ROUTING_KEY = QUEUE_ROUTING_KEY;
        this.EXCHANGE_NAME = EXCHANGE_NAME;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(topicExchange())
                .with(QUEUE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
