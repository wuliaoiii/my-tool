package com.yangy.rabbitmq.sender;

import com.yangy.rabbitmq.enums.MQConstant;
import com.yangy.rabbitmq.model.DLXMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息生产者
 *
 * @author yang yang
 * @email java_yangy@126.com
 * @create 2018/8/11
 * @since 1.0.0
 */
@Service
public class Sender implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendDelay(String queueName, String msg, Long delayTime) {
        DLXMessage dlxMessage = new DLXMessage(queueName, msg, delayTime);

        MessagePostProcessor processor = message -> {
            message.getMessageProperties().setExpiration(delayTime + "");
            return message;
        };

        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
        dlxMessage.setExchange(MQConstant.DEFAULT_EXCHANGE);
        rabbitTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE, MQConstant.DEAD_LETTER_QUEUE_NAME, dlxMessage, processor);

    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("发送后返回消息"+ message.toString());
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            System.out.println("消息确认失败");
        } else {
            System.out.println("消息确认成功");
        }
    }
}