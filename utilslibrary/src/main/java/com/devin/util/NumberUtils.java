package com.devin.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtils {


    /**
     * 四舍五入保留0位
     * @param number
     * @return
     */
    public static String toZeroDotDouble(double number) {
        return formatNumber(number, 0, true);
    }

    /**
     * 四舍五入保留0位
     * @param number
     * @return
     */
    public static String toZeroDotDouble(String number) {
        return formatNumber(Double.parseDouble(number), 0, true);
    }



    /**
     * 四舍五入保留1位
     * @param number
     * @return
     */
    public static String toOneDotDouble(double number) {
        return formatNumber(number, 1, true);
    }

    /**
     * 四舍五入保留1位
     * @param number
     * @return
     */
    public static String toOneDotDouble(String number) {
        return formatNumber(Double.parseDouble(number), 1, true);
    }


    /**
     * 四舍五入保留2位
     * @param number
     * @return
     */
    public static String toTwoDotDouble(double number) {
        return formatNumber(number, 2, true);
    }

    /**
     * 四舍五入保留2位
     * @param number
     * @return
     */
    public static String toTwoDotDouble(String number) {
        return formatNumber(Double.parseDouble(number), 2, true);
    }


    /**
     *
     * @param number
     * @param decimalPlaces 小数位数
     * @param round 是否四舍五入
     * @return
     */
    public static String formatNumber(double number, int decimalPlaces, boolean round) {
        return formatNumber(number, decimalPlaces, round ? RoundingMode.HALF_UP : RoundingMode.DOWN);
    }

    /**
     *
     * @param number
     * @param decimalPlaces 小数位数
     * @param roundingMode 舍入模式
     * @return
     */
    public static String formatNumber(double number, int decimalPlaces, RoundingMode roundingMode) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("decimalPlaces参数不能小于0");
        }

        StringBuilder pattern = new StringBuilder("#");
        if (decimalPlaces > 0) {
            pattern.append(".");
            for (int i = 0; i < decimalPlaces; i++) {
                pattern.append("0");
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());
        decimalFormat.setRoundingMode(roundingMode);
        return decimalFormat.format(number);
    }

}
