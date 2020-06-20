package com.example.jingbin.cloudreader.app;

import com.example.jingbin.cloudreader.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

import me.jingbin.bymvvm.base.RootApplication;

/**
 * Created by jingbin on 2016/11/22.
 */

public class App extends RootApplication {

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
//        HttpUtils.getInstance().init(this);
        CrashReport.initCrashReport(getApplicationContext(), "3977b2d86f", BuildConfig.DEBUG);

    }

}
