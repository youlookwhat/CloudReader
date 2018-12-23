package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/12/3
 * @Description wanandroid导航数据的ViewModel
 */

public class NaviViewModel extends AndroidViewModel {

    public NaviViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<NaviJsonBean> getNaviJson() {
        final MutableLiveData<NaviJsonBean> data = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getNaviJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NaviJsonBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onNext(NaviJsonBean naviJsonBean) {
                        if (naviJsonBean != null
                                && naviJsonBean.getData() != null
                                && naviJsonBean.getData().size() > 0) {

                            data.setValue(naviJsonBean);
                        } else {
                            data.setValue(null);
                        }
                    }
                });
        return data;
    }
}
