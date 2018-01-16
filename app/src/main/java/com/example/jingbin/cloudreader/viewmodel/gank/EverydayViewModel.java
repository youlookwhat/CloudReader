package com.example.jingbin.cloudreader.viewmodel.gank;

import android.arch.lifecycle.ViewModel;

import com.example.jingbin.cloudreader.app.CloudReaderApplication;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.bean.FrontpageBean;
import com.example.jingbin.cloudreader.http.RequestImpl;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.data.model.EverydayModel;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * @author jingbin
 * @data 2017/12/15
 */

public class EverydayViewModel extends ViewModel {

    private final EverydayModel mEverydayModel;
    private EverydayNavigator loadListener;
    private BaseFragment activity;
    private ArrayList<List<AndroidBean>> mLists;
    private ArrayList<String> mBannerImages;
    private String year = getTodayTime().get(0);
    private String month = getTodayTime().get(1);
    private String day = getTodayTime().get(2);

    public void setEverydayCallback(EverydayNavigator loadListener) {
        this.loadListener = loadListener;
    }

    public void onDestroy() {
        loadListener = null;
    }

    public EverydayViewModel(BaseFragment activity) {
        this.activity = activity;
        mEverydayModel = new EverydayModel();
        mEverydayModel.setData(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
    }

    private void showRecyclerViewData() {
        mEverydayModel.showRecyclerViewData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                if (mLists != null) {
                    mLists.clear();
                }
                mLists = (ArrayList<List<AndroidBean>>) object;
                if (mLists.size() > 0 && mLists.get(0).size() > 0) {
                    loadListener.showListView(mLists);
                } else {
                    mLists = (ArrayList<List<AndroidBean>>) ACache.get(activity.getContext()).getAsObject(Constants.EVERYDAY_CONTENT);
                    if (mLists != null && mLists.size() > 0) {
                        loadListener.showListView(mLists);
                    } else {
                        // 一直请求，知道请求到数据为止
                        ArrayList<String> lastTime = TimeUtil.getLastTime(year, month, day);
                        mEverydayModel.setData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
                        year = lastTime.get(0);
                        month = lastTime.get(1);
                        day = lastTime.get(2);
                        showRecyclerViewData();
                    }
                }
            }

            @Override
            public void loadFailed() {
                if (mLists != null && mLists.size() > 0) {
                    return;
                }
                loadListener.showErrorView();
            }

            @Override
            public void addSubscription(Subscription subscription) {
                activity.addSubscription(subscription);
            }
        });
    }

    private void showBanncerPage() {
        mEverydayModel.showBanncerPage(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                if (mBannerImages == null) {
                    mBannerImages = new ArrayList<String>();
                } else {
                    mBannerImages.clear();
                }
                FrontpageBean bean = (FrontpageBean) object;
                if (bean != null && bean.getResult() != null && bean.getResult().getFocus() != null && bean.getResult().getFocus().getResult() != null) {
                    final ArrayList<FrontpageBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX> result = (ArrayList<FrontpageBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX>) bean.getResult().getFocus().getResult();
                    if (result != null && result.size() > 0) {
                        for (int i = 0; i < result.size(); i++) {
                            //获取所有图片
                            mBannerImages.add(result.get(i).getRandpic());
                        }
                        loadListener.showBannerView(mBannerImages, result);
                        ACache.get(CloudReaderApplication.getInstance()).remove(Constants.BANNER_PIC);
                        ACache.get(CloudReaderApplication.getInstance()).put(Constants.BANNER_PIC, mBannerImages);
                        ACache.get(CloudReaderApplication.getInstance()).remove(Constants.BANNER_PIC_DATA);
                        ACache.get(CloudReaderApplication.getInstance()).put(Constants.BANNER_PIC_DATA, result);
                    }
                }
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void addSubscription(Subscription subscription) {
                activity.addSubscription(subscription);
            }
        });
    }

    public void handleCache() {
        ArrayList<FrontpageBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX> result = null;
        try {
            mBannerImages = (ArrayList<String>) ACache.get(CloudReaderApplication.getInstance()).getAsObject(Constants.BANNER_PIC);
            result = (ArrayList<FrontpageBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX>) ACache.get(CloudReaderApplication.getInstance()).getAsObject(Constants.BANNER_PIC_DATA);
        } catch (Exception ignored) {
        }
        if (mBannerImages != null && mBannerImages.size() > 0) {
            // 加上缓存使其可以点击
            loadListener.showBannerView(mBannerImages, result);
        } else {
            showBanncerPage();
        }
        mLists = (ArrayList<List<AndroidBean>>) ACache.get(CloudReaderApplication.getInstance()).getAsObject(Constants.EVERYDAY_CONTENT);
        if (mLists != null && mLists.size() > 0) {
            loadListener.showListView(mLists);
        } else {
            loadListener.showRotaLoading();
            showRecyclerViewData();
        }
    }


    public void loadData() {
        String oneData = SPUtils.getString("everyday_data", "2016-11-26");
        if (!oneData.equals(TimeUtil.getData())) {// 是第二天
            if (TimeUtil.isRightTime()) {//大于12：30,请求

                loadListener.setIsOldDayRequest(false);
                mEverydayModel.setData(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
                loadListener.showRotaLoading();
                showBanncerPage();
                showRecyclerViewData();
            } else {// 小于，取缓存没有请求前一天

                ArrayList<String> lastTime = TimeUtil.getLastTime(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
                mEverydayModel.setData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
                year = lastTime.get(0);
                month = lastTime.get(1);
                day = lastTime.get(2);
                // 是昨天
                loadListener.setIsOldDayRequest(true);
                loadListener.getACacheData();
            }
        } else {// 当天，取缓存没有请求当天
            // 是昨天
            loadListener.setIsOldDayRequest(false);
            loadListener.getACacheData();
        }
    }

    /**
     * 获取当天日期
     */
    public static ArrayList<String> getTodayTime() {
        String data = TimeUtil.getData();
        String[] split = data.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        ArrayList<String> list = new ArrayList<>();
        list.add(year);
        list.add(month);
        list.add(day);
        return list;
    }
}
