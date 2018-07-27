package com.lanqi.common.vo;

import java.math.BigDecimal;

/**
 * 支付记录VO
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/7/10
 * @since 1.0.0
 */
public class PayRecordVo {

    /**
     * 支付编号
     */
    private String payNo;

    /**
     * 支付宝订单编号
     */
    private String tradeNo;

    /**
     * 内部订单编号
     */
    private String outTradeNo;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易状态 100 交易发起 101 交易成功 102 交易失败
     */
    private Integer status;

    /**
     * 交易方式 100 支付宝  101 微信  102 花呗 103 银联支付
     */
    private Integer way;

    /**
     * 交易方式 100 支付 101 提现 102 退款
     */
    private Integer type;

    /**
     * 相关类型 100 预约订单 102 优惠券
     */
    private Integer relateType;

    /**
     * 相关信息 预支付订单编号 优惠券id
     */
    private String relate;

    private String subject;

    private String ipAddr;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRelateType() {
        return relateType;
    }

    public void setRelateType(Integer relateType) {
        this.relateType = relateType;
    }

    public String getRelate() {
        return relate;
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}