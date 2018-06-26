package com.yangy.thirdLogin.utils.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.core.util.Base64;
import com.yangy.thirdLogin.config.WxConfig;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 微信小程序信息获取
 *
 * @author zhy
 */
public class WXAppletUserInfo {
    private static Logger log = LoggerFactory.getLogger(WXAppletUserInfo.class);

    /**
     * 获取微信小程序 session_key 和 openid
     *
     * @param code 调用微信登陆返回的Code
     * @return
     * @author zhy
     */
    public static WeChatUserInfo getSessionKeyOropenid(String code) {
        //微信端登录code值  
        String wxCode = code;
//        ResourceBundle resource = ResourceBundle.getBundle("wechat");   //读取属性文件
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";  //请求地址 https://api.weixin.qq.com/sns/jscode2session
        Map<String, String> requestUrlParam = new HashMap<String, String>();
        requestUrlParam.put("appid", WxConfig.getAppId());  //开发者设置中的appId
        requestUrlParam.put("secret", WxConfig.getAppSecret()); //开发者设置中的appSecret
        requestUrlParam.put("js_code", wxCode); //小程序调用wx.login返回的code
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识

        JSONObject jsonObject = JSON.parseObject(sendPost(requestUrl, requestUrlParam));
        WeChatUserInfo weChatUserInfo = new WeChatUserInfo();
        String openId = jsonObject.getString("openid");
        weChatUserInfo.setOpenId(openId);
        String sessionKey = jsonObject.getString("session_key");
        weChatUserInfo.setSessionKey(sessionKey);
        String unionId = jsonObject.getString("unionid");
        if (StringUtils.isEmpty(unionId)) {
            weChatUserInfo.setUnionId(openId);
        } else {
            weChatUserInfo.setUnionId(unionId);
        }
        return weChatUserInfo;
    }


    /**
     * 解密用户敏感数据获取用户信息
     *
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return
     * @author zhy
     */
    public static WeChatUserInfo getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据  
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥  
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量  
        byte[] ivByte = Base64.decode(iv);
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        // 初始化
        Cipher cipher = null;
        WeChatUserInfo wechatUserInfo = null;
        Security.addProvider(new BouncyCastleProvider());

        try {
            //获取加密器实例
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            //初始化加密器
            // 参数:
            //加解密 Cipher.DECRYPT_MODE
            //key spec
            //偏移量 parameters
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

            //解密后获取的byte数组
            byte[] resultByte = cipher.doFinal(dataByte);

            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                System.out.println("微信用户信息 -> " + result);
                JSONObject jsonObject = JSON.parseObject(result);
                if (null != jsonObject) {
                    wechatUserInfo = new WeChatUserInfo();
                    // 用户的标识
                    wechatUserInfo.setOpenId(jsonObject.getString("openId"));
                    // 昵称
                    wechatUserInfo.setNickname(jsonObject.getString("nickName"));
                    // 用户的性别（1是男性，2是女性，0是未知）
                    wechatUserInfo.setSex(jsonObject.getInteger("gender"));
                    // 用户所在国家
                    wechatUserInfo.setCountry(jsonObject.getString("country"));
                    // 用户所在省份
                    wechatUserInfo.setProvince(jsonObject.getString("province"));
                    // 用户所在城市
                    wechatUserInfo.setCity(jsonObject.getString("city"));
                    // 用户头像
                    // 最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像）
                    // 用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
                    wechatUserInfo.setHeadImgUrl(jsonObject.getString("avatarUrl"));
                    //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
                    String unionId = jsonObject.getString("unionid");
                    if (StringUtils.isEmpty(unionId)) {
                        wechatUserInfo.setUnionId(unionId);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wechatUserInfo;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}