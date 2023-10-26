package com.devin.util;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.devin.UtilManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

/**
 * <p>Description: 关于File的一些工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */
public class FileUtils {

    /**
     * 获取系统存储路径
     *
     * @return 系统存储路径
     */
    public static File getRootDir() {
        return Environment.getRootDirectory();
    }

    //=======================================================================

    /**
     * @return 返回一个内置存储 data/data/包名/files/目录
     */
    public static File getInnerFileDir() {
        return UtilManager.getContext().getFilesDir();
    }

    /**
     * @return 内置存储 data/data/包名/cache/目录
     */
    public static File getInnerCacheDir() {
        return UtilManager.getContext().getCacheDir();
    }

    //=======================================================================

    /**
     * 查询设备是否有不可被移除的sd卡
     *
     * @return true 有，false 没有
     */
    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.isExternalStorageRemovable();
    }


    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return 如果有sd卡 则返回实际剩余容量，没有则返回 0
     */
    public static long getExternalStorageAvailableSize() {
        File file = getExternalStorageDir();
        if (file != null) {
            StatFs stat = new StatFs(file.getAbsolutePath());
            // 获取空闲的数据块的数量
            long availableBlocks = stat.getAvailableBlocksLong() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocksLong();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 得到SD的目录路径
     *
     * @return SD的目录路径
     */
    public static File getExternalStorageDir() {
        if (isExternalStorageAvailable()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
    }
    //=======================================================================

    /**
     * 获取根目录下的 Download 目录
     *
     * @return
     */
    public static File getExternalStorageDownloadDir() {
        return getExternalStorageDir(Environment.DIRECTORY_DOWNLOADS);
    }


    /**
     * 获取根目录下的 DCIM 目录
     *
     * @return
     */
    public static File getExternalStorageDCIMDir() {
        return getExternalStorageDir(Environment.DIRECTORY_DCIM);
    }

    /**
     * 获取根目录下的 Pictures 目录
     *
     * @return
     */
    public static File getExternalStoragePicturesDir() {
        return getExternalStorageDir(Environment.DIRECTORY_PICTURES);
    }

    public static File getExternalStorageDir(String type) {
        if (isExternalStorageAvailable()) {
            return Environment.getExternalStoragePublicDirectory(type);
        } else {
            return null;
        }
    }


    //=======================================================================


    /**
     * 得到SDCard/Android/data/应用的包名/files/ 目录
     *
     * @return SDCard/Android/data/应用的包名/files/目录 未获取到则返回null
     */
    public static File getAppFilesDir() {
        return getAppFilesDir(null);
    }

    public static File getAppPicturesDir() {
        return getAppFilesDir(Environment.DIRECTORY_PICTURES);
    }

    public static File getAppDownloadsDir() {
        return getAppFilesDir(Environment.DIRECTORY_DOWNLOADS);
    }


    public static File getAppFilesDir(String type) {
        if (isExternalStorageAvailable()) {
            return UtilManager.getContext().getApplicationContext().getExternalFilesDir(type);
        } else {
            return null;
        }
    }


    /**
     * 得到SDCard/Android/data/应用的包名/cache/ 目录
     *
     * @return SDCard/Android/data/应用的包名/cache/目录，未获取到则返回null
     */
    public static File getAppCacheDir() {
        if (isExternalStorageAvailable()) {
            return UtilManager.getContext().getExternalCacheDir();
        } else {
            return null;
        }
    }

    //=======================================================================


    /**
     * 文件是否存在
     *
     * @param filePath 文件路径
     * @return true 存在，false不存在
     */
    public static boolean fileIsExists(String filePath) {
        try {
            return new File(filePath).exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取文件大小，以字节为单位
     *
     * @param filePath
     * @return
     */
    public static long getFileLength(String filePath) {
        return getFileLength(new File(filePath));
    }

    /**
     * 获取文件大小，以字节为单位
     *
     * @param file
     * @return
     */
    public static long getFileLength(File file) {
        return file.length();
    }

    /**
     * 将文件大小转换为可读格式
     *
     * @param size
     * @return
     */
    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    //=======================================================================

    /**
     * 复制文件
     *
     * @param fromFile
     * @param toFile
     */
    public static boolean copyFile(File fromFile, File toFile) {

        try {
            FileInputStream ins = new FileInputStream(fromFile);
            FileOutputStream out = new FileOutputStream(toFile);
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = ins.read(b)) != -1) {
                out.write(b, 0, n);
            }

            ins.close();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 删除文件/文件夹。
     * 如果需要删除文件夹，需要注意耗时问题和递归问题
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {

        if (!FileUtils.fileIsExists(filePath)) {
            return;
        }

        File rootFile = new File(filePath);
        if (rootFile.isDirectory()) {
            File[] files = rootFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFile(file.getAbsolutePath());
                }
            }
        }
        rootFile.delete();
    }


    //=======================================================================

    //region 将字符串写入到文件中。

    /**
     * 将字符串写入到文件中。
     *
     * @param msg
     * @param file
     * @return
     */
    public static boolean writeStringToFile(String msg, String file) {
        boolean result = false;
        if (TextUtils.isEmpty(file)) {
            return result;
        }
        if (TextUtils.isEmpty(msg)) {
            return result;
        }
        byte[] buffer = msg.getBytes();
        FileOutputStream fos = null;
        File configFile = new File(file);
        File tempFile = new File(configFile.getAbsolutePath() + ".tmp");
        try {
            fos = new FileOutputStream(tempFile);
            fos.write(buffer);
            fos.flush();
            fos.close();
            tempFile.renameTo(configFile);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    fos = null;
                }
            }
        }
        return result;
    }
    //endregion

    //region 读取文件内容

    /**
     * 读取文件内容
     *
     * @param file
     * @return
     */
    public static String readFileContent(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[40960];
        FileInputStream fis = null;
        int len = 0;
        String result = null;
        try {
            fis = new FileInputStream(file);
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            fis.close();
            bos.close();
            fis = null;
            result = bos.toString();
            bos = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return result;
    }
    //endregion
}
