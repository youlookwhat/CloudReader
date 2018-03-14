package com.example.jingbin.cloudreader.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

import com.example.jingbin.cloudreader.BuildConfig;
import com.example.jingbin.cloudreader.bean.UpdateBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description 更新提醒
 */
public class UpdateUtil {

    final private static String UPDATE_TOKEN = "5c5cbd9772b995661a3f00b72e429233";

    /**
     * 检查更新
     * BuildConfig.UPDATE_TOKEN
     *
     * @param activity
     * @param isShowToast 是否弹出Snackbar提示
     */
    public static void check(final Activity activity, final boolean isShowToast) {

        Subscription get = HttpClient.Builder.getFirServer().checkUpdate("58677918ca87a8490d000395", UPDATE_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isShowToast) {
                            ToastUtil.showToastLong("已是最新版本~");
                        }
                    }

                    @Override
                    public void onNext(final UpdateBean updateBean) {

                        if (Integer.valueOf(updateBean.getVersion()) <= BuildConfig.VERSION_CODE) {
                            if (isShowToast) {
                                ToastUtil.showToastLong("已是最新版本~");
//                                Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content), "已是最新版本! (*^__^*)", Snackbar.LENGTH_SHORT).show();
                            }
                            return;
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        // 返回无效
                        builder.setCancelable(false);
                        builder.setTitle("发现新版本")
                                .setMessage(String.format("版本号: %s\n更新时间: %s\n更新内容:\n%s",
                                        updateBean.getVersionShort(),
                                        TimeUtil.formatDataTime(Long.valueOf(updateBean.getUpdated_at() + "000")),
                                        updateBean.getChangelog()));
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("立即下载", (dialogInterface, i) -> BaseTools.openLink(activity, updateBean.getInstallUrl()));
                        builder.show();
                    }
                });
//        addSubscription(get);
    }
}
