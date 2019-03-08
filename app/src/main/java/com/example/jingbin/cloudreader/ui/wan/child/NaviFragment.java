package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.NaviAdapter;
import com.example.jingbin.cloudreader.adapter.NaviContentAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.databinding.FragmentNaviBinding;
import com.example.jingbin.cloudreader.viewmodel.wan.NaviViewModel;

/**
 * @author jingbin
 * @date 2018/10/8.
 * @description 导航数据
 */
public class NaviFragment extends BaseFragment<NaviViewModel, FragmentNaviBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private NaviAdapter mNaviAdapter;
    private NaviContentAdapter mContentAdapter;
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
        layoutManager = new LinearLayoutManager(activity);
        bindingView.xrvNavi.setLayoutManager(layoutManager);
        mNaviAdapter = new NaviAdapter();
        bindingView.xrvNavi.setAdapter(mNaviAdapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(activity);
        bindingView.xrvNaviDetail.setLayoutManager(layoutManager2);
        mContentAdapter = new NaviContentAdapter();
        bindingView.xrvNaviDetail.setAdapter(mContentAdapter);

        mNaviAdapter.setOnSelectListener(new NaviAdapter.OnSelectListener() {
            @Override
            public void onSelected(int position) {
                selectItem(position);
                moveToCenter(position);
                layoutManager2.scrollToPositionWithOffset(position, 0);
            }
        });
        bindingView.xrvNaviDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int itemPosition = layoutManager2.findFirstVisibleItemPosition();
                if (currentPosition != itemPosition) {
                    selectItem(itemPosition);
                    moveToCenter(itemPosition);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        loadCustomData();
    }

    private void loadCustomData() {
        viewModel.getNaviJson().observe(this, new android.arch.lifecycle.Observer<NaviJsonBean>() {
            @Override
            public void onChanged(@Nullable NaviJsonBean naviJsonBean) {
                if (naviJsonBean != null
                        && naviJsonBean.getData() != null
                        && naviJsonBean.getData().size() > 0) {

                    showContentView();
                    mNaviAdapter.clear();
                    mNaviAdapter.addAll(naviJsonBean.getData());
                    mNaviAdapter.notifyDataSetChanged();
                    selectItem(0);

                    mContentAdapter.clear();
                    mContentAdapter.addAll(naviJsonBean.getData());
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
        loadCustomData();
    }
}
