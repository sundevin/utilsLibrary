package com.devin.util;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.devin.UtilManager;

import static android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;

/**
 * <p>Description: 关于一些系统设置跳转的工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/26.
 */

public class SystemIntent {

    /**
     * 打开网络设置界面
     */
    public static void startWifiSetting() {
        //也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UtilManager.getContext().startActivity(intent);
    }

    /**
     * 打开系统浏览器
     *
     * @param url 链接
     */
    public static void startBrowser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UtilManager.getContext().startActivity(intent);
    }

    /**
     * 打开gps设置
     */
    public static void startGPSSetting() {
        // 转到手机设置界面，用户设置GPS
        Intent intent = new Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UtilManager.getContext().startActivity(intent);
    }

    /**
     * 打开蓝牙设置
     */
    public static void startBleSetting() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UtilManager.getContext().startActivity(intent);
    }

    /**
     * / 显示NFC设置。这显示了用户界面,允许NFC打开或关闭
     * 【API 16及以上】
     */
    public static void startNFCSetting() {
        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UtilManager.getContext().startActivity(intent);
    }


    /**
     * 自动拨打电话
     *
     * @param phoneNumber 手机号码
     */
    public static void startCallPhone(String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);//直接拨打
//        intent.setAction(Intent.ACTION_DIAL);//手动拨打
        intent.setData(uri);
        UtilManager.getContext().startActivity(intent);
    }


    /**
     * 手动拨打电话
     *
     * @param phoneNumber 手机号码
     */
    public static void startDialPhone(String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_CALL);//直接拨打
        intent.setAction(Intent.ACTION_DIAL);//手动拨打
        intent.setData(uri);
        UtilManager.getContext().startActivity(intent);
    }


    /**
     * 根据包名跳转到系统自带的应用程序信息界面
     *
     * @param packageName 应用包名
     */
    public static void startAppInfo(String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        UtilManager.getContext().startActivity(intent);
    }

    /**
     * 打开应用市场
     *
     * @param packageName 应用包名
     */
    public static void startAppMarket(String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        UtilManager.getContext().startActivity(intent);
    }


}
