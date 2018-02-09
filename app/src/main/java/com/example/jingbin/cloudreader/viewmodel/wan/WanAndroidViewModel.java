package com.example.jingbin.cloudreader.viewmodel.wan;

import android.arch.lifecycle.ViewModel;

import com.example.jingbin.cloudreader.base.BaseFragment;
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

public class WanAndroidViewModel extends ViewModel {

    private final BaseFragment activity;
    private WanNavigator.WanAndroidNavigator navigator;
    private int mPage = 0;
    private ArrayList<String> mBannerImages;
    private ArrayList<String> mBannerTitles;

    public void setNavigator(WanNavigator.WanAndroidNavigator navigator) {
        this.navigator = navigator;
    }

    public void onDestroy() {
        navigator = null;
    }

    public WanAndroidViewModel(BaseFragment activity) {
        this.activity = activity;
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
                        navigator.loadBannerFailure();

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
                                navigator.showBannerView(mBannerImages, mBannerTitles, result);
                            }
                        }
                        if (result == null) {
                            navigator.loadBannerFailure();
                        }

                    }
                });
        activity.addSubscription(subscribe);
    }


    public void getHomeList() {
        Subscription subscribe = HttpClient.Builder.getWanAndroidServer().getHomeList(mPage)
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
