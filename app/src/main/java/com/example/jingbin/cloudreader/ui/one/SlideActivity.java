package com.example.jingbin.cloudreader.ui.one;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ActivitySlideBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.view.CallBack_ScrollChanged;
import com.example.jingbin.cloudreader.view.test.StatusBarUtils;

public class SlideActivity extends AppCompatActivity {

    private ActivitySlideBinding binding;
    SubjectsBean subjectsBean;

    int slidingDistance;

    int currScrollY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_slide);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_slide);

        if (getIntent() != null) {
            subjectsBean = (SubjectsBean) getIntent().getSerializableExtra("bean");
        }


        binding.tvTitle.setText("标题");

        // 先设置状态栏透明
        StatusBarUtils.setTranslucentImageHeader(this, 0, binding.titleToolBar);
        // 高斯模糊
//        ImgLoadUtil.displayGaussian(this, subjectsBean.getImages().getLarge(), binding.imgItemBg);


        if (binding.imgItemBg != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.imgItemBg.getLayoutParams();
            layoutParams.setMargins(0, -getStatusBarHeight(this), 0, 0);
            DebugUtil.error("getStatusBarHeight:"+getStatusBarHeight(this));
        }
        initNewSlidingParams();

        binding.scrollView.setCallBack_scrollChanged(new CallBack_ScrollChanged() {
            @Override
            public void onScrollChanged(int scrolledY) {
                if (scrolledY < 0) {
                    scrolledY = 0;
                }
                if (scrolledY < slidingDistance) {
                    // 状态栏渐变
                    StatusBarUtils.setTranslucentImageHeader(SlideActivity.this, scrolledY * 110 / slidingDistance, binding.titleToolBar);
                    // title渐变
                    binding.titleToolBar.setBackgroundColor(Color.argb(scrolledY * 110 / slidingDistance, 0x00, 0x00, 0x00));
                    // 背景图高度设置
                    binding.imgItemBg.setPadding(0, -scrolledY, 0, 0);
                    currScrollY = scrolledY;
                } else {
                    StatusBarUtils.setTranslucentImageHeader(SlideActivity.this, 110, binding.titleToolBar);
                    binding.titleToolBar.setBackgroundColor(Color.argb(110, 0x00, 0x00, 0x00));
                    binding.imgItemBg.setPadding(0, -slidingDistance, 0, 0);
                    currScrollY = slidingDistance;
                }
            }
        });
    }


    private void initNewSlidingParams() {
        int headerSize = getResources().getDimensionPixelOffset(R.dimen.home_header_size);
        int navBarHeight = getResources().getDimensionPixelOffset(R.dimen.nav_bar_height) + getStatusBarHeight(this);
//        int tabStripHeight = getResources().getDimensionPixelOffset(R.dimen.tabstrip_height);
//        slidingDistance = headerSize - navBarHeight - tabStripHeight;
        slidingDistance = headerSize - navBarHeight;
        Log.d("HomeFragment", "slidingDistance" + slidingDistance);
    }

    /**
     * @param context      activity
     * @param positionData bean
     * @param imageView    imageView
     */
    public static void start(Activity context, SubjectsBean positionData, ImageView imageView) {
        Intent intent = new Intent(context, SlideActivity.class);
        intent.putExtra("bean", positionData);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

}
