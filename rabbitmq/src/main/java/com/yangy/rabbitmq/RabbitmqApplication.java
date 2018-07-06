package com.yangy.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mq")
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
        System.out.println(" mq 工具 启动");
    }
/*

    @GetMapping("/test/send/{msg}")
    public void send(@PathVariable("msg") String msg) {
        rabbitMQService.sendMsg(msg.getBytes());
    }

    @GetMapping("/test/get")
    public void get() {
        rabbitMQService.getDelayMsg();
    }
*/

}
