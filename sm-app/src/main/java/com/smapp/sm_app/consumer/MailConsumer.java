package com.smapp.sm_app.consumer;

import com.smapp.sm_app.pojo.MailMessageContent;
import com.smapp.sm_app.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailConsumer {

    @Autowired
    private MailService mailService;

    private final Logger logger = LoggerFactory.getLogger(MailConsumer.class);

    @RabbitListener(queues = "${smapp.rabbitmq.mail.queue}")
    public void consumeMailQueue(MailMessageContent mailMessageContent){
        mailService.sendWelcomeEmail(mailMessageContent.getUsername(), mailMessageContent.getEmail());
    }

    @RabbitListener(queues = "${smapp.rabbitmq.activation.queue}")
    public void consumeActivationQueue(MailMessageContent mailMessageContent){
        mailService.sendActivationEmail(mailMessageContent.getUsername(), mailMessageContent.getEmail(), mailMessageContent.getActivationToken());
        logger.info("Activation email sent to: " + mailMessageContent.getEmail());
    }
}
