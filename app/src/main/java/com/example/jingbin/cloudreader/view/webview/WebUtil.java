package com.example.jingbin.cloudreader.view.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.example.jingbin.cloudreader.utils.DebugUtil;

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
    public static boolean handleThirdApp(Activity activity, String originalUrl, String backUrl) {
        DebugUtil.error("----backUrl:  " + backUrl);

        /**http开头直接跳过*/
        if (backUrl.startsWith("http")) {
            // 可能有提示下载Apk文件
            if (backUrl.contains(".apk")) {
                startActivity(activity, backUrl);
                return true;
            }
            return false;
        }

        boolean isJump = true;
        if (!TextUtils.isEmpty(originalUrl)) {
            if (backUrl.startsWith("openapp.jdmobile:")// 京东
                    || backUrl.startsWith("zhihu:")// 知乎
                    || backUrl.startsWith("tbopen:")// 淘宝
                    || backUrl.startsWith("vipshop:")//
                    || backUrl.startsWith("youku:")//优酷
                    || backUrl.startsWith("uclink:")// UC
                    || backUrl.startsWith("ucbrowser:")// UC
                    || backUrl.startsWith("alipay:")// 支付宝
                    || backUrl.startsWith("newsapp:")//
                    || backUrl.startsWith("sinaweibo:")// 新浪
                    || backUrl.startsWith("suning:")//
                    || backUrl.startsWith("pinduoduo:")// 拼多多
                    || backUrl.startsWith("jdmobile:")//京东
                    || backUrl.startsWith("baiduboxapp:")// 百度
                    || backUrl.startsWith("alipays:")//支付宝
                    || backUrl.startsWith("qtt:")//
            ) {
                isJump = false;
            }
        }
        if (isJump) {
            startActivity(activity, backUrl);
        }
        return isJump;
    }
}
