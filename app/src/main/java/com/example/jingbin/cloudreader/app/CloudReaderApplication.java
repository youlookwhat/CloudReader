package com.example.jingbin.cloudreader.app;

import android.app.Application;

import com.example.jingbin.cloudreader.http.HttpUtils;

/**
 * Created by jingbin on 2016/11/22.
 */

public class CloudReaderApplication extends Application {

    private static CloudReaderApplication cloudReaderApplication;

    public static CloudReaderApplication getInstance() {
        return cloudReaderApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        cloudReaderApplication = this;
        HttpUtils.getInstance().setContext(getApplicationContext());
    }
}
