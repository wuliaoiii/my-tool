package com.lanqi.common.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：交易记录模型
 *
 * @author yangy
 * @date 2018/06/25
 */
@Entity
@Table(name = "py_pay")
public class Pay implements Serializable {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 支付编号
     */
    @Column(name = "pay_no")
    private String payNo;

    /**
     * 微信支付宝 支付编号
     */
    @Column(name = "out_pay_no")
    private String outPayNo;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 支付方式 100：支付宝 101：微信 102：花呗
     */
    @Column(name = "way")
    private Integer way;

    /**
     * 类型 100：支付 101：提现 102 : 退款
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 100：预约 订单 102：优惠券
     */
    @Column(name = "relate_type")
    private Integer relateType;

    /**
     * 预约订单编号、优惠券id
     */
    @Column(name = "relate")
    private String relate;

    /**
     * 金额
     */
    @Column(name = "amount")
    private BigDecimal amount;

    /**
     * 状态 100：待支付 101：支付成功 102：支付失败
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Long updateTime;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayNo() {
        return this.payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getOutPayNo() {
        return this.outPayNo;
    }

    public void setOutPayNo(String outPayNo) {
        this.outPayNo = outPayNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getWay() {
        return this.way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRelateType() {
        return this.relateType;
    }

    public void setRelateType(Integer relateType) {
        this.relateType = relateType;
    }

    public String getRelate() {
        return this.relate;
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}