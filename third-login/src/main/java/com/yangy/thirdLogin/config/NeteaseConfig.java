package com.yangy.thirdLogin.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class NeteaseConfig {
    //短信发送地址
    private static String SEND_URL;

    //短信校验地址 暂时不做使用
    private static String CHECK_URL;

    //网易云信分配的账号，请替换你在管理后台应用下申请的app_key
    private static String APP_KEY;

    //网易云信分配的密钥，请替换你在管理后台应用下申请的app_secret
    private static String APP_SECRET;

    //随机数
    private static String NONCE;

    //短信模板ID 3872888 语音通知模板
    private static String LOGIN_TEMPLATEID;

    private static String PHONE_TEMPLATEID;

    //手机号
    private static String MOBILE;

    //验证码长度，范围4～10，默认为4
    private static String CODELEN;

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("netease", Locale.getDefault());

    public static String getProperty(String key) {
        try {
            return new String(RESOURCE.getString(key).getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSendUrl() {
        if (StringUtils.isNotBlank(SEND_URL)) {
            return SEND_URL;
        }
        SEND_URL = getProperty("SEND_URL");
        return SEND_URL;
    }

    public static String getCheckUrl() {
        if (StringUtils.isNotBlank(CHECK_URL)) {
            return CHECK_URL;
        }
        CHECK_URL = getProperty("CHECK_URL");
        return CHECK_URL;
    }

    public static String getAppKey() {
        if (StringUtils.isNotBlank(APP_KEY)) {
            return APP_KEY;
        }
        APP_KEY = getProperty("APP_KEY");
        return APP_KEY;
    }

    public static String getAppSecret() {
        if (StringUtils.isNotBlank(APP_SECRET)) {
            return APP_SECRET;
        }
        APP_SECRET = getProperty("APP_SECRET");
        return APP_SECRET;
    }

    public static String getNONCE() {
        if (StringUtils.isNotBlank(NONCE)) {
            return NONCE;
        }
        NONCE = getProperty("NONCE");
        return NONCE;
    }

    public static String getLoginTemplateid() {
        if (StringUtils.isNotBlank(LOGIN_TEMPLATEID)) {
            return LOGIN_TEMPLATEID;
        }
        LOGIN_TEMPLATEID = getProperty("LOGIN_TEMPLATEID");
        return LOGIN_TEMPLATEID;
    }

    public static String getPhoneTemplateid() {
        if (StringUtils.isNotBlank(PHONE_TEMPLATEID)) {
            return PHONE_TEMPLATEID;
        }
        PHONE_TEMPLATEID = getProperty("PHONE_TEMPLATEID");
        return PHONE_TEMPLATEID;
    }

    public static String getMOBILE() {
        if (StringUtils.isNotBlank(MOBILE)) {
            return MOBILE;
        }
        MOBILE = getProperty("MOBILE");
        return MOBILE;
    }

    public static String getCODELEN() {
        if (StringUtils.isNotBlank(CODELEN)) {
            return CODELEN;
        }
        CODELEN = getProperty("CODELEN");
        return CODELEN;
    }
}
