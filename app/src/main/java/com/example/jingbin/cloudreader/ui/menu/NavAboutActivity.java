package com.example.jingbin.cloudreader.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.databinding.ActivityNavAboutBinding;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

public class NavAboutActivity extends BaseActivity<ActivityNavAboutBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_about);
        showContentView();
        setTitle("关于云阅");
        bindingView.tvVersionName.setText("当前版本 V" + BaseTools.getVersionName());


        // 直接写在布局文件里会很耗内存
        Glide.with(this).load(R.drawable.ic_cloudreader).into(bindingView.ivIcon);
        bindingView.tvGankio.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        bindingView.tvGankio.getPaint().setAntiAlias(true);//抗锯齿
        bindingView.tvDouban.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        bindingView.tvDouban.getPaint().setAntiAlias(true);//抗锯齿

        initListener();
    }

    private void initListener() {
        bindingView.tvGankio.setOnClickListener(listener);
        bindingView.tvDouban.setOnClickListener(listener);
        bindingView.tvAboutStar.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                BaseTools.openLink(v.getContext(), "https://github.com/youlookwhat/CloudReader");
            }
        });
        bindingView.tvFunction.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                BaseTools.openLink(v.getContext(), "http://jingbin.me/2016/12/30/%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-%E4%BA%91%E9%98%85/");
            }
        });
        bindingView.tvNewVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseTools.openLink(v.getContext(), "https://fir.im/cloudreader");
            }
        });
    }

    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            String url = null;
            switch (v.getId()) {
                case R.id.tv_gankio:
                    url = "http://gank.io/api";
                    break;
                case R.id.tv_douban:
                    url = "https://developers.douban.com/wiki/?title=terms";
                    break;
                case R.id.tv_about_star:
                    url = "https://github.com/youlookwhat/CloudReader";
                    break;
                case R.id.tv_function:// 更新日志
                    url = "http://jingbin.me/2016/12/30/%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-%E4%BA%91%E9%98%85/";
                    break;
            }
            WebViewActivity.loadUrl(v.getContext(), url, "加载中...");
        }
    };

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavAboutActivity.class);
        mContext.startActivity(intent);
    }
}
