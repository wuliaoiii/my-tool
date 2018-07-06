//package com.yangy.rabbitmq.redis;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.yangy.rabbitmq.entity.Dividend;
//import com.yangy.rabbitmq.enums.RedisKeyEnum;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.util.List;
//
//@Component("dividendListener")
//public class DividendListener {
//
//    @Resource
//    private RedisTemplate redisTemplate;
//
//    //监听Redis消息
//    public void handleMessage(Dividend dividend) {
//        /*缓存队列key*/
//        String tmpKey = RedisKeyEnum.TMP_QUEUE_DIVIDEND.getKey();
//        /*工作队列key*/
//        String taskKey = RedisKeyEnum.TASK_QUEUE_DIVIDEND.getKey();
//        /*获取list操作*/
//        ListOperations forList = redisTemplate.opsForList();
//
//        forList.rightPush(taskKey, JSON.toJSONString(dividend));
//
//        /*开启新线程*/
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                /*当redis中工作队列非空时*/
//                if (forList.size(taskKey) > 0) {
//                    /*将工作队列中的元素取出并推到缓存队列中*/
//                    Object o = forList.rightPopAndLeftPush(taskKey, tmpKey);
//                    /*获取对象*/
//                    Dividend dividendRedis = JSONObject.parseObject(String.valueOf(o), Dividend.class);
//                    /*设置redis支持事务*/
//                    redisTemplate.setEnableTransactionSupport(true);
//                    /*开启redis事务*/
//                    redisTemplate.multi();
//
//                    /*获取数据库中的对象信息*/
//
//                    /*对数据库中的信息做判断*/
//
//                    /*成功清除缓存队列中的元素*/
//                    forList.rightPop(tmpKey);
//                    /*失败回弹队列*/
//                    forList.rightPopAndLeftPush(taskKey, tmpKey);
//                }
//                /*提交事务*/
//                List<Object> exec = redisTemplate.exec();
//                /*提交事务失败*/
//                if (null == exec) {
//                    /*失败回弹缓存队列*/
//                    forList.rightPopAndLeftPush(taskKey, tmpKey);
//                }
//            }
//        }).run();
//    }
//}