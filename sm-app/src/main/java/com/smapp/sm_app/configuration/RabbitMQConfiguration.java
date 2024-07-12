package com.smapp.sm_app.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${smapp.rabbitmq.exchange}")
    public String EXCHANGE_NAME;
    @Value("${smapp.rabbitmq.mail.queue}")
    public String MAIL_QUEUE;
    @Value("${smapp.rabbitmq.activation.queue}")
    public String ACTIVATION_QUEUE;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue activationQueue() {
        return new Queue(ACTIVATION_QUEUE);
    }

    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_QUEUE);
    }


    @Bean
    public Binding mailBinding(Queue mailQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(mailQueue).to(topicExchange).with("mail.#");
    }

    @Bean
    public Binding activationBinding(Queue activationQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(activationQueue).to(topicExchange).with("activation.#");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
