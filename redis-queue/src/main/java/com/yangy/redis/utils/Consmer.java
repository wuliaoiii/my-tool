package com.yangy.redis.utils;

import com.alibaba.fastjson.JSONObject;
import com.yangy.redis.entity.Dividend;
import com.yangy.redis.service.DividendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/6
 * @since 1.0.0
 */
@Component
public class Consmer {

    @Autowired
    private DividendService dividendService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final ThreadLocal<RedisTemplate> threadLocal = new ThreadLocal<>();

    public static final String taskKey = "task_queue";
    public static final String tmpKey = "tmp_queue";

    public void consume() {
        RedisTemplate redisTemplate = threadLocal.get();
        if (null == redisTemplate) {
            threadLocal.set(this.redisTemplate);
        }

        RedisTemplate template = threadLocal.get();
        while (true) {
            ListOperations forList = template.opsForList();
            String json = (String) forList.rightPopAndLeftPush(taskKey, tmpKey, 0, TimeUnit.SECONDS);
            Dividend dividend = JSONObject.parseObject(json, Dividend.class);
            Dividend update = dividendService.update(dividend);
            if (null != update) {
                forList.rightPopAndLeftPush(tmpKey, taskKey);
            } else {
                // 将本次任务从暂存队列"tmp-queue"中清除
                forList.rightPop(tmpKey);
            }
        }
/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {


                    ListOperations<String, String> forList = Consmer.this.redisTemplate.opsForList();
                    //获取redis中的
                    String json = forList.rightPopAndLeftPush(taskKey, tmpKey, 0, TimeUnit.SECONDS);
                    Dividend dividend = JSONObject.parseObject(json, Dividend.class);
                    Dividend update = dividendService.update(dividend);
                    if (null != update) {
                        forList.rightPopAndLeftPush(tmpKey, taskKey);
                    } else {
                        // 将本次任务从暂存队列"tmp-queue"中清除
                        forList.rightPop(tmpKey);
                    }
                }
            }
        }).run();*/
    }
}