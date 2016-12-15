package com.example.jingbin.cloudreader.ui.one;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ActivitySlideScroolViewBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.StringFormatUtil;
import com.example.jingbin.cloudreader.view.CallBack_ScrollChanged;
import com.example.jingbin.cloudreader.view.DiscoverScrollView;
import com.example.jingbin.cloudreader.view.test.RCVListAdapter;
import com.example.jingbin.cloudreader.view.test.StatusBarUtils;

import java.util.Arrays;

import static com.example.jingbin.cloudreader.view.statusbar.StatusBarUtil.getStatusBarHeight;

/**
 * 暂时的电影详情页 2016-11-29
 */
public class SlideScrollViewActivity extends AppCompatActivity {

    private RelativeLayout rlHead;
    private RecyclerView rcvGoodsList;
    private DiscoverScrollView discoverScrollView;
    private LinearLayout llHeader;

    private int slidingDistance;
    private int currScrollY = 0;
    private SubjectsBean subjectsBean;
    private ActivitySlideScroolViewBinding binding;

    private String TAG = "----MainActivity:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_slide_scrool_view);

        rlHead = binding.rlHead;
        rcvGoodsList = binding.rcvGoodsList;
        rcvGoodsList = binding.rcvGoodsList;
        discoverScrollView = binding.discoverScrollView;
        llHeader = binding.llHeader;

        if (getIntent() != null) {
            subjectsBean = (SubjectsBean) getIntent().getSerializableExtra("bean");
        }
        // 先设置状态栏透明
        StatusBarUtils.setTranslucentImageHeader(this, 0, rlHead);
        initNewSlidingParams();

        if (binding.include.imgItemBg != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.include.imgItemBg.getLayoutParams();
            layoutParams.setMargins(0, -getStatusBarHeight(this), 0, 0);
//            DebugUtil.error("getStatusBarHeight:" + getStatusBarHeight(this));
        }

        initRecyclerView();
//        initScrollView();

        setTitleBar();
        setHeaderData(subjectsBean);
    }

    private void setHeaderData(SubjectsBean positionData) {
        binding.include.setSubjectsBean(positionData);
    }

    private void setTitleBar() {
        setSupportActionBar(binding.titleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
//            actionBar.setTitle(subjectsBean.getTitle());
//            actionBar.setSubtitle("主演：" + StringFormatUtil.formatName(subjectsBean.getCasts()));
        }

        // title
        binding.tvTitle.setText(subjectsBean.getTitle());
        // 副标题
        binding.tvSubtitle.setText("主演：" + StringFormatUtil.formatName(subjectsBean.getCasts()));

        binding.titleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initScrollView() {
        discoverScrollView.setVisibility(View.VISIBLE);
        discoverScrollView.setCallBack_scrollChanged(new CallBack_ScrollChanged() {
            @Override
            public void onScrollChanged(int scrolledY) {
                scrollChangeHeader(scrolledY);
            }
        });
    }

    private void initRecyclerView() {
        rcvGoodsList.setVisibility(View.VISIBLE);
        final RCVListAdapter adapter = new RCVListAdapter(this);
        View header = new View(this);
        header.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.new_home_header_size) - getStatusBarHeight(this);
        adapter.setHeader(header);

        rcvGoodsList.setLayoutManager(new LinearLayoutManager(this));
        rcvGoodsList.setItemAnimator(new DefaultItemAnimator());
        rcvGoodsList.setAdapter(adapter);
        adapter.setDataSource(Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", ""));
        adapter.notifyDataSetChanged();


        rcvGoodsList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            public int scrolledY = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrolledY += dy;
                scrollChangeHeader(scrolledY);
            }
        });

    }


    private void initNewSlidingParams() {
        int headerSize = getResources().getDimensionPixelOffset(R.dimen.new_home_header_size);
        int navBarHeight = getResources().getDimensionPixelOffset(R.dimen.nav_bar_height) + 2 * getStatusBarHeight(this);
        slidingDistance = headerSize - navBarHeight;// 172-(56+titleHeight)
        Log.d("HomeFragment", "slidingDistance" + slidingDistance);
    }


    /**
     * 根据页面滑动距离改变Header方法
     */
    private void scrollChangeHeader(int scrolledY) {
        if (scrolledY < 0) {
            scrolledY = 0;
        }
        if (scrolledY < slidingDistance) {
            // 状态栏渐变
            StatusBarUtils.setTranslucentImageHeader(this, scrolledY * 50 / slidingDistance, rlHead);
            // title渐变
            rlHead.setBackgroundColor(Color.argb(scrolledY * 50 / slidingDistance, 0x00, 0x00, 0x00));
            // 背景图高度设置
            llHeader.setPadding(0, -scrolledY, 0, 0);
            currScrollY = scrolledY;
        } else {
            StatusBarUtils.setTranslucentImageHeader(this, 50, rlHead);
            rlHead.setBackgroundColor(Color.argb(50, 0x00, 0x00, 0x00));
            llHeader.setPadding(0, -slidingDistance, 0, 0);
            currScrollY = slidingDistance;
        }
    }


    /**
     * @param context      activity
     * @param positionData bean
     * @param imageView    imageView
     */
    public static void start(Activity context, SubjectsBean positionData, ImageView imageView) {
        Intent intent = new Intent(context, SlideScrollViewActivity.class);
        intent.putExtra("bean", positionData);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

}
