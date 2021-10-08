package com.example.jingbin.cloudreader.utils;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.jingbin.cloudreader.app.Constants;

/**
 * GitHub: https://github.com/youlookwhat
 */
public class NightModeUtil {

    public static boolean isNightMode(Configuration config) {
        int uiMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return uiMode == Configuration.UI_MODE_NIGHT_YES;
    }

    public static boolean isNightMode(Context context) {
        return isNightMode(context.getResources().getConfiguration());
    }

    public static boolean getSystemMode() {
        return SPUtils.getBoolean(Constants.KEY_MODE_SYSTEM, true);
    }

    public static void setSystemMode(boolean nightMode) {
        SPUtils.putBoolean(Constants.KEY_MODE_SYSTEM, nightMode);
    }

    public static boolean getNightMode() {
        return SPUtils.getBoolean(Constants.KEY_MODE_NIGHT, false);
    }

    public static void setNightMode(boolean nightMode) {
        SPUtils.putBoolean(Constants.KEY_MODE_NIGHT, nightMode);
    }

    public static void initNightMode() {
        if (getSystemMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            if (getNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
}
