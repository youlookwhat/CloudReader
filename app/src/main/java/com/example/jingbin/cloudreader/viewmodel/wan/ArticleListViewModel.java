package com.example.jingbin.cloudreader.viewmodel.wan;

import android.arch.lifecycle.MutableLiveData;

import com.example.jingbin.cloudreader.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;
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

public class ArticleListViewModel extends BaseListViewModel {

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
                        data.setValue(null);
                        if (mPage > 0) {
                            mPage--;
                        }
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
