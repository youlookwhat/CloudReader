package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import me.jingbin.bymvvm.base.BaseViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import com.example.jingbin.cloudreader.data.model.JokeModel;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description 玩安卓ViewModel
 */

public class JokeViewModel extends BaseViewModel {

    private final JokeModel mModel;
    private int mPage = 1;
    // 刷新糗事百科
    private boolean isRefreshBK = false;
    private final MutableLiveData<List<DuanZiBean>> data = new MutableLiveData<>();

    public JokeViewModel(@NonNull Application application) {
        super(application);
        mModel = new JokeModel();
    }

    public MutableLiveData<List<DuanZiBean>> getData() {
        return data;
    }

    public void showQSBKList() {
        mModel.showQSBKList(navigator, mPage);
    }

    private WanNavigator.JokeModelNavigator navigator = new WanNavigator.JokeModelNavigator() {
        @Override
        public void loadSuccess(List<DuanZiBean> lists) {
            data.setValue(lists);
        }

        @Override
        public void loadFailed() {
            data.setValue(null);
        }

        @Override
        public void addSubscription(Disposable subscription) {
            addDisposable(subscription);
        }
    };

    public void setRefreshBK(boolean refreshBK) {
        isRefreshBK = refreshBK;
    }

    public boolean isRefreshBK() {
        return isRefreshBK;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }
}
