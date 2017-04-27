package com.devin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 关于 FastJson 解析的一些工具类  需要依赖 FastJson， 如:'com.alibaba:fastjson:1.2.8'
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/27.
 */
public class FastJsonUtils {

    /**
     * fastJson 解析Class
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getObject(String json, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return t;
    }


    /**
     * 通过fastJson获取class集合  String 集合 可直接传String.class
     *
     * @param json
     * @param cls
     * @return
     */
    public static List getList(String json, Class cls) {
        List list;
        try {
            list = JSON.parseArray(json, cls);
            if (list != null) {
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }


    /**
     * 通过fastJson获取map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> getMap(String json) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 通过fastJson获取map的list集合
     *
     * @param json
     * @return 返回一个不为null的list集合，只需要做size是否为0的判断即可
     */
    public static List<Map<String, Object>> listKeyMap(String json) {

        List<Map<String, Object>> list;
        try {
            list = JSON.parseObject(json, new TypeReference<List<Map<String, Object>>>() {
            });
            if (list != null) {
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    /**
     * 通过对象得到json,可以是class ，list. map等。
     *
     * @param object
     * @return
     */
    public static String getJsonByObj(Object object) {

        //有良好格式的json文本
//        JSON.toJSONString(object, true);

        return JSON.toJSONString(object);

    }

}
