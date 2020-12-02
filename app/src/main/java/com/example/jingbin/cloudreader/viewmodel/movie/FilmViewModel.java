package com.example.jingbin.cloudreader.viewmodel.movie;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableField;
import androidx.annotation.NonNull;

import me.jingbin.bymvvm.base.BaseViewModel;
import com.example.jingbin.cloudreader.bean.ComingFilmBean;
import com.example.jingbin.cloudreader.bean.MtimeFilmeBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jingbin
 * @data 2019/05/14
 */

public class FilmViewModel extends BaseViewModel {

    // 开始请求的角标
    private int mStart = 0;
    // 一次请求的数量
    private int mCount = 18;

    public final ObservableField<String> bookType = new ObservableField<>();

    public FilmViewModel(@NonNull Application application) {
        super(application);
    }

    public void setStart(int mStart) {
        this.mStart = mStart;
    }

    public int getStart() {
        return mStart;
    }

    public void handleNextStart() {
        mStart += mCount;
    }

    public MutableLiveData<MtimeFilmeBean> getHotFilm() {
        final MutableLiveData<MtimeFilmeBean> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getMtimeTicketServer().getHotFilm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MtimeFilmeBean>() {
                    @Override
                    public void accept(MtimeFilmeBean bookBean) throws Exception {
                        data.setValue(bookBean);
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

    public MutableLiveData<ComingFilmBean> getComingFilm() {
        final MutableLiveData<ComingFilmBean> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getMtimeTicketServer().getComingFilm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ComingFilmBean>() {
                    @Override
                    public void accept(ComingFilmBean bookBean) throws Exception {
                        data.setValue(bookBean);
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
}
