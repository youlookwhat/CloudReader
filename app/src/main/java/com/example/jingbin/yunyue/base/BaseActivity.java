package com.example.jingbin.yunyue.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.jingbin.yunyue.R;
import com.example.jingbin.yunyue.databinding.TitleBarBinding;
import com.example.jingbin.yunyue.utils.CommonUtils;
import com.example.jingbin.yunyue.utils.PerfectClickListener;
import com.example.jingbin.yunyue.view.statusbar.StatusBarUtil;

public class BaseActivity<SV extends ViewDataBinding> extends AppCompatActivity {

    // 标题
    protected TitleBarBinding bindingTitleView;
    // 布局view
    protected SV bindingView;
    private LinearLayout llProgressBar;
    private View refresh;
    protected View ll;

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        ll = getLayoutInflater().inflate(R.layout.activity_base, null);

        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        bindingTitleView = DataBindingUtil.inflate(getLayoutInflater(), R.layout.title_bar, null, false);

        // title
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bindingTitleView.getRoot().setLayoutParams(titleParams);
        RelativeLayout mTitleContainer = (RelativeLayout) ll.findViewById(R.id.title_container);
        mTitleContainer.addView(bindingTitleView.getRoot());
        getWindow().setContentView(ll);

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) ll.findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        getWindow().setContentView(ll);

        llProgressBar = getView(R.id.ll_progress_bar);
        refresh = getView(R.id.ll_error_refresh);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme),0);
        }
        // 设置titlebar
        setSupportActionBar(bindingTitleView.titleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
        bindingTitleView.titleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 点击加载失败布局
        refresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                onRefresh();
            }
        });
        bindingView.getRoot().setVisibility(View.GONE);

    }

    public void setTitle(CharSequence text) {
        bindingTitleView.tvTitle.setText(text);
    }

    protected void showLoading() {
        if (llProgressBar.getVisibility() != View.VISIBLE) {
            llProgressBar.setVisibility(View.VISIBLE);
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if (llProgressBar.getVisibility() != View.GONE) {
            llProgressBar.setVisibility(View.GONE);
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
        if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
            bindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showError() {
        if (llProgressBar.getVisibility() != View.GONE) {
            llProgressBar.setVisibility(View.GONE);
        }
        if (refresh.getVisibility() != View.VISIBLE) {
            refresh.setVisibility(View.VISIBLE);
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
    }

    protected void onRefresh() {

    }
}
