package com.devin.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 * <p>Description: 关于Activity的一些工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */
public class ActivityUtils {


    /**
     * 设置屏幕透明度
     * 可用于弹出popupWindow时使用，某些机型上可能无效
     * 0.0不透明 ~ 1.0透明
     */
    public static void setActivityBgAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity Activity
     * @return 屏幕截图
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = DeviceInfo.getScreenWidth();
        int height = DeviceInfo.getScreenHeight();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity Activity
     * @return 屏幕截图
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = DeviceInfo.getScreenWidth();
        int height = DeviceInfo.getScreenHeight();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * View 的截图
     *
     * @param view view
     * @return View 的截图
     */
    public static Bitmap snapShotView(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap tBitmap = view.getDrawingCache();
        if (tBitmap != null) {
            // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
            tBitmap = Bitmap.createBitmap(tBitmap);
        }
        view.setDrawingCacheEnabled(false);
        return tBitmap;
    }


    /**
     * 设置状态栏颜色,达到沉浸式状态栏的效果。仅支持21及以上
     *
     * @param activity
     * @param color    颜色
     */
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
    }

    /***
     * 设置状态栏透明 仅支持19及以上
     * @param activity
     */
    public static void setTranslucentStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 隐藏状态栏
     *
     * @param activity
     */
    public static void hideStatusBar(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    /**
     * 显示状态栏
     *
     * @param activity
     */
    public static void showStatusBar(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 应用运行时，保持屏幕高亮，不锁屏
     *
     * @param activity
     */
    public static void keepScreenOn(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);
    }


    /**
     * Activity 禁止截屏
     *
     * @param activity
     */
    public static void prohibitionScreenshot(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_SECURE);
    }


}
