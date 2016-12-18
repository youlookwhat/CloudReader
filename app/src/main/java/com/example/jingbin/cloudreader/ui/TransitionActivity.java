package com.example.jingbin.cloudreader.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.jingbin.cloudreader.MainActivity;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.databinding.ActivityTransitionBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;

public class TransitionActivity extends AppCompatActivity {

    private ActivityTransitionBinding mBinding;
    private boolean animationEnd;
    private int[] mDrawables = new int[]{R.drawable.b_1, R.drawable.b_2, R.drawable.b_3, R.drawable.b_4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);

        mBinding.ivPic.setImageDrawable(CommonUtils.getDrawable(mDrawables[2]));
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition_anim);
        animation.setAnimationListener(animationListener);
        mBinding.ivPic.startAnimation(animation);

        mBinding.tvJump.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                animationEnd();
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_down_in, R.anim.screen_up_out);
        finish();
    }
}
