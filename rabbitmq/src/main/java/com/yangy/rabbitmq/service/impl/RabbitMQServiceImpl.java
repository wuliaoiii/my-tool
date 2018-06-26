package com.yangy.rabbitmq.service.impl;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;
import com.yangy.rabbitmq.config.MQConfig;
import com.yangy.rabbitmq.enums.ExchangeEnum;
import com.yangy.rabbitmq.enums.QueueEnum;
import com.yangy.rabbitmq.service.RabbitMQService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.BlockingQueueConsumer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

/**
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/5
 * @since 1.0.0
 */
@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    @Resource
    private AmqpTemplate amqpTemplate;


    /**
     * 获取连接工厂
     *
     * @return
     */
    @Override
    public ConnectionFactory getConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(MQConfig.getHOST());
        connectionFactory.setPort(Integer.parseInt(MQConfig.getPORT()));
        connectionFactory.setUsername(MQConfig.getUSERNAME());
        connectionFactory.setPassword(MQConfig.getPASSWORD());
        return connectionFactory;
    }

    /**
     * 获取连接
     *
     * @param connectionFactory
     * @return
     */
    @Override
    public Connection getConnection(ConnectionFactory connectionFactory) {
        try {
            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取频道
     *
     * @param connection
     * @return
     */
    @Override
    public Channel getChannel(Connection connection) {
        try {
            Channel channel = connection.createChannel();
            return channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendMsg(byte[] msgBytes) {
        Channel channel = getChannel(getConnection(getConnectionFactory()));
        try {
            //创建交换机
            channel.exchangeDeclare(ExchangeEnum.TEST_TOPIC_EXCHANGE.getName(), "topic");

            //创建队列 参数-> 队列名 是否持久化 是否为排他队列 是否自动删除 额外参数
            channel.queueDeclare(QueueEnum.TEST_QUEUE.getName(), true, false, true, null);

            //队列与交换机绑定
            channel.queueBind(QueueEnum.TEST_QUEUE.getName(), ExchangeEnum.TEST_TOPIC_EXCHANGE.getName(), "test");

            //在交换机中 将消息通过 绑定的 routingKey 发送到绑定的队列中
//            channel.basicPublish(ExchangeEnum.TEST_TOPIC_EXCHANGE.getName(), "test", true, null, msgBytes);

            channel.basicPublish(ExchangeEnum.TEST_TOPIC_EXCHANGE.getName(), "test", true, false, null, msgBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMsg() {
        Channel channel = getChannel(getConnection(getConnectionFactory()));
        try {
            channel.basicConsume(QueueEnum.TEST_QUEUE.getName(), true, new Consumer() {
                @Override
                public void handleConsumeOk(String consumerTag) {

                }

                @Override
                public void handleCancelOk(String consumerTag) {

                }

                @Override
                public void handleCancel(String consumerTag) throws IOException {

                }

                @Override
                public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

                }

                @Override
                public void handleRecoverOk(String consumerTag) {

                }

                /**
                 * 处理消息
                 * @param consumerTag
                 * @param envelope
                 * @param properties
                 * @param body
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String exchange = envelope.getExchange();
                    System.out.println("this is the exchange name -> " + exchange);
                    System.out.println("this is the message -> " + new String(body));
                    try {
                        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
                        channel.waitForConfirms();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendDelayMsg(byte[] msgBytes, Long delayTime) {
        Channel channel = getChannel(getConnection(getConnectionFactory()));
        try {
            HashMap<String, Object> arguments = new HashMap<String, Object>();
            arguments.put("x-max-length", 10000);
            arguments.put("x-dead-letter-exchange", "amq.direct");
            arguments.put("x-dead-letter-routing-key", "test");
            channel.queueDeclare(QueueEnum.DELAY_QUEUE.getName(), true, false, false, arguments);

            channel.queueDeclare(QueueEnum.TEST_QUEUE.getName(), true, false, false, null);

            //队列与交换机绑定
            channel.queueBind(QueueEnum.TEST_QUEUE.getName(), "amq.direct", "test");

            // 设置延时属性
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            // 持久性 non-persistent (1) or persistent (2)
            AMQP.BasicProperties properties = builder.expiration(delayTime.toString()).deliveryMode(2).build();

            //在交换机中 将消息通过 绑定的 routingKey 发送到绑定的队列中
            //channel.basicPublish(ExchangeEnum.TEST_TOPIC_EXCHANGE.getName(), "test", true, null, msgBytes);

            channel.basicPublish("", QueueEnum.DELAY_QUEUE.getName(), properties, msgBytes);
//            channel.basicPublish("amq.direct", "test", true, false, properties, msgBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDelayMsg() {
        Channel channel = getChannel(getConnection(getConnectionFactory()));
        try {
            HashMap<String, Object> arguments = new HashMap<String, Object>();
            arguments.put("x-max-length", 10000);
            arguments.put("x-dead-letter-exchange", "amq.direct");
            arguments.put("x-dead-letter-routing-key", "test");

            channel.queueDeclare(QueueEnum.DELAY_QUEUE.getName(), true, false, false, arguments);

            // 声明队列
            channel.queueDeclare(QueueEnum.TEST_QUEUE.getName(), true, false, false, null);
            // 绑定路由
            channel.queueBind(QueueEnum.TEST_QUEUE.getName(), "amq.direct", "test");

            // 指定消费队列
            channel.basicConsume(QueueEnum.TEST_QUEUE.getName(), true, new Consumer() {
                @Override
                public void handleConsumeOk(String consumerTag) {

                }

                @Override
                public void handleCancelOk(String consumerTag) {

                }

                @Override
                public void handleCancel(String consumerTag) throws IOException {

                }

                @Override
                public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

                }

                @Override
                public void handleRecoverOk(String consumerTag) {

                }

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String exchange = envelope.getExchange();
                    System.out.println("this is the exchange name -> " + exchange);
                    System.out.println("this is the message -> " + new String(body));
                    try {
                        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
                        channel.waitForConfirms();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });


            RpcServer rpcServer = new RpcServer(channel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}