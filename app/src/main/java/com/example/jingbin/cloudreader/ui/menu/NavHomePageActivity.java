package com.example.jingbin.cloudreader.ui.menu;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.databinding.ActivityNavHomePageBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.StatusBarUtil;
import com.example.jingbin.cloudreader.utils.ToolbarHelper;
import com.example.jingbin.cloudreader.ui.WebViewActivity;

/**
 * @author jingbin
 */
public class NavHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 4.4 标题透明
        StatusBarUtil.setTranslucentStatus(this);
        ActivityNavHomePageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_nav_home_page);
        // 解决7.0以上系统 滑动到顶部 标题裁减一半的问题
//        setSupportActionBar(binding.detailToolbar);
        ToolbarHelper.initFullBar(binding.detailToolbar, this);
        binding.detailToolbar.setNavigationIcon(null);

        binding.fabShare.setOnClickListener(v -> WebViewActivity.loadUrl(v.getContext(), CommonUtils.getString(R.string.string_url_cloudreader), "CloudReader"));
    }

    public static void startHome(Context mContext) {
        Intent intent = new Intent(mContext, NavHomePageActivity.class);
        mContext.startActivity(intent);
    }
}
