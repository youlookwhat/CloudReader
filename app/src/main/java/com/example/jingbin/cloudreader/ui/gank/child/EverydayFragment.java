package com.example.jingbin.cloudreader.ui.gank.child;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.EverydayAdapter;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.bean.FrontpageBean;
import com.example.jingbin.cloudreader.databinding.FooterItemEverydayBinding;
import com.example.jingbin.cloudreader.databinding.FragmentEverydayBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemEverydayBinding;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.model.EverydayModel;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.GlideImageLoader;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * 每日推荐
 */
public class EverydayFragment extends BaseFragment<FragmentEverydayBinding> {

    private static final String TAG = "EverydayFragment";
    private ACache maCache;
    private ArrayList<List<AndroidBean>> mLists;
    private ArrayList<String> mBannerImages;
    private EverydayModel mEverydayModel;
    private boolean mIsPrepared = false;
    private boolean mIsFirst = true;
    private HeaderItemEverydayBinding mHeaderBinding;
    private FooterItemEverydayBinding mFooterBinding;
    // 是否正在刷新（避免重复刷新）
    private boolean mIsLoading = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoading();
        maCache = ACache.get(getContext());

        mBannerImages = (ArrayList<String>) maCache.getAsObject(Constants.BANNER_PIC);
        mLists = (ArrayList<List<AndroidBean>>) maCache.getAsObject(Constants.EVERYDAY_CONTENT);
        DebugUtil.error("----mBannerImages: " + (mBannerImages == null));
        DebugUtil.error("----mLists: " + (mLists == null));

        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.header_item_everyday, null, false);
        // 设置本地数据点击事件等
        initLocalSetting();

        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_everyday;
    }

    private void initLocalSetting() {
        String data = TimeUtil.getData();
        String[] split = data.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        mEverydayModel = new EverydayModel(year, month, day);
//        DebugUtil.error("" + year + month + day);
        // 显示日期
        mHeaderBinding.includeEveryday.tvDailyText.setText(day);
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

        // 显示，准备完毕，不是当天，则请求所有数据
        String oneData = SPUtils.getString("everyday_data", "2016-11-26");
        if (!oneData.equals(TimeUtil.getData()) && !mIsLoading) {
            mIsLoading = true;
            showLoading();
            loadBannerPicture();
            showContentData();
            return;
        }

        if (!mIsFirst) {
            return;
        }

        if (mBannerImages != null && mBannerImages.size() > 0) {
            mHeaderBinding.banner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
        } else {
            loadBannerPicture();
        }
        if (mLists != null && mLists.size() > 0) {
            setAdapter(mLists);
        } else {
            showContentData();
        }
    }

    /**
     * 加载正文内容
     */
    private void showContentData() {
        mEverydayModel.showRecyclerViewData(new EverydayModel.HomeImpl() {
            @Override
            public void loadSuccess(Object object) {
                if (mLists != null) {
                    mLists.clear();
                }
                mLists = (ArrayList<List<AndroidBean>>) object;
                setAdapter(mLists);
            }

            @Override
            public void loadFailed() {
                showError();
            }

            @Override
            public void addSubscription(Subscription subscription) {
                EverydayFragment.this.addSubscription(subscription);
            }
        });
    }

    private void setAdapter(ArrayList<List<AndroidBean>> lists) {
        showContentView();
        bindingView.xrvEveryday.setVisibility(View.VISIBLE);
        bindingView.xrvEveryday.setPullRefreshEnabled(false);
        bindingView.xrvEveryday.setLoadingMoreEnabled(false);
        bindingView.xrvEveryday.addHeaderView(mHeaderBinding.getRoot());
        if (mFooterBinding == null) {
            mFooterBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.footer_item_everyday, null, false);
        }
        bindingView.xrvEveryday.addFootView(mFooterBinding.getRoot(), true);
        bindingView.xrvEveryday.noMoreLoading();

        final EverydayAdapter everydayAdapter = new EverydayAdapter();
        everydayAdapter.addAll(lists);
        bindingView.xrvEveryday.setLayoutManager(new LinearLayoutManager(getContext()));
        // 需加，不然滑动不流畅
        bindingView.xrvEveryday.setNestedScrollingEnabled(false);
        bindingView.xrvEveryday.setHasFixedSize(false);
        bindingView.xrvEveryday.setItemAnimator(new DefaultItemAnimator());
        bindingView.xrvEveryday.setAdapter(everydayAdapter);
        everydayAdapter.notifyDataSetChanged();

        maCache.remove(Constants.EVERYDAY_CONTENT);
        maCache.put(Constants.EVERYDAY_CONTENT, lists, 43200);
        // 保存请求的日期
        SPUtils.putString("everyday_data", TimeUtil.getData());

        mIsFirst = false;
        mIsLoading = false;

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
        // 失去焦点，否则recyclerview第一个item会回到顶部
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
        mEverydayModel.showBanncerPage(new EverydayModel.HomeImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
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
                    maCache.put(Constants.BANNER_PIC, mBannerImages, 3000);
                }
            }

            @Override
            public void loadFailed() {
                showError();
            }

            @Override
            public void addSubscription(Subscription subscription) {
                EverydayFragment.this.addSubscription(subscription);
            }
        });
    }

    @Override
    protected void onRefresh() {
        loadBannerPicture();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--EverydayFragment   ----onDestroy");
    }

}
