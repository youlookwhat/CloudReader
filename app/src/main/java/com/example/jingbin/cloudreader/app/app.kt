package com.example.jingbin.cloudreader.app

import com.example.jingbin.cloudreader.BuildConfig
import com.tencent.bugly.crashreport.CrashReport
import me.jingbin.bymvvm.base.RootApplication

/**
 * Created by jingbin on 2016/11/22.
 * Update  on 2020/12/02.
 */
open class App : RootApplication() {

    companion object {
        private lateinit var app: App

        // @JvmStatic 加上后可以直接 getInstance()，不然需要加上Companion
        @JvmStatic
        fun getInstance(): App {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
//        HttpUtils.getInstance().init(this);
        CrashReport.initCrashReport(applicationContext, "3977b2d86f", BuildConfig.DEBUG)
    }
}