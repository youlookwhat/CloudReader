package com.example.jingbin.cloudreader.viewmodel.menu;

import rx.Subscription;

/**
 * @author jingbin
 * @data 2018/5/7
 * @Description
 */

public interface LoginNavigator {

    void loadSuccess();

    /**
     * 取消注册
     */
    void addRxSubscription(Subscription subscription);
}
