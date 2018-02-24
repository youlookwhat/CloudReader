package com.example.jingbin.cloudreader.viewmodel.wan;

import android.arch.lifecycle.ViewModel;

import com.example.jingbin.cloudreader.app.CloudReaderApplication;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import com.example.jingbin.cloudreader.data.model.JokeModel;
import com.example.jingbin.cloudreader.http.cache.ACache;

import java.util.List;

import rx.Subscription;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description 玩安卓ViewModel
 */

public class JokeViewModel extends ViewModel {

    private final BaseFragment activity;
    private final JokeModel mModel;
    private final ACache mACache;
    private WanNavigator.JokeNavigator jokeNavigator;
    private int mPage = 1;
    private boolean isChear = false;

    public void setNavigator(WanNavigator.JokeNavigator navigator) {
        this.jokeNavigator = navigator;
    }

    public void onDestroy() {
        navigator = null;
    }

    public JokeViewModel(BaseFragment activity) {
        this.activity = activity;
        mModel = new JokeModel();
        mACache = ACache.get(CloudReaderApplication.getInstance());
    }

    public void showNhdzList() {
        mModel.showNhdzList(navigator, mPage);
    }

    public void showQSBKList() {
        mModel.showQSBKList(navigator, mPage);
    }

    private WanNavigator.JokeModelNavigator navigator = new WanNavigator.JokeModelNavigator() {
        @Override
        public void loadSuccess(List<DuanZiBean> lists) {
            jokeNavigator.showLoadSuccessView();
            if (mPage == 1) {
                if (lists != null && lists.size() > 0) {
                    jokeNavigator.showAdapterView(lists);
                }
            } else {
                if (lists != null && lists.size() > 0) {
                    jokeNavigator.refreshAdapter(lists);
                } else {
                    jokeNavigator.showListNoMoreLoading();
                }
            }
        }

        @Override
        public void loadFailed() {
            jokeNavigator.loadListFailure();
        }

        @Override
        public void addSubscription(Subscription subscription) {
            activity.addSubscription(subscription);
        }
    };

    public void setClear() {
        isChear = true;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }
}
