package com.example.jingbin.cloudreader.viewmodel.gank;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;
import com.example.jingbin.cloudreader.utils.DataUtil;

import io.reactivex.disposables.Disposable;
import me.jingbin.bymvvm.base.BaseViewModel;

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
        mModel.setData("GanHuo", mType, mPage, 20);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                data.setValue(DataUtil.getTrueData((GankIoDataBean) object));
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
