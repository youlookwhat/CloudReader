package me.jingbin.bymvvm.base;


import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import me.jingbin.bymvvm.http.HttpUtils;


public class RootApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        HttpUtils.getInstance().init(this);
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 方法数超64k 解决 https://developer.android.com/studio/build/multidex?hl=zh-cn
     * 继承 MultiDexApplication 或 实现此方法。
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initWebView();
        MultiDex.install(this);
    }

    /**
     * Android P针对 WebView在不同进程下无法访问非自己进程中的webview目录
     * fix Using WebView from more than one process at once with the same data directory is not supported
     */
    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName();
            String packageName = this.getPackageName();
            if (!packageName.equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }
}
