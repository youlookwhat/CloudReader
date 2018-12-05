package com.example.jingbin.cloudreader.ui.wan.child;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.viewmodel.wan.ArticleListViewModel;
import com.example.jingbin.cloudreader.viewmodel.wan.WanAndroidListViewModel;
import com.example.xrecyclerview.XRecyclerView;

/**
 * 玩安卓分类文章列表、我的收藏文章列表
 *
 * @author jingbin
 */
public class ArticleListActivity extends BaseActivity<FragmentWanAndroidBinding> {

    private ArticleListViewModel viewModel;
    private WanAndroidListViewModel androidViewModel;
    private WanAndroidAdapter mAdapter;
    private int cid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wan_android);
        initRefreshView();
        getIntentData();
        loadData();
    }

    private void getIntentData() {
        cid = getIntent().getIntExtra("cid", 0);
        String chapterName = getIntent().getStringExtra("chapterName");

        if (cid != 0) {
            setTitle(chapterName);
            androidViewModel = ViewModelProviders.of(this).get(WanAndroidListViewModel.class);
            mAdapter.setNoShowChapterName();
        } else {
            setTitle("我的收藏");
            viewModel = ViewModelProviders.of(this).get(ArticleListViewModel.class);
            mAdapter.setCollectList();
        }
    }

    private void loadData() {
        if (cid != 0) {
            androidViewModel.getHomeList(cid).observe(this, this::showContent);
        } else {
            viewModel.getCollectList().observe(this, this::showContent);
        }
    }

    private void showContent(HomeListBean homeListBean) {
        showContentView();
        if (bindingView.srlWan.isRefreshing()) {
            bindingView.srlWan.setRefreshing(false);
        }

        if (homeListBean != null) {
            if (getViewModel().getPage() == 0) {
                mAdapter.clear();
            }
            mAdapter.addAll(homeListBean.getData().getDatas());
            mAdapter.notifyDataSetChanged();
            bindingView.xrvWan.refreshComplete();

        } else {
            if (getViewModel().getPage() == 0) {
                showError();
            } else {
                bindingView.xrvWan.refreshComplete();
                bindingView.xrvWan.noMoreLoading();
            }
        }
    }

    private BaseListViewModel getViewModel() {
        if (viewModel != null) {
            return viewModel;
        } else {
            return androidViewModel;
        }
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.xrvWan.setLayoutManager(new LinearLayoutManager(this));
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.clearHeader();
        mAdapter = new WanAndroidAdapter(this);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.srlWan.setOnRefreshListener(() -> bindingView.srlWan.postDelayed(() -> {
            bindingView.xrvWan.reset();
            getViewModel().setPage(0);
            loadData();
        }, 500));
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                int page = getViewModel().getPage();
                getViewModel().setPage(++page);
                loadData();
            }
        });
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        loadData();
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, ArticleListActivity.class);
        mContext.startActivity(intent);
    }

    public static void start(Context mContext, int cid, String chapterName) {
        Intent intent = new Intent(mContext, ArticleListActivity.class);
        intent.putExtra("cid", cid);
        intent.putExtra("chapterName", chapterName);
        mContext.startActivity(intent);
    }
}
