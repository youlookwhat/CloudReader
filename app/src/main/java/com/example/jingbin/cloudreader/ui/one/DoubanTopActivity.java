package com.example.jingbin.cloudreader.ui.one;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.databinding.ActivityDoubanTopBinding;

public class DouBanTopActivity extends BaseActivity<ActivityDoubanTopBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_top);
//        showLoading();
        setTitle("豆瓣电影Top250");
    }


    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, DouBanTopActivity.class);
        mContext.startActivity(intent);
    }
}
