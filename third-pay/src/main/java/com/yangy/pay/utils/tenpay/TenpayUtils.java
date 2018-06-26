package com.yangy.pay.utils.tenpay;

import com.alibaba.fastjson.JSON;
import com.yangy.pay.entity.Pay;
import com.yangy.pay.utils.DateUtils;
import com.yangy.pay.utils.PayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @ClassName: TenpayUtils
 * @Description: 微信支付(APP支付) 参考:(https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_3)
 */
public class TenpayUtils {

    private static final Logger log = LogManager.getFormatterLogger(TenpayUtils.class);

    /**
     * 处理充值参数
     * @param params
     * @return
     */
    public static Pay acquireRechargeParams(String params) {
        log.info("Tenpay APP params:" + params);
        Pay pay = JSON.parseObject(params, Pay.class);
        if (null == pay) {
            log.error("Tenpay APP [params] is null");
            return null;
        }
        if (null == pay.getUserId()) {
            log.error("Tenpay APP [customerId] is null");
            return null;
        }
        if (null == pay.getAmount()) {
            log.error("Tenpay APP [amount] is null");
            return null;
        }
        //获取支付编码
        pay.setPayNo(PayUtils.createPayNo(pay.getUserId().toString()));
        //支付方式
        pay.setType(100);
        return pay;
    }

    /**
     * 设置预支付请求参数(APP支付)
     * 参考: (https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1)
     * @param pay
     * @param ipAddr
     * @return
     */
    public static SortedMap<String, String> setPrepayParams(Pay pay, String ipAddr) {
        //计算金额 交易金额默认为人民币交易, 接口中参数支付金额单位为【分】, 参数值不能带小数, 对账单中的交易金额单位为【元】
        //int total_fee = mul(order.getOrderMoney());// 计算订单金额
        SortedMap<String, String> map = new TreeMap<>();//创建返回Map
        //应用ID
        map.put("appid", TenpayConfig.getAppId());
        //商户号
        map.put("mch_id", TenpayConfig.getMchId());
        //设备号
        map.put("device_info", "WEB");
        //随机字符串
        map.put("nonce_str", Sha1Utils.randomStr());
        //商品或支付单简要描述
        map.put("body", "hospital:" + pay.getPayNo());
        //商户订单号
        map.put("out_trade_no", pay.getPayNo());
        //订单总金额,只能为整数
        map.put("total_fee", String.valueOf((int) (pay.getAmount().doubleValue() * 100)));
        //APP和网页支付提交用户端ip
        map.put("spbill_create_ip", ipAddr);
        //回调地址
        map.put("notify_url", TenpayConfig.getNotifyUrl());
        //交易类型
        map.put("trade_type", "APP");
        //交易时间 yyyyMMddHHmmss
        map.put("time_start", DateUtils.getNowTimeStamp());
//        map.put("time_expire", DateUtils.getTimeStamp(TenpayConfig.getTimeExpire()));//失效时间 yyyyMMddHHmmss
        map.put("sign", SignUtils.createSign(map));//签名
        return map;
    }

    /**
     * 设置APP支付参数
     * 参考: (https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12&index=2)
     * @param prepayMap
     * @return
     */
    public static SortedMap<String, String> setPayParams(Map<String, String> prepayMap) {
        SortedMap<String, String> map = new TreeMap<>();//返回map
        map.put("appid", TenpayConfig.getAppId());//应用ID
        map.put("partnerid", TenpayConfig.getMchId());//商户号
        map.put("prepayid", prepayMap.get("prepay_id"));//预支付交易会话ID
        map.put("package", TenpayConfig.getPackage());//扩展字段
        map.put("noncestr", prepayMap.get("nonce_str"));//随机字符串
        map.put("timestamp", String.valueOf(System.currentTimeMillis()/ 1000));// 时间戳
        map.put("sign", SignUtils.createSign(map));//签名
        return map;
    }

    /**
     * 设置APP查询支付参数
     * 参考: (https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_2&index=4)
     * @param payNo
     * @return
     */
    public static SortedMap<String, String> setQueryPayParams(String payNo) {
        SortedMap<String, String> map = new TreeMap<>();//创建返回Map
        map.put("appid", TenpayConfig.getAppId());//应用ID
        map.put("mch_id", TenpayConfig.getMchId());//商户号
        map.put("out_trade_no", payNo);//商户订单号
        map.put("nonce_str", Sha1Utils.randomStr());//随机字符串
        map.put("sign", SignUtils.createSign(map));//签名
        return map;
    }
}
