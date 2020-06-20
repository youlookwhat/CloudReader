package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import me.jingbin.bymvvm.base.BaseActivity;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.viewmodel.wan.WanAndroidListViewModel;

import me.jingbin.library.ByRecyclerView;

/**
 * 玩安卓分类文章列表
 *
 * @author jingbin
 */
public class ArticleListActivity extends BaseActivity<WanAndroidListViewModel, FragmentWanAndroidBinding> {

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
        setTitle(chapterName);
        mAdapter.setNoShowChapterName();
    }

    private void loadData() {
        viewModel.getHomeArticleList(cid).observe(this, this::showContent);
    }

    private void initRefreshView() {
        RefreshHelper.initLinear(bindingView.xrvWan, true);
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mAdapter = new WanAndroidAdapter(this);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.srlWan.setOnRefreshListener(() -> bindingView.srlWan.postDelayed(() -> {
            viewModel.setPage(0);
            loadData();
        }, 500));
        bindingView.xrvWan.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                loadData();
            }
        });
    }

    private void showContent(HomeListBean homeListBean) {
        if (bindingView.srlWan.isRefreshing()) {
            bindingView.srlWan.setRefreshing(false);
        }

        if (homeListBean != null
                && homeListBean.getData() != null
                && homeListBean.getData().getDatas() != null
                && homeListBean.getData().getDatas().size() > 0) {
            if (viewModel.getPage() == 0) {
                showContentView();
                mAdapter.setNewData(homeListBean.getData().getDatas());
            } else {
                mAdapter.addData(homeListBean.getData().getDatas());
                bindingView.xrvWan.loadMoreComplete();
            }

        } else {
            if (viewModel.getPage() == 0) {
                showError();
            } else {
                bindingView.xrvWan.loadMoreEnd();
            }
        }
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        loadData();
    }

    public static void start(Context mContext, int cid, String chapterName) {
        Intent intent = new Intent(mContext, ArticleListActivity.class);
        intent.putExtra("cid", cid);
        intent.putExtra("chapterName", chapterName);
        mContext.startActivity(intent);
    }
}
