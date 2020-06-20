package com.example.jingbin.cloudreader.ui.gank;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jingbin.cloudreader.R;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.databinding.FragmentContentBinding;
import me.jingbin.bymvvm.rxbus.RxBus;
import com.example.jingbin.cloudreader.app.RxCodeConstants;
import com.example.jingbin.cloudreader.ui.gank.child.AndroidFragment;
import com.example.jingbin.cloudreader.ui.gank.child.CustomFragment;
import com.example.jingbin.cloudreader.ui.gank.child.GankHomeFragment;
import com.example.jingbin.cloudreader.ui.gank.child.WelfareFragment;
import com.example.jingbin.cloudreader.view.MyFragmentPagerAdapter;
import me.jingbin.bymvvm.base.NoViewModel;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by jingbin on 16/11/21.
 * 展示干货的页面
 */
public class GankFragment extends BaseFragment<NoViewModel, FragmentContentBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
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
            // 左右预加载页面的个数
            bindingView.vpGank.setOffscreenPageLimit(3);
            bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
            // item点击跳转
            initRxBus();
            showContentView();
            mIsFirst = false;
        }, 120);
    }

    @Override
    public int setContent() {
        return R.layout.fragment_content;
    }

    private void initFragmentList() {
        mTitleList.clear();
        mTitleList.add("干货首页");
        mTitleList.add("福利");
        mTitleList.add("订制");
        mTitleList.add("大安卓");
        mFragments.add(new GankHomeFragment());
        mFragments.add(new WelfareFragment());
        mFragments.add(new CustomFragment());
        mFragments.add(AndroidFragment.newInstance("Android"));
    }

    /**
     * 每日推荐点击"更多"跳转
     */
    private void initRxBus() {
        Disposable subscribe = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, Integer.class)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        if (integer == 0) {
                            bindingView.vpGank.setCurrentItem(3);
                        } else if (integer == 1) {
                            bindingView.vpGank.setCurrentItem(1);
                        } else if (integer == 2) {
                            bindingView.vpGank.setCurrentItem(2);
                        }
                    }
                });
        addSubscription(subscribe);
    }
}
