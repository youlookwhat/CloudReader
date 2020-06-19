package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeItemBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WxarticleItemBean;
import com.example.jingbin.cloudreader.databinding.ActivityCategoryDetailBinding;
import com.example.jingbin.cloudreader.utils.StatusBarUtil;
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

    private ActivityCategoryDetailBinding bindingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ToolbarHelper.initTranslucent(this);
        StatusBarUtil.setTranslucentStatus(this);
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, R.layout.activity_category_detail);
        ToolbarHelper.initFullBar(bindingView.toolbar, this);
        bindingView.toolbar.setNavigationOnClickListener(v -> finish());

        initData();
    }

    private void initData() {
        int cid = getIntent().getIntExtra("cid", 0);
        TreeItemBean mTreeBean = (TreeItemBean) getIntent().getSerializableExtra("CategoryBean");
        bindingView.setTreeItemBean(mTreeBean);

        List<String> mTitleList = new ArrayList<>();
        List<Fragment> mFragments = new ArrayList<>();

        int initIndex = 0;
        for (int i = 0, len = mTreeBean.getChildren().size(); i < len; i++) {
            WxarticleItemBean childrenBean = mTreeBean.getChildren().get(i);
            mTitleList.add(childrenBean.getName());
            if (childrenBean.getId() == cid) {
                initIndex = i;
                mFragments.add(CategoryArticleFragment.newInstance(childrenBean.getId(), childrenBean.getName(), true));
            } else {
                mFragments.add(CategoryArticleFragment.newInstance(childrenBean.getId(), childrenBean.getName(), false));
            }
        }

        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        bindingView.viewPager.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        bindingView.tabLayout.setupWithViewPager(bindingView.viewPager);

        // 设置初始位置
        bindingView.viewPager.setCurrentItem(initIndex);
    }

    public static void start(Context mContext, int cid, TreeItemBean bean) {
        Intent intent = new Intent(mContext, CategoryDetailActivity.class);
        intent.putExtra("cid", cid);
        intent.putExtra("CategoryBean", bean);
        mContext.startActivity(intent);
    }
}
