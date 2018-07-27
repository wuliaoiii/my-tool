package com.yangy.pay.utils.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.yangy.pay.config.AlipayConfig;

/**
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/12
 * @since 1.0.0
 */
public class AlipayManager {
    private static class LazyHolder {
        private static final AlipayClient INSTANCE = new DefaultAlipayClient(
                AlipayConfig.getAlipayUrl(),
                AlipayConfig.getAppId(),
                AlipayConfig.getPrivateKey(),
                AlipayConfig.getFormat(),
                AlipayConfig.getCharset(),
                AlipayConfig.getPublicKey(),
                AlipayConfig.getSignType());
    }

    public static final AlipayClient getAlipayClient() {
        return LazyHolder.INSTANCE;
    }

    private AlipayManager() {
    }
}

