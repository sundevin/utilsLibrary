package com.devin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p>Description: 关于时间的一些格式化
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */

public class DateUtils {

    /**
     * @param millisecond 毫秒
     * @return 返回 yy/MM/dd HH:mm
     */
    public static String getYMDHM_1(long millisecond) {
        return getFormatDate("yy/MM/dd HH:mm", millisecond);
    }

    /**
     * @param millisecond 毫秒
     * @return 返回 yy-MM-dd HH:mm
     */
    public static String getYMDHM_2(long millisecond) {
        return getFormatDate("yy-MM-dd HH:mm", millisecond);
    }


    /**
     * @param millisecond 毫秒
     * @return 返回 yy.MM.dd HH:mm
     */
    public static String getYMDHM_3(long millisecond) {
        return getFormatDate("yy.MM.dd HH:mm", millisecond);
    }


    /**
     * 格式化后的日期时间
     *
     * @param pattern
     * @param millisecond 毫秒
     * @return 返回格式化后的日期时间
     */
    public static String getFormatDate(String pattern, long millisecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(new Date(millisecond));
    }


    private static final long ONE_MINUTE_MILLISECOND = 60 * 1000;
    private static final long ONE_HOUR_MILLISECOND = 60 * 60 * 1000;
    private static final long ONE_DAY_MILLISECOND = 24 * 60 * 60 * 1000;
    private static final long ONE_MONTH_MILLISECOND = 24 * 60 * 60 * 1000;

    /**
     * 获取友好时间  "刚刚","xx分钟前","xx小时前","xx天前","yyyy-MM-dd"
     *
     * @param millisecond 毫秒
     * @return
     */
    public static String getFriendlyTime(long millisecond) {
        long ago = System.currentTimeMillis() - millisecond;

        if (ago < ONE_MINUTE_MILLISECOND) {
            return "刚刚";
        } else if (ago < ONE_HOUR_MILLISECOND) {
            return (int) (ago / ONE_MINUTE_MILLISECOND) + "分钟前";
        } else if (ago < ONE_DAY_MILLISECOND) {
            return (int) (ago / ONE_HOUR_MILLISECOND) + "小时前";
        } else if (ago < ONE_MONTH_MILLISECOND) {
            return (int) (ago / ONE_DAY_MILLISECOND) + "天前";
        } else {
            return getFormatDate("yyyy-MM-dd", millisecond);
        }
    }



    /**
     * 将时间字符串转为时间戳
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 毫秒时间戳
     */
    public static long str2Millis(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return Date类型
     */
    public static Date str2Date(String time, String pattern) {
        return new Date(str2Millis(time, pattern));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param date    Date类型时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String date2Str(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }



}
