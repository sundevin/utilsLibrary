package com.devin.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.devin.UtilManager;

import static android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * <p>Description: 关于一些系统设置跳转的工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/26.
 */

public class SystemIntent {


    /**
     * 跳转到桌面
     */
    public static void startHomeActivity() {
        try {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UtilManager.getContext().startActivity(homeIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开网络设置界面
     */
    public static void startWifiSetting() {
        //也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开系统浏览器
     *
     * @param url 链接
     */
    public static void startBrowser(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开gps设置
     */
    public static void startGPSSetting() {
        // 转到手机设置界面，用户设置GPS
        try {
            Intent intent = new Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开蓝牙设置
     */
    public static void startBleSetting() {
        try {
            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * / 显示NFC设置。这显示了用户界面,允许NFC打开或关闭
     * 【API 16及以上】
     */
    public static void startNFCSetting() {
        try {
            Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送短信
     * @param phone 手机号码
     * @param message 短信内容
     */
    public static void startSendMessage(String phone, String message) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("smsto:" + phone));
            intent.putExtra("sms_body", message);
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自动拨打电话
     *
     * @param phone 手机号码
     */
    public static void startCallPhone(String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + phone));
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 手动拨打电话
     *
     * @param phone 手机号码
     */
    public static void startDialPhone(String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + phone));
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据包名跳转到系统自带的应用程序信息界面
     *
     * @param packageName 应用包名
     */
    public static void startAppInfo(String packageName) {
        try {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent intent = new Intent(ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开应用市场
     *
     * @param packageName 应用包名
     */
    public static void startAppMarket(String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UtilManager.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void installApp(String aplFile) {
        File file = new File(aplFile);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String authorities = UtilManager.getContext().getPackageName() + ".FileProvider";
                uri = FileProvider.getUriForFile(UtilManager.getContext(), authorities, file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(file);
            }

            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            UtilManager.getContext().startActivity(intent);
        }
    }


}
