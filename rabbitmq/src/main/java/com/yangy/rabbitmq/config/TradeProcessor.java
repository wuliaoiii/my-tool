package com.yangy.rabbitmq.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.yangy.rabbitmq.enums.MQConstant;
import com.yangy.rabbitmq.model.DLXMessage;
import com.yangy.rabbitmq.service.RabbitMQService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 死信转发处理
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/7/4
 * @since 1.0.0
 */
@Component
@RabbitListener(queues = MQConstant.REPEAT_TRADE_QUEUE_NAME)
public class TradeProcessor {

    @Autowired
    private RabbitMQService rabbitMQService;

    @RabbitHandler
    public void process(@Payload DLXMessage dlxMessage, Message message, Channel channel) {
        System.out.println("消息标志位: " + message.getMessageProperties().getDeliveryTag());
        rabbitMQService.send(dlxMessage.getQueueName(), dlxMessage.getContent());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*@Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        String content = new String(body);
        System.out.println(content);
        DLXMessage dlxMessage = JSONObject.parseObject(content, DLXMessage.class);
        System.out.println(JSON.toJSONString(dlxMessage));
        rabbitMQService.send(dlxMessage.getQueueName(), dlxMessage.getContent());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}