package com.yangy.rabbitmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.yangy.rabbitmq.enums.MQConstant;
import com.yangy.rabbitmq.model.DLXMessage;
import com.yangy.rabbitmq.service.RabbitMQService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/5
 * @since 1.0.0
 */
@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String queueName, Object msg) {
        rabbitTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE, queueName, msg);
    }

    @Override
    public void send(String queueName, String msg, Long delayTime) {
        DLXMessage dlxMessage = new DLXMessage(queueName, msg, delayTime);
        MessagePostProcessor processor = message -> {
            message.getMessageProperties().setExpiration(delayTime + "");
            return message;
        };

        dlxMessage.setExchange(MQConstant.DEFAULT_EXCHANGE);
        rabbitTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE, MQConstant.DEAD_LETTER_QUEUE_NAME, msg.getBytes(), processor);

    }
}