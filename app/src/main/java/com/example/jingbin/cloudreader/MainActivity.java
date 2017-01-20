package com.example.jingbin.cloudreader;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jingbin.cloudreader.app.ConstantsImageUrl;
import com.example.jingbin.cloudreader.databinding.ActivityMainBinding;
import com.example.jingbin.cloudreader.http.rx.RxBus;
import com.example.jingbin.cloudreader.http.rx.RxBusBaseMessage;
import com.example.jingbin.cloudreader.http.rx.RxCodeConstants;
import com.example.jingbin.cloudreader.ui.book.BookFragment;
import com.example.jingbin.cloudreader.ui.gank.GankFragment;
import com.example.jingbin.cloudreader.ui.menu.NavAboutActivity;
import com.example.jingbin.cloudreader.ui.menu.NavDeedBackActivity;
import com.example.jingbin.cloudreader.ui.menu.NavDownloadActivity;
import com.example.jingbin.cloudreader.ui.menu.NavHomePageActivity;
import com.example.jingbin.cloudreader.ui.one.OneFragment;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.ImgLoadUtil;
import com.example.jingbin.cloudreader.view.MyFragmentPagerAdapter;
import com.example.jingbin.cloudreader.view.statusbar.StatusBarUtil;

import java.util.ArrayList;

import rx.functions.Action1;


/**
 * Created by jingbin on 16/11/21.
 * Link to:https://github.com/youlookwhat/CloudReader
 * Contact me:http://www.jianshu.com/u/e43c6e979831
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
        initRxBus();
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

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private void initDrawerLayout() {
        navView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navView.getHeaderView(0);
//        LinearLayout viewById1 = (LinearLayout) headerView.findViewById(R.id.ll_header_bg);
//        viewById1.setBackground();
        ImageView ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        ImgLoadUtil.displayCircle(ivAvatar, ConstantsImageUrl.IC_AVATAR);
        LinearLayout llNavHomepage = (LinearLayout) headerView.findViewById(R.id.ll_nav_homepage);
        LinearLayout llNavScanDownload = (LinearLayout) headerView.findViewById(R.id.ll_nav_scan_download);
        LinearLayout llNavDeedback = (LinearLayout) headerView.findViewById(R.id.ll_nav_deedback);
        LinearLayout llNavAbout = (LinearLayout) headerView.findViewById(R.id.ll_nav_about);
        llNavHomepage.setOnClickListener(this);
        llNavScanDownload.setOnClickListener(this);
        llNavDeedback.setOnClickListener(this);
        llNavAbout.setOnClickListener(this);
    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new GankFragment());
        mFragmentList.add(new OneFragment());
        mFragmentList.add(new BookFragment());
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
            case R.id.ll_title_menu:// 开启菜单
                drawerLayout.openDrawer(GravityCompat.START);
                // 关闭
//                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_gank:// 干货栏
                if (vpContent.getCurrentItem() != 0) {//不然cpu会有损耗
                    llTitleGank.setSelected(true);
                    llTitleOne.setSelected(false);
                    llTitleDou.setSelected(false);
                    vpContent.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_one:// 电影栏
                if (vpContent.getCurrentItem() != 1) {
                    llTitleOne.setSelected(true);
                    llTitleGank.setSelected(false);
                    llTitleDou.setSelected(false);
                    vpContent.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_dou:// 书籍栏
                if (vpContent.getCurrentItem() != 2) {
                    llTitleDou.setSelected(true);
                    llTitleOne.setSelected(false);
                    llTitleGank.setSelected(false);
                    vpContent.setCurrentItem(2);
                }
                break;
            case R.id.ll_nav_homepage:// 主页
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                mBinding.drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavHomePageActivity.startHome(MainActivity.this);
                    }
                }, 360);

                break;

            case R.id.ll_nav_scan_download://扫码下载
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                mBinding.drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavDownloadActivity.start(MainActivity.this);
                    }
                }, 360);
                break;
            case R.id.ll_nav_deedback:// 问题反馈
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                mBinding.drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavDeedBackActivity.start(MainActivity.this);
                    }
                }, 360);
                break;
            case R.id.ll_nav_about:// 关于云阅
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                mBinding.drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavAboutActivity.start(MainActivity.this);
                    }
                }, 360);
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
//                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
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
            if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                // 不退出程序，进入后台
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 每日推荐点击"新电影热映榜"跳转
     */
    private void initRxBus() {
        RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE_TO_ONE, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage integer) {
                        mBinding.include.vpContent.setCurrentItem(1);
                    }
                });
    }
}
