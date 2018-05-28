package com.example.jingbin.cloudreader.viewmodel.wan;

import android.arch.lifecycle.ViewModel;

import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import com.example.jingbin.cloudreader.data.model.JokeModel;

import java.util.List;

import rx.Subscription;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description 玩安卓ViewModel
 */

public class JokeViewModel extends ViewModel {

    private final JokeModel mModel;
    private WanNavigator.JokeNavigator jokeNavigator;
    private int mPage = 1;
    // 刷新糗事百科
    private boolean isRefreshBK = false;

    public void setNavigator(WanNavigator.JokeNavigator navigator) {
        this.jokeNavigator = navigator;
    }

    public void onDestroy() {
        navigator = null;
    }

    public JokeViewModel() {
        mModel = new JokeModel();
    }

    public void showQSBKList() {
        mModel.showQSBKList(navigator, mPage);
    }

    private WanNavigator.JokeModelNavigator navigator = new WanNavigator.JokeModelNavigator() {
        @Override
        public void loadSuccess(List<DuanZiBean> lists) {
            jokeNavigator.showLoadSuccessView();
            if (isRefreshBK) {
                if (lists == null || lists.size() <= 0) {
                    jokeNavigator.loadListFailure();
                    return;
                }
            } else {
                if (lists == null || lists.size() <= 0) {
                    jokeNavigator.showListNoMoreLoading();
                    return;
                }
            }
            jokeNavigator.showAdapterView(lists);
        }

        @Override
        public void loadFailed() {
            jokeNavigator.loadListFailure();
        }

        @Override
        public void addSubscription(Subscription subscription) {
            jokeNavigator.addRxSubscription(subscription);
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
