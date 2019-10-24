package com.example.jingbin.cloudreader.app;

import android.support.multidex.MultiDexApplication;

import com.analysys.track.AnalysysTracker;
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

        CrashReport.initCrashReport(getApplicationContext(), "97876e6b1d", true);
        CrashReport.enableBugly(true);
        CrashReport.postCatchedException(new Exception());

        // 设置打开debug模式，上线请置为false
        AnalysysTracker.setDebugMode(this, false);
        // 初始化接口:第二个参数填写您在平台申请的appKey,第三个参数填写
        AnalysysTracker.init(this, "7752552892442721d", "YUNYUE");
    }

}
