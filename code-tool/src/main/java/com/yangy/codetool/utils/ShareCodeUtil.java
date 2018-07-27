package com.yangy.codetool.utils;

import java.util.Random;

/**
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/7/11
 * @since 1.0.0
 */
public class ShareCodeUtil {

    /**
     * 自定义进制(不建议使用 1 0 z 2 l o i) 26+10 - 7
     * 22 个小写字母 + 7个阿拉伯数字 + 6 个大写字母 + 1个额外补全大写字母
     */
    private static final char[] CHARS = new char[]{
            'g', 'w', 'e', 'H', 's', 'h',
            'q', 'd', 'a', 'x', '9', 'c',
            '7', 'p', '5', 'k', '3', 'm',
            'j', 'u', 'f', 'r', '4', 'y',
            'S', 'N', 't', 'n', '6', 'b',
            'B', 'T', '8', 'Q', 't', 'Y'};

    /**
     * 该字符前面是计算出来的邀请码，后面是用来补全用的）
     */
    private static final char FLAG = 'G';

    /**
     * 补位字符串
     */
    private static final String ELSE = "atgsghj";

    /**
     * 进制长度
     */
    private static final int BIN_LEN = CHARS.length;

    /**
     * 邀请码长度
     */
    private static final int s = 8;

    /**
     * 根据ID生成随机码
     *
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / BIN_LEN) > 0) {
            int ind = (int) (id % BIN_LEN);
            buf[--charPos] = CHARS[ind];
            id /= BIN_LEN;
        }
        buf[--charPos] = CHARS[(int) (id % BIN_LEN)];
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            Random rnd = new Random();
            sb.append(FLAG);
            for (int i = 1; i < s - str.length(); i++) {
                sb.append(CHARS[rnd.nextInt(BIN_LEN)]);
            }
            str += sb.toString();
        }
        return str;
    }


    /**
     * 根据ID生成六位随机码
     * 注: 存在异常
     *
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCodeUnChange(long id) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / BIN_LEN) > 0) {
            int ind = (int) (id % BIN_LEN);
            buf[--charPos] = CHARS[ind];
            id /= BIN_LEN;
        }
        buf[--charPos] = CHARS[(int) (id % BIN_LEN)];
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            sb.append(ELSE.subSequence(0, s - str.length()));
            str += sb.toString();
        }
        return str;
    }


    /**
     * 根据随机码反解析ID
     *
     * @param code 随机码
     * @return ID
     */
    public static long codeToId(String code) {
        char chs[] = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < BIN_LEN; j++) {
                if (chs[i] == CHARS[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == FLAG) {
                break;
            }
            if (i > 0) {
                res = res * BIN_LEN + ind;
            } else {
                res = ind;
            }
        }
        return res;
    }


    public static void main(String[] args) {
        String s = toSerialCode(2);
        String s1 = toSerialCodeUnChange(2);
        long l = codeToId(s);
        long l1 = codeToId(s1);
        System.out.println(s);
        System.out.println(l);
        System.out.println(s1);
        System.out.println(l1);
    }

}