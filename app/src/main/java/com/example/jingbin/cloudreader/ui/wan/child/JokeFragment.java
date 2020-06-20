package com.example.jingbin.cloudreader.ui.wan.child;

import androidx.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.JokeAdapter;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.viewmodel.wan.JokeViewModel;

import java.util.List;
import java.util.Random;

import me.jingbin.library.ByRecyclerView;

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
        RefreshHelper.initLinear(bindingView.xrvWan, true).setItemAnimator(new DefaultItemAnimator());
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mAdapter = new JokeAdapter();
        bindingView.xrvWan.setAdapter(mAdapter);

        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.srlWan.postDelayed(() -> {
                    viewModel.setRefreshBK(true);
                    viewModel.setPage(new Random().nextInt(100));
                    viewModel.showQSBKList();

                }, 100);
            }
        });
        bindingView.xrvWan.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                viewModel.setRefreshBK(false);
                viewModel.showQSBKList();
            }
        });
        bindingView.xrvWan.setOnItemLongClickListener(new ByRecyclerView.OnItemLongClickListener() {
            @Override
            public boolean onLongClick(View v, int position) {
                DialogBuild.showItems(v, mAdapter.getItemData(position).getContent());
                return false;
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
                        mAdapter.setNewData(duanZiBeans);
                    } else {
                        mAdapter.addData(duanZiBeans);
                        bindingView.xrvWan.loadMoreComplete();
                    }
                } else {
                    if (!viewModel.isRefreshBK()) {
                        showError();
                    } else {
                        bindingView.xrvWan.loadMoreComplete();
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
        showLoading();
        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(() -> viewModel.showQSBKList(), 100);
        mIsFirst = false;
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        viewModel.showQSBKList();
    }
}
