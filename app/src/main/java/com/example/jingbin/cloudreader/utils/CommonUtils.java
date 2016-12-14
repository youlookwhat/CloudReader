package com.example.jingbin.cloudreader.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import com.example.jingbin.cloudreader.app.CloudReaderApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by jingbin on 2016/11/22.
 * 获取原生资源
 */
public class CommonUtils {

    /**
     * 随机颜色
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;//50-199
        int green = random.nextInt(150) + 50;//50-199
        int blue = random.nextInt(150) + 50;//50-199
        return Color.rgb(red, green, blue);
    }

    /**
     * 得到年月日的"日"
     */
    private String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("dd");
        return dateFm.format(date);
    }

    /**
     * 获取屏幕px
     *
     * @param context
     * @return 分辨率
     */
    static public int getScreenWidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }

//	public static void RunOnUiThread(Runnable r) {
//		CloudReaderApplication.getInstance().getMainLooper().post(r);
//	}

    public static Drawable getDrawable(int resid) {
        return getResoure().getDrawable(resid);
    }

    public static int getColor(int resid) {
        return getResoure().getColor(resid);
    }

    public static Resources getResoure() {
        return CloudReaderApplication.getInstance().getResources();
    }

    public static String[] getStringArray(int resid) {
        return getResoure().getStringArray(resid);
    }

    public static String getString(int resid) {
        return getResoure().getString(resid);
    }

    public static float getDimens(int resId) {
        return getResoure().getDimension(resId);
    }

    public static void removeSelfFromParent(View child) {

        if (child != null) {

            ViewParent parent = child.getParent();

            if (parent instanceof ViewGroup) {

                ViewGroup group = (ViewGroup) parent;

                group.removeView(child);
            }
        }
    }

}
