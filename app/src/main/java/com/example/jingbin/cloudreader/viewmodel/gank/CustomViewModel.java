package com.example.jingbin.cloudreader.viewmodel.gank;

import android.arch.lifecycle.ViewModel;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;

import rx.Subscription;

/**
 * 干货订制页面ViewModel
 *
 * @author jingbin
 * @data 2018/1/18
 * //        viewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
 * //        viewModel.getUser().observe(this, gankIoDataBean -> {
 * //        });
 */

public class CustomViewModel extends ViewModel {

    private final BaseFragment activity;
    private final GankOtherModel mModel;
    private CustomNavigator navigator;
    private int mPage = 1;
    private String mType;

    public void setNavigator(CustomNavigator navigator) {
        this.navigator = navigator;
    }

    public void onDestroy() {
        navigator = null;
    }

    public CustomViewModel(BaseFragment activity) {
        this.activity = activity;
        mModel = new GankOtherModel();
    }

    public void loadCustomData() {
        mModel.setData(mType, mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                navigator.showLoadSuccessView();
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                if (mPage == 1) {
                    if (gankIoDataBean != null
                            && gankIoDataBean.getResults() != null
                            && gankIoDataBean.getResults().size() > 0) {
                        navigator.showAdapterView(gankIoDataBean);
                    }
                } else {
                    if (gankIoDataBean != null
                            && gankIoDataBean.getResults() != null
                            && gankIoDataBean.getResults().size() > 0) {
                        navigator.refreshAdapter(gankIoDataBean);
                    } else {
                        navigator.showListNoMoreLoading();
                    }
                }
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
                activity.addSubscription(subscription);
            }
        });
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
