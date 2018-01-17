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

import java.util.ArrayList;

import rx.Subscription;

/**
 * @author jingbin
 * @data 2018/1/17
 * @Description 福利页面ViewModel
 */

public class WelfareViewModel extends ViewModel {

    private final BaseFragment activity;
    private final GankOtherModel mModel;
    private final ACache mACache;
    private WelfareNavigator navigator;
    private int mPage = 1;
    private ArrayList<String> imgList = new ArrayList<>();

    public void setNavigator(WelfareNavigator navigator) {
        this.navigator = navigator;
    }

    public void onDestroy() {
        navigator = null;
    }

    public WelfareViewModel(BaseFragment activity) {
        this.activity = activity;
        mModel = new GankOtherModel();
        mACache = ACache.get(CloudReaderApplication.getInstance());
    }

    public void loadWelfareData(){
        mModel.setData("福利", mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                navigator.showLoadSuccessView();

                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                if (mPage == 1) {
                    if (gankIoDataBean != null
                            && gankIoDataBean.getResults() != null
                            && gankIoDataBean.getResults().size() > 0) {
                        imgList.clear();
                        for (int i = 0; i < gankIoDataBean.getResults().size(); i++) {
                            imgList.add(gankIoDataBean.getResults().get(i).getUrl());
                        }
                        navigator.setImageList(imgList);
                        navigator.showAdapterView(gankIoDataBean);
                        mACache.remove(Constants.GANK_MEIZI);
                        mACache.put(Constants.GANK_MEIZI, gankIoDataBean);

                    } else {
                        handlerFailed();
                    }
                } else {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        navigator.refreshAdapter(gankIoDataBean);
                        for (int i = 0; i < gankIoDataBean.getResults().size(); i++) {
                            imgList.add(gankIoDataBean.getResults().get(i).getUrl());
                        }
                        navigator.setImageList(imgList);
                    } else {
                        navigator.showListNoMoreLoading();
                    }
                }
            }

            @Override
            public void loadFailed() {
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
        GankIoDataBean mAndroidBean = (GankIoDataBean) mACache.getAsObject(Constants.GANK_MEIZI);
        if (mAndroidBean != null
                && mAndroidBean.getResults() != null
                && mAndroidBean.getResults().size() > 0) {
            navigator.showLoadSuccessView();
            navigator.showAdapterView(mAndroidBean);
        } else {
            navigator.showLoadFailedView();
        }
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }
}
