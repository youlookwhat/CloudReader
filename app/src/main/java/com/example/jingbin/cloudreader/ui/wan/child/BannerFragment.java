package com.example.jingbin.cloudreader.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.HeaderWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.GlideImageLoader;
import com.example.jingbin.cloudreader.utils.ImageLoadUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanAndroidListViewModel;
import com.example.xrecyclerview.XRecyclerView;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩安卓首页
 *
 * @author jingbin
 *         Updated on 18/02/07...18/12/22
 */
public class BannerFragment extends BaseFragment<WanAndroidListViewModel, FragmentWanAndroidBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private WanAndroidAdapter mAdapter;
    private HeaderWanAndroidBinding androidBinding;
    private boolean isLoadBanner = false;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    public static BannerFragment newInstance() {
        return new BannerFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.xrvWan.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.clearHeader();
        bindingView.xrvWan.setItemAnimator(null);
        mAdapter = new WanAndroidAdapter(getActivity());
        mAdapter.setNoImage();
        bindingView.xrvWan.setAdapter(mAdapter);
        androidBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_wan_android, null, false);
        bindingView.xrvWan.addHeaderView(androidBinding.getRoot());
        DensityUtil.formatBannerHeight(androidBinding.banner, androidBinding.llBannerImage);
        bindingView.srlWan.setOnRefreshListener(this::swipeRefresh);
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                getHomeList();
            }
        });
        viewModel.getWanAndroidBanner().observe(this, new Observer<WanAndroidBannerBean>() {
            @Override
            public void onChanged(@Nullable WanAndroidBannerBean bean) {
                if (bean != null) {
                    showBannerView(bean.getmBannerImages(), bean.getmBannerTitles(), bean.getData());
                    androidBinding.rlBanner.setVisibility(View.VISIBLE);
                } else {
                    androidBinding.rlBanner.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void swipeRefresh() {
        bindingView.srlWan.postDelayed(() -> {
            viewModel.setPage(0);
            bindingView.xrvWan.reset();
            getHomeList();
        }, 350);
    }

    /**
     * 设置banner图
     */
    public void showBannerView(ArrayList<String> bannerImages, ArrayList<String> mBannerTitle, List<WanAndroidBannerBean.DataBean> result) {
        androidBinding.rlBanner.setVisibility(View.VISIBLE);
        androidBinding.banner.setBannerTitles(mBannerTitle);
        androidBinding.banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        androidBinding.banner.setImages(bannerImages).setImageLoader(new GlideImageLoader()).start();
        androidBinding.banner.setOnBannerListener(position -> {
            if (result.get(position) != null && !TextUtils.isEmpty(result.get(position).getUrl())) {
                WebViewActivity.loadUrl(getContext(), result.get(position).getUrl(), result.get(position).getTitle());
            }
        });
        int size = bannerImages.size();
        int position1 = 0;
        int position2 = 0;
        if (size > 1) {
            position1 = size - 2;
            position2 = size - 1;
        }
        ImageLoadUtil.displayFadeImage(androidBinding.ivBannerOne, bannerImages.get(position1), 3);
        ImageLoadUtil.displayFadeImage(androidBinding.ivBannerTwo, bannerImages.get(position2), 3);
        int finalPosition = position1;
        int finalPosition2 = position2;
        androidBinding.ivBannerOne.setOnClickListener(v -> WebViewActivity.loadUrl(getContext(), result.get(finalPosition).getUrl(), result.get(finalPosition).getTitle()));
        androidBinding.ivBannerTwo.setOnClickListener(v -> WebViewActivity.loadUrl(getContext(), result.get(finalPosition2).getUrl(), result.get(finalPosition2).getTitle()));
        isLoadBanner = true;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getHomeList, 500);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoadBanner) {
            androidBinding.banner.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 不可见时轮播图停止滚动
        if (isLoadBanner) {
            androidBinding.banner.stopAutoPlay();
        }
    }

    private void getHomeList() {
        viewModel.getHomeList(null).observe(this, new Observer<HomeListBean>() {
            @Override
            public void onChanged(@Nullable HomeListBean homeListBean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }

                if (homeListBean != null
                        && homeListBean.getData() != null
                        && homeListBean.getData().getDatas() != null
                        && homeListBean.getData().getDatas().size() > 0) {
                    if (viewModel.getPage() == 0) {
                        showContentView();
                        mAdapter.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    //  一个刷新头布局 一个header
                    int positionStart = mAdapter.getItemCount() + 2;
                    mAdapter.addAll(homeListBean.getData().getDatas());
                    mAdapter.notifyItemRangeInserted(positionStart, homeListBean.getData().getDatas().size());
                    bindingView.xrvWan.refreshComplete();

                    if (viewModel.getPage() == 0) {
                        mIsFirst = false;
                    }
                } else {
                    if (viewModel.getPage() == 0) {
                        showError();
                    } else {
                        bindingView.xrvWan.refreshComplete();
                        bindingView.xrvWan.noMoreLoading();
                    }
                }
            }
        });
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getHomeList();
    }
}
