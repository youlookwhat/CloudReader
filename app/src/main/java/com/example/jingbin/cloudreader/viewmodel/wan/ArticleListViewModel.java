package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/5/9
 * @Description 文章列表ViewModel
 */

public class ArticleListViewModel extends BaseListViewModel {

    public ArticleListViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 我的收藏
     */
    public MutableLiveData<HomeListBean> getCollectList() {
        final MutableLiveData<HomeListBean> data = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getCollectList(mPage)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mPage > 0) {
                            mPage--;
                        }
                        data.setValue(null);
                    }

                    @Override
                    public void onNext(HomeListBean bean) {
                        if (bean == null
                                || bean.getData() == null
                                || bean.getData().getDatas() == null
                                || bean.getData().getDatas().size() <= 0) {
                            data.setValue(null);
                        } else {
                            data.setValue(bean);
                        }
                    }
                });
        return data;
    }
}
