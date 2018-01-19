package com.example.jingbin.cloudreader.viewmodel.gank;

import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.bean.FrontpageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jingbin
 * @data 2017/12/19
 * @Description
 */

public interface EverydayNavigator {

    /**
     * 显示列表数据
     */
    void showListView(ArrayList<List<AndroidBean>> mLists);

    /**
     * 显示错误页面
     */
    void showErrorView();

    /**
     * 显示轮播图
     */
    void showBannerView(ArrayList<String> mBannerImages, List<FrontpageBean.ResultBannerBean.FocusBean.ResultBeanX> result);

    /**
     * 显示旋转动画
     */
    void showRotaLoading();

    /**
     * 有一个变量需要单独设置
     */
    void setIsOldDayRequest(boolean isOldDayRequest);

    /**
     * 取缓存
     */
    void getACacheData();
}
