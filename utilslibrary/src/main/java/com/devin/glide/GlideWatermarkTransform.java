package com.devin.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by Devin Sun on 2016/11/7.
 */

public class GlideWatermarkTransform extends BitmapTransformation {

    private Bitmap watermarkBitmap;

    public GlideWatermarkTransform() {
        super();
    }

    public GlideWatermarkTransform(Context context, int resId) {
        super();
        this.watermarkBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public GlideWatermarkTransform(Bitmap watermarkBitmap) {
        super();
        this.watermarkBitmap = watermarkBitmap;
    }


    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        Bitmap newBm = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), toTransform.getConfig());
        Canvas canvas = new Canvas(newBm);

        //先比对toTransform画一张图
        canvas.drawBitmap(toTransform, new Matrix(), new Paint());

        Matrix matrix = new Matrix();
        //移动第二张空白画布到合适的位置
        matrix.setTranslate((toTransform.getWidth() - watermarkBitmap.getWidth()) / 2, (toTransform.getWidth() - watermarkBitmap.getWidth()) / 2);

        canvas.drawBitmap(watermarkBitmap, matrix, new Paint());

        return newBm;

    }


    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
