package com.yangy.rabbitmq.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public interface RabbitMQService {

    void send(String queueName,Object msg);

    void send(String queueName,String msg,Long delayTime);
}
