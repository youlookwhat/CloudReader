package com.example.jingbin.cloudreader.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.bean.FilmDetailBean;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeBean;
import com.example.jingbin.cloudreader.http.cache.ACache;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jingbin
 */
public class DataUtil {

    /**
     * 玩安卓首页列表显示用户名
     */
    public static String getHomeAuthor(boolean isNew, String author, String shareName) {
        String name = author;
        if (TextUtils.isEmpty(name)) {
            name = shareName;
        }
        if (TextUtils.isEmpty(name)) {
            name = "匿名";
        }
        if (isNew) {
            return " " + name;
        } else {
            return name;
        }
    }

    /**
     * 玩安卓知识体系列表显示用户名
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

    /**
     * 积分增加减少
     */
    public static String getAdd(int value) {
        if (value < 0) {
            return String.valueOf(value);
        } else {
            return "+" + value;
        }
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

    /**
     * 剔除不必要的信息
     */
    public static GankIoDataBean getTrueData(GankIoDataBean bean) {
        GankIoDataBean dataBean = new GankIoDataBean();
        if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
            List<GankIoDataBean.ResultBean> removeList = new ArrayList<>();
            List<GankIoDataBean.ResultBean> results = bean.getResults();
            for (GankIoDataBean.ResultBean resultBean : results) {
                if (!TextUtils.isEmpty(resultBean.getUrl())
                        && resultBean.getUrl().contains("yangchong")) {
                    removeList.add(resultBean);
                }
            }
            results.removeAll(removeList);
            dataBean.setResults(results);
        }
        return dataBean;
    }

    /**
     * 剔除不必要的信息
     */
    public static List<AndroidBean> getTrueData(List<AndroidBean> list) {
        ArrayList<AndroidBean> arrayList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            List<AndroidBean> removeList = new ArrayList<>();
            for (AndroidBean resultBean : list) {
                if (!TextUtils.isEmpty(resultBean.getUrl())
                        && resultBean.getUrl().contains("yangchong")) {
                    removeList.add(resultBean);
                }
            }
            list.removeAll(removeList);
            arrayList.addAll(list);
        }
        return arrayList;
    }

    /**
     * 直接使用html格式化会有性能问题
     */
    public static String getHtmlString(String content) {
        if (content != null && content.contains("&amp;")) {
            return content.replace("&amp;", "&");
        }
        return content;
    }

    /**
     * 直接使用html格式化会有性能问题
     */
    public static String getGanHuoTime(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        return content;
    }

    /**
     * 保存知识体系数据
     */
    public static void putTreeData(Context context, TreeBean treeBean) {
        ACache.get(context).put("TreeBean", treeBean);
    }

    public static TreeBean getTreeData(Context context) {
        return (TreeBean) ACache.get(context).getAsObject("TreeBean");
    }

    /**
     * 详情内演员字段
     */
    public static String getActorString(List<FilmDetailBean.ActorsBean> beans) {
        StringBuilder text = new StringBuilder();
        int i = 0;
        if (beans != null) {
            for (FilmDetailBean.ActorsBean bean : beans) {
                if (i == 4) {
                    break;
                }
                String name = bean.getName();
                if (!TextUtils.isEmpty(name)) {
                    i++;
                    text.append(" / ").append(name);
                }
            }
        }
        String toString = text.toString();
        if (toString.contains(" / ")) {
            toString = toString.substring(3);
        }
        return toString;
    }
}
