package com.yangy.pay.utils.tenpay;

import java.security.MessageDigest;
import java.util.*;

/**
 * @ClassName: Sha1Utils
 * @Description: sha1算法
 */
public class Sha1Utils {

    /**
     * @Title: randomStr
     * @Description: 生成随机字符串
     * @param:
     * @return: String
     * @throws: Exception
     */
    public static String randomStr() {
        Random random = new Random();
        return MD5Utils.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
    }

    /**
     * @Title: createSHA1Sign
     * @Description: 创建签名SHA1
     * @param: signParams
     * @return: String
     * @throws: Exception
     */
    public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
        StringBuffer sb = new StringBuffer();
        //Set es = signParams.entrySet();
        Set<Map.Entry<String, String>> es = signParams.entrySet();
        //Iterator it = es.iterator();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        while (it.hasNext()) {
            //Map.Entry entry = (Map.Entry) it.next();
            Map.Entry<String, String> entry = it.next();
            String k = entry.getKey();
            String v = entry.getValue();
            sb.append(k).append("=").append(v).append("&");
            //要采用URLENCODER的原始值！
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        return getSha1(params);
    }

    /**
     * @Title: getSha1
     * @Description: 获取Sha1签名
     * @param: str
     * @return: String
     * @throws: Exception
     */
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes());
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
