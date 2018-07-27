package com.lanqi.common.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @ClassName: TenpayConfig
 * @Description: TenpayConfig配置 tenpay.properties
 */
public class TenpayConfig {

    private static String APP_ID = null;

    private static String APP_SECRET = null;

    private static String MCH_ID = null;

    private static String PRIVATE_KEY = null;

    private static String NOTIFY_URL = null;

//    private static String UNIFIED_ORDER = null;
//
//    private static String ORDER_QUERY = null;

    private static String DOMAIN = null;

    private static final int HTTP_CONNECT_TIMEOUT_MS = 6000;
    private static final int HTTP_READ_TIMEOUT_MS = 8000;

    private final static String PACKAGE = "Sign=WXPay";//扩展字段

    private final static int TIME_EXPIRE = 300000;

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("tenpay", Locale.getDefault());

    private static String getProperty(String key) {
        try {
            return new String(RESOURCE.getString(key).getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            return "";
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

    public static String getMchId() {
        if (StringUtils.isNotBlank(MCH_ID)) {
            return MCH_ID;
        }
        MCH_ID = getProperty("MCH_ID");
        return MCH_ID;
    }

    public static String getPrivateKey() {
        if (StringUtils.isNotBlank(PRIVATE_KEY)) {
            return PRIVATE_KEY;
        }
        PRIVATE_KEY = getProperty("PRIVATE_KEY");
        return PRIVATE_KEY;
    }

    public static String getNotifyUrl() {
        if (StringUtils.isNotBlank(NOTIFY_URL)) {
            return NOTIFY_URL;
        }
        NOTIFY_URL = getProperty("NOTIFY_URL");
        return NOTIFY_URL;
    }

//    public static String getUnifiedOrder() {
//        if (StringUtils.isNotBlank(UNIFIED_ORDER)) {
//            return UNIFIED_ORDER;
//        }
//        UNIFIED_ORDER = getProperty("UNIFIED_ORDER");
//        return UNIFIED_ORDER;
//    }
//
//    public static String getOrderQuery() {
//        if (StringUtils.isNotBlank(ORDER_QUERY)) {
//            return ORDER_QUERY;
//        }
//        ORDER_QUERY = getProperty("ORDER_QUERY");
//        return ORDER_QUERY;
//    }
    public static String getDomain() {
        if (StringUtils.isNotBlank(DOMAIN)) {
            return DOMAIN;
        }
        DOMAIN = getProperty("DOMAIN");
        return DOMAIN;
    }

    public static String getPackage() {
        return PACKAGE;
    }

    public static int getTimeExpire() {
        return TIME_EXPIRE;
    }

    public static int getHttpConnectTimeoutMs() {
        return HTTP_CONNECT_TIMEOUT_MS;
    }

    public static int getHttpReadTimeoutMs() {
        return HTTP_READ_TIMEOUT_MS;
    }
}
