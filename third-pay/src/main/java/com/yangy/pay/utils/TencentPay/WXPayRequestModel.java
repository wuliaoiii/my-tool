package com.yangy.pay.utils.TencentPay;

/**
 * 微信支付model
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/29
 * @since 1.0.0
 */
public class WXPayRequestModel {

    /**
     * 描述: 公众账号id
     * 是否必需: yes
     */
    private String appid;

    /**
     * 描述: 微信支付分配的商户号
     * 是否必需: yes
     */
    private String mchId;

    /**
     * 描述: 随机字符串
     * 是否必需: yes
     */
    private String nonceStr;

    /**
     * 描述: 签名 签名规则 ->
     * 是否必需: yes
     */
    private String sign;

    /**
     * 描述: 签名类型 默认 MD5  支持 HMAC-SHA256 MD5
     * 是否必需: no
     */
    private String signType;

    /**
     * 描述: 商品简单的描述信息
     * 是否必需: yes
     */
    private String body;

    /**
     * 描述: 商户订单号 限制32个字符内
     * 是否必需: yes
     */
    private String outTradeNo;

    /**
     * 描述: 标价金额 单位 分
     * 是否必需: yes
     */
    private int totalFee;

    /**
     * 描述: 终端ip APP和网页支付提交用户端 IP
     * 是否必需: yes
     */
    private String spbillCreateIp;

    /**
     * 描述: 设备号
     * 是否必需: no
     */
    private String deviceInfo;

    /**
     * 描述: 订单起始时间
     * 是否必需: no
     */
    private String timeStart;

    /**
     * 描述: 回调url
     * 是否必需: yes
     */
    private String notifyUrl;

    /**
     * 描述: 交易类型 -> JSAPI 公众号支付 APP APP支付 NATIVE 扫码支付
     * 是否必需: yes
     */
    private String tradeType;

    /**
     * 描述: 订单失效时间
     * 是否必需: no
     */
    private String timeExpire;

    /**
     * 描述: 指定支付方式 no_credit 限制用户不能使用信用卡支付
     * 是否必需: no
     */
    private String limitPay;

    /**
     * 描述: 币种  CNY 人民币
     * 是否必需: no
     */
    private String feeType;


}