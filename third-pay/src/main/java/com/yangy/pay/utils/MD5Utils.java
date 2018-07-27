package com.yangy.pay.utils.tenpay;

import java.security.MessageDigest;

public class MD5Utils {

    public static String MD5Encode(String origin, String charsetName) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName)) {
                origin = byteArrayToHexString(md.digest(origin
                        .getBytes()));
            } else
                origin = byteArrayToHexString(md.digest(origin
                        .getBytes(charsetName)));
        } catch (Exception exception) {
        }
        return origin;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
