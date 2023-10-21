package com.devin.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class ShareUtils {


    /**
     * 分享文本
     *
     * @param context
     * @param text
     */
    public static void shareText(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        if (!(context instanceof Activity)) {
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(shareIntent);
    }

    /**
     * 分享图片
     *
     * @param context
     * @param imgPath
     */
    public static void shareImg(Context context, String imgPath) {
        shareImg(context, Uri.parse(imgPath));
    }

    /**
     * 分享图片
     *
     * @param context
     * @param uri
     */
    public static void shareImg(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("image/jpeg");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        if (!(context instanceof Activity)) {
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(shareIntent);
    }

    /**
     * 分享多张图片
     *
     * @param context
     * @param imagePaths
     */
    public static void shareMultipleImg(Context context, List<String> imagePaths) {
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (String imagePath : imagePaths) {
            imageUris.add(Uri.parse(imagePath));
        }
        shareMultipleImg(context, imageUris);
    }

    /**
     * 分享多张图片
     *
     * @param context
     * @param imageUris
     */
    public static void shareMultipleImg(Context context, ArrayList<Uri> imageUris) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        sendIntent.setType("image/*");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        if (!(context instanceof Activity)) {
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(shareIntent);
    }


}
