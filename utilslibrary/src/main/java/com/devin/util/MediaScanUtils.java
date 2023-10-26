package com.devin.util;

import android.content.Intent;
import android.net.Uri;

import com.devin.UtilManager;

import java.io.File;

public class MediaScanUtils {

    public static void scanFileBeforeAPI29(String filePath) {
        scanFileBeforeAPI29(new File(filePath));
    }

    public static void scanFileBeforeAPI29(File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            UtilManager.getContext().sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
