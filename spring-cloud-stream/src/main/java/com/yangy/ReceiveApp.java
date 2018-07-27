package com.yangy;

import com.alibaba.fastjson.JSON;
import com.yangy.entity.User;
import com.yangy.model.PayProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@EnableBinding({PayProcessor.class})
@SpringBootApplication
public class ReceiveApp implements CommandLineRunner {

    @Autowired
    PayProcessor payProcessor;

    public static void main(String[] args) {
        SpringApplication.run(ReceiveApp.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUserName("yangy");
        user.setPhone("17700000000");
        user.setSex(100);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE,10);
        Date time = calendar.getTime();
        System.out.println(time);

        MessageBuilder<User> builder = MessageBuilder.withPayload(user);
//        builder.setExpirationDate(time);

        Message<User> build = builder.build();
        for (int i = 0; i < 1000000000; i++) {
            boolean send = payProcessor.payCancelOut().send(build);
            System.out.println(i+" -> "+JSON.toJSONString(build));
            Thread.sleep(30000);
        }



    }
}
