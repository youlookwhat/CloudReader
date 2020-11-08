package com.example.jingbin.cloudreader.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import me.jingbin.web.ByWebTools;

/**
 * @author jingbin
 */
public class WebUtil {

    public static void startActivity(Activity activity, String url) {
        try {
            Intent intent1 = new Intent();
            intent1.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent1.setData(uri);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理三方链接
     * 屏蔽网页里的广告
     * 简书“jianshu.com”
     **/
    /**
     * 默认处理流程：网页里可能唤起其他的app
     */
    public static boolean handleThirdApp(Activity activity, String backUrl) {
        DebugUtil.error("---backUrl:" + backUrl);
        /**http开头直接跳过*/
        if (backUrl.startsWith("http")) {
            // 可能有提示下载Apk文件
            if (backUrl.contains(".apk")) {
                startActivity(activity, backUrl);
                return true;
            }
            return false;
        }
        if (backUrl.contains("alipays")) {
            // 网页跳支付宝支付
            if (ByWebTools.hasPackage(activity, "com.eg.android.AlipayGphone")) {
                startActivity(activity, backUrl);
            }

        } else if (backUrl.contains("weixin://wap/pay")) {
            // 微信支付
            if (ByWebTools.hasPackage(activity, "com.tencent.mm")) {
                startActivity(activity, backUrl);
            }
        } else if (backUrl.contains("jianshu://")) {
            // 简书
            if (ByWebTools.hasPackage(activity, "com.jianshu.haruki")) {
                DialogBuild.show(activity, "是否使用《简书App》打开？", "打开", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(activity, backUrl);
                    }
                });
            }
        } else if (backUrl.contains("wvhzpj://")) {
            // csdn
            if (ByWebTools.hasPackage(activity, "net.csdn.csdnplus")) {
                startActivity(activity, backUrl);
            }
        } else {

            // 会唤起手机里有的App，如果不想被唤起，复制出来然后添加屏蔽即可
            boolean isJump = true;
            if (backUrl.contains("tbopen:")// 淘宝
                    || backUrl.contains("openapp.jdmobile:")// 京东
                    || backUrl.contains("jdmobile:")//京东
                    || backUrl.contains("zhihu:")// 知乎
                    || backUrl.contains("vipshop:")//
                    || backUrl.contains("youku:")//优酷
                    || backUrl.contains("uclink:")// UC
                    || backUrl.contains("ucbrowser:")// UC
                    || backUrl.contains("newsapp:")//
                    || backUrl.contains("sinaweibo:")// 新浪微博
                    || backUrl.contains("suning:")//
                    || backUrl.contains("pinduoduo:")// 拼多多
                    || backUrl.contains("qtt:")//
                    || backUrl.contains("baiduboxapp:")// 百度
                    || backUrl.contains("baiduhaokan:")// 百度看看
            ) {
                isJump = false;
            }
            if (isJump) {
                startActivity(activity, backUrl);
            }
        }
        return true;
    }
}
