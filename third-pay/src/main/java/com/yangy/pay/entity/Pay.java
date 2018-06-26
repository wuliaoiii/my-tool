package com.yangy.pay.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data //get set 方法
@Builder //builder 模式
//@AllArgsConstructor //有参构造
//@NoArgsConstructor //无参构造
public class Pay {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 支付账号
     */
    private String payNo;

    /**
     * 外部支付账户
     */
    private String outPayNo;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 支付方式  100 支付宝 101 微信  102 蚂蚁花呗
     */
    private Integer way;

    /**
     * 操作类型 100:支付 101:退款
     */
    private Integer type;

    /**
     * 状态 100 待支付 101 支付成功 102  支付失败
     */
    private Integer status;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getOutPayNo() {
        return outPayNo;
    }

    public void setOutPayNo(String outPayNo) {
        this.outPayNo = outPayNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}