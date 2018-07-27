package com.lanqi.common.utils.tenpay;

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }
}