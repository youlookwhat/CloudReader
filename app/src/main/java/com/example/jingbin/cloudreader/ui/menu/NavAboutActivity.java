package com.example.jingbin.cloudreader.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.jingbin.cloudreader.BuildConfig;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.databinding.ActivityNavAboutBinding;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.UpdateUtil;

import me.jingbin.bymvvm.base.BaseActivity;
import me.jingbin.bymvvm.base.NoViewModel;

/**
 * @author jingbin
 */
public class NavAboutActivity extends BaseActivity<NoViewModel, ActivityNavAboutBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_about);
        showContentView();
        setTitle("关于云阅");
        bindingView.tvVersionName.setText("当前版本 V" + BuildConfig.VERSION_NAME);


        // 直接写在布局文件里会很耗内存
        Glide.with(this).load(R.drawable.ic_cloudreader).into(bindingView.ivIcon);
        bindingView.tvGankio.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        bindingView.tvGankio.getPaint().setAntiAlias(true);//抗锯齿
        bindingView.tvDouban.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        bindingView.tvDouban.getPaint().setAntiAlias(true);//抗锯齿
        bindingView.tvWanandroid.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        bindingView.tvWanandroid.getPaint().setAntiAlias(true);//抗锯齿

        initListener();
    }

    private void initListener() {
        bindingView.tvGankio.setOnClickListener(listener);
        bindingView.tvDouban.setOnClickListener(listener);
        bindingView.tvAboutStar.setOnClickListener(listener);
        bindingView.tvFunction.setOnClickListener(listener);
        bindingView.tvWanandroid.setOnClickListener(listener);
        bindingView.tvDownloadUrl.setOnClickListener(listener);

        // 酷安评分鼓励
        if (BaseTools.isApplicationAvilible(this, "com.coolapk.market")) {
            bindingView.lineRate.setVisibility(View.VISIBLE);
            bindingView.tvAboutRate.setVisibility(View.VISIBLE);
            bindingView.tvAboutRate.setOnClickListener(v -> BaseTools.launchAppDetail(NavAboutActivity.this, getPackageName(), "com.coolapk.market"));
        }
    }

    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            String url = null;
            String title = "加载中...";
            switch (v.getId()) {
                case R.id.tv_gankio:
                    url = CommonUtils.getString(R.string.string_url_gankio);
                    title = "干货集中营";
                    break;
                case R.id.tv_douban:
                    url = CommonUtils.getString(R.string.string_url_mtime);
                    title = "时光网";
                    break;
                case R.id.tv_about_star:
                    url = CommonUtils.getString(R.string.string_url_cloudreader);
                    title = "CloudReader";
                    break;
                case R.id.tv_function:
                    url = CommonUtils.getString(R.string.string_url_update_log);
                    title = "更新日志";
                    break;
                case R.id.tv_download_url:
                    url = Constants.DOWNLOAD_URL;
                    title = "云阅";
                    break;
                case R.id.tv_wanandroid:
                    url = CommonUtils.getString(R.string.string_url_wanandroid);
                    title = "玩Android";
                    break;
                default:
                    break;
            }
            WebViewActivity.loadUrl(v.getContext(), url, title);
        }
    };

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavAboutActivity.class);
        mContext.startActivity(intent);
    }

    public void checkUpdate(View view) {
        UpdateUtil.check(this, true);
    }
}
