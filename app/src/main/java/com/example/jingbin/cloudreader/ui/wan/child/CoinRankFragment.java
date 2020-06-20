package com.example.jingbin.cloudreader.ui.wan.child;

import androidx.lifecycle.Observer;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.CoinAdapter;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.CoinBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.HeaderCoinRankBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.viewmodel.wan.CoinListViewModel;

import me.jingbin.library.ByRecyclerView;

/**
 * @author jingbin
 * @date 2019/09/26.
 * @description 积分详情
 */
public class CoinRankFragment extends BaseFragment<CoinListViewModel, FragmentWanAndroidBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FragmentActivity activity;
    private CoinAdapter mAdapter;
    private HeaderCoinRankBinding headerBinding;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static CoinRankFragment newInstance() {
        return new CoinRankFragment();
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
        // 准备就绪
        mIsPrepared = true;
        loadData();
    }


    private void initRefreshView() {
        headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_coin_rank, (ViewGroup) bindingView.xrvWan.getParent(), false);
        RefreshHelper.initLinear(bindingView.xrvWan, true, 1);
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mAdapter = new CoinAdapter(activity, true);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.xrvWan.addHeaderView(headerBinding.getRoot());
        headerBinding.ivCoinRank.setVisibility(View.INVISIBLE);
        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.xrvWan.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.setPage(1);
                        getCoinRank();
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
                    getCoinRank();
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
        bindingView.srlWan.postDelayed(this::getCoinRank, 150);
        mIsFirst = false;
    }

    private void getCoinRank() {
        viewModel.getCoinRank().observe(this, new Observer<CoinBean>() {
            @Override
            public void onChanged(@Nullable CoinBean homeListBean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (homeListBean != null) {
                    if (homeListBean.getDatas() != null && homeListBean.getDatas().size() > 0) {
                        if (viewModel.getPage() == 1) {
                            showContentView();
                            mAdapter.clear();
                            mAdapter.notifyDataSetChanged();
                            headerBinding.ivCoinRank.setVisibility(View.VISIBLE);
                            headerBinding.setBean(homeListBean.getDatas().get(0));
                        }
                        int positionStart = mAdapter.getItemCount() + 1;
                        mAdapter.addAll(homeListBean.getDatas());
                        mAdapter.notifyItemRangeInserted(positionStart, homeListBean.getDatas().size());
                        bindingView.xrvWan.loadMoreComplete();
                    } else {
                        if (viewModel.getPage() == 1) {
                            showEmptyView("还没有积分信息哦~");
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
        getCoinRank();
    }
}
