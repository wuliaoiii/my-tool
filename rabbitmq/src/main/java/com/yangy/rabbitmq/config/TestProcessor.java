package com.yangy.rabbitmq.config;

import com.alibaba.fastjson.JSONObject;
import com.yangy.rabbitmq.enums.MQConstant;
import com.yangy.rabbitmq.model.DLXMessage;
import com.yangy.rabbitmq.service.RabbitMQService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public void process(String content) {
        DLXMessage dlxMessage = JSONObject.parseObject(content, DLXMessage.class);
        rabbitMQService.send(dlxMessage.getQueueName(),dlxMessage.getContent());
    }

}