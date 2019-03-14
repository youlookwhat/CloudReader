package com.example.jingbin.cloudreader.viewmodel.wan;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.http.RequestImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jingbin
 * @data 2019/3/13
 * @Description 搜索ViewModel
 */

public class SearchViewModel extends BaseListViewModel {

    /**
     * 干货集中营的page 从1开始
     */
    private int gankPage = 1;
    private String mType;

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 搜索
     */
    @SuppressLint("CheckResult")
    public MutableLiveData<HomeListBean> searchWan(String keyWord) {
        final MutableLiveData<HomeListBean> data = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().searchWan(mPage, keyWord)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeListBean>() {
                    @Override
                    public void accept(HomeListBean bean) throws Exception {
                        if (bean == null
                                || bean.getData() == null
                                || bean.getData().getDatas() == null
                                || bean.getData().getDatas().size() <= 0) {
                            data.setValue(null);
                        } else {
                            data.setValue(bean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mPage > 0) {
                            mPage--;
                        }
                        data.setValue(null);
                    }
                });
        return data;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<GankIoDataBean> loadGankData(String keyWord) {
        final MutableLiveData<GankIoDataBean> data = new MutableLiveData<>();
        HttpClient.Builder.getGankIOServer().searchGank(gankPage, mType, keyWord)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankIoDataBean>() {
                    @Override
                    public void accept(GankIoDataBean bean) throws Exception {
                        if (bean == null
                                || bean.getResults() == null
                                || bean.getResults().size() <= 0) {
                            data.setValue(null);
                        } else {
                            data.setValue(bean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mPage > 1) {
                            mPage--;
                        }
                        data.setValue(null);
                    }
                });
        return data;
    }

    public int getGankPage() {
        return gankPage;
    }

    public void setGankPage(int mPage) {
        this.gankPage = mPage;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
