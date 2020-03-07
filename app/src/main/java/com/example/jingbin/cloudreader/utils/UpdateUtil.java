package com.example.jingbin.cloudreader.utils;

import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.example.jingbin.cloudreader.BuildConfig;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.bean.UpdateBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jingbin
 * @data 2020/03/04
 * @Description 更新提醒
 */
public class UpdateUtil {

    /**
     * 检查更新
     *
     * @param isShowToast 是否弹出更新框
     */
    public static void check(final BaseActivity activity, final boolean isShowToast) {
        if (activity == null) {
            return;
        }
        Disposable subscribe = HttpClient.Builder.getGiteeServer().checkUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateBean>() {
                    @Override
                    public void accept(UpdateBean updateBean) throws Exception {
                        // 检查更新
                        if (updateBean != null && Integer.valueOf(updateBean.getVersion()) <= BuildConfig.VERSION_CODE) {
                            if (isShowToast) {
                                ToastUtil.showToastLong("已是最新版本~");
                            }
                            return;
                        }

                        // 进入应用体提示更新
                        if (updateBean != null
                                && "1".equals(updateBean.getIsShow())
                                && !TextUtils.isEmpty(updateBean.getInstallUrl())) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            // 返回无效
                            builder.setCancelable(false);
                            builder.setTitle("发现新版本")
                                    .setMessage(String.format("版本号: %s\n更新时间: %s\n更新内容:\n%s",
                                            updateBean.getVersionShort(),
                                            TimeUtil.formatDataTime(Long.valueOf(updateBean.getupdatedAt() + "000")),
                                            updateBean.getChangelog()));
                            builder.setNegativeButton("取消", null);
                            builder.setPositiveButton("立即下载", (dialogInterface, i) -> BaseTools.openLink(activity, updateBean.getInstallUrl()));
                            builder.show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isShowToast) {
                            ToastUtil.showToastLong("抱歉，检查失败，请进入链接查看或联系作者~");
                        }
                    }
                });
        activity.addSubscription(subscribe);
    }
}
