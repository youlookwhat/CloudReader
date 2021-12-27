package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.HeaderWanAndroidBinding;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.GlideUtil;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.viewmodel.wan.WanAndroidListViewModel;

import java.util.ArrayList;
import java.util.List;

import me.jingbin.banner.config.BannerConfig;
import me.jingbin.banner.config.ScaleRightTransformer;
import me.jingbin.banner.holder.ByBannerViewHolder;
import me.jingbin.bymvvm.base.BaseFragment;
import me.jingbin.library.ByRecyclerView;
import me.jingbin.library.skeleton.ByRVItemSkeletonScreen;
import me.jingbin.library.skeleton.BySkeleton;


/**
 * 玩安卓首页
 *
 * @author jingbin
 * Updated on 18/02/07...19/05/16
 */
public class HomeFragment extends BaseFragment<WanAndroidListViewModel, FragmentWanAndroidBinding> {

    private boolean mIsFirst = true;
    private WanAndroidAdapter mAdapter;
    private HeaderWanAndroidBinding headerBinding;
    private boolean isLoadBanner = false;
    // banner图的宽
    private int width;
    private FragmentActivity activity;
    private ByRVItemSkeletonScreen skeletonScreen;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

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

        loadData();
    }

    private void initRefreshView() {
        RefreshHelper.initLinear(bindingView.xrvWan, true, 1);
        RefreshHelper.setSwipeRefreshView(bindingView.srlWan);
        headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_wan_android, (ViewGroup) bindingView.xrvWan.getParent(), false);
        mAdapter = new WanAndroidAdapter(getActivity());
        mAdapter.setNoImage(true);
        bindingView.xrvWan.addHeaderView(headerBinding.getRoot());
        width = DensityUtil.getDisplayWidth() - DensityUtil.dip2px(bindingView.xrvWan.getContext(), 160);
        float height = width / 1.8f;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
        headerBinding.banner.setLayoutParams(lp);
        headerBinding.rb1.setOnCheckedChangeListener((buttonView, isChecked) -> refresh(isChecked, true));
        headerBinding.rb2.setOnCheckedChangeListener((buttonView, isChecked) -> refresh(isChecked, false));
        bindingView.srlWan.setOnRefreshListener(this::swipeRefresh);
        bindingView.xrvWan.setOnLoadMoreListener(true, new ByRecyclerView.OnLoadMoreListener() {
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
                    bindingView.xrvWan.loadMoreComplete();
                }
            }
        });

        showSkeletonView();
    }

    private void showSkeletonView() {
        ArrayList<WanAndroidBannerBean.DataBean> dataBeans = new ArrayList<>();
        WanAndroidBannerBean.DataBean dataBean = new WanAndroidBannerBean.DataBean();
        dataBeans.add(dataBean);
        dataBeans.add(dataBean);
        dataBeans.add(dataBean);
        showBannerView(dataBeans);
        skeletonScreen = BySkeleton.bindItem(bindingView.xrvWan)
                .adapter(mAdapter)
                .count(10)
                .color(R.color.colorSkeleton)
                .duration(1100)
                .load(R.layout.item_wan_android_skeleton)
                .frozen(false)
                .show();
    }

    private void refresh(boolean isChecked, boolean isArticle) {
        if (isChecked) {
            bindingView.srlWan.setRefreshing(true);
            viewModel.setPage(0);
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
        bindingView.srlWan.postDelayed(this::getWanAndroidBanner, 350);
    }

    /**
     * 设置banner图
     */
    public void showBannerView(List<WanAndroidBannerBean.DataBean> result) {
        if (!isLoadBanner) {
            headerBinding.banner
                    .setIndicatorRes(R.drawable.shape_banner_select, R.drawable.shape_banner_unselect)
                    .setBannerStyle(BannerConfig.NOT_INDICATOR)
                    .setBannerAnimation(ScaleRightTransformer.class)
                    .setDelayTime(6000)
                    .setOffscreenPageLimit(result.size())
                    .setAutoPlay(false)
                    .setPages(result, CustomViewHolder::new)
                    .start();
            headerBinding.banner.setOnBannerClickListener(i -> WebViewActivity.loadUrl(getContext(), result.get(i).getUrl(), result.get(i).getTitle()));
            headerBinding.banner.startAutoPlay();
            isLoadBanner = true;
        } else {
            headerBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR).setAutoPlay(true);
            headerBinding.banner.update(result);
        }
    }

    class CustomViewHolder implements ByBannerViewHolder<WanAndroidBannerBean.DataBean> {

        private ImageView imageView;
        private View viewMask;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_wanandroid, null);
            imageView = view.findViewById(R.id.iv_banner);
            viewMask = view.findViewById(R.id.view_mask);
            return view;
        }

        @Override
        public void onBind(Context context, int position, WanAndroidBannerBean.DataBean data) {
            if (data != null) {
                DensityUtil.setWidthHeight(imageView, width, 1.8f);
                DensityUtil.setWidthHeight(viewMask, width, 1.8f);
                GlideUtil.displayEspImage(data.getImagePath(), imageView, 3);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsFirst) {
            bindingView.srlWan.postDelayed(this::getWanAndroidBanner, 1000);
            mIsFirst = false;
        }
        if (isLoadBanner) {
            headerBinding.banner.startAutoPlay();
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
        viewModel.setPage(0);
        viewModel.getWanAndroidBanner().observe(this, new Observer<WanAndroidBannerBean>() {
            @Override
            public void onChanged(@Nullable WanAndroidBannerBean bean) {
                if (bean != null) {
                    headerBinding.banner.setVisibility(View.VISIBLE);
                    showBannerView(bean.getData());
                } else {
                    headerBinding.banner.setVisibility(View.GONE);
                }
                if (headerBinding.rb1.isChecked()) {
                    getHomeArticleList();
                } else {
                    getHomeProjectList();
                }
            }
        });
    }

    private void getHomeArticleList() {
        viewModel.getHomeArticleList(null).observe(getViewLifecycleOwner(), observer);
    }

    private void getHomeProjectList() {
        viewModel.getHomeProjectList().observe(getViewLifecycleOwner(), observer);
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
                    skeletonScreen.hide();
                    mAdapter.setNewData(homeListBean.getData().getDatas());
                } else {
                    mAdapter.addData(homeListBean.getData().getDatas());
                    bindingView.xrvWan.loadMoreComplete();
                }
            } else {
                if (mAdapter.getItemCount() == 0) {
                    showError();
                } else {
                    bindingView.xrvWan.loadMoreEnd();
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
