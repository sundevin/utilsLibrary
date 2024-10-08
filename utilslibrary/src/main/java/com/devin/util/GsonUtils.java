package com.devin.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * <p>Description: 关于Gson解析的一些工具类  需要依赖Gson 如:'com.google.code.gson:gson:2.7'
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */
public class GsonUtils {

    private static final Gson gson = new Gson();

    /**
     * 通过Gson获取Object
     *
     * @param json json字符串
     * @param cls
     * @return 可能为null的Object
     */
    public static <T> T parseObject(String json, Class<T> cls) {
        T t = null;
        try {
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 通过Gson获取class集合
     *
     * @param jsonArray    json字符串
     * @param typeOfT 例如 new TypeToken<List<T>>() {}.getType()
     * @return 返回一个不为null的list集合，只需要做size是否为0的判断即可
     */
    public static <T> List<T> parseList(String jsonArray, Type typeOfT) {

        try {
            List<T> list = gson.fromJson(jsonArray, typeOfT);
            if (list != null) {
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    public static <T> List<T> parseList(String jsonArray, Class<T> clazz) {
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        return parseList(jsonArray,listType);
    }

    /**
     * 通过Gson获取String 集合
     *
     * @param jsonArray json字符串
     * @return 返回一个不为null的StrList集合，只需要做size是否为0的判断即可
     */
    public static List<String> parseStrList(String jsonArray) {
        return parseList(jsonArray, String.class);
    }

    /**
     * 通过Gson获取map的list集合
     *
     * @param jsonArray json字符串
     * @return 返回一个不为null的list集合，只需要做size是否为0的判断即可
     */
    public static List<HashMap<String, Object>> parseHashMapList(String jsonArray) {
        return parseList(jsonArray, new TypeToken<List<HashMap<String, Object>>>() {
        }.getType());
    }


    /**
     * 通过Gson获取map集合
     *
     * @param json json字符串
     * @return 返回一个不为null的map集合
     */
    public static HashMap<String, Object> parseHashMap(String json) {

        try {
            HashMap<String, Object> map = gson.fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
            if (map != null) {
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * 通过Gson获取map集合
     *
     * @param json json字符串
     * @return 返回一个不为null的map集合
     */
    public static TreeMap<String, Object> parseTreeMap(String json) {

        try {
            TreeMap<String, Object> map = gson.fromJson(json, new TypeToken<TreeMap<String, Object>>() {}.getType());
            if (map != null) {
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TreeMap<>();
    }


    /**
     * 序列化生成json
     *
     * @param object 可以是 class ，list. map等
     * @return 序列化后的json
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

}
