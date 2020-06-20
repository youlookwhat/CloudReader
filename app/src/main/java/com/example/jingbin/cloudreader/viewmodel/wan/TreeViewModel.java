package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import me.jingbin.bymvvm.base.BaseViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jingbin
 * @data 2018/12/3
 * @Description wanandroid知识体系的ViewModel
 */

public class TreeViewModel extends BaseViewModel {


    public TreeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<TreeBean> getTree() {
        final MutableLiveData<TreeBean> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().getTree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TreeBean>() {
                    @Override
                    public void accept(TreeBean treeBean) throws Exception {
                        data.setValue(treeBean);
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
