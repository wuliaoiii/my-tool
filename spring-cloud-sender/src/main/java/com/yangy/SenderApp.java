package com.yangy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

//@EnableBinding(Source.class)
@SpringBootApplication
public class SenderApp/* implements CommandLineRunner */{

//    @Autowired
//    @Qualifier("output")
//    MessageChannel output;

    public static void main(String[] args) {
        SpringApplication.run(SenderApp.class, args);
    }

//    @Override
//    public void run(String... strings) throws Exception {
//        // 字符串类型发送MQ
//        System.out.println("字符串信息发送");
//        output.send(MessageBuilder.withPayload("大家好").build(),100);
//    }


}
