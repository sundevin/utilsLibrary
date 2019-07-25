package com.devin.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.devin.UtilManager;

/**
 * <p>Description: 软键盘工具类
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/4/26.
 */
public class SoftKeyBoardUtils {

    /**
     * 打开软键盘
     *
     * @param view 为接受软键盘输入的视图
     */
    public static void openKeybord(View view) {
        InputMethodManager imm = (InputMethodManager) UtilManager.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param activity activity
     */
    public static void closeKeyBord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) UtilManager.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**
     * 监听软键盘的显示与隐藏
     *
     * @param activity                     activity
     * @param onSoftKeyBoardChangeListener onSoftKeyBoardChangeListener
     */
    public static void setSoftKeyBoardeListener(Activity activity, final OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {

        //获取activity的根视图
        final View rootView = activity.getWindow().getDecorView();

        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            int rootViewVisibleHeight;

            @Override
            public void onGlobalLayout() {
                //获取当前根视图在屏幕上显示的大小
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();

                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过200，可以看作软键盘显示了
                if (rootViewVisibleHeight - visibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度变大超过200，可以看作软键盘隐藏了
                if (visibleHeight - rootViewVisibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                }
            }
        });

    }

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);

        void keyBoardHide(int height);
    }

}
