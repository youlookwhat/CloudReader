package com.example.jingbin.cloudreader.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.jingbin.cloudreader.app.App;

/**
 * Created by jingbin on 2015/10/19.
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param isDp   需要设置的数值是否为DP
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return
     */
    public static ViewGroup.LayoutParams setViewMargin(View view, boolean isDp, int left, int right, int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }

        //根据DP与PX转换计算值
        if (isDp) {
            leftPx = dip2px(view.getContext(), left);
            rightPx = dip2px(view.getContext(), right);
            topPx = dip2px(view.getContext(), top);
            bottomPx = dip2px(view.getContext(), bottom);
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        view.requestLayout();
        return marginParams;
    }

    /**
     * 通过比例得到高度
     *
     * @param bili         图片比例
     * @param type         1:外层 LinearLayout 2：外层 RelativeLayout
     * @param marginLR     左右的dp
     * @param marginTop    上面的dp
     * @param marginBottom 下面的dp
     */
    public static void formatHeight(View imageView, float bili, int type, int marginLR, int marginTop, int marginBottom) {
        WindowManager wm = (WindowManager) App.getInstance().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = (int) (width / bili);
        if (type == 1) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            imageView.setLayoutParams(lp);
        } else if (type == 2) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            imageView.setLayoutParams(lp);
        } else {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
            imageView.setLayoutParams(lp);
        }

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        layoutParams.setMargins(dip2px(imageView.getContext(), marginLR),
                dip2px(imageView.getContext(), marginTop),
                dip2px(imageView.getContext(), marginLR),
                dip2px(imageView.getContext(), marginBottom));
    }

    /**
     * 通过比例设置图片的高度
     *
     * @param width 图片的宽
     * @param bili  图片比例
     * @param type  1:外层 LinearLayout 2：外层 RelativeLayout
     */
    public static void formatHeight(View imageView, int width, float bili, int type) {
        int height = (int) (width / bili);
        if (type == 1) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            imageView.setLayoutParams(lp);
        } else if (type == 2) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            imageView.setLayoutParams(lp);
        } else {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            imageView.setLayoutParams(lp);
        }
    }

    /**
     * 通过比例设置图片的高度
     *
     * @param width 图片的宽
     * @param bili  图片比例
     */
    public static void setWidthHeight(View view, int width, float bili) {
        int height = (int) (width / bili);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
    }

    /**
     * 得到屏幕的宽度
     */
    public static int getDisplayWidth() {
        try {
            WindowManager wm = (WindowManager) App.getInstance().getSystemService(Context.WINDOW_SERVICE);
            return wm.getDefaultDisplay().getWidth();
        } catch (Exception e) {
            return 1080;
        }
    }

    @SuppressLint("ResourceType")
    public static void formatBannerHeight(View imageView, View view) {
        float displayWidth = getDisplayWidth();
        float width = (2f / 3 * displayWidth);
        float height = (2f / 3 * (displayWidth / 1.8f));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) width, (int) height);
        imageView.setLayoutParams(lp);
        imageView.setId(1);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) height);
        lp2.addRule(RelativeLayout.RIGHT_OF, 1);
        view.setLayoutParams(lp2);
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getApplicationContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}