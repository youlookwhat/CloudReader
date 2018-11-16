package com.example.jingbin.cloudreader.ui.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.viewmodel.wan.ArticleListListViewModel;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;
import com.example.xrecyclerview.XRecyclerView;

import rx.Subscription;

/**
 * @author jingbin
 * @date 2018/09/27.
 * @description 文章
 */
public class CollectArticleFragment extends BaseFragment<FragmentWanAndroidBinding> implements WanNavigator.ArticleListNavigator {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FragmentActivity activity;
    private WanAndroidAdapter mAdapter;
    private ArticleListListViewModel viewModel;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static CollectArticleFragment newInstance() {
        return new CollectArticleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();

        viewModel = new ArticleListListViewModel(this);
        mAdapter.setCollectList();

        // 准备就绪
        mIsPrepared = true;

        loadData();
    }


    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.xrvWan.setLayoutManager(new LinearLayoutManager(activity));
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.clearHeader();
        mAdapter = new WanAndroidAdapter(activity);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.srlWan.setOnRefreshListener(() -> bindingView.srlWan.postDelayed(() -> {
            bindingView.xrvWan.reset();
            viewModel.setPage(0);
            viewModel.getCollectList();
        }, 300));
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                viewModel.getCollectList();
            }
        });
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.getCollectList();
            }
        }, 150);
    }


    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        viewModel.getCollectList();
    }

    @Override
    public void loadHomeListFailure() {
        showContentView();
        if (bindingView.srlWan.isRefreshing()) {
            bindingView.srlWan.setRefreshing(false);
        }
        if (viewModel.getPage() == 0) {
            showError();
        } else {
            bindingView.xrvWan.refreshComplete();
        }
    }

    @Override
    public void showAdapterView(HomeListBean bean) {
        if (viewModel.getPage() == 0) {
            mAdapter.clear();
        }
        mAdapter.addAll(bean.getData().getDatas());
        mAdapter.notifyDataSetChanged();
        bindingView.xrvWan.refreshComplete();

        mIsFirst = false;
    }

    @Override
    public void showListNoMoreLoading() {
        bindingView.xrvWan.noMoreLoading();
    }

    @Override
    public void showLoadSuccessView() {
        showContentView();
        bindingView.srlWan.setRefreshing(false);
    }

    @Override
    public void addRxSubscription(Subscription subscription) {
        addSubscription(subscription);
    }
}
