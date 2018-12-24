package com.example.jingbin.cloudreader.viewmodel.movie;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.data.model.OneRepository;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/12/23
 */

public class BookListViewModel extends AndroidViewModel {

    // 开始请求的角标
    private int mStart = 0;
    // 一次请求的数量
    private int mCount = 18;
    private String mType = "综合";

    public BookListViewModel(@NonNull Application application) {
        super(application);
    }

    public void setStart(int mStart) {
        this.mStart = mStart;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public int getStart() {
        return mStart;
    }

    public int getCount() {
        return mCount;
    }

    public MutableLiveData<BookBean> getBook() {
        final MutableLiveData<BookBean> data = new MutableLiveData<>();
        Subscription get = HttpClient.Builder.getDouBanService().getBook(mType, mStart, mCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onNext(BookBean bookBean) {
                        data.setValue(bookBean);
                    }
                });
        return data;
    }
}
