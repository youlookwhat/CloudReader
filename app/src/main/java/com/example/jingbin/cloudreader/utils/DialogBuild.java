package com.example.jingbin.cloudreader.utils;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.data.room.Injection;
import com.example.jingbin.cloudreader.data.room.Wait;
import com.example.jingbin.cloudreader.data.room.WaitDataCallback;
import com.example.jingbin.cloudreader.view.OnLoginListener;

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
        titleTop.setText(title);
        builder.setView(view);
        builder.setPositiveButton("查看详情", clickListener::onClick);
        builder.show();
    }

    /**
     * 显示选项的AlertDialog
     */
    public static void showItems(View v, String content) {
        String[] items = {"复制", "分享"};
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    BaseTools.copy(content);
                    ToastUtil.showToast("复制成功");
                    break;
                case 1:
                    ShareUtils.share(v.getContext(), content);
                    break;
                default:
                    break;
            }
        });
        builder.show();
    }

    public static void showItems(View v, OnLoginListener listener) {
        Injection.get().getSingleBean(new WaitDataCallback() {
            @Override
            public void onDataNotAvailable() {
                String[] items = {"GitHub账号登录", "玩安卓登录"};
                showDialog(v, items, listener, false);
            }

            @Override
            public void getData(Wait bean) {
                String[] items = {"GitHub账号登录", "退出玩安卓"};
                showDialog(v, items, listener, true);
            }
        });

    }

    private static void showDialog(View v, String[] items, OnLoginListener listener, boolean isLogin) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("账号登录");
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    listener.loginGitHub();
                    break;
                case 1:
                    if (isLogin) {
                        Injection.get().deleteAllData();
                        ToastUtil.showToastLong("退出成功");
                    } else {
                        listener.loginWanAndroid();
                    }
                    break;
                default:
                    break;
            }
        });
        builder.show();
    }

}
