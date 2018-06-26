package com.yangy.pay.utils.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/12
 * @since 1.0.0
 */
public class AlipayManager {

    private static final Logger logger = LoggerFactory.getLogger(AlipayManager.class);

    public static AlipayManager INSTANCE = null;

    private static AlipayClient alipayClient = null;

    private AlipayManager() {
    }

    private static class SingletonHolder {
        private static final AlipayClient INSTANCE = new DefaultAlipayClient(
                AlipayConfig.getAlipayUrl(),
                AlipayConfig.getAppId(),
                AlipayConfig.getPrivateKey(),
                AlipayConfig.getFormat(),
                AlipayConfig.getCharset(),
                AlipayConfig.getPublicKey(),
                AlipayConfig.getSignType());
    }

    public static AlipayClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static AlipayClient getAlipayClient() {
        return getInstance();
    }

    public static AlipayTradeQueryResponse query(String params) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent(params);
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.error("exception{}", e);
        }
        return response;
    }






   /* public static AlipayManager getInstance() {
        try {
            if (INSTANCE == null) {
                synchronized (RedisUtils.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new AlipayManager();
                        if (alipayClient == null) {
                            alipayClient = new DefaultAlipayClient(
                                    AlipayConfig.getAlipayUrl(), AlipayConfig.getAppId(),
                                    AlipayConfig.getPrivateKey(), AlipayConfig.getFormat(),
                                    AlipayConfig.getCharset(), AlipayConfig.getPublicKey(),
                                    AlipayConfig.getSignType());
                        }
                    }
                }
            }
            return INSTANCE;
        } catch (Exception e) {
            logger.error("exception{}", e);
            return null;
        }
    }*/

}

