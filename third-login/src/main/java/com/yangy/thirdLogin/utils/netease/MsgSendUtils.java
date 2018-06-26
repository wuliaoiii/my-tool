package com.yangy.thirdLogin.utils.netease;

import com.yangy.thirdLogin.config.NeteaseConfig;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MsgSendUtils {

//    https://api.netease.im/sms/sendtemplate.action    通知类短信
//    https://api.netease.im/sms/sendtemplate.action    运营类类短信
//    https://api.netease.im/sms/sendcode.action        验证码类短信

    //发送验证码的请求路径URL


    public static String sendMsg(String mobile,String temp) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = getHttpPost(NeteaseConfig.getSendUrl());
        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         */
        nvps.add(new BasicNameValuePair("templateid", temp));
        nvps.add(new BasicNameValuePair("mobile", mobile.toString()));
        nvps.add(new BasicNameValuePair("codeLen", NeteaseConfig.getCODELEN()));

        try {
            //赋值
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            //执行请求
            HttpResponse response = httpClient.execute(httpPost);
            //处理后的请求结果 示例 {"code":200,"wechat":"7","obj":"665081"}
            String result = EntityUtils.toString(response.getEntity());
            Map parse = (Map) JSONUtils.parse(result);
            String code = (String) parse.get("obj");
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String checkMsg(String mobile, String code) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = getHttpPost(NeteaseConfig.getCheckUrl());
        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         */
        nvps.add(new BasicNameValuePair("mobile", mobile));
        nvps.add(new BasicNameValuePair("code", code));
        try {
            //赋值
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            //执行请求
            HttpResponse response = httpClient.execute(httpPost);
            //处理后的请求结果 示例 {"code":200,"wechat":"7","obj":"665081"}
            String result = EntityUtils.toString(response.getEntity());
            Map parse = (Map) JSONUtils.parse(result);
            String resultCode = (String) parse.get("code");
            return resultCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpPost getHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */
        String checkSum = CheckSumBuilder.getCheckSum(NeteaseConfig.getAppSecret(), NeteaseConfig.getNONCE(), curTime);
        // 设置请求的header
        httpPost.addHeader("AppKey", NeteaseConfig.getAppKey());
        httpPost.addHeader("Nonce", NeteaseConfig.getNONCE());
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        return httpPost;
    }

    public static void main(String[] args) throws IllegalAccessException {
        String phoneNum = "13167357450";
        String s = MsgSendUtils.sendMsg(phoneNum,NeteaseConfig.getLoginTemplateid());
        System.out.println(s);

        String s1 = checkMsg(phoneNum.toString(), s);
        System.out.println(s1);
    }

}