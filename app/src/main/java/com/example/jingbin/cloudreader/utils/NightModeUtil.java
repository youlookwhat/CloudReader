package com.example.jingbin.cloudreader.utils;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.jingbin.cloudreader.app.Constants;

/**
 * GitHub: https://github.com/youlookwhat
 * https://shenguojun.github.io/post/android-dark-mode/
 */
public class NightModeUtil {

    private boolean mSystemTheme = true;
    private boolean mDarkTheme = false;

    private static boolean isNightMode(Configuration config) {
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
        initNightMode(getSystemMode(), getNightMode());
    }

    public static void initNightMode(boolean systemMode, boolean nightMode) {
        if (systemMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
}
