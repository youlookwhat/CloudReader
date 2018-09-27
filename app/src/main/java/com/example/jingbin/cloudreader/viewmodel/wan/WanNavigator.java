package com.example.jingbin.cloudreader.viewmodel.wan;

import com.example.jingbin.cloudreader.bean.CollectUrlBean;
import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description
 */

public interface WanNavigator {


    interface JokeModelNavigator {
        void loadSuccess(List<DuanZiBean> lists);

        void loadFailed();

        void addSubscription(Subscription subscription);

    }

    /**
     * 段子部分
     */
    interface JokeNavigator {

        /**
         * 加载文章列表失败
         */
        void loadListFailure();

        /**
         * 显示文章列表
         *
         * @param bean 文章数据
         */
        void showAdapterView(List<DuanZiBean> bean);

        /**
         * 没有更多了
         */
        void showListNoMoreLoading();

        /**
         * 显示加载成功页面
         */
        void showLoadSuccessView();

        /**
         * 取消注册
         */
        void addRxSubscription(Subscription subscription);
    }

    /**
     * 玩安卓首页部分
     */
    interface BannerNavigator {
        /**
         * 显示banner图
         *
         * @param bannerImages 图片链接集合
         * @param mBannerTitle 文章标题集合
         * @param result       全部数据
         */
        void showBannerView(ArrayList<String> bannerImages, ArrayList<String> mBannerTitle, List<WanAndroidBannerBean.DataBean> result);

        /**
         * 加载banner图失败
         */
        void loadBannerFailure();

        /**
         * 取消注册
         */
        void addRxSubscription(Subscription subscription);
    }

    /**
     * 我的收藏部分
     */
    interface ArticleListNavigator {
        /**
         * 加载文章列表失败
         */
        void loadHomeListFailure();

        /**
         * 显示文章列表
         *
         * @param bean 文章数据
         */
        void showAdapterView(HomeListBean bean);

        /**
         * 没有更多了
         */
        void showListNoMoreLoading();

        /**
         * 显示加载成功页面
         */
        void showLoadSuccessView();

        /**
         * 取消注册
         */
        void addRxSubscription(Subscription subscription);
    }

    /**
     * 收藏或取消收藏
     */
    interface OnCollectNavigator {
        void onSuccess();

        void onFailure();
    }

    /**
     * 我的收藏部分
     */
    interface CollectUrlNavigator {
        /**
         * 加载文章列表失败
         */
        void loadFailure();

        /**
         * 显示网址数据
         *
         * @param bean 网址数据
         */
        void showAdapterView(CollectUrlBean bean);

        /**
         * 显示加载成功页面
         */
        void showLoadSuccessView();

        /**
         * 取消注册
         */
        void addRxSubscription(Subscription subscription);
    }
}
