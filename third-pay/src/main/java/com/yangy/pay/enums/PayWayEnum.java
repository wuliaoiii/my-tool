package com.yangy.pay.enums;

public enum PayWayEnum {

    WEXIN_PAY(100),
    ALI_PAY(101),
    ANT_STAGE(102);

    private Integer way;

    PayWayEnum(Integer way) {
        this.way = way;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }
}
