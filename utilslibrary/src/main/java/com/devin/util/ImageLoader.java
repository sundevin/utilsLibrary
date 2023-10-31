package com.devin.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devin.UtilManager;
import com.devin.glide.GlideCircleTransform;
import com.devin.glide.GlideRoundTransform;

/**
 * <p>Description:
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/9/30.
 */
public class ImageLoader {

    /**
     * 加载资源图
     *
     * @param resId
     * @param imageView
     */
    public static void loadDrawableRes(@DrawableRes int resId, ImageView imageView) {
        loadDrawableRes(UtilManager.getContext(), resId, imageView);
    }


    /**
     * 加载资源图
     *
     * @param context
     * @param resId
     * @param imageView
     */
    public static void loadDrawableRes(Context context, @DrawableRes int resId, ImageView imageView) {
        loadDrawableRes(context, resId, R.drawable.shape_default_range_bg, imageView);
    }


    /**
     * 加载资源图
     *
     * @param context
     * @param resId
     * @param imageView
     */
    public static void loadDrawableRes(Context context, @DrawableRes int resId, @DrawableRes int placeholder, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder);
        loadModel(context, resId, requestOptions, imageView);
    }


    //======================================================================


    /**
     * 加载URI
     *
     * @param uri
     * @param imageView
     */
    public static void loadUri(Uri uri, ImageView imageView) {
        loadUri(UtilManager.getContext(), uri, imageView);
    }

    /**
     * 加载URI
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static void loadUri(Context context, Uri uri, ImageView imageView) {
        loadUri(context, uri, R.drawable.shape_default_range_bg, imageView);
    }

    /**
     * 加载URI
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static void loadUri(Context context, Uri uri, @DrawableRes int placeholder, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder);
        loadModel(context, uri, requestOptions, imageView);
    }


    //======================================================================

    /**
     * 加载图片
     *
     * @param imgUrl
     * @param imageView
     */
    public static void load(String imgUrl, ImageView imageView) {
        load(UtilManager.getContext(), imgUrl, imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void load(Context context, String imgUrl, ImageView imageView) {
        load(context, imgUrl, R.drawable.shape_default_range_bg, imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imgUrl
     * @param placeholder 展位图和错误图
     * @param imageView
     */
    public static void load(Context context, String imgUrl, @DrawableRes int placeholder, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder);
        loadModel(context, imgUrl, requestOptions, imageView);
    }

    public static void load(Context context, Object model,@DrawableRes int placeholder, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder);
        loadModel(context, model, requestOptions, imageView);
    }

    //======================================================================


    /**
     * 加载圆角图
     *
     * @param imgUrl
     * @param imageView
     * @param radiusDp
     */
    public static void loadRound(String imgUrl, ImageView imageView, int radiusDp) {
        loadRound(UtilManager.getContext(), imgUrl, imageView, radiusDp);
    }


    /**
     * 加载圆角图
     *
     * @param context
     * @param imgUrl
     * @param imageView
     * @param radiusDp
     */
    public static void loadRound(Context context, String imgUrl, ImageView imageView, int radiusDp) {
        loadRound(context, imgUrl, R.drawable.shape_default_round_bg, imageView, radiusDp);
    }

    /**
     * 加载圆角图
     *
     * @param context
     * @param imgUrl
     * @param placeholder
     * @param imageView
     * @param radiusDp
     */
    public static void loadRound(Context context, String imgUrl, @DrawableRes int placeholder, ImageView imageView, int radiusDp) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder).transform(new GlideRoundTransform(radiusDp));
        loadModel(context, imgUrl, requestOptions, imageView);
    }


    public static void loadRound(Context context, Object model,@DrawableRes int placeholder, ImageView imageView, int radiusDp) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder).transform(new GlideRoundTransform(radiusDp));
        loadModel(context, model, requestOptions, imageView);
    }

    //======================================================================

    /**
     * 加载圆图
     *
     * @param imgUrl
     * @param imageView
     */
    public static void loadCircle(String imgUrl, ImageView imageView) {
        loadCircle(UtilManager.getContext(), imgUrl, imageView);
    }

    /**
     * 加载圆图
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadCircle(Context context, String imgUrl, ImageView imageView) {
        loadCircle(context, imgUrl, R.drawable.shape_default_circle_bg, imageView);
    }

    /**
     * 加载圆图
     *
     * @param context
     * @param imgUrl
     * @param placeholder
     * @param imageView
     */
    public static void loadCircle(Context context, String imgUrl, @DrawableRes int placeholder, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder).transform(new GlideCircleTransform());
        loadModel(context, imgUrl, requestOptions, imageView);
    }

    public static void loadCircle(Context context, Object model, @DrawableRes int placeholder, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder).transform(new GlideCircleTransform());
        loadModel(context, model, requestOptions, imageView);
    }

    //======================================================================


    /**
     * 加载带描边的圆图
     *
     * @param imgUrl
     * @param borderDp
     * @param color
     * @param imageView
     */
    public static void loadBorderCircle(String imgUrl, int borderDp, @ColorInt int color, ImageView imageView) {
        loadBorderCircle(UtilManager.getContext(), imgUrl, borderDp, color, imageView);
    }

    /**
     * 加载带描边的圆图
     *
     * @param context
     * @param imgUrl
     * @param borderDp
     * @param color
     * @param imageView
     */
    public static void loadBorderCircle(Context context, String imgUrl, int borderDp, @ColorInt int color, ImageView imageView) {
        loadBorderCircle(context, imgUrl, R.drawable.shape_default_circle_bg, borderDp, color, imageView);
    }

    /**
     * 加载带描边的圆图
     *
     * @param context
     * @param imgUrl
     * @param borderDp
     * @param color
     * @param imageView
     */
    public static void loadBorderCircle(Context context, String imgUrl, @DrawableRes int placeholder, int borderDp, @ColorInt int color, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder).transform(new GlideCircleTransform(borderDp, color));
        loadModel(context,imgUrl,requestOptions,imageView);
    }

    /**
     * 加载带描边的圆图
     *
     * @param context
     * @param model
     * @param borderDp
     * @param color
     * @param imageView
     */
    public static void loadBorderCircle(Context context, Object model, @DrawableRes int placeholder, int borderDp, @ColorInt int color, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(placeholder).error(placeholder).transform(new GlideCircleTransform(borderDp, color));
        loadModel(context,model,requestOptions,imageView);
    }


    //======================================================================
    public static void loadModel(Object model, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.shape_default_range_bg).error(R.drawable.shape_default_range_bg);
        loadModel(UtilManager.getContext(), model, requestOptions, imageView);
    }

    public static void loadModel(Context context, Object model, RequestOptions options, ImageView imageView) {
        if (options != null) {
            Glide.with(context).load(model).apply(options).into(imageView);
        } else {
            Glide.with(context).load(model).into(imageView);
        }
    }

}
