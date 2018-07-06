package com.yangy.rabbitmq.config;

import com.yangy.rabbitmq.enums.MQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
    public void process(String content) {
        System.out.println("接收到的消息 -> " + content);
        System.out.println("当前时间 -> " + new Date());
    }
}