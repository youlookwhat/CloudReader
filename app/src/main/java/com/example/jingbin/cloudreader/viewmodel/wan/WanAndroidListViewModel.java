package com.example.jingbin.cloudreader.viewmodel.wan;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description 玩安卓ViewModel
 */

public class WanAndroidListViewModel extends BaseListViewModel {

    public WanAndroidListViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<WanAndroidBannerBean> getWanAndroidBanner() {
        final MutableLiveData<WanAndroidBannerBean> data = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getWanAndroidBanner()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WanAndroidBannerBean>() {

                    @Override
                    public void accept(WanAndroidBannerBean wanAndroidBannerBean) throws Exception {
                        ArrayList<String> mBannerImages = new ArrayList<String>();
                        ArrayList<String> mBannerTitles = new ArrayList<String>();
                        if (wanAndroidBannerBean != null
                                && wanAndroidBannerBean.getData() != null
                                && wanAndroidBannerBean.getData().size() > 0) {
                            List<WanAndroidBannerBean.DataBean> result = wanAndroidBannerBean.getData();
                            if (result != null && result.size() > 0) {
                                for (int i = 0; i < result.size(); i++) {
                                    //获取所有图片
                                    mBannerImages.add(result.get(i).getImagePath());
                                    mBannerTitles.add(result.get(i).getTitle());
                                }
                                wanAndroidBannerBean.setmBannerImages(mBannerImages);
                                wanAndroidBannerBean.setmBannerTitles(mBannerTitles);
                                data.setValue(wanAndroidBannerBean);
                            }
                        } else {
                            data.setValue(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        data.setValue(null);
                    }
                });
        return data;
    }

    /**
     * @param cid 体系id
     */
    @SuppressLint("CheckResult")
    public MutableLiveData<HomeListBean> getHomeList(Integer cid) {
        final MutableLiveData<HomeListBean> listData = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getHomeList(mPage, cid)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeListBean>() {
                    @Override
                    public void accept(HomeListBean bean) throws Exception {
                        if (bean == null
                                || bean.getData() == null
                                || bean.getData().getDatas() == null
                                || bean.getData().getDatas().size() <= 0) {
                            listData.setValue(null);
                        } else {
                            listData.setValue(bean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mPage > 0) {
                            mPage--;
                        }
                        listData.setValue(null);
                    }
                });
        return listData;
    }

}
