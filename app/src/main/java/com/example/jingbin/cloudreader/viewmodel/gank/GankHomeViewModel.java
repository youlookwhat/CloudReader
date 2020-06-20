package com.example.jingbin.cloudreader.viewmodel.gank;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import me.jingbin.bymvvm.base.BaseViewModel;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.DataUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2020/04/26
 */
public class GankHomeViewModel extends BaseViewModel {

    private final MutableLiveData<Boolean> isShowLoading = new MutableLiveData<>();
    private final MutableLiveData<GankIoDataBean> contentData = new MutableLiveData<>();
    private final MutableLiveData<GankIoDataBean> bannerData = new MutableLiveData<>();

    public GankHomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadData() {
        getBanner();
        showContentData();
    }

    /**
     * banner数据
     */
    public void getBanner() {
        isShowLoading.setValue(true);
        Disposable subscribe = HttpClient.Builder.getGankIOServer().getGankBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankIoDataBean>() {
                    @Override
                    public void accept(GankIoDataBean bean) throws Exception {
                        bannerData.setValue(DataUtil.getTrueData(bean));
                        isShowLoading.setValue(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bannerData.setValue(null);
                        isShowLoading.setValue(false);
                    }
                });
        addDisposable(subscribe);
    }

    /**
     * 内容部分
     */
    private void showContentData() {
        Disposable subscribe = HttpClient.Builder.getGankIOServer().getGankHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankIoDataBean>() {
                    @Override
                    public void accept(GankIoDataBean bean) throws Exception {
                        contentData.setValue(DataUtil.getTrueData(bean));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        contentData.setValue(null);
                    }
                });
        addDisposable(subscribe);
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return isShowLoading;
    }

    public MutableLiveData<GankIoDataBean> getContentData() {
        return contentData;
    }

    public MutableLiveData<GankIoDataBean> getBannerData() {
        return bannerData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
