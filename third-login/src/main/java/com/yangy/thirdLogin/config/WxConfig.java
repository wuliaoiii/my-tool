package com.yangy.thirdLogin.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class WxConfig {

    private static String APP_ID;

    private static String APP_SECRET;
    // 网页授权获取 code
    private static String GET_CODE;
    // 网页授权接口
    private static String GET_OPENID;
    // 刷新 token
    private static String REFRESH_TOKEN;
    // 网页授权得到用户基本信息接口
    private static String GET_USER_INFO;
    // 验证 token 有效性
    private static String VERIFY_AUTHORIZATION;

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("wechat", Locale.getDefault());

    public static String getProperty(String key) {
        try {
            return new String(RESOURCE.getString(key).getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAppId() {
        if (StringUtils.isNotBlank(APP_ID)) {
            return APP_ID;
        }
        APP_ID = getProperty("APP_ID");
        return APP_ID;
    }

    public static String getAppSecret() {
        if (StringUtils.isNotBlank(APP_SECRET)) {
            return APP_SECRET;
        }
        APP_SECRET = getProperty("APP_SECRET");
        return APP_SECRET;
    }

    public static String getGetCode() {
        if (StringUtils.isNotBlank(GET_CODE)) {
            return GET_CODE;
        }
        GET_CODE = getProperty("GET_CODE");
        return GET_CODE;
    }

    public static String getGetOpenid() {
        if (StringUtils.isNotBlank(GET_OPENID)) {
            return GET_OPENID;
        }
        GET_OPENID = getProperty("GET_OPENID");
        return GET_OPENID;
    }

    public static String getRefreshToken() {
        if (StringUtils.isNotBlank(REFRESH_TOKEN)) {
            return APP_ID;
        }
        REFRESH_TOKEN = getProperty("REFRESH_TOKEN");
        return REFRESH_TOKEN;
    }

    public static String getGetUserInfo() {
        if (StringUtils.isNotBlank(GET_USER_INFO)) {
            return GET_USER_INFO;
        }
        GET_USER_INFO = getProperty("GET_USER_INFO");
        return GET_USER_INFO;
    }

    public static String getVerifyAuthorization() {
        if (StringUtils.isNotBlank(VERIFY_AUTHORIZATION)) {
            return VERIFY_AUTHORIZATION;
        }
        VERIFY_AUTHORIZATION = getProperty("VERIFY_AUTHORIZATION");
        return VERIFY_AUTHORIZATION;
    }
}
