package com.example.jingbin.cloudreader.viewmodel.gank;

import com.example.jingbin.cloudreader.bean.GankIoDataBean;

/**
 * @author jingbin
 * @data 2018/1/16
 * @Description
 */

public interface BigAndroidNavigator {

    void showLoadSuccessView();

    /**
     * 显示adapter数据
     */
    void showAdapterView(GankIoDataBean gankIoDataBean);

    /**
     * 刷新adapter数据
     */
    void refreshAdapter(GankIoDataBean gankIoDataBean);

    /**
     * 显示列表没有更多数据布局
     */
    void showListNoMoreLoading();

    void showLoadFailedView();
}
