package com.yangy.rabbitmq;

import com.yangy.rabbitmq.service.RabbitMQService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/mq")
@SpringBootApplication
public class RabbitmqApplication {

    @Resource
    private RabbitMQService rabbitMQService;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
        System.out.println(" mq 工具 启动");
    }

    @GetMapping("/test/send/{msg}")
    public void send(@PathVariable("msg") String msg) {
        rabbitMQService.sendMsg(msg.getBytes());
    }

    @GetMapping("/test/get")
    public void get() {
        rabbitMQService.getDelayMsg();
    }

}
