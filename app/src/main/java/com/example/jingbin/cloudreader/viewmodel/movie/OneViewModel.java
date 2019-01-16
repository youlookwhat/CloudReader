package com.example.jingbin.cloudreader.viewmodel.movie;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.data.model.OneRepository;
import com.example.jingbin.cloudreader.http.HttpClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
