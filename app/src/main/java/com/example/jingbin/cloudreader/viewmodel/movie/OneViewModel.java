package com.example.jingbin.cloudreader.viewmodel.movie;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.data.model.OneRepository;
import com.example.jingbin.cloudreader.http.HttpClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/12/22
 */

public class OneViewModel extends AndroidViewModel {

    private MutableLiveData<HotMovieBean> hotMovieBean;
    private OneRepository oneRepo;
    private int mStart = 0;
    private int mCount = 21;

    public OneViewModel(@NonNull Application application) {
        super(application);
        this.oneRepo = new OneRepository();
    }

    private void setHotMovieBean(MutableLiveData<HotMovieBean> hotMovieBean) {
        this.hotMovieBean = hotMovieBean;
    }

    public LiveData<HotMovieBean> getHotMovie() {
        if (hotMovieBean == null
                || hotMovieBean.getValue() == null
                || hotMovieBean.getValue().getSubjects() == null
                || hotMovieBean.getValue().getSubjects().size() == 0) {
            hotMovieBean = new MutableLiveData<>();
            return loadHotMovie();
        } else {
            return hotMovieBean;
        }
    }

    private MutableLiveData<HotMovieBean> loadHotMovie() {
        MutableLiveData<HotMovieBean> hotMovie = oneRepo.getHotMovie();
        setHotMovieBean(hotMovie);
        return hotMovie;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<HotMovieBean> getComingSoon() {
        final MutableLiveData<HotMovieBean> data = new MutableLiveData<>();
        HttpClient.Builder.getDouBanService().getComingSoon(mStart, mCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotMovieBean>() {
                    @Override
                    public void accept(HotMovieBean hotMovieBean) throws Exception {
                        data.setValue(hotMovieBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        data.setValue(null);
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
