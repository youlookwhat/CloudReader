package com.example.jingbin.cloudreader.data.model;

import android.annotation.SuppressLint;
import androidx.lifecycle.MutableLiveData;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.viewmodel.movie.OnMovieLoadListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2017/12/14
 * @Description 电影部分
 */
@SuppressLint("CheckResult")
public class OneRepository {

    public MutableLiveData<HotMovieBean> getHotMovie() {
        final MutableLiveData<HotMovieBean> data = new MutableLiveData<>();
        HttpClient.Builder.getDouBanService().getHotMovie().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HotMovieBean>() {

            @Override
            public void accept(HotMovieBean hotMovieBean) throws Exception {
                if (hotMovieBean != null) {
                    data.setValue(hotMovieBean);
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

    public void getMovieTop250(int start, int count, OnMovieLoadListener loadListener) {
        HttpClient.Builder.getDouBanService().getMovieTop250(start, count).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HotMovieBean>() {
            @Override
            public void accept(HotMovieBean hotMovieBean) throws Exception {
                if (hotMovieBean != null) {
                    if (loadListener != null) {
                        loadListener.onSuccess(hotMovieBean);
                    }
                }
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (loadListener != null) {
                    loadListener.onFailure();
                }
            }
        });
    }

}
