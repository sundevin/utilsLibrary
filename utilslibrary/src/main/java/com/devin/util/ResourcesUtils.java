package com.devin.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;

import com.devin.UtilManager;

public class ResourcesUtils {

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


    /**
     * @param resId
     * @return
     */
    @ColorInt
    public static int getColor(@ColorRes int resId) {
        return getColor(resId, null);
    }

    /**
     * @param resId
     * @return
     */
    @ColorInt
    public static int getColor(@ColorRes int resId, Resources.Theme theme) {
        return ResourcesCompat.getColor(UtilManager.getContext().getResources(), resId, theme);
    }


    /**
     * @param resId
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        return getDrawable(resId, null);
    }

    /**
     * @param resId
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int resId, Resources.Theme theme) {
        return ResourcesCompat.getDrawable(UtilManager.getContext().getResources(), resId, theme);
    }


}
