package com.yangy.rabbitmq.enums;

public enum ExchangeEnum {

    TEST_TOPIC_EXCHANGE("TEST_TOPIC_EXCHANGE"),
    DELAY_TOPIC_EXCHANGE("DELAY_TOPIC_EXCHANGE")

    ;
    private String name;

    ExchangeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
