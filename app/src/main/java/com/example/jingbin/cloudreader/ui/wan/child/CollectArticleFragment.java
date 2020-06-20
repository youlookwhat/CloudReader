package com.example.jingbin.cloudreader.ui.wan.child;

import androidx.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.viewmodel.wan.ArticleListViewModel;

import me.jingbin.library.ByRecyclerView;

/**
 * @author jingbin
 * @date 2018/09/27.
 * @description 文章
 */
public class CollectArticleFragment extends BaseFragment<ArticleListViewModel, FragmentWanAndroidBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FragmentActivity activity;
    private WanAndroidAdapter mAdapter;

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
        mAdapter.setCollectList();
        // 准备就绪
        mIsPrepared = true;
        loadData();
    }


    private void initRefreshView() {
        RefreshHelper.initLinear(bindingView.xrvWan, true);
        RefreshHelper.setDefaultAnimator(bindingView.xrvWan).setLoadMoreEnabled(true);
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mAdapter = new WanAndroidAdapter(activity);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.xrvWan.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.setPage(0);
                        getCollectList();
                    }
                }, 150);
            }
        });
        bindingView.xrvWan.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (!bindingView.srlWan.isRefreshing()) {
                    int page = viewModel.getPage();
                    viewModel.setPage(++page);
                    getCollectList();
                } else {
                    bindingView.xrvWan.loadMoreComplete();
                }
            }
        });
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getCollectList, 150);
        mIsFirst = false;
    }

    private void getCollectList() {
        viewModel.getCollectList().observe(this, new Observer<HomeListBean>() {
            @Override
            public void onChanged(@Nullable HomeListBean homeListBean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (homeListBean != null) {
                    if (homeListBean.getData() != null && homeListBean.getData().getDatas() != null && homeListBean.getData().getDatas().size() > 0) {
                        if (viewModel.getPage() == 0) {
                            showContentView();
                            mAdapter.setNewData(homeListBean.getData().getDatas());
                        } else {
                            mAdapter.addData(homeListBean.getData().getDatas());
                            bindingView.xrvWan.loadMoreComplete();
                        }
                    } else {
                        if (viewModel.getPage() == 0) {
                            showEmptyView("你还没有收藏文章哦~");
                        } else {
                            bindingView.xrvWan.loadMoreEnd();
                        }
                    }
                } else {
                    showError();
                }
            }
        });
    }


    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getCollectList();
    }
}
