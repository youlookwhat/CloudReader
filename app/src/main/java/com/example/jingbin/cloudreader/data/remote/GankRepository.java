package com.example.jingbin.cloudreader.data.remote;

import android.arch.lifecycle.MutableLiveData;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.viewmodel.movie.OnMovieLoadListener;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2017/12/14
 * @Description 干货订制页面
 * @Singleton // informs Dagger that this class should be constructed once
 */
public class OneRepository {

    public MutableLiveData<HotMovieBean> getHotMovie() {
        final MutableLiveData<HotMovieBean> data = new MutableLiveData<>();
        HttpClient.Builder.getDouBanService().getHotMovie().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<HotMovieBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                data.setValue(null);
            }

            @Override
            public void onNext(HotMovieBean hotMovieBean) {
                if (hotMovieBean != null) {
                    data.setValue(hotMovieBean);
                }
            }
        });
        return data;
    }

    public void getMovieTop250(int start, int count, OnMovieLoadListener loadListener) {
        HttpClient.Builder.getDouBanService().getMovieTop250(start, count).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<HotMovieBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (loadListener != null) {
                    loadListener.onFailure();
                }
            }

            @Override
            public void onNext(HotMovieBean hotMovieBean) {
                if (hotMovieBean != null) {
                    if (loadListener != null) {
                        loadListener.onSuccess(hotMovieBean);
                    }
                }
            }
        });
    }

}
