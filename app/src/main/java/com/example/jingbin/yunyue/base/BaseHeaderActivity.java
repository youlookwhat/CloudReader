package com.example.jingbin.yunyue.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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
import com.example.jingbin.yunyue.utils.PerfectClickListener;

public abstract class BaseHeaderActivity<HV extends ViewDataBinding,SV extends ViewDataBinding> extends AppCompatActivity {

    // 标题
    protected TitleBarBinding bindingTitleView;
    // 内容布局头部
    protected HV bindingHeaderView;
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
        ll = getLayoutInflater().inflate(R.layout.activity_header_base, null);

        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        bindingHeaderView = DataBindingUtil.inflate(getLayoutInflater(), setHeaderLayout(), null, false);
        bindingTitleView = DataBindingUtil.inflate(getLayoutInflater(), R.layout.title_bar, null, false);

        // title
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bindingTitleView.getRoot().setLayoutParams(titleParams);
        RelativeLayout mTitleContainer = (RelativeLayout) ll.findViewById(R.id.title_container);
        mTitleContainer.addView(bindingTitleView.getRoot());
        getWindow().setContentView(ll);

        // header
        RelativeLayout.LayoutParams headerParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bindingHeaderView.getRoot().setLayoutParams(headerParams);
        RelativeLayout mHeaderContainer = (RelativeLayout) ll.findViewById(R.id.header_container);
        mHeaderContainer.addView(bindingHeaderView.getRoot());
        getWindow().setContentView(ll);

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) ll.findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        getWindow().setContentView(ll);

        llProgressBar = getView(R.id.ll_progress_bar);
        refresh = getView(R.id.ll_error_refresh);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme),0);
//            StatusBarUtil.setTranslucent(this);
//        }
//         设置titlebar
        setSupportActionBar(bindingTitleView.titleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
//        bindingTitleView.titleToolBar.setBackgroundColor(CommonUtils.getColor(R.color.colorTheme));
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

    protected abstract int setHeaderLayout();

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
