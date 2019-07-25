//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.devin.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LangUtils {

    public static Context setLanguage(Context context, Locale locale) {

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            return context.createConfigurationContext(config);
        } else {
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            context.getResources().updateConfiguration(config, displayMetrics);
            return context;
        }
    }


}
