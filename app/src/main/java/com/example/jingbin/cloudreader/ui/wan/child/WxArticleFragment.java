package com.example.jingbin.cloudreader.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter;
import com.example.jingbin.cloudreader.adapter.WxArticleAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WxarticleItemBean;
import com.example.jingbin.cloudreader.databinding.FragmentWxarticleBinding;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.viewmodel.wan.WxArticleViewModel;
import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * @author jingbin
 * @date 2019/9/29.
 * @description 公众号
 */
public class WxArticleFragment extends BaseFragment<WxArticleViewModel, FragmentWxarticleBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private WxArticleAdapter wxArticleAdapter;
    private WanAndroidAdapter mContentAdapter;
    private FragmentActivity activity;
    private int currentPosition = 0;
    private int wxArticleId;

    @Override
    public int setContent() {
        return R.layout.fragment_wxarticle;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static WxArticleFragment newInstance() {
        return new WxArticleFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        bindingView.rvWxarticle.postDelayed(() -> viewModel.getWxArticle(), 150);

    }

    private void initRefreshView() {
        RefreshHelper.init(bindingView.rvWxarticleList, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        bindingView.rvWxarticle.setLayoutManager(layoutManager);
        wxArticleAdapter = new WxArticleAdapter();
        bindingView.rvWxarticle.setAdapter(wxArticleAdapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(activity);
        bindingView.rvWxarticleList.setLayoutManager(layoutManager2);
        mContentAdapter = new WanAndroidAdapter(activity);
        mContentAdapter.setNoShowChapterName();
        mContentAdapter.setNoShowAuthorName();
        bindingView.rvWxarticleList.setAdapter(mContentAdapter);

        wxArticleAdapter.setOnSelectListener(new WxArticleAdapter.OnSelectListener() {
            @Override
            public void onSelected(int position) {
                selectItem(position);
            }
        });
        bindingView.rvWxarticleList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                viewModel.getWxarticleDetail(wxArticleId);
            }
        });
        onObserveViewModel();
    }

    private void onObserveViewModel() {
        // 左侧标题
        viewModel.getDataTitle().observe(this, new Observer<List<WxarticleItemBean>>() {
            @Override
            public void onChanged(@Nullable List<WxarticleItemBean> dataBeans) {
                if (dataBeans != null && dataBeans.size() > 0) {
                    wxArticleAdapter.clear();
                    wxArticleAdapter.addAll(dataBeans);
                    wxArticleAdapter.notifyDataSetChanged();
                    selectItem(0);
                } else {
                    showError();
                }
                mIsFirst = false;
            }
        });
        // 右侧内容
        viewModel.getData().observe(this, new Observer<List<ArticlesBean>>() {
            @Override
            public void onChanged(@Nullable List<ArticlesBean> list) {
                if (list != null && list.size() > 0) {
                    if (viewModel.getPage() == 1) {
                        showContentView();
                        mContentAdapter.clear();
                        mContentAdapter.addAll(list);
                        mContentAdapter.notifyDataSetChanged();
                        bindingView.rvWxarticleList.smoothScrollToPosition(0);
                    } else {
                        //  一个刷新头布局
                        int positionStart = mContentAdapter.getItemCount() + 1;
                        mContentAdapter.addAll(list);
                        mContentAdapter.notifyItemRangeInserted(positionStart, list.size());
                        bindingView.rvWxarticleList.refreshComplete();
                    }

                } else {
                    if (viewModel.getPage() == 1) {
                        mContentAdapter.clear();
                        mContentAdapter.notifyDataSetChanged();
                        bindingView.rvWxarticleList.refreshComplete();
                    } else {
                        bindingView.rvWxarticleList.refreshComplete();
                        bindingView.rvWxarticleList.noMoreLoading();
                    }
                }
            }
        });
    }

    private void selectItem(int position) {
        if (position < wxArticleAdapter.getData().size()) {
            wxArticleId = wxArticleAdapter.getData().get(position).getId();
            viewModel.setPage(1);
            bindingView.rvWxarticleList.reset();
            viewModel.getWxarticleDetail(wxArticleId);
            wxArticleAdapter.setId(wxArticleId);
        }
        wxArticleAdapter.notifyItemChanged(currentPosition);
        currentPosition = position;
    }

    @Override
    protected void onRefresh() {
        viewModel.getWxArticle();
    }
}
