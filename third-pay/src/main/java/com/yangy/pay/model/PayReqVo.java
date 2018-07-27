package com.yangy.pay.model;

/**
 * 交易请求参数vo
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/25
 * @since 1.0.0
 */
public class PayReqVo {

    private String orderIdArrStr;
    private Long[] orderIdArr;
    /**
     * 100 预约订单 102 卡券
     */
    private Integer type;

    /**
     * ALI(100, "支付宝"), WX(101, "微信")
     */
    private Integer way;

    public String getOrderIdArrStr() {
        return orderIdArrStr;
    }

    public void setOrderIdArrStr(String orderIdArrStr) {
        this.orderIdArrStr = orderIdArrStr;
    }

    public Long[] getOrderIdArr() {
        return orderIdArr;
    }

    public void setOrderIdArr(Long[] orderIdArr) {
        this.orderIdArr = orderIdArr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }
}