package com.example.jingbin.cloudreader.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.CategoryArticleAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.databinding.FragmentCategoryArticleBinding;
import com.example.jingbin.cloudreader.view.MyDividerItemDecoration;
import com.example.jingbin.cloudreader.viewmodel.wan.WanAndroidListViewModel;

/**
 * @author jingbin
 * @date 2019/01/18.
 * @description 体系类别详情页面
 */
public class CategoryArticleFragment extends BaseFragment<WanAndroidListViewModel, FragmentCategoryArticleBinding> {

    private static final String ID = "categoryId";
    private static final String NAME = "categoryName";
    private static final String IS_LOAD = "isLoad";
    private int categoryId;
    private String categoryName;
    private boolean isLoad;
    private FragmentActivity activity;
    private CategoryArticleAdapter mAdapter;
    private boolean mIsFirst = true;
    private boolean mIsPrepared;

    @Override
    public int setContent() {
        return R.layout.fragment_category_article;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static CategoryArticleFragment newInstance(int categoryId, String categoryName, boolean isLoad) {
        CategoryArticleFragment fragment = new CategoryArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ID, categoryId);
        args.putString(NAME, categoryName);
        args.putBoolean(IS_LOAD, isLoad);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ID);
            categoryName = getArguments().getString(NAME);
            isLoad = getArguments().getBoolean(IS_LOAD);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRefreshView();
        viewModel.setPage(0);
        mIsPrepared = true;
        if (isLoad) {
            // 第一次进来加载
            getHomeList();
        } else {
            // 点击到不被复用的fragment时加载
            loadData();
        }
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        getHomeList();
    }

    private void initRefreshView() {
        bindingView.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        bindingView.recyclerView.setItemAnimator(null);
        mAdapter = new CategoryArticleAdapter(activity);
        mAdapter.bindToRecyclerView(bindingView.recyclerView, true);
        MyDividerItemDecoration itemDecoration = new MyDividerItemDecoration(bindingView.recyclerView.getContext(), DividerItemDecoration.VERTICAL, false);
        itemDecoration.setDrawable(ContextCompat.getDrawable(activity, R.drawable.shape_line));
        bindingView.recyclerView.addItemDecoration(itemDecoration);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                getHomeList();
            }
        }, bindingView.recyclerView);
    }

    private void getHomeList() {
        viewModel.getHomeArticleList(categoryId).observe(this, new Observer<HomeListBean>() {
            @Override
            public void onChanged(@Nullable HomeListBean homeListBean) {
                if (homeListBean != null) {
                    if (homeListBean.getData() != null && homeListBean.getData().getDatas() != null && homeListBean.getData().getDatas().size() > 0) {
                        showContentView();
                        if (viewModel.getPage() == 0) {
                            mAdapter.getData().clear();
//                        mAdapter.setNewData(homeListBean.getData().getDatas());
                            // 设置后不满一屏幕不加载
                            // mAdapter.disableLoadMoreIfNotFullPage();
                        }
                        mAdapter.addData(homeListBean.getData().getDatas());
                        mAdapter.loadMoreComplete();
                    } else {
                        if (viewModel.getPage() == 0) {
                            showEmptyView(String.format("未找到与\"%s\"相关的内容", categoryName));
                        } else {
                            mAdapter.loadMoreEnd();
                        }
                    }
                    if (mIsFirst) {
                        mIsFirst = false;
                    }
                } else {
                    showError();
                }
            }
        });
    }


    @Override
    protected void onRefresh() {
        getHomeList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsFirst = true;
        mIsPrepared = false;
        viewModel.setPage(0);
        if (mAdapter != null) {
            mAdapter.getData().clear();
            mAdapter = null;
        }
    }
}
