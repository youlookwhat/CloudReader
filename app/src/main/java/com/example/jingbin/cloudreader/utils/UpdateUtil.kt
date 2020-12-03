package com.example.jingbin.cloudreader.utils

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.example.jingbin.cloudreader.BuildConfig
import com.example.jingbin.cloudreader.app.Constants
import com.example.jingbin.cloudreader.bean.UpdateBean
import com.example.jingbin.cloudreader.http.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import me.jingbin.bymvvm.base.BaseActivity

/**
 * Created by jingbin on 2020/12/3.
 */
class UpdateUtil {

    companion object {
        /**
         * 检查更新
         *
         * @param isShowToast 是否弹出更新框
         */
        @JvmStatic
        fun check(activity: BaseActivity<*, *>?, isShowToast: Boolean) {
            if (activity == null) {
                return
            }
            val subscribe = HttpClient.Builder.getGiteeServer().checkUpdate()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Consumer<UpdateBean?> {
                        override fun accept(updateBean: UpdateBean?) { // 检查更新
                            if (updateBean != null && Integer.valueOf(updateBean.version) <= BuildConfig.VERSION_CODE) {
                                if (isShowToast) {
                                    ToastUtil.showToastLong("已是最新版本~")
                                }
                                return
                            }
                            // 进入应用体提示更新
                            if (updateBean != null && "1" == updateBean.isShow && !updateBean.installUrl.isNullOrEmpty()) {
                                val builder = AlertDialog.Builder(activity)
                                // 返回无效
                                builder.setCancelable(false)
                                builder.setTitle("发现新版本")
                                        .setMessage(String.format("版本号: %s\n更新时间: %s\n更新内容:\n%s",
                                                updateBean.versionShort,
                                                TimeUtil.formatDataTime((updateBean.updated_at.toString() + "000").toLong()),
                                                updateBean.changelog))
                                builder.setNegativeButton("取消", null)
                                builder.setPositiveButton("立即下载") { dialogInterface: DialogInterface?, i: Int ->
                                    when {
                                        // isJumpMarket == 1 跳应用市场
                                        updateBean.isJumpMarket == 1
                                                && BaseTools.isApplicationAvilible(activity, Constants.COOLAPK_PACKAGE)
                                                && BaseTools.launchAppDetail(activity, activity.packageName, Constants.COOLAPK_PACKAGE) -> {
                                        }
                                        else -> {
                                            BaseTools.openLink(activity, updateBean.installUrl)
                                        }
                                    }
                                }
                                builder.show()
                            }
                        }
                    }, Consumer<Throwable?> {
                        if (isShowToast) {
                            ToastUtil.showToastLong("抱歉，检查失败，请进入链接查看或联系作者~")
                        }
                    })
            activity.addSubscription(subscribe)
        }
    }
}