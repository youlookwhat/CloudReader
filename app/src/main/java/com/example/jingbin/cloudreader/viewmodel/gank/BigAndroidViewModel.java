package com.example.jingbin.cloudreader.viewmodel.gank;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;

import io.reactivex.disposables.Disposable;

/**
 * @author jingbin
 * @data 2018/1/16
 * @Description 大安卓ViewModel
 */

public class BigAndroidViewModel extends AndroidViewModel {

    private final GankOtherModel mModel;
    private String mType = "Android";
    private int mPage = 1;

    public BigAndroidViewModel(@NonNull Application application) {
        super(application);
        mModel = new GankOtherModel();
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public int getPage() {
        return mPage;
    }

    public MutableLiveData<GankIoDataBean> loadAndroidData() {
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
            public void addSubscription(Disposable subscription) {
            }
        });
        return data;
    }
}
