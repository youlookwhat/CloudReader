package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.CategoryArticleAdapter;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.databinding.FragmentCategoryArticleBinding;
import com.example.jingbin.cloudreader.view.byview.NeteaseLoadMoreView;
import com.example.jingbin.cloudreader.view.byview.NeteaseRefreshHeaderView;
import com.example.jingbin.cloudreader.viewmodel.wan.WanAndroidListViewModel;

import me.jingbin.bymvvm.base.BaseFragment;
import me.jingbin.bymvvm.databinding.LayoutLoadingEmptyBinding;
import me.jingbin.bymvvm.utils.CheckNetwork;
import me.jingbin.library.ByRecyclerView;
import me.jingbin.library.decoration.SpacesItemDecoration;

/**
 * @author jingbin
 * @date 2021/11/8.
 * @description 玩安卓项目分类页
 */
public class CategoryArticleFragment extends BaseFragment<WanAndroidListViewModel, FragmentCategoryArticleBinding> {

    private static final String ID = "categoryId";
    private static final String NAME = "categoryName";
    private static final String IS_REFRESH = "isRefresh";
    private int categoryId;
    private String categoryName;
    private boolean isRefresh;
    private FragmentActivity activity;
    private CategoryArticleAdapter mAdapter;
    private boolean mIsFirst = true;

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
        args.putBoolean(IS_REFRESH, isLoad);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ID);
            categoryName = getArguments().getString(NAME);
            isRefresh = getArguments().getBoolean(IS_REFRESH);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.setPage(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsFirst) {
            showLoading();
            initRefreshView();
            getHomeList();
            mIsFirst = false;
        }
    }

    private void initRefreshView() {
        mAdapter = new CategoryArticleAdapter(activity);
        bindingView.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        bindingView.recyclerView.setItemAnimator(null);
        bindingView.recyclerView.addItemDecoration(new SpacesItemDecoration(activity).setHeaderNoShowDivider(isRefresh ? 1 : 0));
        bindingView.recyclerView.setRefreshHeaderView(new NeteaseRefreshHeaderView(activity));
        bindingView.recyclerView.setLoadingMoreView(new NeteaseLoadMoreView(activity));
        bindingView.recyclerView.setAdapter(mAdapter);
        LayoutLoadingEmptyBinding emptyBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_loading_empty, (ViewGroup) bindingView.recyclerView.getParent(), false);
        emptyBinding.tvTipEmpty.setText(String.format("未找到与\"%s\"相关的内容", categoryName));
        mAdapter.setPageEmptyView(emptyBinding.getRoot());

        bindingView.recyclerView.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                getHomeList();
            }
        });
        if (isRefresh) {
            bindingView.recyclerView.setOnRefreshListener(new ByRecyclerView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    viewModel.setPage(0);
                    getHomeList();
                }
            });
        }
    }

    private void getHomeList() {
        viewModel.getHomeArticleList(categoryId).observe(this, new Observer<HomeListBean>() {
            @Override
            public void onChanged(@Nullable HomeListBean homeListBean) {
                if (!CheckNetwork.isNetworkConnected(activity)) {
                    showError();
                    return;
                }
                showContentView();
                if (mAdapter == null) return;
                // 如果在onDestroyView时没有执行viewModel.clear()，且将mAdapter=null，则有可能在数据回来时，mAdapter设置为Null了。
                mAdapter.setPageData(viewModel.getPage() == 0, (homeListBean != null && homeListBean.getData() != null && homeListBean.getData().getDatas() != null) ? homeListBean.getData().getDatas() : null);
            }
        });
    }


    @Override
    protected void onRefresh() {
        getHomeList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsFirst = true;
        viewModel.setPage(0);
        if (mAdapter != null) {
            mAdapter.clear();
            mAdapter = null;
        }
        bindingView.recyclerView.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
