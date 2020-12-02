package com.example.jingbin.cloudreader.view

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface

/**
 * 登录监听
 */
interface OnLoginListener {

    fun loginWanAndroid()

    fun loginGitHub()
}

/**
 * js通信接口
 */
class ImageClickInterface(context: Context?) {

    @JavascriptInterface
    fun imageClick(imgUrl: String, hasLink: String?) {
        Log.e("----点击了图片 url: ", "$imgUrl- hasLink:$hasLink")
    }

    @JavascriptInterface
    fun textClick(type: String?, item_pk: String?) {
        if (!type.isNullOrEmpty() && !item_pk.isNullOrEmpty()) {
            Log.e("----点击了文字", "")
        }
    }
}