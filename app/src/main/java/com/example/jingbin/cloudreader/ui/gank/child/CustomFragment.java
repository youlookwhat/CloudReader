package com.example.jingbin.cloudreader.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.AndroidAdapter;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.FragmentCustomBinding;
import com.example.jingbin.cloudreader.http.HttpUtils;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.xrecyclerview.XRecyclerView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CustomFragment extends BaseFragment<FragmentCustomBinding> {

    private static final String TYPE = "mType";
    private String mType = "all";
    private int mPage = 1;
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private AndroidAdapter mAndroidAdapter;
    private ACache mACache;
    private GankIoDataBean mAllBean;
    private View mHeaderView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DebugUtil.error("--CustomFragment   ----onActivityCreated");

        mACache = ACache.get(getContext());
        mAllBean = (GankIoDataBean) mACache.getAsObject(Constants.GANK_CUSTOM);

        bindingView.xrvCustom.setPullRefreshEnabled(false);
        bindingView.xrvCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mPage++;
                loadCustomData();
            }
        });
        // 准备就绪
        mIsPrepared = true;
    }

    @Override
    public int setContent() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        if (mAllBean != null
                && mAllBean.getResults() != null
                && mAllBean.getResults().size() > 0) {
            showContentView();
            setAdapter(mAllBean);
        } else {
            loadCustomData();
        }
    }

    private void loadCustomData() {
        Subscription subscribe = HttpUtils.getInstance().getGankIOServer().getGankIoData(mType, mPage, HttpUtils.per_page_more)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankIoDataBean>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        bindingView.xrvCustom.refreshComplete();
                        if (mPage == 1) {
                            showError();
                        }
                    }

                    @Override
                    public void onNext(GankIoDataBean gankIoDataBean) {
                        if (mPage == 1) {
                            if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                                setAdapter(gankIoDataBean);

                                mACache.remove(Constants.GANK_CUSTOM);
                                // 缓存50分钟
                                mACache.put(Constants.GANK_CUSTOM, gankIoDataBean, 3);
                            }
                        } else {
                            if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                                bindingView.xrvCustom.refreshComplete();
                                mAndroidAdapter.addAll(gankIoDataBean.getResults());
                                mAndroidAdapter.notifyDataSetChanged();
                            } else {
                                bindingView.xrvCustom.noMoreLoading();
                            }
                        }
                    }
                });
        addSubscription(subscribe);
    }

    /**
     * 设置adapter
     */
    private void setAdapter(GankIoDataBean mCustomBean) {

        if (mHeaderView == null) {
            mHeaderView = View.inflate(getContext(), R.layout.header_item_gank_custom, null);
            View view = mHeaderView.findViewById(R.id.ll_choose_catalogue);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            bindingView.xrvCustom.addHeaderView(mHeaderView);
        }

        mAndroidAdapter = new AndroidAdapter();
        mAndroidAdapter.addAll(mCustomBean.getResults());
        bindingView.xrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.xrvCustom.setAdapter(mAndroidAdapter);
        mAndroidAdapter.notifyDataSetChanged();

        mIsFirst = false;
    }

    /**
     * 加载失败后点击后的操作
     */
    @Override
    protected void onRefresh() {
        loadCustomData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--CustomFragment   ----onDestroy");
    }
}
