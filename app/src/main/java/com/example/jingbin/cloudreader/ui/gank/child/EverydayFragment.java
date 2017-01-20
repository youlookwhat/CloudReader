package com.example.jingbin.cloudreader.ui.gank.child;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.bumptech.glide.Glide;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.EmptyAdapter;
import com.example.jingbin.cloudreader.adapter.EverydayAdapter;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.bean.FrontpageBean;
import com.example.jingbin.cloudreader.databinding.FooterItemEverydayBinding;
import com.example.jingbin.cloudreader.databinding.FragmentEverydayBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemEverydayBinding;
import com.example.jingbin.cloudreader.http.RequestImpl;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.http.rx.RxBus;
import com.example.jingbin.cloudreader.http.rx.RxBusBaseMessage;
import com.example.jingbin.cloudreader.http.rx.RxCodeConstants;
import com.example.jingbin.cloudreader.model.EverydayModel;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.GlideImageLoader;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.TimeUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * 每日推荐
 * 更新逻辑：判断是否是第二天(相对于2016-11-26)
 * 是：判断是否是大于12：30
 * *****     |是：刷新当天数据
 * *****     |否：使用缓存：|无：请求前一天数据,直到请求到数据为止
 * **********             |有：使用缓存
 * 否：使用缓存 ： |无：请求今天数据
 * **********    |有：使用缓存
 */
public class EverydayFragment extends BaseFragment<FragmentEverydayBinding> {

    private static final String TAG = "EverydayFragment";
    private ACache maCache;
    private ArrayList<List<AndroidBean>> mLists;
    private ArrayList<String> mBannerImages;
    private EverydayModel mEverydayModel;
    private HeaderItemEverydayBinding mHeaderBinding;
    private FooterItemEverydayBinding mFooterBinding;
    private View mHeaderView;
    private View mFooterView;
    private EverydayAdapter mEverydayAdapter;
    private boolean mIsPrepared = false;
    private boolean mIsFirst = true;
    // 是否是上一天的请求
    private boolean isOldDayRequest;
    private RotateAnimation animation;
    // 记录请求的日期
    String year = getTodayTime().get(0);
    String month = getTodayTime().get(1);
    String day = getTodayTime().get(2);

    @Override
    public int setContent() {
        return R.layout.fragment_everyday;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        showLoading();
        showContentView();
        bindingView.llLoading.setVisibility(View.VISIBLE);
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());//不停顿
        animation.setRepeatCount(10);
        bindingView.ivLoading.setAnimation(animation);
        animation.startNow();

        maCache = ACache.get(getContext());
        mEverydayModel = new EverydayModel();
        mBannerImages = (ArrayList<String>) maCache.getAsObject(Constants.BANNER_PIC);
//        mLists = (ArrayList<List<AndroidBean>>) maCache.getAsObject(Constants.EVERYDAY_CONTENT);
        DebugUtil.error("----mBannerImages: " + (mBannerImages == null));
        DebugUtil.error("----mLists: " + (mLists == null));

        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.header_item_everyday, null, false);
        // 设置本地数据点击事件等
        initLocalSetting();
        initRecyclerView();

        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    /**
     * 获取当天日期
     */
    private ArrayList<String> getTodayTime() {
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
    protected void loadData() {
        // 显示时轮播图滚动
        if (mHeaderBinding != null && mHeaderBinding.banner != null) {
            mHeaderBinding.banner.startAutoPlay();
            mHeaderBinding.banner.setDelayTime(5000);
        }

        if (!mIsVisible || !mIsPrepared) {
            return;
        }

        String oneData = SPUtils.getString("everyday_data", "2016-11-26");
        if (!oneData.equals(TimeUtil.getData())) {// 是第二天
            if (TimeUtil.isRightTime()) {//大于12：30,请求

                isOldDayRequest = false;
                mEverydayModel.setData(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
                showRotaLoading(true);
                loadBannerPicture();
                showContentData();
            } else {// 小于，取缓存没有请求前一天

                ArrayList<String> lastTime = TimeUtil.getLastTime(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
                mEverydayModel.setData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
                year = lastTime.get(0);
                month = lastTime.get(1);
                day = lastTime.get(2);

                isOldDayRequest = true;// 是昨天
                getACacheData();
            }
        } else {// 当天，取缓存没有请求当天

            isOldDayRequest = false;
            getACacheData();
        }
    }

    private void initLocalSetting() {
        mEverydayModel.setData(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
//        DebugUtil.error("" + year + month + day);
        // 显示日期,去掉第一位的"0"
        mHeaderBinding.includeEveryday.tvDailyText.setText(getTodayTime().get(2).indexOf("0") == 0 ?
                getTodayTime().get(2).replace("0", "") : getTodayTime().get(2));
        mHeaderBinding.includeEveryday.ibXiandu.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), "https://gank.io/xiandu", "加载中...");
            }
        });
        mHeaderBinding.includeEveryday.ibMovieHot.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE_TO_ONE, new RxBusBaseMessage());
            }
        });
    }

    /**
     * 取缓存
     */
    private void getACacheData() {
        if (!mIsFirst) {
            return;
        }

        if (mBannerImages != null && mBannerImages.size() > 0) {
            mHeaderBinding.banner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
        } else {
            loadBannerPicture();
        }
        mLists = (ArrayList<List<AndroidBean>>) maCache.getAsObject(Constants.EVERYDAY_CONTENT);
        if (mLists != null && mLists.size() > 0) {
            setAdapter(mLists);
        } else {
            showRotaLoading(true);
            showContentData();
        }
    }


    /**
     * 加载正文内容
     */
    private void showContentData() {
        mEverydayModel.showRecyclerViewData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                if (mLists != null) {
                    mLists.clear();
                }
                mLists = (ArrayList<List<AndroidBean>>) object;
                if (mLists.size() > 0 && mLists.get(0).size() > 0) {
                    setAdapter(mLists);
                } else {
                    requestBeforeData();
                }
            }

            @Override
            public void loadFailed() {
                if (mLists != null && mLists.size() > 0) {
                    return;
                }
                showError();
            }

            @Override
            public void addSubscription(Subscription subscription) {
                EverydayFragment.this.addSubscription(subscription);
            }
        });
    }

    /**
     * 没请求到数据就取缓存，没缓存一直请求前一天数据
     */
    private void requestBeforeData() {
        mLists = (ArrayList<List<AndroidBean>>) maCache.getAsObject(Constants.EVERYDAY_CONTENT);
        if (mLists != null && mLists.size() > 0) {
            setAdapter(mLists);
        } else {
            // 一直请求，知道请求到数据为止
            ArrayList<String> lastTime = TimeUtil.getLastTime(year, month, day);
            mEverydayModel.setData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
            year = lastTime.get(0);
            month = lastTime.get(1);
            day = lastTime.get(2);
            showContentData();

//            if (mEverydayAdapter != null) {
//                mEverydayAdapter = null;
//            }
//            setEmptyAdapter();
        }
    }

    /**
     * 无数据返回时，暂时去掉
     */
    private void setEmptyAdapter() {
        showRotaLoading(false);

        EmptyAdapter emptyAdapter = new EmptyAdapter();
        ArrayList<String> list = new ArrayList<>();
        list.add(CommonUtils.getString(R.string.string_everyday_empty));
        emptyAdapter.addAll(list);
        bindingView.xrvEveryday.setAdapter(emptyAdapter);

        // 保存请求的日期
        SPUtils.putString("everyday_data", TimeUtil.getData());
//        ArrayList<String> lastTime = TimeUtil.getLastTime(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
//        SPUtils.putString("everyday_data", lastTime.get(0) + "-" + lastTime.get(1) + "-" + lastTime.get(2));

        mIsFirst = false;
    }

    private void initRecyclerView() {
        bindingView.xrvEveryday.setPullRefreshEnabled(false);
        bindingView.xrvEveryday.setLoadingMoreEnabled(false);
        if (mHeaderView == null) {
            mHeaderView = mHeaderBinding.getRoot();
            bindingView.xrvEveryday.addHeaderView(mHeaderView);
        }
        if (mFooterView == null) {
            mFooterBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.footer_item_everyday, null, false);
            mFooterView = mFooterBinding.getRoot();
            bindingView.xrvEveryday.addFootView(mFooterView, true);
            bindingView.xrvEveryday.noMoreLoading();
        }
        bindingView.xrvEveryday.setLayoutManager(new LinearLayoutManager(getContext()));
        // 需加，不然滑动不流畅
        bindingView.xrvEveryday.setNestedScrollingEnabled(false);
        bindingView.xrvEveryday.setHasFixedSize(false);
        bindingView.xrvEveryday.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 当第二天12：30之后请求走这里时，走到
     * bindingView.xrvEveryday.setAdapter(mEverydayAdapter);
     * 就停了！！暂时未找到什么原因！
     */
    private void setAdapter(ArrayList<List<AndroidBean>> lists) {
//        DebugUtil.error("----lists.size():  " + lists.size());
        showRotaLoading(false);
        if (mEverydayAdapter == null) {
            mEverydayAdapter = new EverydayAdapter();
        } else {
            mEverydayAdapter.clear();
        }
        mEverydayAdapter.addAll(lists);
//        DebugUtil.error("----111111 ");
//        bindingView.xrvEveryday.setAdapter(mEverydayAdapter);
//        mEverydayAdapter.notifyDataSetChanged();
//        DebugUtil.error("----222222 ");
        maCache.remove(Constants.EVERYDAY_CONTENT);
        // 缓存三天，这样就可以取到缓存了！
        maCache.put(Constants.EVERYDAY_CONTENT, lists, 259200);

        if (isOldDayRequest) {
            ArrayList<String> lastTime = TimeUtil.getLastTime(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
            SPUtils.putString("everyday_data", lastTime.get(0) + "-" + lastTime.get(1) + "-" + lastTime.get(2));
        } else {
            // 保存请求的日期
            SPUtils.putString("everyday_data", TimeUtil.getData());
        }
        mIsFirst = false;

        bindingView.xrvEveryday.setAdapter(mEverydayAdapter);
        mEverydayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onInvisible() {
        // 不可见时轮播图停止滚动
        if (mHeaderBinding != null && mHeaderBinding.banner != null) {
            mHeaderBinding.banner.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 失去焦点，否则RecyclerView第一个item会回到顶部
        bindingView.xrvEveryday.setFocusable(false);
        DebugUtil.error("-----EverydayFragment----onResume()");
        // 开始图片请求
        Glide.with(getActivity()).resumeRequests();
    }

    @Override
    public void onPause() {
        super.onPause();
        DebugUtil.error("-----EverydayFragment----onPause()");
        // 停止全部图片请求 跟随着Activity
        Glide.with(getActivity()).pauseRequests();

    }

    /**
     * 轮播图
     */
    private void loadBannerPicture() {
        mEverydayModel.showBanncerPage(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                if (mBannerImages == null) {
                    mBannerImages = new ArrayList<String>();
                } else {
                    mBannerImages.clear();
                }
                FrontpageBean frontpageBean = (FrontpageBean) object;
                List<FrontpageBean.DataBeanX.DataBean> data = null;
                if (frontpageBean != null
                        && frontpageBean.getData() != null
                        && frontpageBean.getData().get(0) != null
                        && frontpageBean.getData().get(0).getData() != null) {

                    data = frontpageBean.getData().get(0).getData();
                }
                if (data != null && data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        //获取所有图片
                        FrontpageBean.DataBeanX.DataBean bean = data.get(i);
                        mBannerImages.add(bean.getPicUrl());
                    }
                    mHeaderBinding.banner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
                    maCache.remove(Constants.BANNER_PIC);
                    maCache.put(Constants.BANNER_PIC, mBannerImages, 30000);
                }
            }

            @Override
            public void loadFailed() {
//                showError();
            }

            @Override
            public void addSubscription(Subscription subscription) {
                EverydayFragment.this.addSubscription(subscription);
            }
        });
    }

    private void showRotaLoading(boolean isLoading) {
        if (isLoading) {
            bindingView.llLoading.setVisibility(View.VISIBLE);
            bindingView.xrvEveryday.setVisibility(View.GONE);
            animation.startNow();
        } else {
            bindingView.llLoading.setVisibility(View.GONE);
            bindingView.xrvEveryday.setVisibility(View.VISIBLE);
            animation.cancel();
        }
    }

    @Override
    protected void onRefresh() {
        showContentView();
        showRotaLoading(true);
        loadData();
//        loadBannerPicture();
//        showContentData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--EverydayFragment   ----onDestroy");
    }

}
