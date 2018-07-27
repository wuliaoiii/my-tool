package com.yangy.model;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PayProcessor {

    String PAY_CANCEL_INT = "pay_cancel_int";
    String PAY_RESULT_INT = "pay_result_int";
    String PAY_CANCEL_OUT = "pay_cancel_out";
    String PAY_RESULT_OUT = "pay_result_out";

    @Input(PAY_CANCEL_INT)
    SubscribableChannel payCancelInt();

    @Input(PAY_RESULT_INT)
    SubscribableChannel payResultInt();


    @Output(PAY_CANCEL_OUT)
    MessageChannel payCancelOut();

    @Output(PAY_RESULT_OUT)
    MessageChannel payResultOut();
}
