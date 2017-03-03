package com.example.jingbin.cloudreader.app;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;

import skin.support.SkinCompatManager;
import skin.support.design.SkinMaterialManager;

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
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        // 皮肤
        SkinMaterialManager.init(this);
        SkinCompatManager.init(this).loadSkin();

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
