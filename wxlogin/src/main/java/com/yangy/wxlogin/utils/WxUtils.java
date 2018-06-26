package com.yangy.wxlogin.utils;

import com.alibaba.fastjson.JSONObject;
import com.yangy.wxlogin.config.WxConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WxUtils {

    public static String getCode(String url) {
        String appId = WxConfig.getAppId();//自己的配置appid
        String appSecret = WxConfig.getAppSecret();//自己的配置APPSECRET;
        String requestUrl = WxConfig.getGetCode()
                .replace("APP_ID", appId)
                .replace("URL", url);

        HttpClient client = null;
        // 获取code信息
        JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);

        return "";
    }

    /**
     * 根据 code 获取  openId accessToken scope refreshToken
     *
     * @param code
     * @return
     */
    public static Map<String, Object> getOpenId(String code) {
        String appId = WxConfig.getAppId();//自己的配置appid
        String appSecret = WxConfig.getAppSecret();//自己的配置APPSECRET;
        String requestUrl = WxConfig.getGetOpenid().replace("APP_ID", appId).replace("SECRET", appSecret).replace("CODE", code);
        HttpClient client = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);
            JSONObject openIdJSON = JSONObject.parseObject(response);
            //OpenidJSONO可以得到的内容：
            //access_token  -->网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
            //expires_in    -->access_token接口调用凭证超时时间，单位（秒）
            //refresh_token -->用户刷新access_token
            //openid        -->用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
            //scope         -->用户授权的作用域，使用逗号（,）分隔
            String openId = String.valueOf(openIdJSON.get("openid"));
            String accessToken = String.valueOf(openIdJSON.get("access_token"));
            String scope = String.valueOf(openIdJSON.get("scope"));//用户保存的作用域
            String refreshToken = String.valueOf(openIdJSON.get("refresh_token"));
            String expiresIn = String.valueOf(openIdJSON.get("expires_in"));

            resultMap.put("openId", openId);
            resultMap.put("accessToken", accessToken);
            resultMap.put("scope", scope);
            resultMap.put("refreshToken", refreshToken);
            resultMap.put("expiresIn", expiresIn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return resultMap;
    }

    /**
     * 根据 refreshToken 刷新 token
     *
     * @param token
     * @return
     */
    public static Map<String, Object> refreshToken(String token) {
        String appId = WxConfig.getAppId();//自己的配置appid
        String requestUrl = WxConfig.getRefreshToken().replace("APP_ID", appId).replace("REFRESH_TOKEN", token);
        HttpClient client = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);
            JSONObject refreshJSON = JSONObject.parseObject(response);

            //OpenidJSONO可以得到的内容：
            //access_token  -->网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
            //expires_in    -->access_token接口调用凭证超时时间，单位（秒）
            //refresh_token -->用户刷新access_token
            //openid        -->用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
            //scope         -->用户授权的作用域，使用逗号（,）分隔
            String openId = String.valueOf(refreshJSON.get("openid"));
            String accessToken = String.valueOf(refreshJSON.get("access_token"));
            String scope = String.valueOf(refreshJSON.get("scope"));//用户保存的作用域
            String refreshToken = String.valueOf(refreshJSON.get("refresh_token"));
            String expiresIn = String.valueOf(refreshJSON.get("expires_in"));

            resultMap.put("openId", openId);
            resultMap.put("accessToken", accessToken);
            resultMap.put("scope", scope);
            resultMap.put("expiresIn", expiresIn);
            resultMap.put("refreshToken", refreshToken);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return resultMap;
    }

    /**
     * 根据 openId accessToken 获取微信用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public static WeChatUserInfo getUserInfo(String accessToken, String openId) {
        Logger log = LoggerFactory.getLogger(HttpUtil.class);
        WeChatUserInfo wechatUserInfo = null;
        // 拼接请求地址
        String requestUrl = WxConfig.getGetUserInfo().replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);

        // 获取用户信息
        JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                wechatUserInfo = new WeChatUserInfo();
                // 用户的标识
                wechatUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                wechatUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
//                wechatUserInfo.setSex(jsonObject.getInteger("sex"));
                // 用户所在国家
                wechatUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                wechatUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                wechatUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                // 最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像）
                // 用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
                wechatUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
                wechatUserInfo.setUnionId(jsonObject.getString("unionid"));
                //用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
                wechatUserInfo.setPrivilege(jsonObject.getString("privilege"));
            } catch (Exception e) {
                if (0 == wechatUserInfo.getSubscribe())
                    log.error("用户{}已取消关注", wechatUserInfo.getOpenId());
                else {
                    int errorCode = jsonObject.getIntValue("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        return wechatUserInfo;
    }

    public static WeChatUserInfo getUserInfo(String code) {
        Map<String, Object> map = getOpenId(code);
        if (map.size() > 0) {
            String openId = (String) map.get("openId");
            String accessToken = (String) map.get("accessToken");
            if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(accessToken)) {
                WeChatUserInfo userInfo = getUserInfo(accessToken, openId);
                return userInfo;
            }
        }
        return null;
    }
}
