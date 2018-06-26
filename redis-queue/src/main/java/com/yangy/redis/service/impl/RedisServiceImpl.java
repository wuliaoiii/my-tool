package com.yangy.redis.service.impl;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * redis 业务实现类
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/14
 * @since 1.0.0
 */
public class RedisServiceImpl {
    
    @Resource
    private RedisTemplate redisTemplate;
    
    public void send(){

        //获取对集合的操作
        ListOperations forList = redisTemplate.opsForList();
        
        //从一个集合中自右推出 自左放入 一个元素
        Object element = forList.rightPopAndLeftPush("task", "tmp");










    }
    
    
    
    
    
    
    
    
    

}