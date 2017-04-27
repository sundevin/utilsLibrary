package com.devin.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.devin.UtilManager;

/**
 * <p>Description: 关于剪切板的一些工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/25.
 */
public class ClipboardUtils {

    /**
     * 复制文本到剪切板
     *
     * @param text 文本
     */
    public static void copy2Clipboard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) UtilManager.getContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    /**
     * 从剪切板获取文本
     *
     * @return   切板获取文本 ，未获取到时返回""
     */
    public static String getTextFromClipboar() {
        ClipboardManager clipboardManager = (ClipboardManager) UtilManager.getContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            return clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
        }
        return "";
    }
}
