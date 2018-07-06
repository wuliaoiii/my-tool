package com.yangy.rabbitmq.enums;

/**
 * mq常量
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/7/3
 * @since 1.0.0
 */
public final class MQConstant {

    //交换机名称
    public static final String DEFAULT_EXCHANGE = "my.topic.exchange";

    //死信队列
    public static final String DEAD_LETTER_QUEUE_NAME = "my.dead.letter.queue";

    //死信转发队列
    public static final String REPEAT_TRADE_QUEUE_NAME = "my.dead.repeat.trade.queue";

    //测试队列名称
    public static final String TEST_QUEUE_NAME = "my.test.queue";

    //绑定key
    public static final String DEAULT_EXCHANGE_BINDING_KEY = "my.topic.bind.key";


}