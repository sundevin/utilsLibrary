package com.devin.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
    public static String add(String... numbers) {
        BigDecimal result = BigDecimal.ZERO;
        for (String number : numbers) {
            BigDecimal decimal = new BigDecimal(number);
            result = result.add(decimal);
        }
        return result.toString();
    }

    public static String subtract(String... numbers) {
        BigDecimal result = new BigDecimal(numbers[0]);
        for (int i = 1; i < numbers.length; i++) {
            BigDecimal decimal = new BigDecimal(numbers[i]);
            result = result.subtract(decimal);
        }
        return result.toString();
    }

    public static String multiply(String... numbers) {
        BigDecimal result = new BigDecimal(numbers[0]);
        for (int i = 1; i < numbers.length; i++) {
            BigDecimal decimal = new BigDecimal(numbers[i]);
            result = result.multiply(decimal);
        }

        return result.toString();
    }

    public static String divide(int scale, RoundingMode roundingMode, String dividend, String... divisors) {
        BigDecimal result = new BigDecimal(dividend);
        for (String divisor : divisors) {
            BigDecimal decimal = new BigDecimal(divisor);
            result = result.divide(decimal, scale, roundingMode);
        }
        return result.toString();
    }

}
