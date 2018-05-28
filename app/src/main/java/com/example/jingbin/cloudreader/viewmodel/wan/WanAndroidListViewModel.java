package com.example.jingbin.cloudreader.viewmodel.wan;

import com.example.jingbin.cloudreader.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description 玩安卓ViewModel
 */

public class WanAndroidListViewModel extends BaseListViewModel {

    private WanNavigator.BannerNavigator loadBannerNavigator;
    private WanNavigator.ArticleListNavigator navigator;
    private ArrayList<String> mBannerImages;
    private ArrayList<String> mBannerTitles;

    public void setNavigator(WanNavigator.BannerNavigator navigator) {
        this.loadBannerNavigator = navigator;
    }

    public void setArticleListNavigator(WanNavigator.ArticleListNavigator navigator) {
        this.navigator = navigator;
    }

    public void getWanAndroidBanner() {
        Subscription subscribe = HttpClient.Builder.getWanAndroidServer().getWanAndroidBanner()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WanAndroidBannerBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadBannerNavigator.loadBannerFailure();

                    }

                    @Override
                    public void onNext(WanAndroidBannerBean wanAndroidBannerBean) {
                        if (mBannerImages == null) {
                            mBannerImages = new ArrayList<String>();
                        } else {
                            mBannerImages.clear();
                        }
                        if (mBannerTitles == null) {
                            mBannerTitles = new ArrayList<String>();
                        } else {
                            mBannerTitles.clear();
                        }
                        List<WanAndroidBannerBean.DataBean> result = null;
                        if (wanAndroidBannerBean != null && wanAndroidBannerBean.getData() != null) {
                            result = wanAndroidBannerBean.getData();
                            if (result != null && result.size() > 0) {
                                for (int i = 0; i < result.size(); i++) {
                                    //获取所有图片
                                    mBannerImages.add(result.get(i).getImagePath());
                                    mBannerTitles.add(result.get(i).getTitle());
                                }
                                loadBannerNavigator.showBannerView(mBannerImages, mBannerTitles, result);
                            }
                        }
                        if (result == null) {
                            loadBannerNavigator.loadBannerFailure();
                        }

                    }
                });
        loadBannerNavigator.addRxSubscription(subscribe);
    }


    /**
     * @param cid 体系id
     */
    public void getHomeList(Integer cid) {
        Subscription subscribe = HttpClient.Builder.getWanAndroidServer().getHomeList(mPage, cid)
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
