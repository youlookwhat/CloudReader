package com.example.jingbin.cloudreader.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.ui.wan.child.MyCoinActivity;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.utils.SPUtils;

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
        if (SPUtils.getInt(Constants.IS_AGREE_PRIVATE, 0) == 0) {
            DialogBuild.showPrivate(this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SPUtils.putInt(Constants.IS_AGREE_PRIVATE, 1);
                    startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                    finish();
                }
            });
        } else {
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
    }

    @Override
    protected void onDestroy() {
        handler = null;
        super.onDestroy();
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, LoadingActivity.class);
        mContext.startActivity(intent);
    }
}
