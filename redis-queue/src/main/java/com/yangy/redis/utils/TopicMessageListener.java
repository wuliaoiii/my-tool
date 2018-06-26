package com.yangy.redis.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lanqi.common.entity.Dividend;
import com.lanqi.common.enums.RedisKeyEnum;
import com.lanqi.pay.dao.DividendMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TopicMessageListener implements MessageListener {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DividendMapper dividendMapper;

    @Resource
    private DividendListener dividendListener;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 客户端监听订阅的topic，当有消息的时候，会触发该方法
        byte[] body = message.getBody();
        // 请使用valueSerializer
        byte[] channel = message.getChannel();
        String topic = new String(channel);
        String itemValue = new String(body);
        try {
            String substring = itemValue.substring(itemValue.indexOf("{"));
            /*获取redis中的对象信息* */
            Dividend dividend = JSONObject.parseObject(substring, Dividend.class);
            /*缓存队列key*/
            String tmpKey = RedisKeyEnum.TMP_QUEUE_DIVIDEND.getKey();
            /*工作队列key* */
            String taskKey = RedisKeyEnum.TASK_QUEUE_DIVIDEND.getKey();
            ListOperations forList = redisTemplate.opsForList();
            RedisConnectionUtils.bindConnection(redisTemplate.getConnectionFactory());

           new Thread(new Runnable() {
                public void run() {
                    Long aLong = forList.rightPush(taskKey, JSON.toJSONString(dividend));

                    System.out.println("11");

                    Dividend dividendDB = dividendMapper.find(dividend.getUserId());
                    dividendDB.setUpdateTime(System.currentTimeMillis());
                    dividendDB.setAmount(dividendDB.getAmount().add(dividend.getAmount()));
                    int update = dividendMapper.update(dividend);
                    if (update > 0) {
                        System.out.println("success");
                    } else {
                        System.out.println("error");
                    }
                }
            }).run();
            //处理信息
//            dividendListener.handleMessage(dividend);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
} 