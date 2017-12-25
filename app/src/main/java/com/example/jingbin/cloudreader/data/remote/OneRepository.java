package com.example.jingbin.cloudreader.data.remote;

import android.arch.lifecycle.MutableLiveData;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.http.HttpClient;

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

}
