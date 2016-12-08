package com.example.jingbin.yunyue.app;

import android.app.Application;

import com.example.jingbin.yunyue.http.HttpUtils;

/**
 * Created by jingbin on 2016/11/22.
 */

public class YunyueApplication extends Application {

    private static YunyueApplication yunyueApplication;

    public static YunyueApplication getInstance() {
        if (yunyueApplication == null) {
            synchronized (YunyueApplication.class) {
                if (yunyueApplication != null) {
                    yunyueApplication = new YunyueApplication();
                }
            }
        }
        return yunyueApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        yunyueApplication = this;
        HttpUtils.getInstance().setContext(getApplicationContext());
    }
}
