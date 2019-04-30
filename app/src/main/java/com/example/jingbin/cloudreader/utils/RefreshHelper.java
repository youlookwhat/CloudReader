package com.example.jingbin.cloudreader.utils;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.view.MyDividerItemDecoration;
import com.example.xrecyclerview.XRecyclerView;

/**
 * @author jingbin
 * @data 2019/4/30
 * @description
 */

public class RefreshHelper {

    public static void init(XRecyclerView recyclerView) {
        init(recyclerView, true, true);
    }

    /**
     * @param isShowFirstDivider 第一个item是否显示分割线
     */
    public static void init(XRecyclerView recyclerView, boolean isShowFirstDivider) {
        init(recyclerView, isShowFirstDivider, true);
    }

    /**
     * 默认不显示最后一个item的分割线
     *
     * @param isShowFirstDivider  第一个item是否显示分割线
     * @param isShowSecondDivider 第二个item是否显示分割线
     */
    public static void init(XRecyclerView recyclerView, boolean isShowFirstDivider, boolean isShowSecondDivider) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.clearHeader();
        recyclerView.setItemAnimator(null);
        MyDividerItemDecoration itemDecoration = new MyDividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL, false, isShowFirstDivider, isShowSecondDivider);
        itemDecoration.setDrawable(ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.shape_line));
        recyclerView.addItemDecoration(itemDecoration);
    }
}
