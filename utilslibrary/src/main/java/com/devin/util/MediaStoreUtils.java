package com.devin.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MediaStoreUtils {

    /**
     * 图片
     */
    public static final String IMAGE_JPEG = "image/jpeg";
    /**
     * 视频
     */
    public static final String VIDEO_MP4 = "video/mp4";
    /**
     * 音频
     */
    public static final String AUDIO_MPEG = "audio/mpeg";

    /**
     * apk 文件
     */
    public static final String APK_TYPE = "application/vnd.android.package-archive";
    /**
     * text 文件
     */
    public static final String TXT_TYPE = "text/plain";


    /**
     * 使用MediaStore将内部媒体资源保存到公共目录
     *
     * @param context
     * @param file
     * @param mimeType 媒体类型 MIME_TYPE  {IMAGE_JPEG、VIDEO_MP4、AUDIO_MPEG，APK_TYPE，TXT_TYPE}
     * @return
     */
    public static boolean saveMediaFile(Context context, File file, String mimeType) {
        if (file == null) {
            return false;
        }
        Uri fileUri = insertFileIntoMediaStore(context, file, mimeType);
        return saveFileToUri(context, file, fileUri);
    }

    //创建视频或图片的URI
    private static Uri insertFileIntoMediaStore(Context context, File file, String mimeType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.DATE_TAKEN, file.lastModified());
        }

        Uri uri = null;
        try {
            if (IMAGE_JPEG.equals(mimeType)) {
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else if (VIDEO_MP4.equals(mimeType)) {
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MOVIES);
                uri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else if (AUDIO_MPEG.equals(mimeType)) {
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MUSIC);
                uri = context.getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else {
                contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }


    public static boolean saveFileToUri(Context context, File file, Uri uri) {
        if (uri == null) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();

        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "w");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (parcelFileDescriptor == null) {
            return false;
        }
        boolean saveResult = true;
        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            outputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            inputStream = new FileInputStream(file);
            copy(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            saveResult = false;
        } catch (IOException e) {
            e.printStackTrace();
            saveResult = false;
        } finally {
            close(outputStream);
            close(inputStream);
        }
        return saveResult;
    }


    private static void close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //注意当文件比较大时该方法耗时会比较大
    private static void copy(InputStream ist, OutputStream ost) throws IOException {
        byte[] buffer = new byte[4096];
        int byteCount;
        while ((byteCount = ist.read(buffer)) != -1) {
            ost.write(buffer, 0, byteCount);
        }
        ost.flush();
    }

}
