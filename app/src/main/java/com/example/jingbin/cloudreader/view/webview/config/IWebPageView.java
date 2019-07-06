package com.example.jingbin.cloudreader.view.webview.config;

import android.view.View;

/**
 * Created by jingbin on 2016/11/17.
 */
public interface IWebPageView {

    /**
     * 隐藏进度条
     */
    void hindProgressBar();

    /**
     * 显示webview
     */
    void showWebView();

    /**
     * 隐藏webview
     */
    void hindWebView();

    /**
     * 进度条变化时调用
     */
    void startProgress(int newProgress);

    /**
     * 添加js监听
     */
    void addImageClickListener();

    /**
     * 播放网络视频全屏调用
     */
    void fullViewAddView(View view);

    /**
     * 显示视频全屏布局
     */
    void showVideoFullView();

    /**
     * 隐藏视频全屏布局
     */
    void hindVideoFullView();

    /**
     * 得到网页标题
     */
    void setTitle(String title);
}
