package me.jingbin.bymvvm.base;


import android.content.Context;

import androidx.multidex.MultiDexApplication;

import me.jingbin.bymvvm.http.HttpUtils;


public class RootApplication extends MultiDexApplication {

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
}
