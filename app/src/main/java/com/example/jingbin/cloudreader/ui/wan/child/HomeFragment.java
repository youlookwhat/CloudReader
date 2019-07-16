package com.example.jingbin.cloudreader.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.HeaderWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.GlideUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanAndroidListViewModel;
import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import me.jingbin.sbanner.config.ScaleRightTransformer;
import me.jingbin.sbanner.holder.BannerViewHolder;


/**
 * 玩安卓首页
 *
 * @author jingbin
 * Updated on 18/02/07...19/05/16
 */
public class HomeFragment extends BaseFragment<WanAndroidListViewModel, FragmentWanAndroidBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private WanAndroidAdapter mAdapter;
    private HeaderWanAndroidBinding headerBinding;
    private boolean isArticle = true;
    private boolean isLoadBanner = false;
    // banner图的宽
    private int width;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        RefreshHelper.init(bindingView.xrvWan, false, false);
        headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_wan_android, null, false);
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mAdapter = new WanAndroidAdapter(getActivity());
        bindingView.xrvWan.setAdapter(mAdapter);
        mAdapter.setNoImage(true);
        bindingView.xrvWan.addHeaderView(headerBinding.getRoot());
        width = DensityUtil.getDisplayWidth() - DensityUtil.dip2px(160);
        float height = width / 1.8f;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
        headerBinding.banner.setLayoutParams(lp);
        headerBinding.radioGroup.setVisibility(View.INVISIBLE);

        headerBinding.rb1.setOnCheckedChangeListener((buttonView, isChecked) -> refresh(isChecked, true));
        headerBinding.rb2.setOnCheckedChangeListener((buttonView, isChecked) -> refresh(isChecked, false));
        bindingView.srlWan.setOnRefreshListener(this::swipeRefresh);
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (!bindingView.srlWan.isRefreshing()) {
                    int page = viewModel.getPage();
                    viewModel.setPage(++page);
                    if (headerBinding.rb1.isChecked()) {
                        getHomeArticleList();
                    } else {
                        getHomeProjectList();
                    }
                } else {
                    bindingView.xrvWan.refreshComplete();
                }
            }
        });
    }

    private void refresh(boolean isChecked, boolean isArticle) {
        if (isChecked) {
            bindingView.srlWan.setRefreshing(true);
            viewModel.setPage(0);
            bindingView.xrvWan.reset();
            mAdapter.setNoImage(isArticle);
            if (isArticle) {
                getHomeArticleList();
            } else {
                getHomeProjectList();
            }
        }
    }

    /**
     * 下拉刷新
     */
    private void swipeRefresh() {
        bindingView.srlWan.postDelayed(() -> {
            viewModel.setPage(0);
            bindingView.xrvWan.reset();
            mAdapter.setNoImage(isArticle);
            if (headerBinding.rb1.isChecked()) {
                getHomeArticleList();
            } else {
                getHomeProjectList();
            }
        }, 350);
    }

    /**
     * 设置banner图
     */
    public void showBannerView(List<WanAndroidBannerBean.DataBean> result) {
        if (!isLoadBanner) {
            headerBinding.banner
                    .setIndicatorRes(R.drawable.banner_red, R.drawable.banner_grey)
                    .setBannerAnimation(ScaleRightTransformer.class)
                    .setDelayTime(5000)
                    .setPages(result, CustomViewHolder::new)
                    .start();
            headerBinding.banner.stopAutoPlay();
            isLoadBanner = true;
        } else {
            headerBinding.banner.update(result);
        }
    }

    class CustomViewHolder implements BannerViewHolder<WanAndroidBannerBean.DataBean> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_wanandroid, null);
            imageView = view.findViewById(R.id.iv_banner);
            return view;
        }

        @Override
        public void onBind(Context context, int position, WanAndroidBannerBean.DataBean data) {
            if (data != null) {
                DensityUtil.formatHeight(imageView, width, 1.8f, 3);
                GlideUtil.displayEspImage(data.getImagePath(), imageView, 3);
                imageView.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        WebViewActivity.loadUrl(getContext(), data.getUrl(), data.getTitle());
                    }
                });
            }
        }
    }

    @Override
    protected void loadData() {
        if (mIsPrepared && isLoadBanner) {
            onResume();
        }
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getWanAndroidBanner, 500);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoadBanner) {
            headerBinding.banner.startAutoPlay();
        }
    }

    @Override
    protected void onInvisible() {
        if (mIsPrepared && isLoadBanner) {
            onPause();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 不可见时轮播图停止滚动
        if (isLoadBanner) {
            headerBinding.banner.stopAutoPlay();
        }
    }

    public void getWanAndroidBanner() {
        viewModel.getWanAndroidBanner().observe(this, new Observer<WanAndroidBannerBean>() {
            @Override
            public void onChanged(@Nullable WanAndroidBannerBean bean) {
                if (bean != null) {
                    headerBinding.rlBanner.setVisibility(View.VISIBLE);
                    showBannerView(bean.getData());
                } else {
                    headerBinding.rlBanner.setVisibility(View.GONE);
                }
                headerBinding.radioGroup.setVisibility(View.VISIBLE);
                getHomeArticleList();
            }
        });
    }

    private void getHomeArticleList() {
        viewModel.getHomeArticleList(null).observe(this, observer);
    }

    private void getHomeProjectList() {
        viewModel.getHomeProjectList().observe(this, observer);
    }

    private Observer<HomeListBean> observer = new Observer<HomeListBean>() {
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
                    mAdapter.addAll(homeListBean.getData().getDatas());
                    mAdapter.notifyDataSetChanged();
                } else {
                    //  一个刷新头布局 一个header
                    int positionStart = mAdapter.getItemCount() + 2;
                    mAdapter.addAll(homeListBean.getData().getDatas());
                    mAdapter.notifyItemRangeInserted(positionStart, homeListBean.getData().getDatas().size());
                    bindingView.xrvWan.refreshComplete();
                }

                if (mIsFirst && viewModel.getPage() == 0) {
                    if (isLoadBanner) {
                        headerBinding.banner.startAutoPlay();
                    }
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
    };

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getWanAndroidBanner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isLoadBanner) {
            headerBinding.banner.stopAutoPlay();
            headerBinding.banner.releaseBanner();
            isLoadBanner = false;
        }
        if (mAdapter != null) {
            mAdapter.clear();
            mAdapter = null;
        }
    }
}
