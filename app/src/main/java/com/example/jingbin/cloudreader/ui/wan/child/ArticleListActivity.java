package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;

/**
 * 玩安卓文章列表
 *
 * @author jingbin
 */
public class ArticleListActivity extends BaseActivity<FragmentWanAndroidBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wan_android);
        showContentView();
        setTitle("我的收藏");
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, ArticleListActivity.class);
        mContext.startActivity(intent);
    }
}
