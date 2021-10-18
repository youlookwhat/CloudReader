package com.example.jingbin.cloudreader.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.app.RxCodeConstants;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.model.LoginModel;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.view.OnLoginListener;
import com.example.jingbin.cloudreader.view.OnShareDialogListener;

import me.jingbin.bymvvm.room.Injection;
import me.jingbin.bymvvm.room.User;
import me.jingbin.bymvvm.room.UserDataCallback;
import me.jingbin.bymvvm.rxbus.RxBus;
import me.jingbin.bymvvm.utils.CommonUtils;


/**
 * @author jingbin
 * @data 2018/2/27
 * @Description
 */

public class DialogBuild {

    /**
     * 显示自定义布局
     */
    public static void showCustom(View v, String content, String buttonText, DialogInterface.OnClickListener clickListener) {
        showCustom(v, 0, content, buttonText, null, clickListener);
    }

    /**
     * @param status 0弹框点击消失，1弹框不可点击消失
     */
    public static void showCustom(View v, int status, String content, String positiveText, String negativeText, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = View.inflate(v.getContext(), R.layout.title_douban_top, null);
        TextView titleTop = view.findViewById(R.id.title_top);
        if (status == 1) {
            builder.setCancelable(false);
        } else {
            builder.setCancelable(true);
        }
        titleTop.setText(content);
        builder.setView(view);
        builder.setPositiveButton(positiveText, clickListener);
        if (!TextUtils.isEmpty(negativeText)) {
            builder.setNegativeButton(negativeText, null);
        }
        builder.show();
    }

    public static void show(View v, String message, String buttonText, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton(buttonText, clickListener);
        builder.show();
    }

    public static void show(Context context, String message, String positiveText, String negativeText, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.title_douban_top, null);
        TextView titleTop = view.findViewById(R.id.title_top);
        titleTop.setText(message);
        builder.setView(view);
        builder.setPositiveButton(positiveText, clickListener);
        if (!TextUtils.isEmpty(negativeText)) {
            builder.setNegativeButton(negativeText, null);
        }
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

    /**
     * 用于账号登录
     */
    public static void showItems(View v, OnLoginListener listener) {
        Injection.get().getSingleBean(new UserDataCallback() {
            @Override
            public void onDataNotAvailable() {
                String[] items = {"GitHub账号", "玩安卓账号"};
                showDialog(v, items, listener, false);
            }

            @Override
            public void getData(User bean) {
                String[] items = {"GitHub账号", "退出玩安卓（" + bean.getUsername() + "）"};
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
                        new LoginModel().logout(() -> {
                            Injection.get().deleteAllData();
                            UserUtil.handleLoginFailure();
//                            ToastUtil.showToastLong("退出成功");
                            RxBus.getDefault().post(RxCodeConstants.LOGIN, false);
                        });
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

    public static void showItemDialog(View v, OnShareDialogListener listener) {
        String[] items = {"查看", "删除此篇分享"};
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    listener.look();
                    break;
                case 1:
                    listener.delete();
                    break;
                default:
                    break;
            }
        });
        builder.show();
    }

    /**
     * 编辑收藏网址
     */
    public static void show(View v, String name, String link, OnEditClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("编辑");
        View inflate = View.inflate(v.getContext(), R.layout.dialog_eidt_url, null);
        builder.setView(inflate);
        AppCompatEditText etName = inflate.findViewById(R.id.et_name);
        AppCompatEditText etLink = inflate.findViewById(R.id.et_link);
        if (!TextUtils.isEmpty(name)) {
            etName.setText(name);
            etName.setSelection(name.length());
        }
        etLink.setText(link);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("编辑完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString().trim();
                String link = etLink.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToastLong("请输入名称");
                    return;
                }
                if (TextUtils.isEmpty(link)) {
                    ToastUtil.showToastLong("请输入链接");
                    return;
                }
                listener.onClick(name, link);
            }
        });
        builder.show();
    }

    public interface OnEditClickListener {
        void onClick(String name, String link);
    }

    /**
     * 显示隐私政策弹框
     */
    public static void showPrivate(Context context, View.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_layout_private, null);
        TextView tv_agree = view.findViewById(R.id.tv_agree);
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_close_app = view.findViewById(R.id.tv_close_app);
        SpannableString doubleClickTag = BaseTools.getDoubleClickTag(context, R.color.colorTheme, CommonUtils.getString(context, R.string.string_private), "《隐私政策》", "", new BaseTools.OnActionListener<Integer>() {
            @Override
            public void click(Integer bean) {
                WebViewActivity.loadUrl(context, Constants.PRIVATE_URL, "隐私政策");
            }
        });
        tv_content.setText(doubleClickTag);
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        builder.setCancelable(false);
        builder.setView(view);
        AlertDialog show = builder.show();
        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                clickListener.onClick(v);
            }
        });
        tv_close_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
                // 杀死该应用进程 需要权限;
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
}
