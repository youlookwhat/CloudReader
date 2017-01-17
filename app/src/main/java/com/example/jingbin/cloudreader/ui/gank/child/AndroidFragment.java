package com.example.jingbin.cloudreader.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.AndroidAdapter;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.FragmentAndroidBinding;
import com.example.jingbin.cloudreader.http.HttpUtils;
import com.example.jingbin.cloudreader.http.RequestImpl;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.model.GankOtherModel;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.xrecyclerview.XRecyclerView;

import rx.Subscription;

/**
 * 大安卓 fragment
 */
public class AndroidFragment extends BaseFragment<FragmentAndroidBinding> {

    private static final String TAG = "AndroidFragment";
    private static final String TYPE = "mType";
    private String mType = "Android";
    private int mPage = 1;
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private AndroidAdapter mAndroidAdapter;
    private ACache mACache;
    private GankIoDataBean mAndroidBean;
    private GankOtherModel mModel;

    public static AndroidFragment newInstance(String type) {
        AndroidFragment fragment = new AndroidFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
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
    public int setContent() {
        return R.layout.fragment_android;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mACache = ACache.get(getContext());
        mModel = new GankOtherModel();
//        mAndroidBean = (GankIoDataBean) mACache.getAsObject(Constants.GANK_ANDROID);
        DebugUtil.error(TAG + "----onActivityCreated");
        mAndroidAdapter = new AndroidAdapter();
        bindingView.xrvAndroid.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                loadAndroidData();
            }

            @Override
            public void onLoadMore() {
                mPage++;
                loadAndroidData();
            }
        });
        // 准备就绪
        mIsPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        if (mAndroidBean != null
                && mAndroidBean.getResults() != null
                && mAndroidBean.getResults().size() > 0) {
            showContentView();
            mAndroidBean = (GankIoDataBean) mACache.getAsObject(Constants.GANK_ANDROID);
            setAdapter(mAndroidBean);
        } else {
            loadAndroidData();
        }
    }

    private void loadAndroidData() {
        mModel.setData(mType, mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                if (mPage == 1) {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        setAdapter(gankIoDataBean);

                        mACache.remove(Constants.GANK_ANDROID);
                        // 缓存50分钟
                        mACache.put(Constants.GANK_ANDROID, gankIoDataBean, 30000);
                    }
                } else {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        bindingView.xrvAndroid.refreshComplete();
                        mAndroidAdapter.addAll(gankIoDataBean.getResults());
                        mAndroidAdapter.notifyDataSetChanged();
                    } else {
                        bindingView.xrvAndroid.noMoreLoading();
                    }
                }
            }

            @Override
            public void loadFailed() {
                bindingView.xrvAndroid.refreshComplete();
                // 注意：这里不能写成 mPage == 1，否则会一直显示错误页面
                if (mAndroidAdapter.getItemCount() == 0) {
                    showError();
                }
                if (mPage > 1) {
                    mPage--;
                }
            }

            @Override
            public void addSubscription(Subscription subscription) {
                AndroidFragment.this.addSubscription(subscription);
            }
        });
    }

    /**
     * 设置adapter
     */
    private void setAdapter(GankIoDataBean mAndroidBean) {
        mAndroidAdapter.clear();
        mAndroidAdapter.addAll(mAndroidBean.getResults());
        bindingView.xrvAndroid.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.xrvAndroid.setAdapter(mAndroidAdapter);
        mAndroidAdapter.notifyDataSetChanged();
        bindingView.xrvAndroid.refreshComplete();

        mIsFirst = false;
    }

    /**
     * 加载失败后点击后的操作
     */
    @Override
    protected void onRefresh() {
        loadAndroidData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error(TAG + "   ----onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugUtil.error(TAG + "   ----onResume");
    }
}
