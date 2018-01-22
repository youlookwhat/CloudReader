package com.example.jingbin.cloudreader.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.jingbin.cloudreader.app.CloudReaderApplication;

/**
 * Created by jingbin on 2016/12/14.
 * 单例Toast
 */

public class ToastUtil {

    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(CloudReaderApplication.getInstance(), text, Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToastLong(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(CloudReaderApplication.getInstance(), text, Toast.LENGTH_SHORT);
        }
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(text);
        mToast.show();
    }

}
