package com.example.jingbin.cloudreader.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.jingbin.cloudreader.app.App;
import com.example.jingbin.cloudreader.app.Constants;

/**
 * @author jingbin
 * GitHub: https://github.com/youlookwhat
 */
public class NightModeUtil {

    /**
     * 当前系统是否是深色模式
     */
    public static boolean isNightMode(Context context) {
        int uiMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return uiMode == Configuration.UI_MODE_NIGHT_YES;
    }

    /**
     * 获取是否跟随系统，默认true
     */
    public static boolean getSystemMode() {
        return SPUtils.getBoolean(Constants.KEY_MODE_SYSTEM, true);
    }

    public static void setSystemMode(boolean nightMode) {
        SPUtils.putBoolean(Constants.KEY_MODE_SYSTEM, nightMode);
    }

    /**
     * 获取是否设置深色模式，默认false
     */
    public static boolean getNightMode() {
        return SPUtils.getBoolean(Constants.KEY_MODE_NIGHT, false);
    }

    public static void setNightMode(boolean nightMode) {
        SPUtils.putBoolean(Constants.KEY_MODE_NIGHT, nightMode);
    }

    public static void initNightMode() {
        initNightMode(getSystemMode(), getNightMode());
    }

    /**
     * 初始化App深色模式
     *
     * @param systemMode 是否是跟随系统
     * @param nightMode  是否是深色模式
     */
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

    /**
     * 重启App
     */
    public static void restartApp(Activity activity) {
        final Intent intent = App.getInstance().getPackageManager().getLaunchIntentForPackage(App.getInstance().getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
