//package com.yangy.rabbitmq.service.impl;
//
//import com.yangy.rabbitmq.service.RedisService;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author yangy
// * @email java_yangy@126.com
// * @create 2018/6/14
// * @since 1.0.0
// */
//@Service
//public class RedisServiceImpl implements RedisService {
//
//    @Resource
//    private RedisTemplate redisTemplate;
//
//    @Override
//    @Transactional
//    public void test() {
//        ListOperations listOperations = redisTemplate.opsForList();
//        redisTemplate.watch("test");
//        redisTemplate.setEnableTransactionSupport(true);
//        redisTemplate.multi();
//        listOperations.rightPush("test", "test");
//        List exec = redisTemplate.exec();
//        if (CollectionUtils.isEmpty(exec)) {
//            System.out.println("执行失败");
//        } else {
//            System.out.println("执行成功");
//        }
//
//    }
//}