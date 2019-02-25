package com.example.jingbin.cloudreader.http;

import io.reactivex.disposables.Disposable;

/**
 * Created by jingbin on 2017/1/17.
 * 用于数据请求的回调
 */

public interface RequestImpl {
    void loadSuccess(Object object);

    void loadFailed();

    void addSubscription(Disposable subscription);
}
