package com.devin.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.devin.UtilManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 关于设备信息的一些工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */
public class DeviceInfo {

    /**
     * 得到屏幕的宽 适用安卓3.0以上
     *
     * @return 屏幕的宽  px单位
     */
    public static int getScreenWidth() {

        // 得到手机窗体管理者
        WindowManager wm = (WindowManager) UtilManager.getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        // 3.0才有的API
        Point outSize = new Point();
        wm.getDefaultDisplay().getSize(outSize);
        return outSize.x;

    }

    /**
     * 得到屏幕的高 适用安卓3.0以上
     *
     * @return 屏幕的高 px单位
     */
    public static int getScreenHeight() {

        // 得到手机窗体管理者
        WindowManager wm = (WindowManager) UtilManager.getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        // 3.0才有的API
        Point outSize = new Point();
        wm.getDefaultDisplay().getSize(outSize);
        return outSize.y;
    }


    /**
     * 设备屏幕是否是点亮的
     * retrue 如果为true，则表示屏幕“亮”了，否则屏幕“暗”了
     */
    public static boolean isScreenOn() {

        PowerManager pm = (PowerManager) UtilManager.getContext().getSystemService(Context.POWER_SERVICE);
        //如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        return pm.isScreenOn();
    }

    /**
     * 获取手机imei码
     *
     * @return 手机imei码
     */
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) UtilManager.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取手机的mac地址
     *
     * @return 手机的mac地址  Android N 上获取的可能是错误的
     */
    public static String getMacAddress() {
        WifiManager wm = (WifiManager) UtilManager.getContext().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }

    /**
     * 获取设备里所有软件的包名
     *
     * @return
     */
    public static List<String> getPackageNames() {
        List<String> packageNameList = new ArrayList<>();
        // 得到包管理器
        PackageManager manager = UtilManager.getContext().getPackageManager();
        // 所有安装在系统上的应用的程序包信息
        List<PackageInfo> packageInfos = manager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {

//            String appName = (String) packageInfo.applicationInfo
//                    .loadLabel(manager);// 得到应用名
            //得到包名
            packageNameList.add(packageInfo.packageName);
        }
        return packageNameList;
    }


    /**
     * 获取设备里所有软件
     *
     * @return
     */
    public static List<PackageInfo> getAllPackageInfo() {
        PackageManager manager = UtilManager.getContext().getPackageManager();
        // 所有安装在系统上的应用的程序包信息
        return manager.getInstalledPackages(0);
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp(final String packageName) {
        try {
            PackageManager pm = UtilManager.getContext().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取设备硬件的基本信息 可用于打印设备信息
     *
     * @return 获取设备基本信息
     */
    public static String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("主板：").append(Build.BOARD);
        sb.append("\n系统启动程序版本号：").append(Build.BOOTLOADER);
        sb.append("\n系统定制商：").append(Build.BRAND);
        sb.append("\ncpu指令集：").append(Build.CPU_ABI);
        sb.append("\ncpu指令集2").append(Build.CPU_ABI2);
        sb.append("\n设置参数：").append(Build.DEVICE);
        sb.append("\n显示屏参数：").append(Build.DISPLAY);
        sb.append("\n无线电固件版本：").append(Build.getRadioVersion());
        sb.append("\n硬件识别码：").append(Build.FINGERPRINT);
        sb.append("\n硬件名称：").append(Build.HARDWARE);
        sb.append("\nHOST:").append(Build.HOST);
        sb.append("\n修订版本列表：").append(Build.ID);
        sb.append("\n硬件制造商：").append(Build.MANUFACTURER);
        sb.append("\n版本：").append(Build.MODEL);
        sb.append("\n硬件序列号：").append(Build.SERIAL);
        sb.append("\n手机制造商：").append(Build.PRODUCT);
        sb.append("\n描述Build的标签：").append(Build.TAGS);
        sb.append("\nTIME:").append(Build.TIME);
        sb.append("\nbuilder类型：").append(Build.TYPE);
        sb.append("\nUSER:").append(Build.USER);
        return sb.toString();
    }

    /**
     * 获取内部存储器的size
     *
     * @return 内部存储器的大小 单位 字节
     */
    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockSize();
        return totalBlocks * blockSize;
    }

    /**
     * 获取手机 系统定制商
     *
     * @return 系统定制商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取系统启动程序版本号
     *
     * @return
     */
    public static String getDeviceBootLoader() {
        return Build.BOOTLOADER;
    }

    /**
     * 获取设备型号
     *
     * @return 设备型号 例如 小米的 MI NOTE
     */
    public static String getDeviceModel() {
        return Build.MODEL; // 设备型号
    }

    /**
     * 获取系统版本号 如19 ，20 等
     *
     * @return 系统版本号
     */
    public static int getSysVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统版本号 4.0 ，4.4等
     *
     * @return 系统版本号
     */
    public static String getSysVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Android ID 通常被认为不可信，
     * 因为它有时为null。
     * 开发文档中说明了：这个ID会改变如果进行了出厂设置。
     * 并且，如果某个Andorid手机被Root过的话，这个ID也可以被任意改变。
     *
     * @return
     */
    public static String getAndroidID() {
        return Settings.Secure.getString(UtilManager.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * 获取系统状态栏的高度
     *
     * @param activity Activity
     * @return 如果获取失败 这返回0
     */
    public static int getStatusHeight(Activity activity) {

        int statusHeight;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;

        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = activity.getResources()
                        .getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }


    /**
     * 获取NavigationBar的高度：
     *
     * @return int
     */
    public static int getNavigationBarHeight() {
        int navigationBarHeight = 0;
        if (hasNavigationBar()) {
            Resources rs = UtilManager.getContext().getResources();
            int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
            if (id > 0) {
                navigationBarHeight = rs.getDimensionPixelSize(id);
            }
        }
        return navigationBarHeight;
    }


    /**
     * 判断屏幕下方是否有虚拟按键
     *
     * @return bool
     */
    public static boolean hasNavigationBar() {

        boolean hasNavigationBar = false;
        Resources rs = UtilManager.getContext().getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            return "0".equals(navBarOverride);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }


    /**
     * 获取cpu型号，
     *
     * @return 获取cpu型号，获取失败后则会为null
     */
    public static String getCpuName() {

        String cpuName;

        //第一种方法
        String[] abiArr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abiArr = Build.SUPPORTED_ABIS;
        } else {
            abiArr = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }

        StringBuilder abiStr = new StringBuilder();
        for (String abi : abiArr) {
            abiStr.append(abi);
            abiStr.append(',');
        }
        cpuName = abiStr.toString();
        if (!TextUtils.isEmpty(cpuName)) {
            return cpuName;
        }

        //第二种方法
        try {
            FileReader e = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(e);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            e.close();
            br.close();
            return array[1];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取设备的全部RAM 信息
     * http://blog.csdn.net/aminfo/article/details/7603302
     */
    public static void getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                Log.i("---", "---" + str2);//要获取android手机总内存大小，只需读取”/proc/meminfo”文件的第1行，并进行简单的字符串处理即可。
            }
        } catch (IOException e) {
        }
    }

    /**
     * 获取设备的可用内存
     *
     * @return
     */
    public long getAvailMemory() {
        ActivityManager am = (ActivityManager) UtilManager.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }


    /**
     * 检测设备是否支持相机
     *
     * @return true 支持，false 不支持
     */
    public static boolean isSupportCameraHardware() {
        return UtilManager.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


    /**
     * 是否支持前置摄像头
     *
     * @return true 支持，false 不支持
     */
    public static boolean isSupportFrontCamera() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            return false;
        }
        int numberOfCameras = android.hardware.Camera.getNumberOfCameras();
        return numberOfCameras >= 2;
    }


    /**
     * 判断是否支持闪光灯
     *
     * @return true 支持，false 不支持
     */
    public static boolean isSupportCameraLedFlash() {
        PackageManager pm = UtilManager.getContext().getPackageManager();
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    //判断设备是否支持闪光灯
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 检查是否支持蓝牙
     *
     * @return true 支持，false 不支持
     */
    private boolean isSupportBlu() {
        return UtilManager.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
    }

    /**
     * 检查是否支持蓝牙4.0
     *
     * @return true 支持，false 不支持
     */
    private boolean isSupportBluFour() {
        return UtilManager.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }


    /**
     * 获取是否root
     *
     * @return
     */
    public static boolean isDeviceRooted() {
        boolean bool = false;
        try {
            //检查系统目录：在Android设备上，已root的设备会具有特定的系统目录，
            // 如/system/bin/su或/system/xbin/su
            File suFile = new File("/system/bin/su");
            File suFileX = new File("/system/xbin/su");
            bool = suFile.exists() || suFileX.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!bool) {
            try {
                //另一种方法是执行一个Shell命令来检查设备是否已root。
                // 可以使用Runtime.getRuntime().exec()方法来执行Shell命令
                Process process = Runtime.getRuntime().exec("su");
                int exitValue = process.waitFor();
                bool = exitValue == 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bool;
    }



}
