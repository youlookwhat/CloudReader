package com.example.jingbin.cloudreader.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.NavigationAdapter;
import com.example.jingbin.cloudreader.adapter.NavigationContentAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.databinding.FragmentNaviBinding;
import com.example.jingbin.cloudreader.viewmodel.wan.NavigationViewModel;

import java.util.List;

/**
 * @author jingbin
 * @date 2018/10/8.
 * @description 导航数据
 */
public class NavigationFragment extends BaseFragment<NavigationViewModel, FragmentNaviBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private NavigationAdapter mNaviAdapter;
    private NavigationContentAdapter mContentAdapter;
    private FragmentActivity activity;
    private int currentPosition = 0;
    private LinearLayoutManager layoutManager;

    @Override
    public int setContent() {
        return R.layout.fragment_navi;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
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
        viewModel.getNavigationJson();
    }

    private void initRefreshView() {
        layoutManager = new LinearLayoutManager(activity);
        bindingView.xrvNavi.setLayoutManager(layoutManager);
        mNaviAdapter = new NavigationAdapter();
        bindingView.xrvNavi.setAdapter(mNaviAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 6, GridLayoutManager.VERTICAL, false);
        bindingView.xrvNaviDetail.setLayoutManager(gridLayoutManager);
        mContentAdapter = new NavigationContentAdapter();
        bindingView.xrvNaviDetail.setAdapter(mContentAdapter);

        mNaviAdapter.setOnSelectListener(new NavigationAdapter.OnSelectListener() {
            @Override
            public void onSelected(int position) {
                selectItem(position);
                moveToCenter(position);
                Integer titlePosition = viewModel.getTitlePositions().getValue().get(position);
                gridLayoutManager.scrollToPositionWithOffset(titlePosition, 0);
            }
        });
        bindingView.xrvNaviDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int itemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                Integer integer = viewModel.getHashMap().get(itemPosition);
                if (integer != null && currentPosition != integer) {
                    selectItem(integer);
                    moveToCenter(integer);
                }
            }
        });
        onObserveViewModel();
    }

    private void onObserveViewModel() {
        // 左侧标题
        viewModel.getDataTitle().observe(this, new Observer<List<NaviJsonBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<NaviJsonBean.DataBean> dataBeans) {
                mNaviAdapter.clear();
                mNaviAdapter.addAll(dataBeans);
                mNaviAdapter.notifyDataSetChanged();
                selectItem(0);
            }
        });
        // 右侧内容
        viewModel.getData().observe(this, new Observer<List<ArticlesBean>>() {
            @Override
            public void onChanged(@Nullable List<ArticlesBean> list) {
                if (list != null && list.size() > 0) {
                    showContentView();

                    mContentAdapter.clear();
                    mContentAdapter.addAll(list);
                    mContentAdapter.notifyDataSetChanged();

                    mIsFirst = false;
                } else {
                    showError();
                }
            }
        });
    }

    private void selectItem(int position) {
        if (position < 0 || mNaviAdapter.getData().size() < position) {
            return;
        }
        mNaviAdapter.getData().get(currentPosition).setSelected(false);
        mNaviAdapter.notifyItemChanged(currentPosition);
        currentPosition = position;
        mNaviAdapter.getData().get(position).setSelected(true);
        mNaviAdapter.notifyItemChanged(position);
    }

    /**
     * 将当前选中的item居中
     */
    private void moveToCenter(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = bindingView.xrvNavi.getChildAt(position - layoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - bindingView.xrvNavi.getHeight() / 2);
            bindingView.xrvNavi.smoothScrollBy(0, y);
        }
    }

    @Override
    protected void onRefresh() {
        viewModel.getNavigationJson();
    }
}
