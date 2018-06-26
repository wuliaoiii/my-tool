package com.yangy.pay.enums.ali;

/**
 * 交易状态枚举
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/25
 * @since 1.0.0
 */
public enum AliBizStatusEnum {

    WAIT_BUYER_PAY("交易创建，等待买家付款"),
    TRADE_CLOSED("未付款交易超时关闭，或支付完成后全额退款"),
    TRADE_SUCCESS("交易支付成功"),
    TRADE_FINISHED("交易结束，不可退款"),

    ;
    private String result;

    AliBizStatusEnum(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
