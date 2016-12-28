package com.example.jingbin.cloudreader.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.databinding.ActivityNavHomePageBinding;
import com.example.jingbin.cloudreader.utils.ShareUtils;
import com.example.jingbin.cloudreader.view.statusbar.StatusBarUtil;

public class NavHomePageActivity extends AppCompatActivity {

    private ActivityNavHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nav_home_page);

        binding.toolbarLayout.setTitle(getString(R.string.app_name));
        initTranslucentBar();
        binding.fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
    }


    private void initTranslucentBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, binding.toolbarLayout);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.toolbarLayout.getLayoutParams();
        layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(this), 0, 0);
        ViewGroup.MarginLayoutParams layoutParams2 = (ViewGroup.MarginLayoutParams) binding.toolbar.getLayoutParams();
        layoutParams2.setMargins(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
    }


    public static void startHome(Context mContext) {
        Intent intent = new Intent(mContext, NavHomePageActivity.class);
        mContext.startActivity(intent);
    }
}
