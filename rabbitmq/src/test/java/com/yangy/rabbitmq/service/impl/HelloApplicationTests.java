package com.yangy.rabbitmq.service.impl;

import com.yangy.rabbitmq.config.TestProcessor;
import com.yangy.rabbitmq.enums.MQConstant;
import com.yangy.rabbitmq.service.RabbitMQService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = RabbitmqApplication.class)

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloApplicationTests {

    @Autowired
    private RabbitMQService rabbitMQService;

    @Test
    public void hello() throws Exception {
//        rabbitMQService.send(MQConstant.TEST_QUEUE_NAME,"这是一条测试消息");
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000*60L);
            rabbitMQService.send(MQConstant.TEST_QUEUE_NAME,"这是一条延时消息 发送时间 -> "+new Date(),1000*60*3L);
        }
    }

}