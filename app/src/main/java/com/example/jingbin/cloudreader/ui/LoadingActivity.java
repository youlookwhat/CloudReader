package com.example.jingbin.cloudreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.FragmentActivity;

import com.example.jingbin.cloudreader.R;

/**
 * 解决启动白屏问题
 *
 * @author jingbin
 */
public class LoadingActivity extends FragmentActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 后台返回时可能启动这个页面 http://blog.csdn.net/jianiuqi/article/details/54091181
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                finish();
            }
        }, 200);
    }

    @Override
    protected void onDestroy() {
        handler = null;
        super.onDestroy();
    }
}
