package com.example.jingbin.cloudreader.base;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * @author jingbin
 * @data 2018/5/12
 * @Description 有列表的页面
 */

public class BaseListViewModel extends BaseViewModel {

    public int mPage = 0;

    public BaseListViewModel(@NonNull Application application) {
        super(application);
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }
}
