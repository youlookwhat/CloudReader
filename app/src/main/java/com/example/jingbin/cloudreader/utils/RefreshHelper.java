package com.example.jingbin.cloudreader.utils;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.view.MyDividerItemDecoration;
import com.example.xrecyclerview.XRecyclerView;

/**
 * @author jingbin
 * @data 2019/1/20
 * @description
 */

public class RefreshHelper {

    public static void init(XRecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.clearHeader();
        recyclerView.setItemAnimator(null);
        MyDividerItemDecoration itemDecoration = new MyDividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL, false);
        itemDecoration.setDrawable(ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.shape_line));
        recyclerView.addItemDecoration(itemDecoration);
    }
}
