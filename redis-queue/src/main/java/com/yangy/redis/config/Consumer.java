//package com.yangy.redis.config;
//
//import com.alibaba.fastjson.JSONObject;
//import com.lanqi.common.enums.RedisKeyEnum;
//import com.lanqi.common.service.RedisService;
//import com.lanqi.common.utils.StringUtil;
//import com.lanqi.common.vo.DividendVo;
//import com.lanqi.pay.service.DividendService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.net.InetAddress;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author yangy
// * @email java_yangy@126.com
// * @create 2018/6/6
// * @since 1.0.0
// */
//
//@Component
//public class Consumer implements CommandLineRunner {
//
//    @Autowired
//    private DividendService dividendService;
//
//    @Resource
//    private RedisService template;
//
//    /**
//     * Callback used to run the bean.
//     *
//     * @param args incoming main method arguments
//     * @throws Exception on error
//     */
//    @Override
//    public void run(String... args) throws Exception {
//        //redis队列key
//        String taskKey = RedisKeyEnum.TASK_QUEUE_DIVIDEND.getKey();
//        String tmpKey = RedisKeyEnum.TMP_QUEUE_DIVIDEND.transfer(InetAddress.getLocalHost());
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    //获取redis模板
//                    RedisTemplate<String, String> redisTemplate = template.getRedisTemplate();
//                    //获取对list的操作
//                    ListOperations<String, String> forList = redisTemplate.opsForList();
//                    //设置事务开启
//                    redisTemplate.setEnableTransactionSupport(false);
//                    //阻塞获取redis中的信息
//                    String json = forList.rightPopAndLeftPush(taskKey, tmpKey, 0, TimeUnit.SECONDS);
//                    if (StringUtil.isBlank(json)) {
//                        continue;
//                    }
//                    //
//                    DividendVo dividendVo = JSONObject.parseObject(json, DividendVo.class);
//                    if (null == dividendVo) {
//                        continue;
//                    }
//                    //业务代码
//                    boolean update = dividendService.update(dividendVo);
//                    //修改成功时 从缓存队列中删除该业务方法
//                    //修改失败时 将该业务从缓存队列中移动到工作队列
//                    if (update) {
//                        forList.rightPop(tmpKey);
//                    } else {
//                        forList.rightPopAndLeftPush(tmpKey, taskKey);
//                    }
//                }
//            }
//        }).run();
//    }
//}
//
