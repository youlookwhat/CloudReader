package com.example.jingbin.cloudreader.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.jingbin.cloudreader.app.App;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by jingbin on 2016/12/14.
 * 单例Toast，兼容索尼部分手机不弹提示的问题，和vivo7.1.1部分手机崩溃问题
 * An Android library to hook and fix Toast BadTokenException
 * https://github.com/PureWriter/ToastCompat
 */

public class ToastUtil {

    private static ToastCompat mToast;

    @SuppressLint("ShowToast")
    public static void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = ToastCompat.makeText(App.getInstance(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.cancel();
                mToast = ToastCompat.makeText(App.getInstance(), text, Toast.LENGTH_SHORT);
            }
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
            mToast.show();
        }
    }

    @SuppressLint("ShowToast")
    public static void showToastLong(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = ToastCompat.makeText(App.getInstance(), text, Toast.LENGTH_LONG);
            } else {
                mToast.cancel();
                mToast = ToastCompat.makeText(App.getInstance(), text, Toast.LENGTH_LONG);
            }
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setText(text);
            mToast.show();
        }
    }

}
