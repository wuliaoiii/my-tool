package com.yangy.rabbitmq.enums;

public enum QueueEnum {

    TEST_QUEUE("TEST_QUEUE"),

    DELAY_QUEUE("DELAY_QUEUE")


    ;

    private String name;

    QueueEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
