package com.example.jingbin.cloudreader.utils;

import android.text.TextUtils;

/**
 * @author jingbin
 */
public class DataUtil {

    /**
     * 玩安卓列表显示用户名
     */
    public static String getAuthor(String author, String shareName) {
        String name = author;
        if (TextUtils.isEmpty(name)) {
            name = shareName;
        }
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        return " · " + name;
    }

}
