package com.yangy.pay.utils.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.yangy.pay.config.AlipayConfig;
import com.yangy.pay.entity.Pay;
import com.yangy.pay.model.PayRecordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author yangy
 * @des 支付宝支付(APP支付) 参考: (https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.MU5sxk&treeId=193&articleId=105051&docType=1)
 * @email java_yangy@126.com
 * @create 2018/6/12
 * @since 1.0.0
 */
public class AlipayUtils {

    private static final Logger log = LoggerFactory.getLogger(AlipayUtils.class);

    /**
     * 支付宝支付接口
     * https://docs.open.alipay.com/api_1/alipay.trade.app.pay
     * 所需参数  订单总金额 描述 支付单号
     * @param pay
     * @return
     */
    public static AlipayTradeAppPayResponse forPay(PayRecordVo pay) {
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        //最晚支付时间
        model.setTimeoutExpress(AlipayConfig.getTimeoutExpress());
        //订单总金额, 单位为元, 精确到小数点后两位
        model.setTotalAmount(String.valueOf(pay.getAmount()));
        //商品的标题/交易标题/订单标题/订单关键字等
        model.setSubject("共享医院 " + pay.getSubject());
        //商户网站唯一订单号
        model.setOutTradeNo(pay.getPayNo());
        //销售产品码，商家和支付宝签约的产品码，为固定值
        model.setProductCode(AlipayConfig.getProductCode());

        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.getNotifyUrl());

//        request.setNeedEncrypt(true);//TODO 是否加密
        AlipayTradeAppPayResponse response = null;

        try {
            response = AlipayManager.getAlipayClient().sdkExecute(request);
        } catch (AlipayApiException e) {
            log.error("exception{}", e);
        }
        if (null == response) {
            return null;
        }
        return response;//就是orderString 可以直接给客户端请求，无需再做处理。
    }

    /**
     * 蚂蚁分期支付
     *
     * @param pay
     * @return
     */
    public static AlipayTradeAppPayResponse forAntPay(PayRecordVo pay) {
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        //最晚支付时间
        model.setTimeoutExpress(AlipayConfig.getTimeoutExpress());
        //订单总金额, 单位为元, 精确到小数点后两位
        model.setTotalAmount(String.valueOf(pay.getAmount()));
        //商品的标题/交易标题/订单标题/订单关键字等
        model.setSubject("share-hospital:" + pay.getPayNo());
        //商户网站唯一订单号
        model.setOutTradeNo(pay.getPayNo());
        //销售产品码，商家和支付宝签约的产品码，为固定值
        model.setProductCode(AlipayConfig.getProductCode());
        //业务拓展参数
        ExtendParams extendParams = new ExtendParams();
        //花呗分期 3 6 12
        extendParams.setHbFqNum("3");
        //使用花呗分期需要卖家承担的手续费比例 0 代表 0 %
        extendParams.setHbFqSellerPercent("0");
        //卡类型
        extendParams.setCardType("");
        model.setExtendParams(extendParams);

        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.getNotifyUrl());

        AlipayTradeAppPayResponse response = null;
        try {
            response = AlipayManager.getAlipayClient().sdkExecute(request);
            throw new AlipayApiException("");
//            response = AliPayManager.getInstance().getAlipayClient().sdkExecute(request);
        } catch (AlipayApiException e) {
            log.error("exception{}", e);
        }
        if (null == response) {
            return null;
        }
        return response;//就是orderString 可以直接给客户端请求，无需再做处理。
    }

    /**
     * 支付宝退款接口
     * 参考 https://docs.open.alipay.com/api_1/alipay.trade.refund/
     *
     * @param pay
     * @return
     */
    public static AlipayTradeRefundResponse forRefund(PayRecordVo pay) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();

        //商户交易号
        model.setOutTradeNo(pay.getPayNo());
        //支付宝交易号
        model.setTradeNo(pay.getOutTradeNo());
        //退款金额
        model.setRefundAmount(String.valueOf(pay.getAmount().doubleValue()));
        //退款原因
//        model.setRefundReason(pay.getRefundReason());
        //退款请求 同一笔交易多次退款必须保证唯一 如需部分退款 则此参数比船
        model.setOutRequestNo(pay.getPayNo());

        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.getNotifyUrl());
        AlipayTradeRefundResponse response = null;
        try {
            response = AlipayManager.getAlipayClient().sdkExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 支付宝转账接口
     * 参考 https://docs.open.alipay.com/api_28/alipay.fund.trans.toaccount.transfer
     *
     * @param pay
     * @return
     */
    public static AlipayFundTransToaccountTransferResponse forTransfer(PayRecordVo pay) {
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();

        model.setOutBizNo(pay.getPayNo());
        model.setPayeeType("ALIPAY_LOGONID");
        //收款方账户
        model.setPayeeAccount("");
        //转账金额 单位 元 只支持2位小数
        model.setAmount(String.valueOf(pay.getAmount().doubleValue()));
        //收款人真实姓名
        model.setPayeeRealName("");
        //转账说明
        model.setRemark("");

        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.getNotifyUrl());
        AlipayFundTransToaccountTransferResponse response = null;
        try {
            response = AlipayManager.getAlipayClient().sdkExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 支付宝转账接口
     * 参考 https://docs.open.alipay.com/api_28/alipay.fund.trans.toaccount.transfer
     *
     * @param pay
     * @return
     */
    public static AlipayTradeQueryResponse forPayQuery(Pay pay) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();

        model.setOutTradeNo(pay.getPayNo());
        model.setTradeNo(pay.getOutPayNo());

        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.getNotifyUrl());
        AlipayTradeQueryResponse response = null;
        try {
            response = AlipayManager.getAlipayClient().sdkExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 支付宝转账接口
     * 参考 https://docs.open.alipay.com/api_28/alipay.fund.trans.toaccount.transfer
     *
     * @param pay
     * @return
     */
    public static AlipayFundTransOrderQueryResponse forTransferQuery(Pay pay) {
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();

        model.setOutBizNo(pay.getPayNo());
        model.setOrderId(pay.getOutPayNo());

        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.getNotifyUrl());
        AlipayFundTransOrderQueryResponse response = null;
        try {
            response = AlipayManager.getAlipayClient().sdkExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 验证调用是否来自支付宝
     *
     * @param params
     * @return
     */
    public static boolean verifySign(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(
                    params, AlipayConfig.getPublicKey(), AlipayConfig.getCharset(), AlipayConfig.getSignType());
        } catch (AlipayApiException e) {
            log.error("exception{}", e);
        }
        return false;
    }

    /**
     * 处理返回结果
     *
     * @param params
     * @return
     */
    public static Map<String, String> parsePayResult(Map<String, String[]> params) {
        Map<String, String> map = new HashMap<>();
        if (null == params || params.size() == 0) {
            return map;
        }

        for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
            String name = iterator.next();
            String[] values = params.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            map.put(name, valueStr);
        }
        return map;
    }

    /**
     * 签名 RSA256
     *
     * @param params
     * @return
     */
    private static String rsaSign(Map<String, String> params) {
        try {
            String signContent = AlipaySignature.getSignContent(params);
            return AlipaySignature.rsaSign(signContent, AlipayConfig.getPrivateKey(),
                    AlipayConfig.getCharset(), AlipayConfig.getSignType());
        } catch (AlipayApiException e) {
            log.error("exception{}", e);
        }
        return null;
    }

    /**
     * 设置APP支付查询参数
     * 参考: (https://docs.open.alipay.com/api_1/alipay.trade.query/)
     *
     * @param payNo
     * @return
     */
    public static String setQueryParams(String payNo) {
        Map<String, String> map = new HashMap<>();
        map.put("out_trade_no", payNo);//本地商户交易号
        return JSON.toJSONString(map);
    }

    /**
     * 对请求字符串的所有一级value（biz_content作为一个value）进行encode，
     * 编码格式按请求串中的charset为准，没传charset按UTF-8处理
     * 参考: (https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.ssQuGx&treeId=193&articleId=105465&docType=1)
     *
     * @param map
     * @return
     */
    public static String paramsEncoder(SortedMap<String, String> map) {
        if (null == map) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            try {
                if ("sign".equals(k)) {
                    sb.append(k).append("=").append(v).append("&");
                } else {
                    sb.append(k).append("=").append(URLEncoder.encode(v, "utf-8")).append("&");
                }
                //sb.append(k).append("=").append(URLEncoder.encode(v, "utf-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                log.error("exception{}", e);
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 支付宝转账接口
     * 参考 https://docs.open.alipay.com/api_28/alipay.fund.trans.toaccount.transfer
     *
     * @param pay
     * @return
     */
    public static AlipayTradeFastpayRefundQueryResponse forRefundQuery(Pay pay) {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();

        model.setOutTradeNo(pay.getPayNo());
        model.setTradeNo(pay.getOutPayNo());
        model.setOutRequestNo(pay.getPayNo());

        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.getNotifyUrl());
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            response = AlipayManager.getAlipayClient().sdkExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }
}
