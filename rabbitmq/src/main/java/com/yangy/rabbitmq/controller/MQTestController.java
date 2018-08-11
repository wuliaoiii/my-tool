package com.yangy.rabbitmq.controller;

import com.yangy.rabbitmq.enums.MQConstant;
import com.yangy.rabbitmq.sender.Sender;
import com.yangy.rabbitmq.service.RabbitMQService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/5
 * @since 1.0.0
 */
@Controller
@RequestMapping
public class MQTestController {

    @Resource
    private Sender rabbitMQService;

    @GetMapping("/test")
    @ResponseBody
    public String get() {
        Sender sender = new Sender();
        rabbitMQService.sendDelay(MQConstant.TEST_QUEUE_NAME, "this is a msg", 15_000L);
        return "";
    }
}