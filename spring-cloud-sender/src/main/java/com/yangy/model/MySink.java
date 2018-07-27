package com.yangy.model;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySink {
    String GROUP_CHANNEL = "group-channel";
 
    @Output(GROUP_CHANNEL)
    MessageChannel groupOutput();
}
