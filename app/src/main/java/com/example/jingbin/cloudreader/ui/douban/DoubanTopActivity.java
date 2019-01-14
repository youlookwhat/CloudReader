package com.example.jingbin.cloudreader.ui.douban;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.DouBanTopAdapter;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.databinding.ActivityDoubanTopBinding;
import com.example.jingbin.cloudreader.viewmodel.movie.DoubanTopViewModel;
import com.example.xrecyclerview.XRecyclerView;

/**
 * Created by jingbin on 16/12/10.
 * Updated by jingbin on 17/12/26.
 */
public class DoubanTopActivity extends BaseActivity<DoubanTopViewModel, ActivityDoubanTopBinding> {

    private DouBanTopAdapter mDouBanTopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_top);
        setTitle("豆瓣电影Top250");
        initRecyclerView();
        loadDouBanTop250();
    }

    private void initRecyclerView() {
        mDouBanTopAdapter = new DouBanTopAdapter();
        bindingView.xrvTop.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        bindingView.xrvTop.setPullRefreshEnabled(false);
        bindingView.xrvTop.setItemAnimator(null);
        bindingView.xrvTop.clearHeader();
        bindingView.xrvTop.setLoadingMoreEnabled(true);
        bindingView.xrvTop.setAdapter(mDouBanTopAdapter);
        mDouBanTopAdapter.setListener((bean, view) -> OneMovieDetailActivity.start(DoubanTopActivity.this, bean, view));
        bindingView.xrvTop.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                viewModel.handleNextStart();
                loadDouBanTop250();
            }
        });
    }

    private void loadDouBanTop250() {
        viewModel.getHotMovie().observe(this, new Observer<HotMovieBean>() {
            @Override
            public void onChanged(@Nullable HotMovieBean bean) {
                if (bean != null && bean.getSubjects() != null && bean.getSubjects().size() > 0) {
                    if (viewModel.getStart() == 0) {
                        showContentView();
                        mDouBanTopAdapter.clear();
                    }
                    int positionStart = mDouBanTopAdapter.getItemCount() + 1;
                    mDouBanTopAdapter.addAll(bean.getSubjects());
                    mDouBanTopAdapter.notifyItemRangeInserted(positionStart, bean.getSubjects().size());
                    bindingView.xrvTop.refreshComplete();
                } else {
                    if (mDouBanTopAdapter.getItemCount() == 0) {
                        showError();
                    } else {
                        bindingView.xrvTop.noMoreLoading();
                    }
                }
            }
        });
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
