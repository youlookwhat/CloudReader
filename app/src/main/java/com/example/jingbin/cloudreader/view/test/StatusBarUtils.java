package com.example.jingbin.cloudreader.view.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by zhouwei on 16/10/21.
 */
public class StatusBarUtils {


    public static void setColor(Activity activity, @ColorInt int color, int statusBarAlpha){
        //先设置的全屏模式
        setFullScreen(activity);
        //在透明状态栏的垂直下方放置一个和状态栏同样高宽的view
        addStatusBarBehind(activity,color,statusBarAlpha);
    }
    /**
     * 添加了一个状态栏(实际上是个view)，放在了状态栏的垂直下方
     */
    public static void addStatusBarBehind(Activity activity, @ColorInt int color, int statusBarAlpha) {
        //获取windowphone下的decorView
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int       count     = decorView.getChildCount();
        //判断是否已经添加了statusBarView
        if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
            decorView.getChildAt(count - 1).setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
        } else {
            //新建一个和状态栏高宽的view
            StatusBarView statusView = createStatusBarView(activity, color, statusBarAlpha);
            decorView.addView(statusView);
        }
        setRootView(activity);
    }

    public static void setTranslucentImageHeader(Activity activity, int alpha,View needOffsetView){
        setFullScreen(activity);
        //获取windowphone下的decorView
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int       count     = decorView.getChildCount();
        //判断是否已经添加了statusBarView
        if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
            decorView.getChildAt(count - 1).setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        } else {
            //新建一个和状态栏高宽的view
            StatusBarView statusView = createTranslucentStatusBarView(activity, alpha);
            decorView.addView(statusView);
        }

        if (needOffsetView != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
            layoutParams.setMargins(0, getStatusBarHeight(activity), 0, 0);
        }

    }



    private static StatusBarView createTranslucentStatusBarView(Activity activity, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        return statusBarView;
    }

    /**
     * 设置根布局参数
     */
    private static void setRootView(Activity activity) {
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        //rootview不会为状态栏流出状态栏空间
        ViewCompat.setFitsSystemWindows(rootView,true);
        rootView.setClipToPadding(true);
    }
    private static StatusBarView createStatusBarView(Activity activity, int color, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
        return statusBarView;
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    public static  void setFullScreen(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置透明状态栏,这样才能让 ContentView 向上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static class StatusBarView extends View {

        public StatusBarView(Context context) {
            super(context);
        }

        public StatusBarView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public StatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
    }
}
