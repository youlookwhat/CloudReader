package com.example.jingbin.cloudreader.ui.wan.child;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;
import com.example.xrecyclerview.XRecyclerView;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * @author jingbin
 *         Updated by jingbin on 18/02/07.
 */
public class BannerFragment extends BaseFragment<FragmentWanAndroidBinding> implements WanNavigator.BannerNavigator, WanNavigator.ArticleListNavigator {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private WanAndroidAdapter mAdapter;
    private HeaderWanAndroidBinding androidBinding;
    private WanAndroidListViewModel viewModel;
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
        viewModel = new WanAndroidListViewModel();
        viewModel.setNavigator(this);
        viewModel.setArticleListNavigator(this);
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    private void initRefreshView() {
        bindingView.srlBook.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.srlBook.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.srlBook.postDelayed(() -> {
                    viewModel.setPage(0);
                    loadCustomData();
                }, 350);

            }
        });
        bindingView.xrvBook.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.xrvBook.setPullRefreshEnabled(false);
        bindingView.xrvBook.clearHeader();
        mAdapter = new WanAndroidAdapter(getActivity());
        mAdapter.setNoImage();
        bindingView.xrvBook.setAdapter(mAdapter);
        androidBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_wan_android, null, false);
        viewModel.getWanAndroidBanner();
        bindingView.xrvBook.addHeaderView(androidBinding.getRoot());
        DensityUtil.formatBannerHeight(androidBinding.banner, androidBinding.llBannerImage);
        bindingView.xrvBook.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                loadCustomData();
            }
        });
    }

    /**
     * 设置banner图
     */
    @Override
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
    public void loadBannerFailure() {
        androidBinding.rlBanner.setVisibility(View.GONE);
    }

    @Override
    public void addRxSubscription(Subscription subscription) {
        addSubscription(subscription);
    }

    @Override
    public void loadHomeListFailure() {
        showContentView();
        if (bindingView.srlBook.isRefreshing()) {
            bindingView.srlBook.setRefreshing(false);
        }
        if (viewModel.getPage() == 0) {
            showError();
        } else {
            bindingView.xrvBook.refreshComplete();
        }
    }

    @Override
    public void showAdapterView(HomeListBean bean) {
        if (viewModel.getPage() == 0) {
            mAdapter.clear();
        }
        mAdapter.addAll(bean.getData().getDatas());
        mAdapter.notifyDataSetChanged();
        bindingView.xrvBook.refreshComplete();

        if (viewModel.getPage() == 0) {
            mIsFirst = false;
        }
    }

    @Override
    public void showListNoMoreLoading() {
        bindingView.xrvBook.noMoreLoading();
    }

    @Override
    public void showLoadSuccessView() {
        showContentView();
        bindingView.srlBook.setRefreshing(false);
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        bindingView.srlBook.setRefreshing(true);
        bindingView.srlBook.postDelayed(this::loadCustomData, 500);
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

    private void loadCustomData() {
        viewModel.getHomeList(null);
    }

    @Override
    protected void onRefresh() {
        bindingView.srlBook.setRefreshing(true);
        loadCustomData();
    }
}
