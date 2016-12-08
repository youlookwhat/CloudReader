package com.example.jingbin.yunyue;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingbin.yunyue.databinding.ActivityMainBinding;
import com.example.jingbin.yunyue.ui.gank.GankFragment;
import com.example.jingbin.yunyue.ui.gank.child.AndroidFragment;
import com.example.jingbin.yunyue.ui.one.OneFragment;
import com.example.jingbin.yunyue.utils.CommonUtils;
import com.example.jingbin.yunyue.view.MyFragmentPagerAdapter;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;


/**
 * Created by jingbin on 16/11/21.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private FrameLayout llTitleMenu;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private ViewPager vpContent;

    // 一定需要对应的bean
    private ActivityMainBinding mBinding;
    private ImageView llTitleGank;
    private ImageView llTitleOne;
    private ImageView llTitleDou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initId();
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawerLayout, CommonUtils.getColor(R.color.colorTheme));
        initContentFragment();
        initDrawerLayout();
        initListener();
    }

    private void initId() {
        drawerLayout = mBinding.drawerLayout;
        navView = mBinding.navView;
        fab = mBinding.include.fab;
        toolbar = mBinding.include.toolbar;
        llTitleMenu = mBinding.include.llTitleMenu;
        vpContent = mBinding.include.vpContent;
        fab.setVisibility(View.GONE);

        llTitleGank = mBinding.include.ivTitleGank;
        llTitleOne = mBinding.include.ivTitleOne;
        llTitleDou = mBinding.include.ivTitleDou;
    }

    private void initListener() {
        llTitleMenu.setOnClickListener(this);
        mBinding.include.ivTitleGank.setOnClickListener(this);
        mBinding.include.ivTitleDou.setOnClickListener(this);
        mBinding.include.ivTitleOne.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    private void initDrawerLayout() {
        ImageView viewById = (ImageView) navView.getHeaderView(0).findViewById(R.id.iv_avatar);
        TextView tv_about = (TextView) navView.getHeaderView(0).findViewById(R.id.tv_about);
        viewById.setImageDrawable(CommonUtils.getDrawable(R.drawable.titlebar_discover_normal));
        tv_about.setText("联系我");
    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new GankFragment());
        mFragmentList.add(new OneFragment());
        mFragmentList.add(new AndroidFragment());
        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        vpContent.setOffscreenPageLimit(2);
        vpContent.addOnPageChangeListener(this);
        mBinding.include.ivTitleGank.setSelected(true);
        vpContent.setCurrentItem(0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                // 关闭
//                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_gank:
                if (vpContent.getCurrentItem() != 0) {//不然cpu会有损耗
                    llTitleGank.setSelected(true);
                    llTitleOne.setSelected(false);
                    llTitleDou.setSelected(false);

                    vpContent.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_one:
                if (vpContent.getCurrentItem() != 1) {
                    llTitleOne.setSelected(true);
                    llTitleGank.setSelected(false);
                    llTitleDou.setSelected(false);
                    vpContent.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_dou:
                if (vpContent.getCurrentItem() != 2) {
                    llTitleDou.setSelected(true);
                    llTitleOne.setSelected(false);
                    llTitleGank.setSelected(false);
                    vpContent.setCurrentItem(2);
                }
                break;
            case R.id.fab:
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Dialog");
                builder.setMessage("少数派客户端");
                builder.setPositiveButton("OK", null);
                builder.setNegativeButton("Cancel", null);
                builder.show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                llTitleGank.setSelected(true);
                llTitleOne.setSelected(false);
                llTitleDou.setSelected(false);
                break;
            case 1:
                llTitleOne.setSelected(true);
                llTitleGank.setSelected(false);
                llTitleDou.setSelected(false);
                break;
            case 2:
                llTitleDou.setSelected(true);
                llTitleOne.setSelected(false);
                llTitleGank.setSelected(false);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 不退出程序，进入后台
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
