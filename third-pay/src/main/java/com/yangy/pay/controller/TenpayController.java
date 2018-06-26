package com.yangy.pay.controller;

import com.yangy.pay.entity.Pay;
import com.yangy.pay.utils.PayUtils;
import com.yangy.pay.utils.tenpay.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @ClassName: TenpayController
 * @Description: 微信支付(APP支付) 参考:(https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_3)
 */
@RestController
@RequestMapping(value = "/tenpay")
public class TenpayController {

    private static final Logger log = LoggerFactory.getLogger(TenpayController.class);

    /**
     * APP支付 —— 调用微信APP支付统一下单接口
     * 参考: (https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1)
     * @param request
     * @return
     */
    @PostMapping(value = "/unified")
    public String unified(HttpServletRequest request) {
        //String ipAddr = request.getParameter("ipAddr");//APP和网页支付提交用户端ip
        String params = request.getParameter("params");//获取参数
        String ip = PayUtils.getIpAddr(request);
        if (StringUtils.isBlank(ip)) {
//            return ResultUtils.toErrorString(ResultEnum.BAD_REQUEST);
        }
        Pay pay = TenpayUtils.acquireRechargeParams(params);
        //参数错误
        if (null == pay) {
//            return ResultUtils.toErrorString(ResultEnum.BAD_REQUEST);
        }

        //设置统一下单支付参数
        SortedMap<String, String> prepayParams = TenpayUtils.setPrepayParams(pay, ip);
        //记录支付参数日志
        log.info("Tenpay APP unifiedOrder prepay params:" + prepayParams);
        //生成xml(统一下单发送格式)
        String requestXML = XMLUtils.mapToXmlStr(prepayParams);
        //记录统一下单参数日志
        log.info("Tenpay APP unifiedOrder prepay xml params:" + requestXML);
        //向微信发送请求(POST)生成预付单, 获取预付单信息
        String single = HttpsClientUtils.httpsPostRequest(TenpayConfig.getUnifiedOrder(), requestXML);
        //解析微信返回的信息，以Map形式存储便于取值
        Map<String, String> prepayResultMap = XMLUtils.xmlStrToMap(single);
        //记录统一下单结果
        log.info("Tenpay APP prepay result:" + prepayResultMap);

        /*
        * 验证微信返回信息
        * */
        if (null == prepayResultMap || "FAIL".equals(prepayResultMap.get("return_code")) ||
                "FAIL".equals(prepayResultMap.get("result_code"))) {
//            return ResultUtils.toErrorString(ResultEnum.BAD_REQUEST);
        }

        //设置支付参数用于返回前端
        SortedMap<String, String> payParams = TenpayUtils.setPayParams(prepayResultMap);
        log.info("Tenpay APP unifiedOrder params:" + payParams);//记录支付参数日志

        //支付类型 充值
        pay.setType(100);
        //微信预支付订单号
        //pay.setOutPayNo(prepayResultMap.get("prepay_id"));
        //添加数据 充值
        return null;
    }

    /**
     * 接收微信回调信息 (APP支付)
     * 参考: (https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7&index=3)
     * @param request
     * @param response
     */
    @PostMapping(value = "/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        //通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒
        //获取微信调用notify_url的返回信息
        String result = XMLUtils.createTenpayReturnXml("FAIL", "");
        InputStream inStream = null;
        ByteArrayOutputStream outSteam = null;
        String params = null;
        try {
            inStream = request.getInputStream();
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            params = new String(outSteam.toByteArray(), "utf-8");
        } catch (Exception e) {
            log.error("exception{}:", e);
        } finally {
            try {
                if (null != outSteam) {
                    outSteam.close();
                }
                if (null != inStream) {
                    inStream.close();
                }
            } catch (IOException e) {
                log.error("exception{}:", e);
            }
        }
        log.info("Tenpay APP notify start: " + params);
        //结果处理: 解析xml成map
        Map<String, String> resultMap = XMLUtils.xmlStrToMap(params);

        /*
        * 验证系统结果 SUCCESS/FAIL
        * */
        if (null == resultMap || "FAIL".equals(resultMap.get("return_code"))) {
            log.error("Tenpay APP notify system is error");
        } else {
            //过滤空 设置 TreeMap
            SortedMap<String, String> packageParams = new TreeMap<>();
            for (String keyValue : resultMap.keySet()) {
                if (null != resultMap.get(keyValue)) {
                    packageParams.put(keyValue, resultMap.get(keyValue).trim());
                }
            }
            //判断签名是否正确
            boolean verifyResult = SignUtils.isTenpaySign(packageParams);
            if (!verifyResult) {
                log.error("Tenpay APP notify sign is error");
            } else {
                String result_code = resultMap.get("result_code");//业务结果 SUCCESS/FAIL
                String payNo = resultMap.get("out_trade_no");//商户交易号
                String outPayNo = resultMap.get("transaction_id");//微信订单号
                String amount = resultMap.get("total_fee");
                Integer status = null;
                //验证业务结果
                if ("SUCCESS".equals(result_code)) {
//                    status = PayStatusEnum.SUCCESS.getStatus();
                }
                if ("FAIL".equals(result_code)) {
//                    status = PayStatusEnum.FAIL.getStatus();
                }
                Double amountd = Integer.valueOf(amount) / 100D;

                //TODO 支付成功 处理结果
            }
        }

        try {
            response.getWriter().write(result);
        } catch (Exception e) {
            log.error("notify fail:", e);
        }
    }

    /**
     * @Title: result
     * @Description: 同步查询支付结果 (APP支付)
     * 参考: (https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_7&index=3)
     * @param: pno
     * @return: String
     * @throws: Exception
     */
    @PostMapping(value = "/result")
    public String result(@RequestParam String pno) {
        //参数错误
        if (StringUtils.isBlank(pno)) {
//            return ResultUtils.toErrorString(ResultEnum.BAD_REQUEST);
        }
        //TODO 查询数据库 支付是否完成

        //如果未完成 查询微信订单信息
        //设置查询订单参数
        SortedMap<String, String> queryParams = TenpayUtils.setQueryPayParams(pno);
        //记录查询订单日志
        log.info("Tenpay APP order_query params:" + queryParams);
        //生成xml(统一下单发送格式)
        String requestXML = XMLUtils.mapToXmlStr(queryParams);
        //记录查询订单参数日志
        log.info("Tenpay APP order_query xml params:" + requestXML);
        //向微信发送请求(POST), 获取订单信息
        String single = HttpsClientUtils.httpsPostRequest(TenpayConfig.getOrderQuery(), requestXML);
        //解析微信返回的信息，以Map形式存储便于取值
        Map<String, String> resultMap = XMLUtils.xmlStrToMap(single);
        log.info("Tenpay APP order_query result:" + resultMap);//记录查询订单结果
        //验证微信返回信息
        if (null == resultMap || "FAIL".equals(resultMap.get("return_code")) ||
                "FAIL".equals(resultMap.get("result_code"))) {
//            return ResultUtils.toErrorString(ResultEnum.MISSION_FAIL);
        }
        //支付状态
        String trade_state = resultMap.get("trade_state");
        //支付中 NOTPAY—未支付 USERPAYING--用户支付中
        if ("NOTPAY".equals(trade_state) || "USERPAYING".equals(trade_state)) {
//            return ResultUtils.toString(PayStatusEnum.WAIT.getStatus());
        }
        //String payNo = resultMap.get("out_trade_no");//商户交易号
        String outPayNo = resultMap.get("transaction_id");//微信订单号
        String amount = resultMap.get("total_fee");
        Integer status = null;
        if ("SUCCESS".equals(trade_state)) {//支付成功
//            status = PayStatusEnum.SUCCESS.getStatus();
        } else {                            //其他情况均视为失败
//            status = PayStatusEnum.FAIL.getStatus();
        }
        //修改数据
        Double amountd = Integer.valueOf(amount) / 100D;
        //TODO 修改支付状态
        return null;
    }
}
