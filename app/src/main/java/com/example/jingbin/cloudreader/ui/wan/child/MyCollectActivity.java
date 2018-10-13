package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.databinding.ActivityMyCollectBinding;
import com.example.jingbin.cloudreader.ui.menu.CollectArticleFragment;
import com.example.jingbin.cloudreader.ui.menu.CollectLinkFragment;
import com.example.jingbin.cloudreader.view.MyFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 玩安卓收藏
 *
 * @author jingbin
 */
public class MyCollectActivity extends BaseActivity<ActivityMyCollectBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        showLoading();
        setTitle("我的收藏");
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        bindingView.vpMyCollect.setAdapter(myAdapter);
        bindingView.vpMyCollect.setOffscreenPageLimit(3);
        myAdapter.notifyDataSetChanged();
        bindingView.tabMyCollect.setupWithViewPager(bindingView.vpMyCollect);
        showContentView();
    }

    private void initFragmentList() {
        mTitleList.clear();
        mTitleList.add("文章");
        mTitleList.add("网址");
        mTitleList.add("书籍");
        mTitleList.add("段子");
        mFragments.add(CollectArticleFragment.newInstance());
        mFragments.add(CollectLinkFragment.newInstance());
        mFragments.add(BookListFragment.newInstance("心理学"));
        mFragments.add(JokeFragment.newInstance("段子"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTitleList.clear();
        mFragments.clear();
        mTitleList = null;
        mFragments = null;
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, MyCollectActivity.class);
        mContext.startActivity(intent);
    }
}
