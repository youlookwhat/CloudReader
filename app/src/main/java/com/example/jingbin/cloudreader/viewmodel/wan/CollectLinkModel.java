package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.CollectUrlBean;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/5/9
 * @Description 文章列表ViewModel
 */

public class CollectLinkModel extends BaseListViewModel {

    public CollectLinkModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<CollectUrlBean> getCollectUrlList() {
        final MutableLiveData<CollectUrlBean> data = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getCollectUrlList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectUrlBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onNext(CollectUrlBean bean) {
                        data.setValue(bean);
                    }
                });
        return data;
    }
}
