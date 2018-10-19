package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.NaviAdapter;
import com.example.jingbin.cloudreader.adapter.NaviContentAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.databinding.FragmentNaviBinding;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.DebugUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @date 2018/10/8.
 * @description 导航数据
 */
public class NaviFragment extends BaseFragment<FragmentNaviBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private NaviAdapter mNaviAdapter;
    private NaviContentAdapter mContentAdapter;
    private FragmentActivity activity;

    @Override
    public int setContent() {
        return R.layout.fragment_navi;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static NaviFragment newInstance() {
        return new NaviFragment();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        bindingView.xrvNavi.setLayoutManager(layoutManager);
        mNaviAdapter = new NaviAdapter();
        bindingView.xrvNavi.setAdapter(mNaviAdapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(activity);
        bindingView.xrvNaviDetail.setLayoutManager(layoutManager2);
        mContentAdapter = new NaviContentAdapter(activity);
        bindingView.xrvNaviDetail.setAdapter(mContentAdapter);

        mNaviAdapter.setOnSelectListener(new NaviAdapter.OnSelectListener() {
            @Override
            public void onSelected(int position) {
                if (position <= oldPosition) {
                    bindingView.xrvNaviDetail.smoothScrollToPosition(position);
                } else {
                    layoutManager2.scrollToPositionWithOffset(position, 0);
                }
            }
        });
        bindingView.xrvNaviDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = layoutManager2.findFirstVisibleItemPosition();
                if (oldPosition != firstPosition) {
                    bindingView.xrvNavi.smoothScrollToPosition(firstPosition);
                    mNaviAdapter.setSelected(firstPosition);
                    oldPosition = firstPosition;
                }
            }
        });
    }

    private int oldPosition = 0;

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        loadCustomData();
        DebugUtil.error("-----setRefreshing");
    }

    private void loadCustomData() {
        Subscription get = HttpClient.Builder.getWanAndroidServer().getNaviJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NaviJsonBean>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showContentView();
                        if (mIsFirst) {
                            showError();
                        }
                    }

                    @Override
                    public void onNext(NaviJsonBean naviJsonBean) {
                        if (naviJsonBean != null
                                && naviJsonBean.getData() != null
                                && naviJsonBean.getData().size() > 0) {

                            mNaviAdapter.clear();
                            mNaviAdapter.addAll(naviJsonBean.getData());
                            mNaviAdapter.notifyDataSetChanged();
                            mNaviAdapter.setSelected(0);

                            mContentAdapter.clear();
                            mContentAdapter.addAll(naviJsonBean.getData());
                            mContentAdapter.notifyDataSetChanged();

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
        loadCustomData();
    }
}
