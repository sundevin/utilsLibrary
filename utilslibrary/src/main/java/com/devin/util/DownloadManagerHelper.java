package com.devin.util;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.Nullable;

public class DownloadManagerHelper {

    public long startDownload(Context context, String url, String filename, boolean isPublicDownloadDir) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(filename);
        request.setDescription("文件下载中");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        if (isPublicDownloadDir) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
        } else {
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, filename);
        }

        // 获取DownloadManager实例并提交下载请求
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return downloadManager.enqueue(request);
    }


    @Nullable
    public DownloadStatus queryDownloadStatus(Context context, long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId); // 设置要查询的下载任务ID
        Cursor cursor = downloadManager.query(query);

        DownloadStatus downloadStatus = null;

        if (cursor != null && cursor.moveToFirst()) {

            /*
             *          mStatusMap.put(DownloadManager.STATUS_PENDING, "挂起");
             *         mStatusMap.put(DownloadManager.STATUS_RUNNING, "运行中");
             *         mStatusMap.put(DownloadManager.STATUS_PAUSED, "暂停");
             *         mStatusMap.put(DownloadManager.STATUS_SUCCESSFUL, "成功");
             *         mStatusMap.put(DownloadManager.STATUS_FAILED, "失败");
             */
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)); // 获取下载状态
            long currSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)); // 获取已下载的字节数
            long totalSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)); // 获取总字节数
            String mediaType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE)); // 获取媒体类型
            String fileUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)); // 获取文件路径
            downloadStatus = new DownloadStatus();
            downloadStatus.downloadId = downloadId;
            downloadStatus.status = status;
            downloadStatus.currSize = currSize;
            downloadStatus.totalSize = totalSize;
            downloadStatus.mediaType = mediaType;
            downloadStatus.fileUri = fileUri;
        }
        if (cursor != null) {
            cursor.close();
        }
        return downloadStatus;
    }

    static class DownloadStatus {
        long downloadId;
        int status;
        long currSize;
        long totalSize;
        String mediaType;
        String fileUri;
    }

}
