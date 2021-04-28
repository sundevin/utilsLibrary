package com.devin.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Description: 汉字转拼音的一些工具类 需要依赖pinyin4j 如: 'com.belerweb:pinyin4j:2.5.0'
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/26.
 */
public class PinYinUtils {

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     *
     * @param inputString 中文
     * @return 中文转化为拼音 出错后返回"".
     */
    public static String chinese2PinYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        Pattern p = Pattern.compile("^[\u4E00-\u9FA5A-Za-z_]+$");
        Matcher matcher = p.matcher(inputString.substring(0, 1));
        if (matcher.find()) {
            char[] input = inputString.trim().toCharArray();
            StringBuilder output = new StringBuilder();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (java.lang.Character.toString(input[i]).matches(
                            "[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                                input[i], format);
                        output.append(temp[0]);
                    } else
                        output.append(input[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            return output.toString();
        } else {
            return "";
        }
    }

    /**
     * 获取中文字符串中每个汉字的首字母 例如 “安卓”返回"AZ"。
     *
     * @param inputString 字符串
     * @return 每个汉字的首字母组成的字符串
     */
    public static String getFirstLetterStr(String inputString) {
        StringBuilder sb = new StringBuilder();

        char[] nameChar = inputString.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(nameChar[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 获取汉字字符串转拼音后的的第一个字母 例如 “安卓”返回"a"。
     * @param inputString 字符串
     * @return 汉字字符串转拼音后的的第一个字
     */
    public static char getPinYinFirstLetter(String inputString) {

        char c = inputString.charAt(0);
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyinArray != null) {
            return pinyinArray[0].charAt(0);
        } else {
            return c;
        }
    }
}