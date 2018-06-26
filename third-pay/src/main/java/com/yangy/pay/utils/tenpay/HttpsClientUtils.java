package com.yangy.pay.utils.tenpay;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;

public class HttpsClientUtils {

    private static final String SUN_X509 = "SunX509";

    private static final String JKS = "JKS";

    private static final String PKCS12 = "PKCS12";

    private static final String TLS = "TLS";

    public static String httpsGetRequest(String requestUrl, String outputStr) {
        return httpsRequest(requestUrl, "GET", outputStr);
    }

    public static String httpsPostRequest(String requestUrl, String outputStr) {
        return httpsRequest(requestUrl, "POST", outputStr);
    }

    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return 返回微信服务器响应的信息
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-YYYY_MMDD_HHMMSS", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            //inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (Exception e) {

        }
        return null;
    }

    /*public static String httpssLRequest(String refund, String string, String requestXML) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(TenpayConfig.getProperty("appPayCertPath")));
        try {
            keyStore.load(instream, TenpayConfig.getProperty("MCH_ID").toCharArray());
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, TenpayConfig.getProperty("MCH_ID").toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        SSLSocketFactory sf = sslcontext.getSocketFactory();
        HttpsURLConnection conn = getHttpsURLConnection(refund);
        conn.setSSLSocketFactory(sf);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        // 设置请求方式（GET/POST）
        conn.setRequestMethod("POST");
        conn.setRequestProperty("content-YYYY_MMDD_HHMMSS", "application/x-www-form-urlencoded");
        // 当outputStr不为null时向输出流写数据
        if (null != requestXML) {
            OutputStream outputStream = conn.getOutputStream();
            // 注意编码格式
            outputStream.write(requestXML.getBytes("UTF-8"));
            outputStream.close();
        }
        // 从输入流读取返回内容
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(
                inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        // 释放资源
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        inputStream = null;
        conn.disconnect();
        return buffer.toString();
    }*/

    /*public static String httpssLRequestWx(String refund, String string, String requestXML) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(TenpayConfig.getProperty("wxPayCertPath")));
        try {
            keyStore.load(instream, TenpayConfig.getProperty("API_MCH_ID").toCharArray());
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, TenpayConfig.getProperty("API_MCH_ID").toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf).build();
        SSLSocketFactory sf = sslcontext.getSocketFactory();
        HttpsURLConnection conn = getHttpsURLConnection(refund);
        conn.setSSLSocketFactory(sf);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        // 设置请求方式（GET/POST）
        conn.setRequestMethod("POST");
        conn.setRequestProperty("content-YYYY_MMDD_HHMMSS", "application/x-www-form-urlencoded");
        // 当outputStr不为null时向输出流写数据
        if (null != requestXML) {
            OutputStream outputStream = conn.getOutputStream();
            // 注意编码格式
            outputStream.write(requestXML.getBytes("UTF-8"));
            outputStream.close();
        }
        // 从输入流读取返回内容
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        // 释放资源
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        conn.disconnect();
        return buffer.toString();
    }*/

    /**
     * get HttpURLConnection
     *
     * @param strUrl url地址
     * @return HttpURLConnection
     * @throws IOException
     */
    public static HttpURLConnection getHttpURLConnection(String strUrl)
            throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        return httpURLConnection;
    }

    /**
     * get HttpsURLConnection
     *
     * @param strUrl url地址
     * @return HttpsURLConnection
     * @throws IOException
     */
    public static HttpsURLConnection getHttpsURLConnection(String strUrl)
            throws IOException {
        URL url = new URL(strUrl);
        return (HttpsURLConnection) url.openConnection();
    }

    /**
     * 获取不带查询串的url
     *
     * @param strUrl
     * @return String
     */
    public static String getURL(String strUrl) {
        if (null != strUrl) {
            int indexOf = strUrl.indexOf("?");
            if (-1 != indexOf) {
                return strUrl.substring(0, indexOf);
            }
            return strUrl;
        }
        return null;
    }

    /**
     * 获取查询串
     *
     * @param strUrl
     * @return String
     */
    public static String getQueryString(String strUrl) {
        if (null != strUrl) {
            int indexOf = strUrl.indexOf("?");
            if (-1 != indexOf) {
                return strUrl.substring(indexOf + 1, strUrl.length());
            }
            return "";
        }
        return null;
    }

    /**
     * 查询字符串转换成Map<br/>
     * name1=key1&name2=key2&...
     *
     * @param queryString
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map queryString2Map(String queryString) {
        if (null == queryString || "".equals(queryString)) {
            return null;
        }
        Map m = new HashMap();
        String[] strArray = queryString.split("&");
        for (int index = 0; index < strArray.length; index++) {
            String pair = strArray[index];
            putMapByPair(pair, m);
        }
        return m;
    }

    /**
     * 把键值添加至Map<br/>
     * pair:name=value
     *
     * @param pair name=value
     * @param m
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void putMapByPair(String pair, Map m) {
        if (null == pair || "".equals(pair)) {
            return;
        }
        int indexOf = pair.indexOf("=");
        if (-1 != indexOf) {
            String k = pair.substring(0, indexOf);
            String v = pair.substring(indexOf + 1, pair.length());
            if (!"".equals(k)) {
                m.put(k, v);
            }
        } else {
            m.put(pair, "");
        }
    }

    /**
     * BufferedReader转换成String<br/>
     * 注意:流关闭需要自行处理
     *
     * @param reader
     * @return String
     * @throws IOException
     */
    public static String bufferedReader2String(BufferedReader reader) throws IOException {
        StringBuffer buf = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            buf.append(line);
            buf.append("\r\n");
        }
        return buf.toString();
    }

    /**
     * 处理输出<br/>
     * 注意:流关闭需要自行处理
     *
     * @param out
     * @param data
     * @param len
     * @throws IOException
     */
    public static void doOutput(OutputStream out, byte[] data, int len)
            throws IOException {
        int dataLen = data.length;
        int off = 0;
        while (off < data.length) {
            if (len >= dataLen) {
                out.write(data, off, dataLen);
                off += dataLen;
            } else {
                out.write(data, off, len);
                off += len;
                dataLen -= len;
            }
            // 刷新缓冲区
            out.flush();
        }
    }

    /**
     * 获取SSLContext
     *
     * @param trustPasswd
     * @param keyPasswd
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    public static SSLContext getSSLContext(
            FileInputStream trustFileInputStream, String trustPasswd,
            FileInputStream keyFileInputStream, String keyPasswd)
            throws NoSuchAlgorithmException, KeyStoreException,
            CertificateException, IOException, UnrecoverableKeyException,
            KeyManagementException {
        // ca
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(SUN_X509);
        KeyStore trustKeyStore = KeyStore.getInstance(JKS);
        trustKeyStore.load(trustFileInputStream, str2CharArray(trustPasswd));
        tmf.init(trustKeyStore);

        final char[] kp = str2CharArray(keyPasswd);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(SUN_X509);
        KeyStore ks = KeyStore.getInstance(PKCS12);
        ks.load(keyFileInputStream, kp);
        kmf.init(ks, kp);

        SecureRandom rand = new SecureRandom();
        SSLContext ctx = SSLContext.getInstance(TLS);
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

        return ctx;
    }

    /**
     * 获取CA证书信息
     *
     * @param cafile CA证书文件
     * @return Certificate
     * @throws CertificateException
     * @throws IOException
     */
    public static java.security.cert.Certificate getCertificate(File cafile)
            throws CertificateException, IOException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(cafile);
        java.security.cert.Certificate cert = cf.generateCertificate(in);
        in.close();
        return cert;
    }

    /**
     * 字符串转换成char数组
     *
     * @param str
     * @return char[]
     */
    public static char[] str2CharArray(String str) {
        if (null == str) return null;
        return str.toCharArray();
    }

    /**
     * 存储ca证书成JKS格式
     *
     * @param cert
     * @param alias
     * @param password
     * @param out
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     */
    public static void storeCACert(java.security.cert.Certificate cert, String alias,
                                   String password, OutputStream out) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);
        ks.setCertificateEntry(alias, cert);
        // store keystore
        ks.store(out, str2CharArray(password));
    }

    public static InputStream String2InputStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    /**
     * InputStream转换成Byte
     * 注意:流关闭需要自行处理
     *
     * @param in
     * @return byte
     * @throws Exception
     */
    public static byte[] InputStreamTOByte(InputStream in) throws IOException {
        int BUFFER_SIZE = 4096;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        byte[] outByte = outStream.toByteArray();
        outStream.close();
        return outByte;
    }

    /**
     * InputStream转换成String
     * 注意:流关闭需要自行处理
     *
     * @param in
     * @param encoding 编码
     * @return String
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in, String encoding) throws IOException {
        return new String(InputStreamTOByte(in), encoding);
    }
}