package com.example.jingbin.cloudreader.viewmodel.gank;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.example.jingbin.cloudreader.app.App;
import com.example.jingbin.cloudreader.app.Constants;
import me.jingbin.bymvvm.base.BaseViewModel;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.bean.BannerItemBean;
import com.example.jingbin.cloudreader.bean.FrontpageBean;
import com.example.jingbin.cloudreader.data.model.EverydayModel;
import com.example.jingbin.cloudreader.http.RequestImpl;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.TimeUtil;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * @author jingbin
 * @data 2017/12/15
 */
public class EverydayViewModel extends BaseViewModel {

    private EverydayModel mEverydayModel;
    private ACache maCache;
    private ArrayList<ArrayList<AndroidBean>> mLists;
    private ArrayList<String> mBannerImages;
    private String year;
    private String month;
    private String day;
    private BannerDataBean bannerDataBean = new BannerDataBean();
    // 是否是上一天的请求
    private boolean isOldDayRequest;
    // 是否已经展示了数据
    private boolean isHaveData = false;

    private final MutableLiveData<Boolean> isShowLoading = new MutableLiveData<>();
    private final MutableLiveData<BannerDataBean> bannerData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<ArrayList<AndroidBean>>> contentData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getShowLoading() {
        return isShowLoading;
    }

    public MutableLiveData<BannerDataBean> getBannerData() {
        return bannerData;
    }

    public MutableLiveData<ArrayList<ArrayList<AndroidBean>>> getContentData() {
        return contentData;
    }

    public EverydayViewModel(@NonNull Application application) {
        super(application);
        maCache = ACache.get(App.getInstance());
        mEverydayModel = new EverydayModel();
        year = getTodayTime().get(0);
        month = getTodayTime().get(1);
        day = getTodayTime().get(2);
        mEverydayModel.setData(year, month, day);
    }

    /**
     * 最后请求的时间是否是今天：
     * 不是：
     * ---- 大于12:30：请求数据，保存时间为今天
     * ---- 小于12:30: 取缓存，没有数据请求数据，保存时间为之前的时间
     * 是：
     * ---- 已经展示数据 - 不处理
     * ---- 没有展示数据 - 使用缓存
     */
    public void loadData() {
        String oneData = SPUtils.getString("everyday_data", "2016-11-26");
        if (!oneData.equals(TimeUtil.getData())) {
            // 不是今天
            isShowLoading.setValue(true);
            if (TimeUtil.isRightTime()) {
                //大于12：30,请求
                isOldDayRequest = false;
                mEverydayModel.setData(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
                showBannerPage();
                showRecyclerViewData();

            } else {
                // 小于，取缓存没有请求前一天
                ArrayList<String> lastTime = TimeUtil.getLastTime(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
                mEverydayModel.setData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
                year = lastTime.get(0);
                month = lastTime.get(1);
                day = lastTime.get(2);
                // 是昨天
                isOldDayRequest = true;
                handleCache();
            }
        } else {
            /** 当天：是否已经显示数据；是 不处理，否 取缓存没有请求当天*/
            isOldDayRequest = false;
            if (!isHaveData) {
                handleCache();
            }
        }
    }

    /**
     * 内容部分
     */
    private void showRecyclerViewData() {
        mEverydayModel.showRecyclerViewData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                if (mLists != null) {
                    mLists.clear();
                }
                mLists = (ArrayList<ArrayList<AndroidBean>>) object;
                if (mLists.size() > 0 && mLists.get(0).size() > 0) {
                    maCache.remove(Constants.EVERYDAY_CONTENT);
                    maCache.put(Constants.EVERYDAY_CONTENT, mLists);
                    saveDate();
                    contentData.setValue(mLists);
                } else {
                    mLists = (ArrayList<ArrayList<AndroidBean>>) maCache.getAsObject(Constants.EVERYDAY_CONTENT);
                    if (mLists != null && mLists.size() > 0) {
                        saveDate();
                        contentData.setValue(mLists);
                    } else {
                        // 一直请求，直到请求到数据为止
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
                handleNoData();
            }

            @Override
            public void addSubscription(Disposable subscription) {
                addDisposable(subscription);
            }
        });
    }

    /**
     * banner数据
     */
    private void showBannerPage() {
        mEverydayModel.showBannerPage(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                FrontpageBean bean = (FrontpageBean) object;
                if (bean != null && bean.getResult() != null && bean.getResult().getFocus() != null && bean.getResult().getFocus().getResult() != null) {
                    final ArrayList<BannerItemBean> result = (ArrayList<BannerItemBean>) bean.getResult().getFocus().getResult();
                    ArrayList<String> mBannerImages = new ArrayList<String>();
                    if (result != null && result.size() > 0) {
                        for (int i = 0; i < result.size(); i++) {
                            //获取所有图片
                            mBannerImages.add(result.get(i).getRandpic());
                        }
                        maCache.put(Constants.BANNER_PIC, mBannerImages);
                        maCache.put(Constants.BANNER_PIC_DATA, result);
                        bannerDataBean.setData(mBannerImages, result);

                        bannerData.setValue(bannerDataBean);
                    }
                }
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void addSubscription(Disposable subscription) {
                addDisposable(subscription);
            }
        });
    }

    private void handleNoData() {
        mLists = (ArrayList<ArrayList<AndroidBean>>) maCache.getAsObject(Constants.EVERYDAY_CONTENT);
        if (mLists != null && mLists.size() > 0) {
            saveDate();
            contentData.setValue(mLists);
        } else {
            isShowLoading.setValue(false);
            contentData.setValue(null);
        }
    }

    private void handleCache() {
        ArrayList<BannerItemBean> result = null;
        ArrayList<String> mBannerImages = null;
        try {
            mBannerImages = (ArrayList<String>) maCache.getAsObject(Constants.BANNER_PIC);
            result = (ArrayList<BannerItemBean>) maCache.getAsObject(Constants.BANNER_PIC_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mBannerImages != null && mBannerImages.size() > 0) {
            // 加上缓存使其可以点击
            bannerDataBean.setData(mBannerImages, result);
            bannerData.setValue(bannerDataBean);
        } else {
            showBannerPage();
        }
        mLists = (ArrayList<ArrayList<AndroidBean>>) maCache.getAsObject(Constants.EVERYDAY_CONTENT);
        if (mLists != null && mLists.size() > 0) {
            saveDate();
            contentData.setValue(mLists);
        } else {
            showRecyclerViewData();
        }
    }

    /**
     * 保存时间
     */
    private void saveDate() {
        isHaveData = true;
        isShowLoading.setValue(false);
        if (isOldDayRequest) {
            ArrayList<String> lastTime = TimeUtil.getLastTime(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
            SPUtils.putString("everyday_data", lastTime.get(0) + "-" + lastTime.get(1) + "-" + lastTime.get(2));
        } else {
            // 保存请求的日期
            SPUtils.putString("everyday_data", TimeUtil.getData());
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

    @Override
    protected void onCleared() {
        super.onCleared();
        isHaveData = false;
        mEverydayModel = null;
        if (mLists != null) {
            mLists.clear();
            mLists = null;
        }
        if (mBannerImages != null) {
            mBannerImages.clear();
            mBannerImages = null;
        }
    }

    public class BannerDataBean {

        private ArrayList<String> imageUrls;
        private ArrayList<BannerItemBean> list;

        protected void setData(ArrayList<String> imageUrls, ArrayList<BannerItemBean> list) {
            this.imageUrls = imageUrls;
            this.list = list;
        }

        public ArrayList<String> getImageUrls() {
            return imageUrls;
        }


        public ArrayList<BannerItemBean> getList() {
            return list;
        }

    }
}
