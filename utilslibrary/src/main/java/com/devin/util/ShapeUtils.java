package com.devin.util;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;

/**
 * <p>Description:
 * <p>Company:
 * <p>Email:bjxm2013@163.com
 * <p>@author:Created by Devin Sun on 2017/12/1.
 */

public class ShapeUtils {


    /**
     * 创建 简单的 shape
     *
     * @param option
     * @return
     */
    public static GradientDrawable createShapeDrawable(ShapeOption option) {
        GradientDrawable gradientDrawable = new GradientDrawable(option.getOrientation(), option.getColors());
        gradientDrawable.setColor(option.getSolidColor());
        gradientDrawable.setCornerRadius(option.getCornersRadius());
        gradientDrawable.setStroke(option.getStrokeWidth(), option.getStrokeColor());
        return gradientDrawable;
    }


    public static class ShapeOption {

        /**
         * 上下文
         */
        private Context context;

        /**
         * 背景
         */
        private int solidColor;


        /**
         * 圆角 px
         */
        private float cornersRadius;
        /**
         * 边框粗细 px
         */
        private int strokeWidth;
        /**
         * 边框颜色
         */
        private int strokeColor;

        /**
         * 渐变色方向
         */
        private GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TOP_BOTTOM;

        /**
         * 渐变需要的颜色
         */
        private int[] colors;


        public ShapeOption(Context context) {
            this.context = context;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public int getSolidColor() {
            return solidColor;
        }

        public void setSolidColor(@ColorInt int solidColor) {
            this.solidColor = solidColor;
        }

        public float getCornersRadius() {
            return cornersRadius;
        }

        public void setCornersRadius(float cornersRadius) {
            this.cornersRadius = cornersRadius;
        }

        public int getStrokeWidth() {
            return strokeWidth;
        }

        public void setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
        }

        public int getStrokeColor() {
            return strokeColor;
        }

        public void setStrokeColor(@ColorInt int strokeColor) {
            this.strokeColor = strokeColor;
        }

        public GradientDrawable.Orientation getOrientation() {
            return orientation;
        }

        public void setOrientation(GradientDrawable.Orientation orientation) {
            this.orientation = orientation;
        }

        public int[] getColors() {
            return colors;
        }

        public void setColors(@ColorInt int[] colors) {
            this.colors = colors;
        }
    }
}
