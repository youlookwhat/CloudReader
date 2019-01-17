package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeItemBean;
import com.example.jingbin.cloudreader.databinding.ActivityCategoryDetailBinding;
import com.example.jingbin.cloudreader.utils.ToolbarHelper;
import com.example.jingbin.cloudreader.view.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识体系详情
 *
 * @author jingbin
 */
public class CategoryDetailActivity extends AppCompatActivity {

    private int cid = 0;
    private ActivityCategoryDetailBinding bindingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ToolbarHelper.initTranslucent(this);
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, R.layout.activity_category_detail);
        ToolbarHelper.initFullBar(bindingView.toolbar, this);
        bindingView.toolbar.setNavigationIcon(null);
        bindingView.btnBack.setOnClickListener(v -> finish());

        getIntentData();
        loadData();
    }

    private void getIntentData() {
        cid = getIntent().getIntExtra("cid", 0);
        String chapterName = getIntent().getStringExtra("chapterName");
        TreeItemBean mTreeBean = (TreeItemBean) getIntent().getSerializableExtra("CategoryBean");
        bindingView.setTreeItemBean(mTreeBean);

        List<Fragment> mFragments = new ArrayList<>();
        List<String> mTitleList = new ArrayList<>();

        int initIndex = 0;
        for (int i = 0, len = mTreeBean.getChildren().size(); i < len; i++) {
            TreeItemBean.ChildrenBean childrenBean = mTreeBean.getChildren().get(i);

            if (childrenBean.getId() == cid) {
                initIndex = i;
            }
            mTitleList.add(childrenBean.getName());
            mFragments.add(CollectArticleFragment.newInstance());
        }

        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        bindingView.viewPager.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        bindingView.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        bindingView.tabLayout.setupWithViewPager(bindingView.viewPager);

        // 设置初始位置
        bindingView.viewPager.setCurrentItem(initIndex);
    }

    private void loadData() {
    }

    public static void start(Context mContext, int cid, String chapterName, TreeItemBean bean) {
        Intent intent = new Intent(mContext, CategoryDetailActivity.class);
        intent.putExtra("cid", cid);
        intent.putExtra("chapterName", chapterName);
        intent.putExtra("CategoryBean", bean);
        mContext.startActivity(intent);
    }
}
