package com.lanqi.common.enums;

public enum PayEnum {

    ALI(100, "支付宝"),
    WX(101, "微信"),

    PAY(100, "支付"),
    TRANSFER(101, "转账"),
    REFUND(102, "退款"),

    DOING(100, "申请中"),
    SUCCESS(101, "成功"),
    FAILURE(102, "失败"),

    RESERVE_ORDER(100,"预约金"),
    COUPON_ORDER(102,"卡卷"),
    ;

    private Integer value;
    private String des;

    PayEnum(Integer value, String des) {
        this.value = value;
        this.des = des;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static PayEnum getWayEnum(Integer value) {
        switch (value) {
            case 100:
                return PayEnum.ALI;
            case 101:
                return PayEnum.WX;
            default:
                return null;
        }
    }

    public static PayEnum getTypeEnum(Integer value) {
        switch (value) {
            case 100:
                return PayEnum.PAY;
            case 101:
                return PayEnum.REFUND;
            case 102:
                return PayEnum.TRANSFER;
            default:
                return null;
        }
    }

    public static PayEnum getStatusEnum(Integer value) {
        switch (value) {
            case 100:
                return PayEnum.DOING;
            case 101:
                return PayEnum.SUCCESS;
            case 102:
                return PayEnum.FAILURE;
            default:
                return null;
        }
    }

    public static PayEnum getOrderTypeEnum(Integer value) {
        switch (value) {
            case 100:
                return PayEnum.RESERVE_ORDER;
            case 102:
                return PayEnum.COUPON_ORDER;
            default:
                return null;
        }
    }


}
