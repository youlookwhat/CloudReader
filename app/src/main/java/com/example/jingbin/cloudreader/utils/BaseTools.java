package com.example.jingbin.cloudreader.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.jingbin.cloudreader.app.CloudReaderApplication;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by jingbin on 2017/2/13.
 */

public class BaseTools {

    //获取图片所在文件夹名称
    public static String getDir(String path) {
        String subString = path.substring(0, path.lastIndexOf('/'));
        return subString.substring(subString.lastIndexOf('/') + 1, subString.length());
    }

    public static int getWindowWidth(Context context) {
        // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (context
                .getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        return mScreenWidth;
    }

    public static int getWindowHeigh(Context context) {
        // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (context
                .getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenHeigh = dm.heightPixels;
        return mScreenHeigh;
    }

    //获得状态栏/通知栏的高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 使用默认方式显示货币：
     * 例如:￥12,345.46 默认保留2位小数，四舍五入
     *
     * @param d double
     * @return String
     */
    public static String formatCurrency(double d) {
        String s = "";
        try {
            DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.CHINA);
            s = nf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return "" + d;
        }
        return s;
    }


    /**
     * 去掉无效小数点 ".00"
     */

    public static String formatMoney(double d) {
        String tmp = formatCurrency(d);

        if (tmp.endsWith(".00")) {
            return tmp.substring(0, tmp.length() - 3);
        } else {
            return tmp;
        }
    }


    /**
     * 处于栈顶的Activity名
     */
    public String getTopActivityName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List var2 = am.getRunningTasks(1);
        return ((ActivityManager.RunningTaskInfo) var2.get(0)).topActivity.getClassName();
    }

    public static void setText(String text, TextView textView) {
        if (textView != null) {
            if (TextUtils.isEmpty(text)) {
                textView.setText("");
            } else {
                textView.setText(text);
            }
        }
    }

    /**
     * 获取当前应用的版本号
     */
    public static String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = CloudReaderApplication.getInstance().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(CloudReaderApplication.getInstance().getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }

    /**
     * 实现文本复制功能
     *
     * @param content 复制的文本
     */
    public static void copy(String content) {
        if (!TextUtils.isEmpty(content)) {
            // 得到剪贴板管理器
            ClipboardManager cmb = (ClipboardManager) CloudReaderApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(content.trim());
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            ClipData clipData = ClipData.newPlainText(null, content);
            // 把数据集设置（复制）到剪贴板
            cmb.setPrimaryClip(clipData);
        }
    }

    /**
     * 获取系统剪切板内容
     */
    public static String getClipContent() {
        ClipboardManager manager = (ClipboardManager) CloudReaderApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String addedTextString = String.valueOf(addedText);
                if (!TextUtils.isEmpty(addedTextString)) {
                    return StringFormatUtil.formatUrl(String.valueOf(addedText));
                }
            }
        }
        return "";
    }

    /**
     * 清空剪切板内容
     * 加上  manager.setText(null);  不然小米3Android6.0 清空无效
     * 因为api过期使用最新注意使用 manager.getPrimaryClip()，不然小米3Android6.0 清空无效
     */
    public static void clearClipboard() {
        ClipboardManager manager = (ClipboardManager) CloudReaderApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                manager.setPrimaryClip(manager.getPrimaryClip());
                manager.setText(null);
            } catch (Exception e) {
                DebugUtil.error(e.getMessage());
            }
        }
    }


    /**
     * 使用浏览器打开链接
     */
    public static void openLink(Context context, String content) {
        Uri issuesUrl = Uri.parse(content);
        Intent intent = new Intent(Intent.ACTION_VIEW, issuesUrl);
        context.startActivity(intent);
    }

    /**
     * 判断手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true：安装，false：未安装
     */
    public static boolean isApplicationAvilible(Context context, String appPackageName) {
        try {
            // 获取packagemanager
            PackageManager packageManager = context.getPackageManager();
            // 获取所有已安装程序的包信息
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (appPackageName.equals(pn)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity 要隐藏软键盘的activity
     */
    public static void hideSoftKeyBoard(Activity activity) {

        final View v = activity.getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            try {
                ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                Log.w("TAG", e.toString());
            }
        }
    }

    /**
     * 显示软键盘
     */
    public static void showSoftKeyBoard(Activity activity, View view) {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
    }

    /**
     * 发起添加群流程。群号：Android 云阅交流群(727379132) 的 key 为： jSdY9xxzZ7xXG55_V8OUb8ds_YT6JjAn
     * 调用 joinQQGroup(jSdY9xxzZ7xXG55_V8OUb8ds_YT6JjAn) 即可发起手Q客户端申请加群 Android 云阅交流群(727379132)
     *
     * @param key 由官网生成的key
     */
    public static void joinQQGroup(Context context, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            ToastUtil.showToastLong("未安装手Q或安装的版本不支持~");
        }
    }

    public static void joinQQChat(Context context, String qqNumber) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qqNumber));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            ToastUtil.showToastLong("未安装手Q或安装的版本不支持~");
        }
    }

}
