package com.example.jingbin.cloudreader.ui.douban;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.databinding.FragmentGankBinding;
import com.example.jingbin.cloudreader.view.MyFragmentPagerAdapter;
import com.example.jingbin.cloudreader.viewmodel.menu.NoViewModel;

import java.util.ArrayList;

/**
 * Created by jingbin on 18/12/27.
 * 展示电影和书籍的页面
 */
public class DoubanFragment extends BaseFragment<NoViewModel, FragmentGankBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);
    private boolean mIsFirst = true;
    private boolean mIsPrepared;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showContentView();
        mIsPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        bindingView.vpGank.postDelayed(() -> {
            initFragmentList();
            MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
            bindingView.vpGank.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
            bindingView.vpGank.setOffscreenPageLimit(2);
            bindingView.tabGank.setTabMode(TabLayout.MODE_FIXED);
            bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
            mIsFirst = false;
        }, 120);
    }

    @Override
    public int setContent() {
        return R.layout.fragment_gank;
    }

    private void initFragmentList() {
        mTitleList.clear();
        mTitleList.add("热映榜");
        mTitleList.add("即将上映");
        mTitleList.add("书籍");
        mFragments.add(new OneFragment());
        mFragments.add(UpcomingFragment.newInstance("即将上映"));
        mFragments.add(BookListFragment.newInstance("沟通"));
    }

}
