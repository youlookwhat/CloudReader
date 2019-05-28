package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.CollectUrlBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().getCollectUrlList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(data::setValue, throwable -> data.setValue(null));
        addDisposable(subscribe);
        return data;
    }
}
