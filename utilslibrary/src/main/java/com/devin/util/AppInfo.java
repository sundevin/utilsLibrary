package com.devin.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.devin.UtilManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <p>Description: 关于当前app信息的一些工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */
public class AppInfo {


    /**
     * 获取当前应用 的包名
     *
     * @return
     */
    public static String getPackageName() {
        return UtilManager.getContext().getPackageName();
    }


    /**
     * 获取当前应用的版本名称
     *
     * @return 失败时返回 null
     */
    public static String getVersionName() {

        try {
            return UtilManager.getContext().getPackageManager().getPackageInfo(
                    UtilManager.getContext().getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前应用的icon
     *
     * @return 失败时返回null
     */
    public static Drawable getAppIcon() {

        try {
            return UtilManager.getContext().getPackageManager().getPackageInfo(
                    UtilManager.getContext().getPackageName(), 0).applicationInfo.loadIcon(UtilManager.getContext().getPackageManager());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 得到当前应用的应用名
     *
     * @return 失败时返回null
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = UtilManager.getContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(UtilManager.getContext().getPackageName(), 0);
            return (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }


    /**
     * 获取当前应用的版本号
     *
     * @return 失败时返回-1
     */
    public static int getVersionCode() {

        try {
            return UtilManager.getContext().getPackageManager().getPackageInfo(
                    UtilManager.getContext().getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前app的权限列表
     *
     * @return
     */
    public static List<String> getPermissionsList() {
        List<String> permissionsList = new ArrayList<>();
        try {
            PackageInfo packageInfo = UtilManager.getContext().getPackageManager()
                    .getPackageInfo(UtilManager.getContext().getPackageName(),
                            PackageManager.GET_PERMISSIONS);
            String[] permissions = packageInfo.requestedPermissions;
            Collections.addAll(permissionsList, permissions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionsList;
    }


    /**
     * 获取当前app的友盟渠道,以umeng为例，key默认为UMENG_CHANNE
     *
     * @return 失败时为null
     */
    public static String getUmengChannel() {
        return getMetaData("UMENG_CHANNEL");
    }


    /**
     * 获取manifest里的meta-data 数据，可以用来获取渠道名称等
     *
     * @return 失败时会返回null
     */
    public static String getMetaData(String keyName) {

        try {
            ApplicationInfo packageInfo = UtilManager.getContext().getPackageManager()
                    .getApplicationInfo(UtilManager.getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            Object o = packageInfo.metaData.get(keyName);
            if (null != o) {
                return (String) o;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 判断app当前是否在前台运行
     *
     * @return true 在前台运行，false 不在前台运行。
     */
    public static boolean isRunningForeground() {
        ActivityManager am = (ActivityManager) UtilManager.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String packageName = cn.getPackageName();
        return TextUtils.equals(packageName, getPackageName());
    }


    /**
     * 获取当前进程名
     */
    public static String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) UtilManager.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                if (process.pid == pid) {
                    processName = process.processName;
                }
            }
        }
        return processName;
    }

    /**
     * 包名判断是否为主进程
     */
    public static boolean isMainProcess() {
        return UtilManager.getContext().getPackageName().equals(getCurrentProcessName());
    }
}


