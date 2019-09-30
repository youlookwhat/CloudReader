package com.example.jingbin.cloudreader.utils;

import android.text.TextUtils;

import com.example.jingbin.cloudreader.R;

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

    /**
     * 将int转为String
     */
    public static String getStringValue(int value) {
        return String.valueOf(value);
    }

    public static String getAdd(int value) {
        return "+" + value;
    }

    /**
     * 时间格式化
     */
    public static String getTime(long time) {
        return TimeUtil.getTime(time);
    }

    /**
     * 处理标题
     */
    public static String replaceTime(String desc, long time) {
        if (!TextUtils.isEmpty(desc)) {
            return desc.replace(TimeUtil.getTime(time), "").trim();
        } else {
            return "";
        }
    }

    /**
     * 我的积分 分享文章的文字颜色区别
     */
    public static int getCoinTextColor(String desc) {
        if (!TextUtils.isEmpty(desc) && desc.contains("分享文章")) {
            return CommonUtils.getColor(R.color.colorPrimary);
        } else {
            return CommonUtils.getColor(R.color.colorTheme);
        }
    }

    /**
     * 设置显示赞赏入口
     */
    public static boolean isShowAdmire() {
        int currentMonthDay = TimeUtil.getCurrentMonthDay();
        String day = TimeUtil.getDay();
        if (Integer.valueOf(day) + 7 > currentMonthDay) {
            return true;
        }
        return false;
    }

}
