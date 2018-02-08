package com.example.jingbin.cloudreader.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;

import com.bumptech.glide.Glide;
import com.example.jingbin.cloudreader.MainActivity;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.app.ConstantsImageUrl;
import com.example.jingbin.cloudreader.databinding.ActivityTransitionBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;

import java.util.Random;

public class TransitionActivity extends AppCompatActivity {

    private ActivityTransitionBinding mBinding;
    private boolean animationEnd;
    private boolean isIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);
        // 后台返回时可能启动这个页面 http://blog.csdn.net/jianiuqi/article/details/54091181
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }

        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        // 先显示默认图
        mBinding.ivDefultPic.setImageDrawable(CommonUtils.getDrawable(R.drawable.img_transition_default));
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(mBinding.ivPic);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.ivDefultPic.setVisibility(View.GONE);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 3500);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition_anim);
//        animation.setAnimationListener(animationListener);
//        mBinding.ivPic.startAnimation(animation);

        mBinding.tvJump.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                toMainActivity();
//                animationEnd();
            }
        });
    }

    /**
     * 实现监听跳转效果
     */
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            animationEnd();
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };


    private void animationEnd() {
        synchronized (TransitionActivity.this) {
            if (!animationEnd) {
                animationEnd = true;
                mBinding.ivPic.clearAnimation();
                toMainActivity();
            }
        }
    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }
}
