package com.yangy.rabbitmq.config;

import com.yangy.rabbitmq.enums.MQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitConfig {

    //测试队列
    @Bean
    public Queue testQueue() {
        return new Queue(MQConstant.TEST_QUEUE_NAME, true, false,false);
    }

    //死信转发队列
    @Bean
    public Queue repeatTradeQueue() {
        return new Queue(MQConstant.REPEAT_TRADE_QUEUE_NAME, true, false, false);
    }

    //死信队列
    @Bean
    public Queue deadLetterQueue() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-length", 10000);
        arguments.put("x-dead-letter-exchange", MQConstant.DEFAULT_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", MQConstant.REPEAT_TRADE_QUEUE_NAME);
        return new Queue(MQConstant.DEAD_LETTER_QUEUE_NAME, true, false, false, arguments);
    }

    //默认topic交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MQConstant.DEFAULT_EXCHANGE, true, false);
    }

    @Bean
    public Binding testBinding() {
        return BindingBuilder.bind(testQueue()).to(directExchange()).with(MQConstant.TEST_QUEUE_NAME);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(directExchange()).with(MQConstant.DEAD_LETTER_QUEUE_NAME);
    }

    @Bean
    public Binding repeatTradeBinding() {
        return BindingBuilder.bind(repeatTradeQueue()).to(directExchange()).with(MQConstant.REPEAT_TRADE_QUEUE_NAME);
    }


}