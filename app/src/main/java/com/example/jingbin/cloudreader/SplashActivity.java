package com.example.jingbin.cloudreader;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jingbin.cloudreader.databinding.ActivitySplashBinding;

/**
 * 引导图，正在完成中
 */
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mDataBing;
//    private int[] mDrawables = new int[]{R.drawable.b_1, R.drawable.b_2, R.drawable.b_3, R.drawable.b_4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBing = DataBindingUtil.setContentView(this, R.layout.activity_splash);

//        ArrayList<Integer> mDrawables = new ArrayList<>();
//        mDrawables.add(R.drawable.b_1);
//        mDrawables.add(R.drawable.b_2);
//        mDrawables.add(R.drawable.b_3);
//        mDrawables.add(R.drawable.b_4);
//        mDataBing.bannerSplash.setImages(mDrawables).setImageLoader(new GlideImageLoader()).start();


//        int length = mDrawables.length;
//        for (int i = 0; i < length; i++) {
//            mDataBing.slider.addSlider(new GuidanceItem(this, drawables[i], i));
//        }
//        mDataBing.slider.disableCycle();
//        mDataBing.slider.setPosition(0);
    }
}
