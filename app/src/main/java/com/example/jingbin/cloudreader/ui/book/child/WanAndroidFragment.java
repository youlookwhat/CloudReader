package com.example.jingbin.cloudreader.ui.book.child;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.example.jingbin.cloudreader.MainActivity;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.HeaderWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.GlideImageLoader;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanAndroidViewModel;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;
import com.example.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jingbin
 *         Updated by jingbin on 18/02/07.
 */
public class WanAndroidFragment extends BaseFragment<FragmentWanAndroidBinding> implements WanNavigator.WanAndroidNavigator {

    private static final String TYPE = "param1";
    private String mType = "综合";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private MainActivity activity;
    private WanAndroidAdapter mAdapter;
    private HeaderWanAndroidBinding androidBinding;
    private WanAndroidViewModel viewModel;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    public static WanAndroidFragment newInstance(String param1) {
        WanAndroidFragment fragment = new WanAndroidFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        viewModel = new WanAndroidViewModel(this);
        viewModel.setNavigator(this);
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
                }, 1000);

            }
        });
        bindingView.xrvBook.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.xrvBook.setPullRefreshEnabled(false);
        bindingView.xrvBook.clearHeader();
        mAdapter = new WanAndroidAdapter(getActivity());
        bindingView.xrvBook.setAdapter(mAdapter);
        androidBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_wan_android, null, false);
        viewModel.getWanAndroidBanner();
        bindingView.xrvBook.addHeaderView(androidBinding.getRoot());

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
        androidBinding.banner.setVisibility(View.VISIBLE);
        androidBinding.banner.setBannerTitles(mBannerTitle);
        androidBinding.banner.setImages(bannerImages).setImageLoader(new GlideImageLoader()).start();
        androidBinding.banner.setOnBannerListener(position -> {
            if (result.get(position) != null && !TextUtils.isEmpty(result.get(position).getUrl())) {
                WebViewActivity.loadUrl(getContext(), result.get(position).getUrl(), result.get(position).getTitle());
            }
        });
    }

    @Override
    public void loadBannerFailure() {
        androidBinding.banner.setVisibility(View.GONE);
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
        mAdapter.clear();
        mAdapter.addAll(bean.getData().getDatas());
        mAdapter.notifyDataSetChanged();
        bindingView.xrvBook.refreshComplete();

        mIsFirst = false;
    }

    @Override
    public void refreshAdapter(HomeListBean bean) {
        mAdapter.addAll(bean.getData().getDatas());
        mAdapter.notifyDataSetChanged();
        bindingView.xrvBook.refreshComplete();
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
        DebugUtil.error("-----mIsPrepared:" + mIsPrepared);
        DebugUtil.error("-----mIsVisible:" + mIsVisible);
        DebugUtil.error("-----mIsFirst:" + mIsFirst);
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlBook.setRefreshing(true);
        bindingView.srlBook.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCustomData();
            }
        }, 500);
        DebugUtil.error("-----setRefreshing");
    }

    @Override
    protected void onInvisible() {
        // 不可见时轮播图停止滚动
        if (androidBinding != null && androidBinding.banner != null) {
            androidBinding.banner.stopAutoPlay();
        }
    }

    private void loadCustomData() {
        viewModel.getHomeList();
    }

    @Override
    protected void onRefresh() {
        bindingView.srlBook.setRefreshing(true);
        loadCustomData();
    }
}
