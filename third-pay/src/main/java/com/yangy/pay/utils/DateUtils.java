package com.yangy.pay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/13
 * @since 1.0.0
 */
public class DateUtils {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MMDD_HHMMSS = "yyyyMMddHHmmss";

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static String getTimeFormat(Date date,String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

    /**
     *
     * @return
     */
    public static String getNowTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MMDD_HHMMSS);
        String timeStr = simpleDateFormat.format(new Date());
        return timeStr;
    }
    /**
     *
     * @return
     */
    public static String getTimeStamp(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MMDD_HHMMSS);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }
}