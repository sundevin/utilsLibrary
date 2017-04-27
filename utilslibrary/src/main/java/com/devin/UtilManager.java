package com.devin;

import android.app.Application;
import android.content.Context;

/**
 * <p>Description: 工具类初始化
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */

public class UtilManager {

    private static Context context;

    /**
     * 初始化工具类集合
     * @param application  Application
     */
    public static void init(Application application) {
        context = application;
    }

    public static Context getContext() {
        return context;
    }
}
