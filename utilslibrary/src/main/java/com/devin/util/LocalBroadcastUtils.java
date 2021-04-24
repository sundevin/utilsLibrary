package com.devin.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public class LocalBroadcastUtils {


    /**
     * 注册本地广播
     *
     * @param context
     * @param receiver
     * @param filter
     */
    public static void registerLocalReceiver(Context context, BroadcastReceiver receiver, IntentFilter filter) {
        LocalBroadcastManager mBroadcastManager = null;
        if (context != null) {
            mBroadcastManager = LocalBroadcastManager.getInstance(context);
        }
        if ((mBroadcastManager != null) && (receiver != null)) {
            if (filter != null) {
                mBroadcastManager.registerReceiver(receiver, filter);
            }
        }
    }

    /**
     * 停止注册
     *
     * @param context
     * @param receiver
     */
    public static void unRegisterLocalReceiver(Context context, BroadcastReceiver receiver) {
        LocalBroadcastManager mBroadcastManager = null;
        if (context != null) {
            mBroadcastManager = LocalBroadcastManager.getInstance(context);
        }
        if (mBroadcastManager != null && receiver != null) {
            mBroadcastManager.unregisterReceiver(receiver);
        }
    }

    /**
     * 发送本地广播。
     *
     * @param context
     * @param intent
     */
    public static void sendLocalBroadcast(Context context, Intent intent) {
        LocalBroadcastManager mBroadcastManager = null;
        if (context != null) {
            mBroadcastManager = LocalBroadcastManager.getInstance(context);
        }
        if (mBroadcastManager != null && intent != null) {
            mBroadcastManager.sendBroadcast(intent);
        }
    }

}
