package com.example.jingbin.cloudreader.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.databinding.ActivityNavHomePageBinding;

public class NavHomePageActivity extends BaseActivity<ActivityNavHomePageBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home_page);
        setTitle("主页");
        showContentView();
    }


    public static void startHome(Context mContext) {
        Intent intent = new Intent(mContext, NavHomePageActivity.class);
        mContext.startActivity(intent);
    }
}
