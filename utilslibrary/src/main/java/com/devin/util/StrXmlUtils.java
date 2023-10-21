package com.devin.util;


import androidx.annotation.StringRes;

import com.devin.UtilManager;

/**
 * <p>Description: 关于string.xml 转 String 的工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/26.
 */
public class StrXmlUtils {

    /**
     * @param resId
     * @return
     */
    public static String getString(@StringRes int resId) {
        return UtilManager.getContext().getResources().getString(resId);
    }

    /**
     * @param resId
     * @param formatAr
     * @return
     */
    public static String getString(@StringRes int resId, Object... formatAr) {
        return UtilManager.getContext().getResources().getString(resId, formatAr);
    }

}
