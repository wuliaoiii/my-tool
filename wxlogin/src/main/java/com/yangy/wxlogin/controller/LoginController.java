package com.yangy.wxlogin.controller;

import com.yangy.wxlogin.config.Constants;
import com.yangy.wxlogin.wexin.HttpClientUtils;
import com.yangy.wxlogin.wexin.URLEncodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/8
 * @since 1.0.0
 */
@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private Constants constants;

    @RequestMapping("getCode")
    public String getCode() throws Exception {
        //拼接url
        StringBuilder url = new StringBuilder();
//        https://open.weixin.qq.com/connect/oauth2/authorize?appid=APP_ID&redirect_uri=URL&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
        url.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
        url.append("appid=" + "wx8e80c58a758ce2e5");
        //回调地址 ,回调地址要进行Encode转码
        url.append("&redirect_uri=" + URLEncodeUtil.getURLEncoderString(constants.getWeCatRedirectUrl()));
        url.append("&response_type=code");
        url.append("&scope=snsapi_userinfo");
        //转码
        url.append("&state=1#wechat_redirect");
//        url.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf68782ded2a1a4c2&redirect_uri="+URLEncodeUtil.getURLEncoderString("http://localhost:8097/usercompany/login/third")+"&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
        String result = HttpClientUtils.get(url.toString(), "GBK");
        System.out.println(result.toString());
        System.out.println(url.toString());
        return url.toString();
    }

    /**
     * 获取token,该步骤返回的token期限为一个月
     *
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping("callback.do")
    public String getAccessToken(String code) throws Exception {
        if (code != null) {
            System.out.println(code);
        }
//        https://api.weixin.qq.com/sns/oauth2/access_token?appid=APP_ID&secret=SECRET&code=CODE&grant_type=authorization_code

        StringBuilder url = new StringBuilder();
        url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
        url.append("&appid=" + constants.getWeCatAppId());
        url.append("&secret=" + constants.getWeCatAppSecret());
        url.append("&code=" + code);
        url.append("&grant_type=authorization_code");
        //回调地址
        String redirect_uri = constants.getQqRedirectUrl();
        //转码
        url.append("&redirect_uri=" + URLEncodeUtil.getURLEncoderString(redirect_uri));
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        System.out.println("url:" + url.toString());
        //把token保存
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");

        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
       /* if (qqProperties.get("accessToken") != null){
            qqProperties.remove("accessToken");
        }
        if (qqProperties.get("expiresIn") != null){
            qqProperties.remove("expiresIn");
        }
        if (qqProperties.get("refreshToken") != null){
            qqProperties.remove("refreshToken");
        }
        qqProperties.put("accessToken",accessToken);
        qqProperties.put("expiresIn",expiresIn);
        qqProperties.put("refreshToken",refreshToken);*/
        return result;
    }
    /**
     * 刷新token
     * @return
     * @throws Exception
     */
//    @RequestMapping("refreshToken")
//    public String refreshToken() throws Exception {
//        StringBuilder url = new StringBuilder("https://graph.qq.com/oauth2.0/token?");
//        url.append("grant_type=refresh_token");
//        url.append("&client_id=" + constants.getQqAppId());
//        url.append("&client_secret=" + constants.getQqAppSecret());
//        //获取refreshToken
//        String refreshToken = (String) qqProperties.get("refreshToken");
//        url.append("&refresh_token=" + refreshToken);  // 该处需要传入上个步骤获取到的refreshToken;
//        String result = HttpClientUtils.get(url.toString(),"UTF-8");
//        System.out.println("url:" + url.toString());
//        //把新获取的token存到map中
//        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
//
//        String accessToken = StringUtils.substringAfterLast(items[0], "=");
//        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
//        String newRefreshToken = StringUtils.substringAfterLast(items[2], "=");
//        if (qqProperties.get("accessToken") != null){
//            qqProperties.remove("accessToken");
//        }
//        if (qqProperties.get("expiresIn") != null){
//            qqProperties.remove("expiresIn");
//        }
//        if (qqProperties.get("refreshToken") != null){
//            qqProperties.remove("refreshToken");
//        }
//        qqProperties.put("accessToken",accessToken);
//        qqProperties.put("expiresIn",expiresIn);
//        qqProperties.put("refreshToken",newRefreshToken);
//        return result;
//    }
    /**
     * 获取用户openId
     * @return
     * @throws Exception
     */
//    @RequestMapping("getOpenId")
//    public String getOpenId() throws Exception {
//        StringBuilder url = new StringBuilder("https://graph.qq.com/oauth2.0/me?");
//        //获取保存的用户的token
//        String accessToken = (String) qqProperties.get("accessToken");
//        if (!StringUtils.isNotEmpty(accessToken)){
//            return "未授权";
//        }
//        url.append("access_token=" + accessToken);
//        String result = HttpClientUtils.get(url.toString(),"UTF-8");
//        String openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
//        System.out.println(openId);
//        //把openId存到map中
//        if (qqProperties.get("openId") != null) {
//            qqProperties.remove("openId");
//        }
//        qqProperties.put("openId",openId);
//        return result;
//    }

    /**
     * 根据openId获取用户信息
     */
//    @RequestMapping("getUserInfo")
//    public QQUserInfo getUserInfo() throws Exception {
//        StringBuilder url = new StringBuilder("https://graph.qq.com/user/get_user_info?");
//        //取token
//        String accessToken = (String) qqProperties.get("accessToken");
//        String openId = (String) qqProperties.get("openId");
//        if (!StringUtils.isNotEmpty(accessToken) || !StringUtils.isNotEmpty(openId)){
//            return null;
//        }
//        url.append("access_token=" + accessToken);
//        url.append("&oauth_consumer_key=" + constants.getQqAppId());
//        url.append("&openid=" + openId);
//        String result = HttpClientUtils.get(url.toString(),"UTF-8");
//        Object json = JSON.parseObject(result,QQUserInfo.class);
//        QQUserInfo QQUserInfo = (QQUserInfo)json;
//        return QQUserInfo;
//    }

}