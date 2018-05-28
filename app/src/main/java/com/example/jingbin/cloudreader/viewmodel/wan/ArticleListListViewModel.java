package com.example.jingbin.cloudreader.viewmodel.wan;

import com.example.jingbin.cloudreader.base.BaseListViewModel;
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

public class ArticleListListViewModel extends BaseListViewModel {

    private WanNavigator.ArticleListNavigator navigator;

    public ArticleListListViewModel(WanNavigator.ArticleListNavigator navigator) {
        this.navigator = navigator;
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
                            if (bean == null || bean.getData() == null || bean.getData().getDatas() == null || bean.getData().getDatas().size() <= 0) {
                                navigator.loadHomeListFailure();
                                return;
                            }
                        } else {
                            if (bean == null || bean.getData() == null || bean.getData().getDatas() == null || bean.getData().getDatas().size() <= 0) {
                                navigator.showListNoMoreLoading();
                                return;
                            }
                        }
                        navigator.showAdapterView(bean);
                    }
                });
        navigator.addRxSubscription(subscribe);
    }

    public void onDestroy() {
        navigator = null;
    }
}
