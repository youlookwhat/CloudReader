package com.example.jingbin.cloudreader.viewmodel.gank;

import android.arch.lifecycle.ViewModel;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;

import rx.Subscription;

/**
 * @author jingbin
 * @data 2018/1/16
 * @Description 大安卓ViewModel
 */

public class BigAndroidViewModel extends ViewModel {

    private final GankOtherModel mModel;
    private String mType = "Android";
    private BigAndroidNavigator navigator;
    private int mPage = 1;

    public BigAndroidViewModel(String mType) {
        this.mType = mType;
        mModel = new GankOtherModel();
    }

    public void setBigAndroidNavigator(BigAndroidNavigator bigAndroidNavigator) {
        this.navigator = bigAndroidNavigator;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public int getPage() {
        return mPage;
    }

    public void loadAndroidData() {
        mModel.setData(mType, mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                navigator.showLoadSuccessView();
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                if (mPage == 1) {
                    if (gankIoDataBean == null || gankIoDataBean.getResults() == null || gankIoDataBean.getResults().size() <= 0) {
                        navigator.showLoadFailedView();
                        return;
                    }
                } else {
                    if (gankIoDataBean == null || gankIoDataBean.getResults() == null || gankIoDataBean.getResults().size() <= 0) {
                        navigator.showListNoMoreLoading();
                        return;
                    }
                }
                navigator.showAdapterView(gankIoDataBean);
            }

            @Override
            public void loadFailed() {
                navigator.showLoadFailedView();
                if (mPage > 1) {
                    mPage--;
                }
            }

            @Override
            public void addSubscription(Subscription subscription) {
                navigator.addRxSubscription(subscription);
            }
        });
    }

    public void onDestroy() {
        navigator = null;
    }
}
