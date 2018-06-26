package com.yangy.rabbitmq.enums;

import java.text.MessageFormat;

public enum RedisKeyEnum {

    BANNER_KEY("BANNER_KEY_{0}"),
    //验证码 {0}:手机号
    VERIFY_CODE("VERIFY_CODE_{0}"),

    DIVIDEND("DIVIDEND_{0}"),

    DICT_KEY("DICT_KEY_{0}"),

    USER_C("USER_C_{0}"),
    USER_B("USER_B_{0}"),
    USER_F("USER_F_{0}"),

    USER_C_WENUM("USER_C_WENUM_{0}"),
    USER_B_WENUM("USER_B_WENUM_{0}"),
    USER_F_WENUM("USER_F_WENUM_{0}"),

    TASK_QUEUE_DIVIDEND("TASK_QUEUE_DIVIDEND_{0}"),
    TMP_QUEUE_DIVIDEND("TMP_QUEUE_DIVIDEND_{0}"),

    USER_STORE_POSITION("USER_STORE_{0}_POSITION_{1}"),

    APPOINT_TIME("APPOINT_TIME"),

    /**
     * 店铺信息的缓存
     */
    STORE_KEY("STORE_KEY_{0}"),


    /**
     * 商品信息的redis 前置
     */
    PRODUCT_KEY("PRODUCT_KEY_{0}"),

    /**
     * 资讯文章
     */
    ARTICLE_KEY("ARTICLE_KEY_{0}"),


    /**
     * 商学院
     */
    COLLEGE_KEY("COLLEGE_KEY_{0}"),


    /**
     * 文化节的
     */
    CULTURE_KEY("CULTURE_KEY_{0}");

    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String transfer(Object... params) {
        return MessageFormat.format(this.getKey(), params);
    }
}
