package com.example.jingbin.cloudreader.ui.gank.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.ui.MainActivity;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.GankAndroidAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.databinding.FragmentAndroidBinding;
import com.example.jingbin.cloudreader.viewmodel.gank.GankViewModel;
import com.example.xrecyclerview.XRecyclerView;

/**
 * 大安卓 fragment
 */
public class AndroidFragment extends BaseFragment<GankViewModel, FragmentAndroidBinding> {

    private static final String TAG = "AndroidFragment";
    private static final String TYPE = "mType";
    private String mType = "Android";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private GankAndroidAdapter adapter;
    private MainActivity activity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

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

        viewModel.setType(mType);
        initRecyclerView();
        // 准备就绪
        mIsPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        loadAndroidData();
    }

    private void initRecyclerView() {
        adapter = new GankAndroidAdapter();
        bindingView.xrvAndroid.setItemAnimator(null);
        bindingView.xrvAndroid.setLayoutManager(new LinearLayoutManager(activity));
        bindingView.xrvAndroid.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                viewModel.setPage(1);
                loadAndroidData();
            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                page++;
                viewModel.setPage(page);
                loadAndroidData();
            }
        });
        bindingView.xrvAndroid.setAdapter(adapter);
    }

    private void loadAndroidData() {
        viewModel.loadGankData().observe(this, bean -> {
            if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
                if (viewModel.getPage() == 1) {
                    showContentView();
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                }
                int positionStart = adapter.getItemCount() + 1;
                adapter.addAll(bean.getResults());
                adapter.notifyItemRangeInserted(positionStart, bean.getResults().size());
                bindingView.xrvAndroid.refreshComplete();
                if (mIsFirst) {
                    mIsFirst = false;
                }
            } else {
                if (viewModel.getPage() == 1) {
                    showError();
                } else {
                    bindingView.xrvAndroid.noMoreLoading();
                }
            }
        });
    }

    /**
     * 加载失败后点击后的操作
     */
    @Override
    protected void onRefresh() {
        loadAndroidData();
    }
}
