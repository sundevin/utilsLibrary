package com.devin.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.devin.UtilManager;


/**
 * <p>Description: SharedPreferences的一个工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */
public class SharedPreUtils {


    /**
     * 默认的 SharedPreferences 文件名
     */
    private static final String DEFAULT_FILE_NAME = UtilManager.getContext().getPackageName();

    //========================================================================================================================

    public static SharedPreferences getSharedPreferences() {
        return getSharedPreferences(DEFAULT_FILE_NAME);
    }

    public static SharedPreferences getSharedPreferences(String fileName) {
        return UtilManager.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    //========================================================================================================================

    public static void putString(String key, String value) {
        putString(DEFAULT_FILE_NAME, key, value);
    }

    public static void putString(String fileName, String key, String value) {
        getSharedPreferences(fileName).edit().putString(key, value).apply();
    }


    public static void putInt(String key, int value) {
        putInt(DEFAULT_FILE_NAME, key, value);
    }

    public static void putInt(String fileName, String key, int value) {
        getSharedPreferences(fileName).edit().putInt(key, value).apply();
    }


    public static void putBoolean(String key, boolean value) {
        putBoolean(DEFAULT_FILE_NAME, key, value);
    }

    public static void putBoolean(String fileName, String key, boolean value) {
        getSharedPreferences(fileName).edit().putBoolean(key, value).apply();
    }


    public static void putLong(String key, long value) {
        putLong(DEFAULT_FILE_NAME, key, value);
    }

    public static void putLong(String fileName, String key, long value) {
        getSharedPreferences(fileName).edit().putLong(key, value).apply();
    }

    public static void putFloat(String key, float value) {
        putFloat(DEFAULT_FILE_NAME, key, value);
    }

    public static void putFloat(String fileName, String key, float value) {
        getSharedPreferences(fileName).edit().putFloat(key, value).apply();
    }

    //========================================================================================================================

    public static String getString(String key, String defValue) {
        return getString(DEFAULT_FILE_NAME, key, defValue);
    }

    public static String getString(String fileName, String key, String defValue) {
        return getSharedPreferences(fileName).getString(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return getInt(DEFAULT_FILE_NAME, key, defValue);
    }

    public static int getInt(String fileName, String key, int defValue) {
        return getSharedPreferences(fileName).getInt(key, defValue);
    }


    public static boolean getBoolean(String key, boolean defValue) {
        return getBoolean(DEFAULT_FILE_NAME, key, defValue);
    }

    public static boolean getBoolean(String fileName, String key, boolean defValue) {
        return getSharedPreferences(fileName).getBoolean(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return getLong(DEFAULT_FILE_NAME, key, defValue);
    }

    public static long getLong(String fileName, String key, long defValue) {
        return getSharedPreferences(fileName).getLong(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return getFloat(DEFAULT_FILE_NAME, key, defValue);
    }

    public static float getFloat(String fileName, String key, float defValue) {
        return getSharedPreferences(fileName).getFloat(key, defValue);
    }
    //========================================================================================================================

    public static void removeKey(String key) {
        removeKey(DEFAULT_FILE_NAME, key);
    }

    public static void removeKey(String fileName, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(fileName).edit();
        editor.remove(key);
        editor.apply();
    }

    //========================================================================================================================
    public static boolean containKey(String key) {
        return containKey(DEFAULT_FILE_NAME, key);
    }

    public static boolean containKey(String fileName, String key) {
        return getSharedPreferences(fileName).contains(key);
    }

    //========================================================================================================================


    public static void clear() {
        clear(DEFAULT_FILE_NAME);
    }


    public static void clear(String fileName) {
        SharedPreferences.Editor editor = getSharedPreferences(fileName).edit();
        editor.clear();
        editor.apply();
    }


}