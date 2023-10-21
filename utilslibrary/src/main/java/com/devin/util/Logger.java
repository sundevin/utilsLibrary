package com.devin.util;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description:关于日志的的一些工具类 长文本可以分为多条打印，并可定位log位置
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */
public class Logger {

    private static boolean logEnabled = true;
    private static String DEFAULT_TAG = "----------";

    private static final int DEBUG = 1;
    private static final int WARN = 2;
    private static final int ERROR = 3;

    @IntDef({DEBUG, WARN, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    private @interface LogPriority {
    }


    private static final int MSG_MAX_LENGTH = 3072;


    /**
     * 设置 defaultTag，全局生效
     *
     * @param defaultTag
     */
    public static void setDefaultTag(String defaultTag) {
        DEFAULT_TAG = defaultTag;
    }

    /**
     * 设置是否开启日志打印 全局生效
     *
     * @param logEnabled
     */
    public static void setLogEnabled(boolean logEnabled) {
        Logger.logEnabled = logEnabled;
    }


    //====================================================

    /**
     * 默认 tag "----------"
     *
     * @param msg msg
     */
    public static void d(String msg) {
        d(DEFAULT_TAG, msg);
    }

    /**
     * @param tag tag
     * @param msg msg
     */
    public static void d(String tag, String... msg) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : msg) {
            stringBuilder.append(s);
        }

        d(tag, stringBuilder.toString());
    }

    /**
     * @param tag tag
     * @param msg msg
     */
    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }


    public static void d(String tag, String msg, Throwable tr) {
        printLog(tag, msg, tr, DEBUG);
    }

    //====================================================
    /**
     * 默认 tag "----------"
     *
     * @param msg msg
     */
    public static void w(String msg) {
        w(DEFAULT_TAG, msg);
    }

    /**
     * @param tag tag
     * @param msg msg
     */
    public static void w(String tag, String... msg) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : msg) {
            stringBuilder.append(s);
        }

        w(tag, stringBuilder.toString());
    }

    /**
     * @param tag tag
     * @param msg msg
     */
    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(Throwable tr) {
        w("", tr);
    }


    public static void w(String msg, Throwable tr) {
        w(DEFAULT_TAG, msg, tr);
    }


    public static void w(String tag, String msg, Throwable tr) {
        printLog(tag, msg, tr, WARN);
    }


     //====================================================


    public static void e(String msg) {
        e(DEFAULT_TAG, msg);
    }

    /**
     * @param tag tag
     * @param msg msg
     */
    public static void e(String tag, String... msg) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : msg) {
            stringBuilder.append(s);
        }

        e(tag, stringBuilder.toString());
    }


    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }


    public static void e(Throwable tr) {
        e("", tr);
    }


    public static void e(String msg, Throwable tr) {
        e(DEFAULT_TAG, msg, tr);
    }


    public static void e(String tag, String msg, Throwable tr) {
        printLog(tag, msg, tr, ERROR);
    }

    /**
     * 打印最终的日志
     *
     * @param tag         tag
     * @param msg         msg
     * @param tr          Throwable
     * @param logPriority logPriority
     */
    private static void printLog(String tag, String msg, Throwable tr, @LogPriority int logPriority) {
        if (!logEnabled) {
            return;
        }
        if (logPriority == DEBUG) {
            Log.d(tag, "________________LOG START__________________");
        } else if (logPriority == WARN) {
            Log.w(tag, "________________LOG START__________________");
        } else if (logPriority == ERROR) {
            Log.e(tag, "________________LOG START__________________");
        }
        printLogLocation(tag);

        for (String str : splitLargeMsg(msg)) {
            if (logPriority == DEBUG) {
                Log.d(tag, str);
            } else if (logPriority == WARN) {
                Log.w(tag, str);
            } else if (logPriority == ERROR) {
                Log.e(tag, str);
            }
        }

        if (tr != null) {
            if (logPriority == DEBUG) {
                Log.d(tag, "", tr);
            } else if (logPriority == WARN) {
                Log.w(tag, "", tr);
            } else if (logPriority == ERROR) {
                Log.e(tag, "", tr);
            }
        }

        if (logPriority == DEBUG) {
            Log.d(tag, "================LOG END====================");
        } else if (logPriority == WARN) {
            Log.w(tag, "================LOG END====================");
        } else if (logPriority == ERROR) {
            Log.e(tag, "================LOG END====================");
        }
    }


    /**
     * 将比较长的msg 分割为 string list
     *
     * @param msg msg
     * @return string list
     */
    private static List<String> splitLargeMsg(String msg) {
        List<String> stringList = new ArrayList<>();
        int msgLength = msg.length();
        int beginIndex = 0;
        while (beginIndex < msgLength) {
            int endIndex = Math.min(msgLength, beginIndex + MSG_MAX_LENGTH);
            stringList.add(msg.substring(beginIndex, endIndex));
            beginIndex = endIndex;
        }
        return stringList;
    }


    /**
     * 打印log 的调用地址
     *
     * @param tag tag
     */
    private static void printLogLocation(String tag) {
        StackTraceElement stackTraceElement = getTargetStackTraceElement();
        Log.e(tag, "log location →(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")");
    }


    /**
     * 因为我们的入口是Logger的方法，所以，我们直接遍历，Logger类相关的下一个非Logger类的栈帧信息就是具体调用的方法。
     *
     * @return StackTraceElement
     */
    private static StackTraceElement getTargetStackTraceElement() {
        // find the target invoked method
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            boolean isLogMethod = stackTraceElement.getClassName().equals(Logger.class.getName());
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }
}
