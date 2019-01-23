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
    private int categoryId;
    private FragmentActivity activity;
    private CategoryArticleAdapter mAdapter;

    @Override
    public int setContent() {
        return R.layout.fragment_category_article;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static CategoryArticleFragment newInstance(int categoryId) {
        CategoryArticleFragment fragment = new CategoryArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ID);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRefreshView();
        viewModel.setPage(0);
        bindingView.recyclerView.postDelayed(this::getHomeList, 100);
    }

    private void initRefreshView() {
        bindingView.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        bindingView.recyclerView.setItemAnimator(null);
        mAdapter = new CategoryArticleAdapter(activity);
        mAdapter.bindToRecyclerView(bindingView.recyclerView, true);
        MyDividerItemDecoration itemDecoration = new MyDividerItemDecoration(bindingView.recyclerView.getContext(), DividerItemDecoration.VERTICAL, false);
        itemDecoration.setDrawable(ContextCompat.getDrawable(bindingView.recyclerView.getContext(), R.drawable.shape_line));
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
        viewModel.getHomeList(categoryId).observe(this, new Observer<HomeListBean>() {
            @Override
            public void onChanged(@Nullable HomeListBean homeListBean) {
                showContentView();
                if (homeListBean != null
                        && homeListBean.getData() != null
                        && homeListBean.getData().getDatas() != null
                        && homeListBean.getData().getDatas().size() > 0) {
                    if (viewModel.getPage() == 0) {
                        mAdapter.getData().clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    int positionStart = mAdapter.getItemCount() + 1;
                    mAdapter.addData(homeListBean.getData().getDatas());
                    mAdapter.notifyItemRangeInserted(positionStart, homeListBean.getData().getDatas().size());
                    mAdapter.loadMoreComplete();
                } else {
                    if (viewModel.getPage() == 0) {
                        showError();
                    } else {
                        mAdapter.loadMoreEnd();
                    }
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
        viewModel.setPage(0);
    }
}
