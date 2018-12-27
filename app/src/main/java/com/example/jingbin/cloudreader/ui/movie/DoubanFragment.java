package com.example.jingbin.cloudreader.ui.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.databinding.FragmentGankBinding;
import com.example.jingbin.cloudreader.http.rx.RxBus;
import com.example.jingbin.cloudreader.http.rx.RxCodeConstants;
import com.example.jingbin.cloudreader.ui.gank.child.AndroidFragment;
import com.example.jingbin.cloudreader.ui.gank.child.CustomFragment;
import com.example.jingbin.cloudreader.ui.gank.child.EverydayFragment;
import com.example.jingbin.cloudreader.ui.gank.child.WelfareFragment;
import com.example.jingbin.cloudreader.ui.wan.child.BookListFragment;
import com.example.jingbin.cloudreader.view.MyFragmentPagerAdapter;
import com.example.jingbin.cloudreader.viewmodel.menu.NoViewModel;

import java.util.ArrayList;

import rx.Subscription;
import rx.functions.Action1;

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
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        bindingView.vpGank.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        bindingView.tabGank.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
        mIsFirst = false;
    }

    @Override
    public int setContent() {
        return R.layout.fragment_gank;
    }

    private void initFragmentList() {
        mTitleList.clear();
        mTitleList.add("电影");
        mTitleList.add("书籍");
        mFragments.add(new OneFragment());
        mFragments.add(BookListFragment.newInstance("沟通"));
    }

}
