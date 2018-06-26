package com.yangy.rabbitmq.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public interface RabbitMQService {

    ConnectionFactory getConnectionFactory();

    Connection getConnection(ConnectionFactory connectionFactory);

    Channel getChannel(Connection connection);

    void sendMsg(byte[] msgBytes);

    void getMsg();

    void sendDelayMsg(byte[] msgBytes,Long delayTime);

    void getDelayMsg();

}
