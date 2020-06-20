package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import me.jingbin.bymvvm.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.cloudreader.http.HttpClient;

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

    public MutableLiveData<WanAndroidBannerBean> getWanAndroidBanner() {
        final MutableLiveData<WanAndroidBannerBean> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().getWanAndroidBanner()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WanAndroidBannerBean>() {

                    @Override
                    public void accept(WanAndroidBannerBean bannerBean) throws Exception {
                        if (bannerBean != null
                                && bannerBean.getData() != null
                                && bannerBean.getData().size() > 0) {
                            data.setValue(bannerBean);
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
        addDisposable(subscribe);
        return data;
    }

    /**
     * @param cid 体系id
     */
    public MutableLiveData<HomeListBean> getHomeArticleList(Integer cid) {
        final MutableLiveData<HomeListBean> listData = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getHomeList(mPage, cid)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(HomeListBean homeListBean) {
                        listData.setValue(homeListBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return listData;
    }

    public MutableLiveData<HomeListBean> getHomeProjectList() {
        final MutableLiveData<HomeListBean> listData = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getProjectList(mPage)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(HomeListBean homeListBean) {
                        listData.setValue(homeListBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return listData;
    }
}
