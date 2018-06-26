package com.yangy.redis.utils;

import com.alibaba.fastjson.JSONObject;
import com.lanqi.common.entity.Dividend;
import com.lanqi.common.enums.RedisKeyEnum;
import com.lanqi.common.service.RedisService;
import com.lanqi.pay.service.DividendService;
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
    private  DividendService dividendService;

    @Autowired
    private RedisService redisService;

    public  void consume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String taskKey = RedisKeyEnum.TASK_QUEUE_DIVIDEND.getKey();
                    String tmpKey = RedisKeyEnum.TMP_QUEUE_DIVIDEND.getKey();
                    RedisTemplate<String, String> redisTemplate = redisService.getRedisTemplate();
                    ListOperations<String, String> forList = redisTemplate.opsForList();
                    //获取redis中的
                    String json = forList.rightPopAndLeftPush(taskKey, tmpKey, 0, TimeUnit.SECONDS);
                    Dividend dividend = JSONObject.parseObject(json, Dividend.class);
                    boolean update = dividendService.update(dividend);
                    if (update) {
                        forList.rightPopAndLeftPush(tmpKey, taskKey);
                    } else {
                        // 将本次任务从暂存队列"tmp-queue"中清除
                        forList.rightPop(tmpKey);
                    }
                }
            }
        }).run();
    }
}