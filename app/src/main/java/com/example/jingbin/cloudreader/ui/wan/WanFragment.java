package com.example.jingbin.cloudreader.ui.wan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.databinding.FragmentContentBinding;
import com.example.jingbin.cloudreader.ui.wan.child.HomeFragment;
import com.example.jingbin.cloudreader.ui.wan.child.SquareFragment;
import com.example.jingbin.cloudreader.ui.wan.child.WendaFragment;
import com.example.jingbin.cloudreader.view.CommonTabPagerAdapter;

import java.util.Arrays;

import me.jingbin.bymvvm.base.BaseFragment;
import me.jingbin.bymvvm.base.NoViewModel;

/**
 * Created by jingbin on 16/12/14.
 * 展示玩安卓的页面
 */
public class WanFragment extends BaseFragment<NoViewModel, FragmentContentBinding> implements CommonTabPagerAdapter.TabPagerListener {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoading();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         */
        CommonTabPagerAdapter myAdapter = new CommonTabPagerAdapter(getChildFragmentManager(), Arrays.asList("玩安卓", "广场", "问答"));
        myAdapter.setListener(this);
        bindingView.vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpGank.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
        showContentView();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_content;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return SquareFragment.Companion.newInstance();
            case 2:
                return WendaFragment.Companion.newInstance();
        }
        return HomeFragment.newInstance();
    }
}
