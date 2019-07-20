package com.example.jingbin.cloudreader.app;

import android.support.multidex.MultiDexApplication;

import com.example.http.BuildConfig;
import com.example.http.HttpUtils;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by jingbin on 2016/11/22.
 */

public class App extends MultiDexApplication {

    private static App app;

    public static App getInstance() {
        return app;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        HttpUtils.getInstance().init(this);
        CrashReport.initCrashReport(getApplicationContext(), "3977b2d86f", BuildConfig.DEBUG);

    }

}
