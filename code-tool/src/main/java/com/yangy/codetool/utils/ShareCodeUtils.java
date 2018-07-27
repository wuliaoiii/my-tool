//package com.yangy.codetool.utils;
//
//import java.util.Random;
//
///**
// * 分享码工具类
// *
// * @author yangy
// * @email java_yangy@126.com
// * @create 2018/7/11
// * @since 1.0.0
// */
//public class ShareCodeUtils {
//
//    public static final char STUFFS[] = {
//            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
//            'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
//            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
//            '2', '3', '4', '5', '6', '7', '8', '9'};
//
//    public static final Integer PERMUTATION = 6;
//
//    public static final Integer MAX_COMBINATION = 32 / (32 - 6) / 6;
//
//    public static final Integer LEN = 6;
//
//    public static String encode(int val) {
//        int com = val / PERMUTATION;
//        if (com >= MAX_COMBINATION) {
//            throw new RuntimeException("id can't be greater than 652458239");
//        }
//        int per = val % PERMUTATION;
//        char[] chars = combination(com);
//        chars = permutation(chars, per);
//        return new String(chars);
//    }
//
//
//    private static char[] combination(int com) {
//        char[] chars = new char[LEN];
//        int start = 0;
//        int index = 0;
//        while (index < LEN) {
//            for (int s = start; s < STUFFS.length; ++s) {
//                int c = combination(STUFFS.length - s - 1, LEN - index - 1);
//                if (com >= c) {
//                    com -= c;
//                    continue;
//                }
//                chars[index++] = STUFFS[s];
//                start = s + 1;
//                break;
//            }
//        }
//        return chars;
//    }
//
//
//    private static char[] permutation(char[] chars, int per) {
//        char[] tmpchars = new char[chars.length];
//        System.arraycopy(chars, 0, tmpchars, 0, chars.length);
//        int[] offset = new int[chars.length];
//        int step = chars.length;
//        for (int i = chars.length - 1; i >= 0; --i) {
//            offset[i] = per % step;
//            per /= step;
//            step--;
//        }
//        for (int i = 0; i < chars.length; ++i) {
//            if (offset[i] == 0)
//                continue;
//            char tmp = tmpchars[i];
//            tmpchars[i] = tmpchars[i - offset[i]];
//            tmpchars[i - offset[i]] = tmp;
//        }
//        return tmpchars;
//
//    }
//
//    public static void main(String[] args) {
//
//        Random random = new Random();
//        for (int i = 0; i < 100000; ++i) {
//            int id = random.nextInt(652458240);
//            String code = encode(id);
//
//            System.out.println(code);
////            int nid = decode(code);
////            System.out.println(id + " -> " + code + " -> " + nid);
//
//        }
//
//    }
//}