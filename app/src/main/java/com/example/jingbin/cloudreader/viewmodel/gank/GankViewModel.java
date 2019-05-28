package com.example.jingbin.cloudreader.viewmodel.gank;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.base.BaseViewModel;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;

import io.reactivex.disposables.Disposable;

/**
 * 干货集中营页面 ViewModel
 *
 * @author jingbin
 * @data 2019/3/14
 */

public class GankViewModel extends BaseViewModel {

    private final GankOtherModel mModel;
    private int mPage = 1;
    private String mType;

    public GankViewModel(@NonNull Application application) {
        super(application);
        mModel = new GankOtherModel();
    }

    public MutableLiveData<GankIoDataBean> loadGankData() {
        final MutableLiveData<GankIoDataBean> data = new MutableLiveData<>();
        mModel.setData(mType, mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                data.setValue((GankIoDataBean) object);
            }

            @Override
            public void loadFailed() {
                if (mPage > 1) {
                    mPage--;
                }
                data.setValue(null);
            }

            @Override
            public void addSubscription(Disposable disposable) {
                addDisposable(disposable);
            }
        });
        return data;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
