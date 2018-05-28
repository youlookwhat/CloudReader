package com.example.jingbin.cloudreader.viewmodel.gank;

import android.arch.lifecycle.ViewModel;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/1/17
 * @Description 福利页面ViewModel
 */

public class WelfareViewModel extends ViewModel {

    private final GankOtherModel mModel;
    private WelfareNavigator navigator;
    private int mPage = 1;

    /**
     * 图片url集合
     */
    private ArrayList<String> imgList = new ArrayList<>();
    /**
     * 图片标题集合，用于保存图片时使用
     */
    private ArrayList<String> imageTitleList = new ArrayList<>();
    /**
     * 传递给Activity数据集合
     */
    private ArrayList<ArrayList<String>> allList = new ArrayList<>();

    public void setNavigator(WelfareNavigator navigator) {
        this.navigator = navigator;
    }

    public WelfareViewModel() {
        mModel = new GankOtherModel();
    }

    public void loadWelfareData() {
        mModel.setData("福利", mPage, HttpUtils.per_page_more);
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
                handleImageList(gankIoDataBean);
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

    /**
     * 异步处理用于图片详情显示的数据
     *
     * @param gankIoDataBean 原数据
     */
    private void handleImageList(GankIoDataBean gankIoDataBean) {
        Subscription subscribe = Observable.just(gankIoDataBean)
                .map(new Func1<GankIoDataBean, ArrayList<ArrayList<String>>>() {
                    @Override
                    public ArrayList<ArrayList<String>> call(GankIoDataBean gankIoDataBean) {
                        imgList.clear();
                        imageTitleList.clear();
                        for (int i = 0; i < gankIoDataBean.getResults().size(); i++) {
                            imgList.add(gankIoDataBean.getResults().get(i).getUrl());
                            imageTitleList.add(gankIoDataBean.getResults().get(i).getDesc());
                        }
                        allList.clear();
                        allList.add(imgList);
                        allList.add(imageTitleList);
                        return allList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<ArrayList<String>>>() {
                    @Override
                    public void call(ArrayList<ArrayList<String>> arrayLists) {
                        navigator.setImageList(arrayLists);
                    }
                });
        navigator.addRxSubscription(subscribe);
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public void onDestroy() {
        imgList.clear();
        imageTitleList.clear();
        allList.clear();
        imgList = null;
        imageTitleList = null;
        allList = null;
        navigator = null;
    }
}
