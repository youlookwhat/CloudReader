package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import me.jingbin.bymvvm.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().getCollectList(mPage)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeListBean>() {
                    @Override
                    public void accept(HomeListBean bean) throws Exception {
                        data.setValue(bean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mPage > 0) {
                            mPage--;
                        }
                        data.setValue(null);
                    }
                });
        addDisposable(subscribe);
        return data;
    }
}
