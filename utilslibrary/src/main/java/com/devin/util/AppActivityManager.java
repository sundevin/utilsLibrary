package com.devin.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.devin.UtilManager;

import java.util.Stack;

/**
 * <p>Description:  Activity 栈管理，一般在base里使用
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */
public class AppActivityManager {

    private static Stack<Activity> activityStack;
    private static AppActivityManager instance;

    private AppActivityManager() {
        activityStack = new Stack<>();
    }

    /**
     * 单一实例
     */
    public static AppActivityManager getInstance() {
        if (instance == null) {
            synchronized (AppActivityManager.class) {
                if (instance == null) {
                    instance = new AppActivityManager();
                }
            }
        }
        return instance;
    }


    /**
     * 获取栈里activity的个数
     *
     * @return
     */
    public int getActivityCount() {
        return activityStack.size();
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    /**
     * 移除Activity栈
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 获取堆栈中最后一个压入Activity
     *
     * @return
     */
    public Activity getLastActivity() {
        return activityStack.empty() ? null : activityStack.lastElement();
    }

    /**
     * 结束堆栈中最后一个压入Activity
     */
    public void finishLastActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }


    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<? extends Activity> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }


    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) UtilManager.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(UtilManager.getContext().getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束所有Activity
     */
    private void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            activityStack.get(i).finish();
        }
        activityStack.clear();
    }


    /**
     * 结束指定的Activity
     * @param activity
     */
    private void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }
}
