package com.example.jingbin.cloudreader.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by jingbin on 2016/11/17.
 * js通信接口
 */
public class ImageClickInterface {

    public ImageClickInterface(Context context) {
    }

    @JavascriptInterface
    public void imageClick(String imgUrl, String hasLink) {
        Log.e("----点击了图片 url: ", "" + imgUrl);
    }

    @JavascriptInterface
    public void textClick(String type, String item_pk) {
        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(item_pk)) {
            Log.e("----点击了文字", "");
        }
    }
}
