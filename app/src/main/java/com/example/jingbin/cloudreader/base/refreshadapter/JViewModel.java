package com.example.jingbin.cloudreader.base.refreshadapter;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * @author jingbin
 * @data 2019/1/19
 * @description
 */

public class JViewModel extends AndroidViewModel {

    private int loadOffset = initLoadOffset();

    public JViewModel(@NonNull Application application) {
        super(application);
    }

    public void onDestroy() {

    }

    public void onListRefresh() {
        loadOffset = initLoadOffset();
    }

    public void onListLoad(int offset) {
    }

    public int getLoadOffset() {
        return ++loadOffset;
    }

    /**
     * 超时时间(单位：s)
     */
    public int timeout() {
        return 20;
    }

    public int initLoadOffset() {
        return 0;
    }
}
