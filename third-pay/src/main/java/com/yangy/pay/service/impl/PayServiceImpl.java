package com.yangy.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.yangy.pay.config.TenpayConfig;
import com.yangy.pay.entity.Pay;
import com.yangy.pay.enums.AliBizStatusEnum;
import com.yangy.pay.enums.PayEnum;
import com.yangy.pay.model.PayRecordVo;
import com.yangy.pay.model.PayReqVo;
import com.yangy.pay.service.PayService;
import com.yangy.pay.utils.PayUtils;
import com.yangy.pay.utils.alipay.AlipayUtils;
import com.yangy.pay.utils.tenpay.WXPay;
import com.yangy.pay.utils.tenpay.WXPayUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付实现类
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/7/13
 * @since 1.0.0
 */
@Service
public class PayServiceImpl implements PayService{
    private static final Logger log = LoggerFactory.getLogger(PayService.class);

    /*
     * 交易业务逻辑
     * 根据支付方式 判断交易方式 微信支付 支付宝支付
     * 根据交易类型 判断交易类型 支付 退款 提现
     * 预支付    参数 订单编号 用户token
     * 业务操作  根据订单编号 获取用户所需支付的金额 根据用户token获取用户信息
     * 返回信息  支付宝交易号 订单编号 ||  微信预支付会话标识 prepay_id 订单编号
     *
     * 消息回调
     *           微信  : 微信支付编号 内部支付编号 支付金额 付款银行(微信) 支付完成时间
     *           支付宝: 支付宝交易号 内部支付编号 支付总额 交易当前状态
     *
     * 支付-> 所需参数 订单编号 用户 token
     * 根据订单编号 查询用户所需支付金额 根据用户token
     *
     * */

    @Override
    public String forPay(HttpServletRequest request, PayReqVo payReqVo) {
        log.info("创建支付记录 -> payReqVo={}", JSON.toJSONString(payReqVo));
        String result = "";
        //支付方式
        Integer way = payReqVo.getWay();
        PayEnum payWayEnum = PayEnum.getWayEnum(way);

        if (null == payWayEnum) {
            log.error("记录异常信息");
        }
        switch (payWayEnum) {
            case ALI:
                result = aliPay(payReqVo);
                break;
            case WX:
                result = tenPay(request, payReqVo);
                break;
        }
        return result;
    }

    /**
     * 预支付
     *
     * @param payReqVo
     * @return
     */
    @Override
    public String aliPay(PayReqVo payReqVo) {
        PayRecordVo payRecordVo = new PayRecordVo();
        //支付编号
        payRecordVo.setSubject("支付宝 商品信息");
        payRecordVo.setPayNo("支付单号");
        payRecordVo.setAmount(null);//支付金额
        AlipayTradeAppPayResponse response = AlipayUtils.forPay(payRecordVo);
        //交易编号
        if (null == response || null == response.getTradeNo()) {
            log.error("支付失败");
        }
        //预支付单号
        String tradeNo = response.getTradeNo();
        //TODO 业务代码逻辑
        // 创建本地支付记录信息
        return tradeNo;
    }

    /**
     * 微信支付
     *
     * @param request
     * @param payReqVo
     * @return
     */
    @Override
    public String tenPay(HttpServletRequest request, PayReqVo payReqVo) {

        //请求的IP地址
        String ipAddr = PayUtils.getIpAddr(request);
        //微信支付的参数
        PayRecordVo payRecordVo = new PayRecordVo();
        payRecordVo.setSubject("微信  支付的商品信息");
        payRecordVo.setPayNo("支付单号");
        payRecordVo.setAmount(null);//支付金额
        payRecordVo.setIpAddr(ipAddr);
        Map<String, String> map = WXPayUtil.payModelToMap(payRecordVo);

        Map<String, String> resultMap = null;
        try {
            WXPay wxPay = new WXPay();
            resultMap = wxPay.unifiedOrder(map);
            System.out.println(JSON.toJSONString(resultMap));
        } catch (Exception e) {

        }
        //交易编号
        if (CollectionUtils.isEmpty(resultMap) || StringUtils.isEmpty(resultMap.get("prepay_id"))) {
            log.error("微信支付失败");
        }
        String tradeNo = resultMap.get("prepay_id");
        //TODO 业务代码逻辑
        // 创建本地支付记录信息
        return tradeNo;
    }

    @Override
    @Transactional
    public String tenNotify(HttpServletRequest request) {
        try {
            ServletInputStream inputStream = request.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            int len = -1;
            byte[] buffer = new byte[1024];

            while ((len = inputStream.read(buffer)) != -1) {
                stringBuffer.append(new String(buffer, 0, len));
            }
            inputStream.close();

            //将读取到的 XML字符串装换为 map
            Map<String, String> map = WXPayUtil.xmlToMap(stringBuffer.toString());
            //对微信参数验签
            boolean valid = WXPayUtil.isSignatureValid(map, TenpayConfig.getPrivateKey());

            Map<String, String> returnMap = new HashMap<>();
            //验签失败 返回错误 信息
            if (!valid) {
                returnMap.put("return_code", "FAIL");
                returnMap.put("return_msg", "签名错误");
                return WXPayUtil.mapToXml(returnMap);
            }

            //微信返回码错误
            if (!map.get("return_code").equals("SUCCESS")) {
                returnMap.put("return_code", "FAIL");
                returnMap.put("return_msg", "返回码错误");
                return WXPayUtil.mapToXml(returnMap);
            }

            //微信返回结果码错误
            if (!map.get("result_code").equals("SUCCESS")) {
                returnMap.put("return_code", "FAIL");
                returnMap.put("return_msg", "结果码错误");
                return WXPayUtil.mapToXml(returnMap);
            }

            //付款完成后，支付宝系统发送该交易状态通知
            System.out.println("交易成功");

            /*
             * TODO 业务逻辑
             * #1 根据订单编号查询 支付金额 支付方式 与微信返回的数据进行匹配 失败时为错误回调
             * #2 回调成功 切为正确回调时 修改支付记录状态 修改订单状态
             * */

            //用户openId
            String openId = map.get("openid");
            //内部交易号
            String outTradeNo = map.get("out_trade_no");
            //微信交易号
            String tradeNo = map.get("transaction_id");
            //支付完成时间
            String timeEnd = map.get("time_end");
            //支付总金额
            BigDecimal totalFee = new BigDecimal(map.get("total_fee"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public String aliNotify(HttpServletRequest request) {

        String msg = PayEnum.FAILURE.getDes();
        //处理支付宝的反馈信息
        Map<String, String[]> params = request.getParameterMap();
        //解密
        Map<String, String> resultMap = AlipayUtils.parsePayResult(params);
        //验证调用是否来自支付宝
        boolean verifyResult = AlipayUtils.verifySign(resultMap);
        //确定, 处理订单内部业务逻辑
        if (!verifyResult) {
            log.error("支付宝验签失败 -> resultMap={}", JSON.toJSONString(resultMap));
        }
        //交易状态
        String tradeStatus = resultMap.get("trade_status");
        if (AliBizStatusEnum.WAIT_BUYER_PAY.getResult().equals(tradeStatus)) {
            log.error("支付宝交易状态不匹配 -> resultMap={}", JSON.toJSONString(resultMap));
        }

        String payNo = null;
        String amount = null;
        String outPayNo = null;
        String sellerId = null;
        Pay payReq = new Pay();

        //交易支付成功 交易结束 不可退款
        if (AliBizStatusEnum.TRADE_SUCCESS.getResult().equals(tradeStatus) || AliBizStatusEnum.TRADE_FINISHED.getResult().equals(tradeStatus)) {
            //支付宝交易号
            outPayNo = resultMap.get("trade_no");
            //本地交易单号
            payNo = resultMap.get("out_trade_no");
            //交易金额
            amount = resultMap.get("total_amount");
            //卖家支付宝用户号
            sellerId = resultMap.get("seller_id");
        }

        //未付款交易超时关闭 或支付完成后全额退款
        if (AliBizStatusEnum.TRADE_CLOSED.getResult().equals(tradeStatus)) {
            //支付宝交易号
            outPayNo = resultMap.get("trade_no");
            //本地交易单号
            payNo = resultMap.get("out_trade_no");
            //卖家支付宝用户号
            sellerId = resultMap.get("seller_id");
            //退款金额
            amount = resultMap.get("refund_fee");
        }

        //TODO 在数据库中匹配对应的交易信息
        log.error("无法查询到交易信息 -> resultMap={}", JSON.toJSONString(resultMap));

        //TODO 数据库中的信息与支付宝返回的信息不匹配 说明是错误的回调信息
        log.error("数据库中信息与支付宝信息不匹配 -> resultMap={} payData={}", JSON.toJSONString(resultMap), JSON.toJSONString(payReq));

        //TODO 对于支付或者退款成功的修改支付记录信息
        msg = PayEnum.SUCCESS.toString();
        return msg;
    }

}