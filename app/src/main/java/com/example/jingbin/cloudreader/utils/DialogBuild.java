package com.example.jingbin.cloudreader.utils;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;

/**
 * @author jingbin
 * @data 2018/2/27
 * @Description
 */

public class DialogBuild {

    /**
     * 显示单行文字的AlertDialog
     */
    public static void show(View v, String title, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = View.inflate(v.getContext(), R.layout.title_douban_top, null);
        TextView titleTop = view.findViewById(R.id.title_top);
//        titleTop.setTextSize(14);
        titleTop.setText(title);
        builder.setCustomTitle(view);
        builder.setPositiveButton("查看详情", clickListener::onClick);
        builder.show();
    }
}
