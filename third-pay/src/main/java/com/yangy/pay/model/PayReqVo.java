package com.lanqi.common.vo;

/**
 * 支付请求参数
 *
 * @author yangy
 * @email yangyang@lanqikeji.cn
 * @create 2018/7/12
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