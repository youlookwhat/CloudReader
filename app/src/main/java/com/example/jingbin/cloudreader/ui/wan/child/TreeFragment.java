package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.TreeAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemTreeBinding;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @date 2018/09/15.
 * @description 知识体系
 */
public class TreeFragment extends BaseFragment<FragmentWanAndroidBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private TreeAdapter mTreeAdapter;
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

    public static TreeFragment newInstance() {
        return new TreeFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.srlWan.setOnRefreshListener(() -> bindingView.srlWan.postDelayed(() -> {
            bindingView.xrvWan.reset();
            loadCustomData();
        }, 150));
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        bindingView.xrvWan.setLayoutManager(layoutManager);
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.setLoadingMoreEnabled(false);
        bindingView.xrvWan.clearHeader();
        mTreeAdapter = new TreeAdapter();
        bindingView.xrvWan.setAdapter(mTreeAdapter);
        HeaderItemTreeBinding oneBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_tree, null, false);
        bindingView.xrvWan.addHeaderView(oneBinding.getRoot());
        oneBinding.tvPosition.setOnClickListener(v -> layoutManager.scrollToPositionWithOffset(mTreeAdapter.mProjectPosition + 2, 0));
    }

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::loadCustomData, 150);
        DebugUtil.error("-----setRefreshing");
    }

    private void loadCustomData() {
        Subscription get = HttpClient.Builder.getWanAndroidServer().getTree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TreeBean>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                        if (bindingView.srlWan.isRefreshing()) {
                            bindingView.srlWan.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showContentView();
                        if (bindingView.srlWan.isRefreshing()) {
                            bindingView.srlWan.setRefreshing(false);
                        }
                        if (mIsFirst) {
                            showError();
                        } else {
                            bindingView.xrvWan.refreshComplete();
                        }
                    }

                    @Override
                    public void onNext(TreeBean treeBean) {
                        if (treeBean != null
                                && treeBean.getData() != null
                                && treeBean.getData().size() > 0) {

                            mTreeAdapter.clear();
                            mTreeAdapter.addAll(treeBean.getData());
                            mTreeAdapter.notifyDataSetChanged();
                            bindingView.xrvWan.refreshComplete();

                            mIsFirst = false;
                        } else {
                            showError();
                        }
                    }
                });
        addSubscription(get);
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        loadCustomData();
    }
}
