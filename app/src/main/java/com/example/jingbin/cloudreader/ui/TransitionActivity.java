package com.example.jingbin.cloudreader.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jingbin.cloudreader.MainActivity;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.databinding.ActivityTransitionBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;

import java.lang.ref.WeakReference;

/**
 * @author jingbin
 */
public class TransitionActivity extends AppCompatActivity {

    public ActivityTransitionBinding mBinding;
    private boolean isIn;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);
        // 后台返回时可能启动这个页面 http://blog.csdn.net/jianiuqi/article/details/54091181
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        showImage();
//        toMainActivity();
    }

    private void showImage() {
        // 先显示默认图
        mBinding.ivDefultPic.setImageDrawable(CommonUtils.getDrawable(R.drawable.img_transition_default));


        //        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
//        Glide.with(this)
//                .load(ConstantsImageUrl.TRANSITION_URLS[i])
//                .placeholder(R.drawable.img_transition_default)
//                .error(R.drawable.img_transition_default)
//                .into(mBinding.ivPic);

        mBinding.tvJump.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                toMainActivity();
            }
        });

        handler = new MyHandler(this);
        handler.sendEmptyMessageDelayed(1, 300);
//        handler.sendEmptyMessageDelayed(1, 3500);
    }

    static class MyHandler extends Handler {
        private WeakReference<TransitionActivity> mOuter;

        MyHandler(TransitionActivity activity) {
            mOuter = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TransitionActivity outer = mOuter.get();
            if (outer != null) {
                something(outer, msg);
            }
        }

        private void something(TransitionActivity outer, Message msg) {
            switch (msg.what) {
                case 0:
                    outer.mBinding.ivDefultPic.setVisibility(View.GONE);
                    break;
                case 1:
                    outer.toMainActivity();
                    break;
                default:
                    break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
