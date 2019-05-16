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
import com.example.jingbin.cloudreader.utils.DebugUtil;
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
public class BannerFragment extends BaseFragment<WanAndroidListViewModel, FragmentWanAndroidBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private WanAndroidAdapter mAdapter;
    private HeaderWanAndroidBinding androidBinding;
    private boolean isLoadBanner = false;
    // banner图的宽
    private int width;

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
        RefreshHelper.init(bindingView.xrvWan, false, false);
        androidBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_wan_android, null, false);
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mAdapter = new WanAndroidAdapter(getActivity());
        mAdapter.setNoImage();
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.xrvWan.addHeaderView(androidBinding.getRoot());
        width = DensityUtil.getDisplayWidth() - DensityUtil.dip2px(160);
        float height = width / 1.8f;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
        androidBinding.banner.setLayoutParams(lp);

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
                    getHomeList();
                } else {
                    bindingView.xrvWan.refreshComplete();
                }
            }
        });
        viewModel.getWanAndroidBanner().observe(this, new Observer<WanAndroidBannerBean>() {
            @Override
            public void onChanged(@Nullable WanAndroidBannerBean bean) {
                if (bean != null) {
                    androidBinding.rlBanner.setVisibility(View.VISIBLE);
                    showBannerView(bean.getData());
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
    public void showBannerView(List<WanAndroidBannerBean.DataBean> result) {
        androidBinding.rlBanner.setVisibility(View.VISIBLE);
        androidBinding.banner
                .setIndicatorRes(R.drawable.banner_red, R.drawable.banner_grey)
                .setBannerAnimation(ScaleRightTransformer.class)
                .setDelayTime(5000)
                .setPages(result, CustomViewHolder::new)
                .start();
        androidBinding.banner.stopAutoPlay();
        isLoadBanner = true;
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

                    if (mIsFirst && viewModel.getPage() == 0) {
                        if (isLoadBanner) {
                            androidBinding.banner.startAutoPlay();
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
        });
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getHomeList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isLoadBanner) {
            androidBinding.banner.stopAutoPlay();
            androidBinding.banner.releaseBanner();
        }
        if (mAdapter != null) {
            mAdapter.clear();
            mAdapter = null;
        }
    }
}
