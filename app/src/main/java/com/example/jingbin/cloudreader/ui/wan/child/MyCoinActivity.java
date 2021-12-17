package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.app.App;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.databinding.ActivityMyCollectBinding;
import com.example.jingbin.cloudreader.ui.MainActivity;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.view.MyFragmentPagerAdapter;

import java.util.ArrayList;

import me.jingbin.bymvvm.base.BaseActivity;
import me.jingbin.bymvvm.base.NoViewModel;

/**
 * 玩安卓积分
 *
 * @author jingbin
 */
public class MyCoinActivity extends BaseActivity<NoViewModel, ActivityMyCollectBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        showLoading();
        setTitle("积分系统");
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        bindingView.vpMyCollect.setAdapter(myAdapter);
        bindingView.vpMyCollect.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        bindingView.tabMyCollect.setupWithViewPager(bindingView.vpMyCollect);
        showContentView();
        boolean isBank = getIntent().getBooleanExtra("isBank", false);
        bindingView.vpMyCollect.setCurrentItem(isBank ? 1 : 0);
        handleShortcuts();
    }

    private void initFragmentList() {
        mTitleList.clear();
        mTitleList.add("我的积分");
        mTitleList.add("积分排行榜");
        mFragments.add(CoinDetailFragment.newInstance());
        mFragments.add(CoinRankFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionbar_help) {
            WebViewActivity.loadUrl(MyCoinActivity.this, getString(R.string.string_url_coin_help), "积分规则");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTitleList.clear();
        mFragments.clear();
        mTitleList = null;
        mFragments = null;
    }

    /**
     * 处理快捷方式进来
     */
    private void handleShortcuts() {
        if (!MainActivity.isLaunch) {
            App.isShortcuts = true;
            if (!UserUtil.isLogin()) {
                ToastUtil.showToast("请先登录");
                MainActivity.start(this);
                LoginActivity.start(this);
                finish();
                return;
            }
            Toolbar toolbar = findViewById(R.id.tool_bar);
            toolbar.setNavigationOnClickListener(v -> BaseTools.handleFinish(this));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BaseTools.handleFinish(this);
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, MyCoinActivity.class);
        mContext.startActivity(intent);
    }

    public static void startRank(Context mContext) {
        Intent intent = new Intent(mContext, MyCoinActivity.class);
        intent.putExtra("isBank", true);
        mContext.startActivity(intent);
    }
}
