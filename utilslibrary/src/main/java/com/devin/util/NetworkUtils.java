package com.devin.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.devin.UtilManager;

/**
 * <p>Description: 关于设备网络的工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/26.
 */

public class NetworkUtils {

    /**
     * 未知网络
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;

    /**
     * 没有网络
     */
    public static final int NETWORK_TYPE_INVALID = 0;
    /**
     * wap网络
     */
    public static final int NETWORK_TYPE_WAP = 1;
    /**
     * 2G网络
     */
    public static final int NETWORK_TYPE_2G = 2;

    /**
     * 3G网络
     */
    public static final int NETWORK_TYPE_3G = 3;

    /**
     * 4G网络
     */
    public static final int NETWORK_TYPE_4G = 4;

    /**
     * wifi网络
     */
    public static final int NETWORK_TYPE_WIFI = 10;

    /**
     * 检查是否有网络
     *
     * @return true 有网，false 无网
     */
    public static boolean isAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) UtilManager.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();

        return activeNetworkInfo != null;
    }

    /**
     * 判断是否是wifi连接
     *
     * @return true wifi,false 非wifi
     */
    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) UtilManager.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();

    }


    /**
     * 获取网络状态，wifi,wap,2g,3g,4g.
     *
     * @return int 网络状态 {@link #NETWORK_TYPE_2G},{@link #NETWORK_TYPE_3G},{@link #NETWORK_TYPE_4G}
     * {@link #NETWORK_TYPE_INVALID},{@link #NETWORK_TYPE_WAP},
     * {@link #NETWORK_TYPE_WIFI}
     */
    public static int getNetworkType() {
        int strNetworkType = NETWORK_TYPE_INVALID;

        NetworkInfo networkInfo = ((ConnectivityManager) UtilManager.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = NETWORK_TYPE_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = NETWORK_TYPE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = NETWORK_TYPE_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = NETWORK_TYPE_4G;
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = NETWORK_TYPE_3G;
                        } else {
                            strNetworkType = NETWORK_TYPE_UNKNOWN;
                        }

                        break;
                }

            }
        }


        return strNetworkType;
    }


    /**
     * 判断是否是快速网络，将3G或者3G以上的网络称为快速网络
     *
     * @return true  快速网络,false 非快速网络
     */
    public static boolean isFastNetwork() {

        TelephonyManager telephonyManager = (TelephonyManager) UtilManager.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
}
