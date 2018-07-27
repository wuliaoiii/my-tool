package com.yangy.pay.service;

import com.yangy.pay.model.PayReqVo;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付接口类
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/7/13
 * @since 1.0.0
 */
public interface PayService {

    /**
     * 阿里支付
     *
     * @param payReqVo
     * @return
     */
    String aliPay(PayReqVo payReqVo);

    /**
     * 微信支付
     *
     * @param request
     * @param payReqVo
     * @return
     */
    String tenPay(HttpServletRequest request, PayReqVo payReqVo);

    /**
     * 阿里回调
     *
     * @param request
     * @return
     */
    String aliNotify(HttpServletRequest request);

    /**
     * 微信回调
     *
     * @param request
     * @return
     */
    String tenNotify(HttpServletRequest request);

    /**
     * @param request
     * @param pay
     * @return
     */
    String forPay(HttpServletRequest request, PayReqVo pay);
}