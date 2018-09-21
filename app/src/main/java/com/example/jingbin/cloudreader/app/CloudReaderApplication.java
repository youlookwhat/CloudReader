package com.example.jingbin.cloudreader.app;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by jingbin on 2016/11/22.
 */

public class CloudReaderApplication extends MultiDexApplication{

    private static CloudReaderApplication cloudReaderApplication;

    public static CloudReaderApplication getInstance() {
        return cloudReaderApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        cloudReaderApplication = this;
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        CrashReport.initCrashReport(getApplicationContext(), "3977b2d86f", DebugUtil.DEBUG);

        initTextSize();
    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

}
