package com.yangy.pay.utils.tenpay;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.SortedMap;

/**
 * @ClassName: SignUtils
 * @Description: Sign工具类
 */
public class SignUtils {

    private static final Logger logger = LoggerFactory.getLogger(SignUtils.class);

    /**
     * @Title: createSign
     * @Description: 创建md5签名, 规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     * @param: map
     * @return: String
     * @throws: Exception
     */
    public static String createSign(SortedMap<String, String> map) {
        if (null == map) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            if (!"sign".equals(k) && StringUtils.isNotBlank(v)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(TenpayConfig.getPrivateKey());
        logger.info("拼接API密钥:" + sb);
        String sign = MD5Utils.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        logger.info("sign签名:" + sign);
        return sign;
    }

    /**
     * @Title: isTenpaySign
     * @Description: 是否签名正确, 规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     * @param: map
     * @return: boolean
     * @throws: Exception
     */
    public static boolean isTenpaySign(SortedMap<String, String> map) {
        if (null == map) {
            return false;
        }
        String mySign = createSign(map);
        String tenpaySign = (map.get("sign")).toUpperCase();
        //logger.info("mySign签名:" + mySign);
        logger.info("tenpaySign签名:" + tenpaySign);
        return tenpaySign.equals(mySign);
    }
}
