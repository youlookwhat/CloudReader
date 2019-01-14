package com.example.jingbin.cloudreader.ui.douban;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.DouBookAdapter;
import com.example.jingbin.cloudreader.adapter.UpcomingAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.viewmodel.movie.UpcomingViewModel;
import com.example.xrecyclerview.XRecyclerView;

/**
 * @author jingbin
 * @date 19/01/14.
 */
public class UpcomingFragment extends BaseFragment<UpcomingViewModel, FragmentWanAndroidBinding> {

    private static final String TYPE = "param1";
    private String mType = "即将上映";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private UpcomingAdapter mAdapter;
    private FragmentActivity activity;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static UpcomingFragment newInstance(String param1) {
        UpcomingFragment fragment = new UpcomingFragment();
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
        showContentView();
        initRefreshView();
        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mAdapter = new UpcomingAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.xrvWan.setLayoutManager(mLayoutManager);
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.setItemAnimator(null);
        bindingView.xrvWan.clearHeader();
        bindingView.xrvWan.setHasFixedSize(true);
        bindingView.xrvWan.setAdapter(mAdapter);
        mAdapter.setListener((bean, view) -> OneMovieDetailActivity.start(activity, bean, view));
        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!bindingView.xrvWan.isLoadingData()) {
                    bindingView.srlWan.postDelayed(() -> {
                        viewModel.setStart(0);
                        bindingView.xrvWan.reset();
                        getComingSoon();
                    }, 150);
                }

            }
        });
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (!bindingView.srlWan.isRefreshing()) {
                    viewModel.handleNextStart();
                    getComingSoon();
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
        bindingView.srlWan.postDelayed(this::getComingSoon, 300);
    }

    private void getComingSoon() {
        viewModel.getComingSoon().observe(this, new android.arch.lifecycle.Observer<HotMovieBean>() {
            @Override
            public void onChanged(@Nullable HotMovieBean bookBean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (bookBean != null && bookBean.getSubjects() != null && bookBean.getSubjects().size() > 0) {
                    if (viewModel.getStart() == 0) {
                        showContentView();
                        mAdapter.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    // +2 一个刷新头布局 一个自己新增的头布局
                    int positionStart = mAdapter.getItemCount() + 1;
                    mAdapter.addAll(bookBean.getSubjects());
                    mAdapter.notifyItemRangeInserted(positionStart, bookBean.getSubjects().size());
                    bindingView.xrvWan.refreshComplete();
                    if (mIsFirst) {
                        mIsFirst = false;
                    }
                } else {
                    if (mAdapter.getItemCount() == 0) {
                        showError();
                    } else {
                        bindingView.xrvWan.noMoreLoading();
                    }
                }
            }
        });
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getComingSoon();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.clear();
            mAdapter = null;
        }
    }
}
