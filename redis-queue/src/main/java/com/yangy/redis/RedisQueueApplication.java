package com.yangy.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.yangy.redis.entity")
public class RedisQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisQueueApplication.class, args);
    }
}
