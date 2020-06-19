package com.example.jingbin.cloudreader.utils;

import android.app.Activity;
import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ToolbarHelper {

    public static void initTranslucent(Activity activity) {
        StatusBarUtil.setTranslucentStatus(activity);
        StatusBarUtil.setLightMode(activity);
    }

    /**
     * 将该View的 top Padding 向下偏移一个状态栏的高度
     */
    public static void initPaddingTopDiffBar(View view) {
        view.setPadding(
                view.getPaddingStart(),
                view.getPaddingTop() + DensityUtil.getStatusHeight(view.getContext()),
                view.getPaddingEnd(),
                view.getPaddingBottom());
    }

    /**
     * 将View的top margin 向下偏移一个状态栏的高度
     */
    public static void initMarginTopDiffBar(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) params;
            linearParams.topMargin += DensityUtil.getStatusHeight(view.getContext());
        } else if (params instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams frameParams = (FrameLayout.LayoutParams) params;
            frameParams.topMargin += DensityUtil.getStatusHeight(view.getContext());
        } else if (params instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) params;
            relativeParams.topMargin += DensityUtil.getStatusHeight(view.getContext());
        } else if (params instanceof ConstraintLayout.LayoutParams) {
            ConstraintLayout.LayoutParams constraintParams = (ConstraintLayout.LayoutParams) params;
            constraintParams.topMargin += DensityUtil.getStatusHeight(view.getContext());
        }
        view.setLayoutParams(params);
    }

    /**
     * 将Toolbar高度填充到状态栏
     */
    public static void initFullBar(Toolbar toolbar, AppCompatActivity activity) {
        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
        params.height = DensityUtil.getStatusHeight(activity) + getSystemActionBarSize(activity);
        toolbar.setLayoutParams(params);
        toolbar.setPadding(
                toolbar.getLeft(),
                toolbar.getTop() + DensityUtil.getStatusHeight(activity),
                toolbar.getRight(),
                toolbar.getBottom()
        );
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private static int getSystemActionBarSize(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        } else {
            return DensityUtil.dip2px(context, 48);
        }
    }
}
