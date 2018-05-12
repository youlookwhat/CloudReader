package com.example.jingbin.cloudreader.viewmodel.wan;

import android.arch.lifecycle.ViewModel;

import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/5/9
 * @Description 文章列表ViewModel
 */

public class ArticleListViewModel extends ViewModel {

    private final BaseActivity activity;
    private WanNavigator.ArticleListNavigator navigator;
    private int mPage = 0;

    public void setNavigator(WanNavigator.ArticleListNavigator navigator) {
        this.navigator = navigator;
    }

    public void onDestroy() {
        navigator = null;
    }

    public ArticleListViewModel(BaseActivity activity) {
        this.activity = activity;
    }



    /**
     * 我的收藏
     */
    public void getCollectList() {
        Subscription subscribe = HttpClient.Builder.getWanAndroidServer().getCollectList(mPage)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        navigator.loadHomeListFailure();

                    }

                    @Override
                    public void onNext(HomeListBean bean) {
                        navigator.showLoadSuccessView();
                        if (mPage == 0) {
                            if (bean != null && bean.getData() != null && bean.getData().getDatas() != null && bean.getData().getDatas().size() > 0) {
                                navigator.showAdapterView(bean);
                            } else {
                                navigator.loadHomeListFailure();
                            }
                        } else {
                            if (bean != null && bean.getData() != null && bean.getData().getDatas() != null && bean.getData().getDatas().size() > 0) {
                                navigator.refreshAdapter(bean);
                            } else {
                                navigator.showListNoMoreLoading();
                            }
                        }
                    }
                });
        activity.addSubscription(subscribe);
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

}
