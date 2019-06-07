package com.example.jingbin.cloudreader.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.JokeAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.viewmodel.wan.JokeViewModel;
import com.example.xrecyclerview.XRecyclerView;

import java.util.List;
import java.util.Random;

/**
 * @author jingbin
 * Updated by jingbin on 18/12/23.
 */
public class JokeFragment extends BaseFragment<JokeViewModel, FragmentWanAndroidBinding> {

    private static final String TYPE = "param1";
    private String mType = "综合";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private JokeAdapter mAdapter;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static JokeFragment newInstance(String param1) {
        JokeFragment fragment = new JokeFragment();
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
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initRefreshView() {
        RefreshHelper.init(bindingView.xrvWan, false);
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mAdapter = new JokeAdapter();
        bindingView.xrvWan.setAdapter(mAdapter);

        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.srlWan.postDelayed(() -> {
                    bindingView.xrvWan.reset();
                    viewModel.setRefreshBK(true);
                    viewModel.setPage(new Random().nextInt(100));
                    viewModel.showQSBKList();

                }, 100);
            }
        });
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                viewModel.setRefreshBK(false);
                viewModel.showQSBKList();
            }
        });
        viewModel.getData().observe(this, new Observer<List<DuanZiBean>>() {
            @Override
            public void onChanged(@Nullable List<DuanZiBean> duanZiBeans) {
                showContentView();
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (duanZiBeans != null && duanZiBeans.size() > 0) {
                    if (viewModel.isRefreshBK()) {
                        mAdapter.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    int positionStart = mAdapter.getItemCount() + 1;
                    mAdapter.addAll(duanZiBeans);
                    mAdapter.notifyItemRangeInserted(positionStart, duanZiBeans.size());
                    bindingView.xrvWan.refreshComplete();

                    if (mIsFirst) {
                        mIsFirst = false;
                    }

                } else {
                    if (!viewModel.isRefreshBK()) {
                        showError();
                    } else {
                        bindingView.xrvWan.refreshComplete();
                    }
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
        bindingView.srlWan.postDelayed(() -> viewModel.showQSBKList(), 100);
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        viewModel.showQSBKList();
    }
}
