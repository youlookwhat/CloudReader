package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.viewmodel.wan.WanAndroidListViewModel;
import com.example.xrecyclerview.XRecyclerView;

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
        viewModel.getHomeList(cid).observe(this, this::showContent);
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.xrvWan.setLayoutManager(new LinearLayoutManager(this));
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.clearHeader();
        bindingView.xrvWan.setItemAnimator(null);
        mAdapter = new WanAndroidAdapter(this);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.srlWan.setOnRefreshListener(() -> bindingView.srlWan.postDelayed(() -> {
            bindingView.xrvWan.reset();
            viewModel.setPage(0);
            loadData();
        }, 500));
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

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

        if (homeListBean != null) {
            if (viewModel.getPage() == 0) {
                showContentView();
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
            }
            int positionStart = mAdapter.getItemCount() + 1;
            mAdapter.addAll(homeListBean.getData().getDatas());
            mAdapter.notifyItemRangeInserted(positionStart, homeListBean.getData().getDatas().size());
            bindingView.xrvWan.refreshComplete();

        } else {
            if (viewModel.getPage() == 0) {
                showError();
            } else {
                bindingView.xrvWan.noMoreLoading();
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
