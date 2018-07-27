//package com.yangy.receiver;
//
//import com.alibaba.fastjson.JSON;
//import com.yangy.entity.User;
//import com.yangy.model.PayProcessor;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.messaging.handler.annotation.Payload;
//
//@EnableBinding(value = {PayProcessor.class})
//public class SinkReceiver {
//
//    @StreamListener(value = PayProcessor.PAY_CANCEL_INT)
//    public void groupReceiver(@Payload User user) {
//        System.out.println(PayProcessor.PAY_CANCEL_INT + " " + JSON.toJSONString(user));
//    }
//}
