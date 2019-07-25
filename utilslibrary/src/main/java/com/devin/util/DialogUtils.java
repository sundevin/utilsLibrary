package com.devin.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * <p>Description:
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/10/1.
 */

public class DialogUtils {

    public static AlertDialog createSingleChoiceDialog(Activity activity, String[] items, int which, DialogInterface.OnClickListener onClickListener) {
        return new AlertDialog.Builder(activity)
                .setSingleChoiceItems(items, which, onClickListener)
                .create();

    }

    public static AlertDialog createSingleChoiceDialog(Activity activity, String title, String[] items, int which, DialogInterface.OnClickListener onClickListener) {
        return new AlertDialog.Builder(activity)
                .setTitle(title)
                .setSingleChoiceItems(items, which, onClickListener)
                .create();
    }

    public static AlertDialog.Builder createBuilder(Activity activity) {
        return new AlertDialog.Builder(activity);
    }

    public static AlertDialog createDialog(Activity activity) {
        return createBuilder(activity).create();
    }

    public static AlertDialog createDialog(Activity activity, View view) {
        return createDialog(activity, false, view);
    }

    public static AlertDialog createDialog(Activity activity, boolean cancelable, View view) {
        return createBuilder(activity)
                .setCancelable(cancelable)
                .setView(view)
                .create();
    }

    public static AlertDialog createDialog(Activity activity, String msg,
                                           String positiveText, DialogInterface.OnClickListener onPositiveClickListener) {
        return createBuilder(activity)
                .setMessage(msg)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .create();
    }


    public static AlertDialog createDialog(Activity activity, String msg,
                                           String positiveText, DialogInterface.OnClickListener onPositiveClickListener,
                                           String negativeText, DialogInterface.OnClickListener onNegativeClickListener) {
        return createBuilder(activity)
                .setMessage(msg)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .create();
    }


    public static AlertDialog createDialog(Activity activity, String title, String msg, boolean cancelable,
                                           String positiveText, DialogInterface.OnClickListener onPositiveClickListener,
                                           String negativeText, DialogInterface.OnClickListener onNegativeClickListener) {
        return createBuilder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(cancelable)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .create();
    }

    public static AlertDialog createDialog(Activity activity, String[] items, DialogInterface.OnClickListener onClickListener) {
        return createBuilder(activity)
                .setItems(items, onClickListener)
                .create();
    }

}
