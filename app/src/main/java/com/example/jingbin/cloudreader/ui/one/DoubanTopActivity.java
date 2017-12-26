package com.example.jingbin.cloudreader.ui.one;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.DouBanTopAdapter;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.databinding.ActivityDoubanTopBinding;
import com.example.jingbin.cloudreader.viewmodel.movie.DoubanTopViewModel;
import com.example.jingbin.cloudreader.viewmodel.movie.OnMovieLoadListener;
import com.example.xrecyclerview.XRecyclerView;

/**
 * Created by jingbin on 16/12/10.
 * Updated by jingbin on 17/12/26.
 */
public class DoubanTopActivity extends BaseActivity<ActivityDoubanTopBinding> implements OnMovieLoadListener {

    private int mStart = 0;
    private int mCount = 21;
    private DouBanTopAdapter mDouBanTopAdapter;
    private DoubanTopViewModel topViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_top);
        setTitle("豆瓣电影Top250");
        topViewModel = ViewModelProviders.of(this).get(DoubanTopViewModel.class);
        topViewModel.setOnMovieLoadListener(this);
        initRecyclerView();
        loadDouBanTop250();
    }

    private void loadDouBanTop250() {
        topViewModel.getHotMovie(mStart, mCount);
    }

    @Override
    public void onSuccess(HotMovieBean hotMovieBean) {
        showContentView();
        if (mStart == 0) {
            if (hotMovieBean != null
                    && hotMovieBean.getSubjects() != null
                    && hotMovieBean.getSubjects().size() > 0) {

                mDouBanTopAdapter.clear();
                mDouBanTopAdapter.addAll(hotMovieBean.getSubjects());
                mDouBanTopAdapter.notifyDataSetChanged();
            } else {
                bindingView.xrvTop.setVisibility(View.GONE);
                bindingView.xrvTop.refreshComplete();
                if (mDouBanTopAdapter.getItemCount() == 0) {
                    showError();
                }
            }
        } else {
            if (hotMovieBean != null
                    && hotMovieBean.getSubjects() != null
                    && hotMovieBean.getSubjects().size() > 0) {
                bindingView.xrvTop.refreshComplete();
                mDouBanTopAdapter.addAll(hotMovieBean.getSubjects());
                mDouBanTopAdapter.notifyDataSetChanged();
            } else {
                bindingView.xrvTop.noMoreLoading();
            }
        }
    }

    @Override
    public void onFailure() {
        bindingView.xrvTop.refreshComplete();
        if (mDouBanTopAdapter.getItemCount() == 0) {
            showError();
        }
    }

    private void initRecyclerView() {
        mDouBanTopAdapter = new DouBanTopAdapter(DoubanTopActivity.this);
        bindingView.xrvTop.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        bindingView.xrvTop.setPullRefreshEnabled(false);
        bindingView.xrvTop.clearHeader();
        bindingView.xrvTop.setLoadingMoreEnabled(true);
        bindingView.xrvTop.setAdapter(mDouBanTopAdapter);
        bindingView.xrvTop.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                mStart += mCount;
                loadDouBanTop250();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        topViewModel.onDestroy();
    }

    @Override
    protected void onRefresh() {
        loadDouBanTop250();
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, DoubanTopActivity.class);
        mContext.startActivity(intent);
    }
}
