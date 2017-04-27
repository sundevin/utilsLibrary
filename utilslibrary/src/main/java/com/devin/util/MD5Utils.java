package com.devin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Description: 关于 MD5 的工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/26.
 */
public class MD5Utils {

    /**
     * 生成md5
     * @param str
     * @return 返回md5 错误时返回 "".
     */
    public static String getMD5(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte[] m = md5.digest();
            return getString(m);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 32位 小写
     *
     * @param hash
     * @return
     */
    private static String getString(byte[] hash) {
        StringBuffer sb = new StringBuffer();
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                sb.append("0");
            sb.append(Integer.toHexString(b & 0xFF));
        }
        return sb.toString();
    }

}
