package com.example.jingbin.cloudreader.ui.film;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;

import com.example.jingbin.cloudreader.R;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.databinding.FragmentContentBinding;
import com.example.jingbin.cloudreader.ui.film.child.FilmComingFragment;
import com.example.jingbin.cloudreader.ui.film.child.FilmShowingFragment;
import com.example.jingbin.cloudreader.view.MyFragmentPagerAdapter;
import me.jingbin.bymvvm.base.NoViewModel;

import java.util.ArrayList;

/**
 * Created by jingbin on 19/05/16.
 * 展示热映榜和即将上映的页面
 */
public class FilmFragment extends BaseFragment<NoViewModel, FragmentContentBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);
    private boolean mIsFirst = true;
    private boolean mIsPrepared;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mIsPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        showLoading();
        bindingView.vpGank.postDelayed(() -> {
            initFragmentList();
            MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
            bindingView.vpGank.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
            bindingView.tabGank.setTabMode(TabLayout.MODE_FIXED);
            bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
            showContentView();
            mIsFirst = false;
        }, 110);
    }

    @Override
    public int setContent() {
        return R.layout.fragment_content;
    }

    private void initFragmentList() {
        mTitleList.clear();
        mTitleList.add("热映榜");
        mTitleList.add("即将上映");
        mFragments.add(FilmShowingFragment.newInstance("热映榜"));
        mFragments.add(FilmComingFragment.newInstance("即将上映"));
//        mTitleList.add("电影");
//        mTitleList.add("书籍");
//        mFragments.add(new OneFragment());
//        mFragments.add(BookListFragment.newInstance("综合"));
    }

}
