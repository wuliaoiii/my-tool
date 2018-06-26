package com.yangy.pay.model;

/**
 * 阿里支付接口的请求参数model
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/23
 * @since 1.0.0
 */
public class AliPayPublicRequest {

    /**
     * 是否必填 true
     * 最大长度 32
     * 描述 支付宝分配给开发者的应用ID
     * 示例值 2014072300007148
     */
    private String appId;

    /**
     * 是否必填 true
     * 最大长度 128
     * 描述 接口名称
     * 示例值 alipay.trade.fastpay.refund.query
     */
    private String method;

    /**
     * 是否必填 true
     * 最大长度 40
     * 描述 仅支持JSON
     * 示例值 JSON
     */
    private String format = "JSON";

    /**
     * 是否必填 true
     * 最大长度 10
     * 描述 请求使用的编码格式，如utf-8,gbk,gb2312等
     * 示例值 utf-8
     */
    private String charset = "utf-8";

    /**
     * 是否必填 true
     * 最大长度 10
     * 描述 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     * 示例值 RSA2
     */
    private String signType;

    /**
     * 是否必填 true
     * 最大长度 344
     * 描述 商户请求参数的签名串，详见签名
     * 示例值 alipay.trade.fastpay.refund.query
     */
    private String sign;

    /**
     * 是否必填 true
     * 最大长度 19
     * 描述 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
     * 示例值 2014-07-24 03:07:50
     */
    private String timestamp;

    /**
     * 是否必填 true
     * 最大长度 3
     * 描述 调用的接口版本，固定为：1.0
     * 示例值 1.0
     */
    private String version = "1.0";

    /**
     * 是否必填 false
     * 最大长度 40
     * 描述 接口名称
     * 示例值 alipay.trade.fastpay.refund.query
     */
    private String appAuthToken;

    /**
     * 是否必填 true
     * 最大长度 128
     * 描述 请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
     * 示例值
     */
    private String bizContent;

}