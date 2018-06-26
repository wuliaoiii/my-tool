package com.yangy.rabbitmq.service.impl;

import com.yangy.rabbitmq.service.RabbitMQService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQServiceImplTest {

    @Resource
    private RabbitMQService rabbitMQService;


    @Test
    public void sendDelayMsg() {
       while (true){
           String msg = "this is a delay msg ->"+new Date();
           try {
               Thread.sleep(5000L);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           rabbitMQService.sendDelayMsg(msg.getBytes(), 30000L);
       }
    }

    @Test
    public void getDelayMsg() {
        while (true){
            rabbitMQService.getDelayMsg();
        }
    }
}