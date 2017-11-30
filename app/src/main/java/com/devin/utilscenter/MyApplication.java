package com.devin.utilscenter;

import android.app.Application;

import com.devin.UtilManager;

/**
 * <p>Description:
 * <p>Company:
 * <p>Email:bjxm2013@163.com
 * <p>@author:Created by Devin Sun on 2017/11/30.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UtilManager.init(this);
    }
}
