package com.yangy.rabbitmq.controller;

import com.yangy.rabbitmq.service.RedisService;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

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
    private RedisTemplate redisTemplate;

    @Resource
    private RedisService redisService;

    @GetMapping("/testExec")
    @Transactional
    public void get() {
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        ListOperations forList = redisTemplate.opsForList();
        forList.rightPopAndLeftPush("task-queue","tmp-queue");
        List exec = redisTemplate.exec();








        System.out.println(exec);
    }

    @GetMapping("/testExec2")
    @Transactional
    public void get2() {
        redisService.test();
    }

}