package com.yangy.pay.utils.tenpay;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * @ClassName: XMLUtils
 * @Description: xml工具类
 */
public class XMLUtils {

    private static final Logger log = LoggerFactory.getLogger(XMLUtils.class);

    /**
     * map转小毛驴
     *
     * @param map
     * @return
     */
    public static String mapToXmlStr(SortedMap<String, String> map) {
        if (null == map) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            sb.append("<").append(k).append(">").append(v).append("</").append(k).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 解析xml, 返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param xmlStr
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> xmlStrToMap(String xmlStr) {
        if (StringUtils.isBlank(xmlStr)) {
            return null;
        }
        xmlStr = xmlStr.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        Map<String, String> m = new HashMap<>();
        InputStream in = null;
        Document doc = null;
        try {
            in = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            doc = builder.build(in);
        } catch (JDOMException | IOException e) {
            log.error("exception{}", e);
        } finally {
            //关闭流
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("exception{}", e);
                }
            }
        }
        if (null == doc) {
            return null;
        }
        Element root = doc.getRootElement();
        List list = root.getChildren();
        for (Object obj : list) {
            Element e = (Element) obj;
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        return m;
    }

    /**
     * 获取子结点的xml
     * @param children
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            for (Object obj : children) {
                Element e = (Element) obj;
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<").append(name).append(">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</").append(name).append(">");
            }
        }
        return sb.toString();
    }

    /**
     * 创建微信支付返回值
     * @param code
     * @param msg
     * @return
     */
    public static String createTenpayReturnXml(String code, String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml><return_code><![CDATA[").
                append(code).append("]]></return_code><return_msg><![CDATA[").
                append(msg).append("]]></return_msg></xml>");
        return sb.toString();
    }

    /**
     * 获取xml编码字符集
     * @param xmlStr
     * @return
     */
    public static String getXMLEncoding(String xmlStr) {
        InputStream in = HttpsClientUtils.String2InputStream(xmlStr);
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(in);
        } catch (JDOMException | IOException e) {
            log.error("exception{}", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error("exception{}", e);
            }
        }
        if (null == doc) {
            return null;
        }
        return (String) doc.getProperty("encoding");
    }
}
