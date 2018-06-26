package com.yangy.rabbitmq.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class MQConfig {

    /**
     * 地址
     */
    private static String HOST;

    /**
     * 端口号
     */
    private static String PORT;

    /**
     * 用户名
     */
    private static String USERNAME;

    /**
     * 密码
     */
    private static String PASSWORD;

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("rabbitmq", Locale.getDefault());

    public static String getProperty(String key) {
        try {
            return new String(RESOURCE.getString(key).getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    public static String getHOST() {
        if (StringUtils.isNotBlank(HOST)) {
            return HOST;
        }
        HOST = getProperty("HOST");
        return HOST;
    }

    public static String getPORT() {
        if (StringUtils.isNotBlank(PORT)) {
            return PORT;
        }
        PORT = getProperty("PORT");
        return PORT;
    }

    public static String getUSERNAME() {
        if (StringUtils.isNotBlank(USERNAME)) {
            return USERNAME;
        }
        USERNAME = getProperty("USERNAME");
        return USERNAME;
    }

    public static String getPASSWORD() {
        if (StringUtils.isNotBlank(PASSWORD)) {
            return PASSWORD;
        }
        PASSWORD = getProperty("PASSWORD");
        return PASSWORD;
    }
}
