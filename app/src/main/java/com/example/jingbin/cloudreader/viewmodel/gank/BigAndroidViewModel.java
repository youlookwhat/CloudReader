package com.example.jingbin.cloudreader.viewmodel.gank;

import android.arch.lifecycle.ViewModel;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.app.CloudReaderApplication;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.utils.DebugUtil;

import rx.Subscription;

/**
 * @author jingbin
 * @data 2018/1/16
 * @Description 大安卓ViewModel
 */

public class BigAndroidViewModel extends ViewModel {

    private final GankOtherModel mModel;
    private final ACache mACache;
    private String mType = "Android";
    private BaseFragment activity;
    private BigAndroidNavigator navigator;
    private int mPage = 1;

    public BigAndroidViewModel(BaseFragment activity, String mType) {
        this.activity = activity;
        this.mType = mType;

        mACache = ACache.get(CloudReaderApplication.getInstance());
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
                navigator.showContentView();
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                if (mPage == 1) {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        navigator.showAdapterView(gankIoDataBean);

                        mACache.remove(Constants.GANK_ANDROID);
                        mACache.put(Constants.GANK_ANDROID, gankIoDataBean);
                    }
                } else {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        navigator.refreshAdapter(gankIoDataBean);
                    } else {
                        navigator.showListNoMoreLoading();
                    }
                }
            }

            @Override
            public void loadFailed() {
                DebugUtil.error("----22222");
                handlerFailed();
                if (mPage > 1) {
                    mPage--;
                }
            }

            @Override
            public void addSubscription(Subscription subscription) {
                activity.addSubscription(subscription);
            }
        });
    }

    /**
     * 处理请求失败后的情况
     */
    private void handlerFailed() {
        GankIoDataBean mAndroidBean = (GankIoDataBean) mACache.getAsObject(Constants.GANK_ANDROID);
        if (mAndroidBean != null
                && mAndroidBean.getResults() != null
                && mAndroidBean.getResults().size() > 0) {
            navigator.showContentView();
            navigator.showAdapterView(mAndroidBean);
        } else {
            DebugUtil.error("-----1111");
            navigator.showLoadFailedView();
        }
    }
}
