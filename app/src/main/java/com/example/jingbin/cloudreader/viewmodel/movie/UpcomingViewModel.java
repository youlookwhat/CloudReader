package com.example.jingbin.cloudreader.viewmodel.movie;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.base.BaseViewModel;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2019/01/14
 */

public class UpcomingViewModel extends BaseViewModel {

    private int mStart = 0;
    private int mCount = 21;

    public UpcomingViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<HotMovieBean> getComingSoon() {
        final MutableLiveData<HotMovieBean> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getDouBanService().getComingSoon(mStart, mCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotMovieBean>() {
                    @Override
                    public void accept(HotMovieBean hotMovieBean) throws Exception {
                        data.setValue(hotMovieBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        data.setValue(null);
                    }
                });
        addDisposable(subscribe);
        return data;
    }

    public int getStart() {
        return mStart;
    }

    public void handleNextStart() {
        mStart += mCount;
    }

    public void setStart(int start) {
        this.mStart = start;
    }
}
