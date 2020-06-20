package com.example.jingbin.cloudreader.ui.gank.child;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.GankAndroidAdapter;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.databinding.FragmentAndroidBinding;
import com.example.jingbin.cloudreader.ui.MainActivity;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.viewmodel.gank.GankViewModel;

import me.jingbin.library.ByRecyclerView;
import me.jingbin.library.decoration.SpacesItemDecoration;

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
        showLoading();
        loadAndroidData();
        mIsFirst = false;
    }

    private void initRecyclerView() {
        adapter = new GankAndroidAdapter();
        // 加了分割线，滚动条才会置顶
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(activity, SpacesItemDecoration.VERTICAL, 1);
        itemDecoration.setDrawable(R.drawable.shape_transparent);
        RefreshHelper.initLinear(bindingView.xrvAndroid, false).addItemDecoration(itemDecoration);
        bindingView.xrvAndroid.setAdapter(adapter);
        bindingView.xrvAndroid.setOnRefreshListener(new ByRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.setPage(1);
                loadAndroidData();
            }
        });
        bindingView.xrvAndroid.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                page++;
                viewModel.setPage(page);
                loadAndroidData();
            }
        });
    }

    private void loadAndroidData() {
        viewModel.loadGankData().observe(this, bean -> {
            if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
                if (viewModel.getPage() == 1) {
                    showContentView();
                    adapter.setNewData(bean.getResults());
                } else {
                    adapter.addData(bean.getResults());
                }
                bindingView.xrvAndroid.loadMoreComplete();
            } else {
                if (viewModel.getPage() == 1) {
                    showError();
                } else {
                    bindingView.xrvAndroid.loadMoreEnd();
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
