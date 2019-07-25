package com.devin.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

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
     * @param context
     * @param resId
     * @param imageView
     */
    public static void loadDrawableRes(Context context, @DrawableRes int resId, ImageView imageView) {

        RequestOptions requestOptions =
                new RequestOptions()
                        .placeholder(R.drawable.shape_default_range_bg)
                        .error(R.drawable.shape_default_range_bg);

        Glide.with(context)
                .load(resId)
                .apply(requestOptions)
                .into(imageView);
    }


    //======================================================================

    /**
     * 加载URI
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static void loadUri(Context context, Uri uri, ImageView imageView) {

        RequestOptions requestOptions =
                new RequestOptions()
                        .placeholder(R.drawable.shape_default_range_bg)
                        .error(R.drawable.shape_default_range_bg);

        Glide.with(context)
                .load(uri)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载URI
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static void loadUri(Context context, Uri uri, @DrawableRes int placeholder, ImageView imageView) {

        RequestOptions requestOptions =
                new RequestOptions()
                        .placeholder(placeholder)
                        .error(placeholder);

        Glide.with(context)
                .load(uri)
                .apply(requestOptions)
                .into(imageView);
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
        RequestOptions requestOptions =
                new RequestOptions()
                        .placeholder(placeholder)
                        .error(placeholder);
        Glide.with(context)
                .load(imgUrl)
                .apply(requestOptions)
                .into(imageView);
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
        RequestOptions requestOptions =
                new RequestOptions()
                        .placeholder(placeholder)
                        .error(placeholder)
                        .transform(new GlideRoundTransform(radiusDp));

        Glide.with(context)
                .load(imgUrl)
                .apply(requestOptions)
                .into(imageView);

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

        RequestOptions requestOptions =
                new RequestOptions()
                        .placeholder(placeholder)
                        .error(placeholder)
                        .transform(new GlideCircleTransform());

        Glide.with(context)
                .load(imgUrl)
                .apply(requestOptions)
                .into(imageView);
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
        loadBorderCircle(context,imgUrl,R.drawable.shape_default_circle_bg,borderDp,color,imageView);
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
    public static void loadBorderCircle(Context context, String imgUrl,@DrawableRes int placeholder, int borderDp, @ColorInt int color, ImageView imageView) {

        RequestOptions requestOptions =
                new RequestOptions()
                        .placeholder(placeholder)
                        .error(placeholder)
                        .transform(new GlideCircleTransform( borderDp, color));

        Glide.with(context)
                .load(imgUrl)
                .apply(requestOptions)
                .into(imageView);
    }
}
