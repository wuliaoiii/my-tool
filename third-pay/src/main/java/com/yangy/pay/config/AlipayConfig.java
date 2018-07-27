package com.yangy.pay.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 阿里支付配置
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/25
 * @since 1.0.0
 */
public class AlipayConfig {

    /**
     * 合作者身份id
     */
    private static String PARTNER = null;

    /**
     * 支付宝分配给开发者的应用ID
     */
    private static String APP_ID = null;

    private static String APP_SERCET = null;

    /**
     * 私钥
     */
    private static String PRIVATE_KEY = null;

    /**
     * 公钥
     */
    private static String PUBLIC_KEY = null;

    /**
     * 接收支付宝回调信息的url
     */
    private static String NOTIFY_URL = null;

    /**
     * 支付url
     */
    private static String ALIPAY_URL = null;

    /**
     * APP支付接口
     */
    private static String PAY_METHOD = null;

    /**
     * 销售产品码，商家和支付宝签约的产品码, 为固定值QUICK_MSECURITY_PAY
     */
    private final static String PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    private final static String VERSION = "1.0";

    /**
     * 签名方式 不需修改
     */
    private final static String SIGN_TYPE = "RSA2";

    private final static String FORMAT = "JSON";

    /**
     * 字符编码格式 目前支持 gbk 或 utf-8
     */
    private final static String CHARSET = "UTF-8";

    private final static String TIMEOUT_EXPRESS = "5m";

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("alipay", Locale.getDefault());

    private static String getProperty(String key) {
        try {
            return new String(RESOURCE.getString(key).getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static String getPartner() {
        if (StringUtils.isNotBlank(PARTNER)) {
            return PARTNER;
        }
        PARTNER = AlipayConfig.getProperty("PARTNER");
        return PARTNER;
    }

    public static String getAppId() {
        if (StringUtils.isNotBlank(APP_ID)) {
            return APP_ID;
        }
        APP_ID = AlipayConfig.getProperty("APP_ID");
        return APP_ID;
    }

    public static String getAppSercet() {
        if (StringUtils.isNotBlank(APP_SERCET)) {
            return APP_SERCET;
        }
        APP_SERCET = AlipayConfig.getProperty("APP_SERCET");
        return APP_SERCET;
    }

    public static String getPrivateKey() {
        if (StringUtils.isNotBlank(PRIVATE_KEY)) {
            return PRIVATE_KEY;
        }
        PRIVATE_KEY = AlipayConfig.getProperty("APP_PRIVATE_KEY");
        return PRIVATE_KEY;
    }

    public static String getPublicKey() {
        if (StringUtils.isNotBlank(PUBLIC_KEY)) {
            return PUBLIC_KEY;
        }
        PUBLIC_KEY = AlipayConfig.getProperty("ALIPAY_PUBLIC_KEY");
        return PUBLIC_KEY;
    }

    public static String getNotifyUrl() {
        if (StringUtils.isNotBlank(NOTIFY_URL)) {
            return NOTIFY_URL;
        }
        NOTIFY_URL = AlipayConfig.getProperty("NOTIFY_URL");
        return NOTIFY_URL;
    }

    public static String getAlipayUrl() {
        if (StringUtils.isNotBlank(ALIPAY_URL)) {
            return ALIPAY_URL;
        }
        ALIPAY_URL = AlipayConfig.getProperty("ALIPAY_URL");
        return ALIPAY_URL;
    }

    public static String getPayMethod() {
        if (StringUtils.isNotBlank(PAY_METHOD)) {
            return PAY_METHOD;
        }
        PAY_METHOD = AlipayConfig.getProperty("PAY_METHOD");
        return PAY_METHOD;
    }

    public static String getProductCode() {
        return PRODUCT_CODE;
    }

    public static String getVersion() {
        return VERSION;
    }

    public static String getSignType() {
        return SIGN_TYPE;
    }

    public static String getFormat() {
        return FORMAT;
    }

    public static String getCharset() {
        return CHARSET;
    }

    public static String getTimeoutExpress() {
        return TIMEOUT_EXPRESS;
    }
}