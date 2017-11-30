package com.devin.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Devin Sun on 2016/11/7.
 */

public class GlideWatermarkTransform extends BitmapTransformation {

    private Bitmap watermarkBitmap;

    public GlideWatermarkTransform(Context context, int resId) {
        super(context);
        this.watermarkBitmap= BitmapFactory.decodeResource(context.getResources(),resId);
    }

    public GlideWatermarkTransform(Context context,Bitmap watermarkBitmap) {
        super(context);
        this.watermarkBitmap=watermarkBitmap;
    }


    public GlideWatermarkTransform(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        if (toTransform == null) return null;

        Bitmap newBm=Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), toTransform.getConfig());
        Canvas canvas =new Canvas(newBm);

        //先比对toTransform画一张图
        canvas.drawBitmap(toTransform, new Matrix(), new Paint());

        Matrix matrix= new Matrix();
        //移动第二张空白画布到合适的位置
        matrix.setTranslate((toTransform.getWidth()-watermarkBitmap.getWidth())/2, (toTransform.getWidth()-watermarkBitmap.getWidth())/2);

        canvas.drawBitmap(watermarkBitmap, matrix, new Paint());

        return newBm;

    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
