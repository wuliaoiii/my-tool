package com.yangy.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.yangy.pay.entity.Pay;
import com.yangy.pay.enums.ali.AliBizStatusEnum;
import com.yangy.pay.utils.alipay.AlipayManager;
import com.yangy.pay.utils.alipay.AlipayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.SortedMap;

/**
 * @ClassName: AlipayController
 * @Description: 支付宝支付(APP支付) 参考: (https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.MU5sxk&treeId=193&articleId=105051&docType=1)
 */

@Controller
@RequestMapping(value = "/alipay")
public class AlipayController {

    private static final Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @PostMapping(value = "/pay")
    @ResponseBody
    public String pay(@RequestBody Pay pay) {
        AlipayTradeAppPayResponse response = AlipayUtils.forPay(pay);
        //记录支付参数编码后的日志
        logger.info("Alipay APP gateway paramsByEncode:" + JSON.toJSONString(response));
        //TODO 执行支付业务
        return null;
    }

    @PostMapping(value = "/refund")
    @ResponseBody
    public String refund(@RequestBody Pay pay) {
        AlipayTradeRefundResponse refund = AlipayUtils.forRefund(pay);
        //记录支付参数编码后的日志
        logger.info("Alipay APP gateway paramsByEncode:" + JSON.toJSONString(refund));
        //TODO 执行支付业务
        return null;
    }

    @PostMapping(value = "/transfer")
    @ResponseBody
    public String transfer(@RequestBody Pay pay) {
        AlipayFundTransToaccountTransferResponse transfer = AlipayUtils.forTransfer(pay);
        //记录支付参数编码后的日志
        logger.info("Alipay APP gateway paramsByEncode:" + JSON.toJSONString(transfer));
        //TODO 执行支付业务
        return null;
    }

    /**
     * 接收支付宝回调信息 (APP支付)
     * 参考: (https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.wUBUAl&treeId=204&articleId=105301&docType=1)
     *
     * @param request
     * @param response
     */
    @PostMapping(value = "/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Alipay APP notify start");//25小时以内完成8次通知
        String result = "failure";
        //获取支付宝调用notify_url的返回信息
        Map<String, String[]> params = request.getParameterMap();
        //处理支付宝的反馈信息
        Map<String, String> resultMap = AlipayUtils.parsePayResult(params);
        //反馈信息日志
        logger.info("Alipay APP notify result:" + JSON.toJSONString(resultMap));
        //验证调用是否来自支付宝
        boolean verifyResult = AlipayUtils.verifySign(resultMap);
        //确定, 处理订单内部业务逻辑
        if (!verifyResult) {
            logger.error("Alipay APP notify sign is error");
        } else {
            String tradeStatus = resultMap.get("trade_status");//交易状态
            //处理业务逻辑--注意交易单不要重复处理
            //支付宝交易号
            String outPayNo = resultMap.get("trade_no");
            //本地交易单号
            String payNo = resultMap.get("out_trade_no");
            //交易金额
            String amount = resultMap.get("total_amount");
            Integer status = null;
            if (AliBizStatusEnum.TRADE_SUCCESS.getResult().equals(tradeStatus) || AliBizStatusEnum.TRADE_FINISHED.getResult().equals(tradeStatus)) {
                //status = PayStatusEnum.SUCCESS.getStatus();
            }
            if (AliBizStatusEnum.TRADE_CLOSED.getResult().equals(tradeStatus)) {
                //status = PayStatusEnum.FAIL.getStatus();
            }
            //Integer amounti = (int) (Double.valueOf(amount) * 100D);

            //交易完成 校验成功后在response中返回success，校验失败返回failure
            //TODO 业务逻辑代码
            result = "success";
        }
        //通知支付宝服务器
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(result);
        } catch (Exception e) {
            logger.error("notify fail:", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * @Title: result
     * @Description: 同步查询支付结果 (APP支付)
     * 参考: (https://docs.open.alipay.com/api_1/alipay.trade.query/)
     * @param: pno
     * @return: String
     * @throws: Exception
     */

    @PostMapping("/result")
    public String result(@RequestBody Pay pay) {
        AlipayTradeQueryResponse response = AlipayUtils.forPayQuery(pay);
        //查询失败
        if (null == response || !response.isSuccess()) {
            throw new RuntimeException("查询失败");
        }
        //支付状态
        String tradeStatus = response.getTradeStatus();
        //交易创建，等待买家付款
        if ("WAIT_BUYER_PAY".equals(tradeStatus)) {
            throw new RuntimeException("待付款");
        }
        //支付宝交易号
        String outPayNo = response.getTradeNo();
        //实收金额
        String amount = response.getReceiptAmount();
        Integer status = null;
        if (AliBizStatusEnum.TRADE_SUCCESS.getResult().equals(tradeStatus) || AliBizStatusEnum.TRADE_FINISHED.getResult().equals(tradeStatus)) {
            //status = PayStatusEnum.SUCCESS.getStatus();
        }
        if (AliBizStatusEnum.TRADE_CLOSED.getResult().equals(tradeStatus)) {
            //status = PayStatusEnum.FAIL.getStatus();
        }

//        //修改数据
//        int opera = payService.updatePay(pno, outPayNo, status, amount);
//        //操作失败 返回系统异常
//        if (opera != 57102) {
//            return ResultUtils.toErrorString(ResultEnum.MISSION_FAIL);
//        }
//        //操作成功 返回 支付结果
//        return ResultUtils.toString(status);
        return null;
    }
}
