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
    private static String DEFAULT_FILE_NAME = UtilManager.getContext().getPackageName();


    /**
     * 存数据 默认存在以当前app包名命名的  SharedPreferences  文件里
     *
     * @param key    key
     * @param object value 目前支持 String， Integer，Boolean，Float，Long
     */
    public static void putParam(String key, Object object) {
        putParam(DEFAULT_FILE_NAME, key, object);
    }


    /**
     * 存数据
     *
     * @param fileName SharedPreferences 文件名
     * @param key      key
     * @param object   value 目前支持 String， Integer，Boolean，Float，Long
     */
    private static void putParam(String fileName, String key, Object object) {

        SharedPreferences sharedPreferences = UtilManager.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String valueType = object.getClass().getSimpleName();

        if (TextUtils.equals(valueType, String.class.getSimpleName())) {//string
            editor.putString(key, (String) object);
        } else if (TextUtils.equals(valueType, Integer.class.getSimpleName())) { //integer
            editor.putInt(key, (Integer) object);
        } else if (TextUtils.equals(valueType, Boolean.class.getSimpleName())) { // boolean
            editor.putBoolean(key, (Boolean) object);
        } else if (TextUtils.equals(valueType, Float.class.getSimpleName())) { // float
            editor.putFloat(key, (Float) object);
        } else if (TextUtils.equals(valueType, Long.class.getSimpleName())) { // long
            editor.putLong(key, (Long) object);
        }

        editor.apply();
    }


    /**
     * 取数据 默认取以当前app包名命名的  SharedPreferences  文件里数据
     *
     * @param key           key
     * @param defaultObject 默认值
     * @return 如果不是string、integer 、boolean、float和long类型 就返回null，否则就返回默认值
     */
    public static Object getParam(String key, Object defaultObject) {
        return getParam(DEFAULT_FILE_NAME, key, defaultObject);
    }


    /**
     * 取数据
     *
     * @param fileName      SharedPreferences 文件名
     * @param key           key
     * @param defaultObject 默认值
     * @return 如果不是string、integer 、boolean、float和long类型 就返回null，否则就返回默认值
     */
    public static Object getParam(String fileName, String key, Object defaultObject) {

        SharedPreferences sp = UtilManager.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String valueType = defaultObject.getClass().getSimpleName();

        if (TextUtils.equals(valueType, String.class.getSimpleName())) {//string
            return sp.getString(key, (String) defaultObject);
        } else if (TextUtils.equals(valueType, Integer.class.getSimpleName())) { //integer
            return sp.getInt(key, (Integer) defaultObject);
        } else if (TextUtils.equals(valueType, Boolean.class.getSimpleName())) { // boolean
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (TextUtils.equals(valueType, Float.class.getSimpleName())) { // float
            return sp.getFloat(key, (Float) defaultObject);
        } else if (TextUtils.equals(valueType, Long.class.getSimpleName())) { // long
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 根据key 删除value
     * 删除默认取以当前app包名命名的 SharedPreferences 文件里数据
     *
     * @param key
     */
    public static void deleteParam(String key) {//清除内容
        deleteParam(DEFAULT_FILE_NAME, key);
    }


    /**
     * 根据key 删除value
     *
     * @param fileName SharedPreferences 文件名
     * @param key      key
     */
    public static void deleteParam(String fileName, String key) {//清除内容

        SharedPreferences sharedPreferences = UtilManager.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}