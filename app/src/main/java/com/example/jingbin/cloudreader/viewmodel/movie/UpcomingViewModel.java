package com.example.jingbin.cloudreader.viewmodel.movie;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.data.model.OneRepository;
import com.example.jingbin.cloudreader.http.HttpClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2019/01/14
 */

public class UpcomingViewModel extends AndroidViewModel {

    private int mStart = 0;
    private int mCount = 21;

    public UpcomingViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<HotMovieBean> getComingSoon() {
        final MutableLiveData<HotMovieBean> data = new MutableLiveData<>();
        HttpClient.Builder.getDouBanService().getComingSoon(mStart, mCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotMovieBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onNext(HotMovieBean bookBean) {
                        data.setValue(bookBean);
                    }
                });
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
