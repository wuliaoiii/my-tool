package com.yangy.rabbitmq.config;

import com.rabbitmq.client.Channel;
import com.yangy.rabbitmq.enums.MQConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 死信转发处理
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/7/4
 * @since 1.0.0
 */
@Component
@RabbitListener(queues = MQConstant.TEST_QUEUE_NAME, concurrency = "1")
public class TestProcessor {

    @RabbitHandler
    public void process(@Payload String content, Message message , Channel channel) {
        System.out.println("接收到的消息 -> " + content);
        System.out.println("接收到的消息 -> " + message.getMessageProperties().getDeliveryTag());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}