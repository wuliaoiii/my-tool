package com.lanqi.pay.service;

import com.alipay.api.AlipayResponse;
import com.lanqi.common.utils.PageInfo;
import com.lanqi.common.utils.PageRequestParams;
import com.lanqi.common.entity.Pay;
import com.lanqi.common.vo.PayReqVo;
import com.lanqi.common.vo.UserResultVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述：交易记录 服务实现层接口
 *
 * @author yangy
 * @date 2018/06/25
 */
public interface PayService {

    /**
     * 阿里支付
     *
     * @param userResultVo
     * @param payReqVo
     * @return
     */
    String aliPay(UserResultVo userResultVo, PayReqVo payReqVo);

    /**
     * 微信支付
     *
     * @param request
     * @param userResultVo
     * @param payReqVo
     * @return
     */
    String tenPay(HttpServletRequest request, UserResultVo userResultVo, PayReqVo payReqVo);

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
     * @param userResultVo
     * @param pay
     * @return
     */
    String forPay(HttpServletRequest request, UserResultVo userResultVo, PayReqVo pay);


    Pay aliAntPay(Pay pay);

    /**
     * 阿里退款
     *
     * @param pay
     * @return
     */
    Pay aliRefund(Pay pay);

    /**
     * 阿里转账
     *
     * @param pay
     * @return
     */
    Pay aliTransfer(Pay pay);

    /**
     * 查询阿里支付结果
     *
     * @param pay
     * @return
     */
    AlipayResponse aliPayResult(Pay pay);

    /**
     * 查询阿里退款结果
     *
     * @param pay
     * @return
     */
    AlipayResponse aliRefundResult(Pay pay);

    /**
     * 查询阿里转账
     *
     * @param pay
     * @return
     */
    AlipayResponse aliTransferResult(Pay pay);

    /**
     * 阿里退款
     *
     * @param pay
     * @return
     */
    Pay tenRefund(Pay pay);

    /**
     * 阿里转账
     *
     * @param pay
     * @return
     */
    Pay tenTransfer(Pay pay);

    /**
     * 查询阿里退款结果
     *
     * @param pay
     * @return
     */
    Pay tenRefundResult(Pay pay);

    /**
     * 查询阿里转账
     *
     * @param pay
     * @return
     */
    Pay tenTransferResult(Pay pay);


    /**
     * @param pay
     * @des 添加Pay
     */
    Pay savePay(Pay pay);

    /**
     * 批量保存支付
     *
     * @param payList
     * @return
     */
    Integer savePayList(List<Pay> payList);

    /**
     * @param recordId
     * @des 根据id删除
     */
    int deleteById(String recordId);

    /**
     * @param idArr
     * @des 根据id集合删除
     */
    int deleteByIdArr(Long[] idArr);

    /**
     * @param pay
     * @des 修改Pay
     */
    Pay updatePay(Pay pay);

    /**
     * @param recordId
     * @des 根据Id获取
     */
    Pay findById(String recordId);

    /**
     * @param idArr
     * @des 根据id 集合查询信息
     */
    List<Pay> findByIdArr(Long[] idArr);

    /**
     * @param pageRequest
     * @des 分页查询
     */
    PageInfo findByParams(PageRequestParams<Pay> pageRequest);

    /**
     * 根据参数查询单个交易记录信息
     *
     * @param pay
     * @return
     */
    Pay findOneByPay(Pay pay);

    /**
     * @param pay
     * @des 根据条件计数
     */
    int count(Pay pay);

}