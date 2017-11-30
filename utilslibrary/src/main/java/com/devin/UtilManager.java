package com.devin;

import android.content.Context;

/**
 * <p>Description: 工具类初始化
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */

public class UtilManager {

    private static Context mContext;

    /**
     * 初始化工具类集合
     * @param context  context
     */
    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
