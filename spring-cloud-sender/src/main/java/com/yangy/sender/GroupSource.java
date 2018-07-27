package com.yangy.sender;

import com.yangy.model.MySink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

@EnableBinding(value = MySink.class)
public class GroupSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupSource.class);
    private static int count = 0;
 
    @Bean
    @InboundChannelAdapter(value = MySink.GROUP_CHANNEL, poller = @Poller(fixedRate = "5000", maxMessagesPerPoll = "1"))
    public MessageSource groupMessageSource() {
        return () -> {
            count++;
            LOGGER.info("send {}", count);
            return new GenericMessage<>(count);
        };
    }
}
