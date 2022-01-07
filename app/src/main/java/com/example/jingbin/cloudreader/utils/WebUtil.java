package com.example.jingbin.cloudreader.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.net.URI;
import java.util.regex.Pattern;

import me.jingbin.web.ByWebTools;

/**
 * @author jingbin
 */
public class WebUtil {

    public static void startActivity(Activity activity, String fromUrl, String url) {
        try {
            DebugUtil.error("----" + url);
            String host = parseDomain(fromUrl);
            String appName = getAppName(url);
            DialogBuild.show(activity, "\" " + host + " \" 想要唤起 \" " + appName + " \" ", "唤起", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri uri = Uri.parse(url);
                        intent.setData(uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAppName(String url) {
        String appName = url;
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("http") && url.contains(".apk")) {
                appName = "浏览器";
            } else if (url.contains("alipays")) {
                appName = "支付宝";
            } else if (url.contains("weixin://")) {
                appName = "微信";
            } else if (url.contains("jianshu://")) {
                appName = "简书";
            } else if (url.contains("wvhzpj://")) {
                appName = "CSDN";
            } else if (url.contains("snssdk2606://")) {
                appName = "掘金";
            } else if (url.contains("://")) {
                appName = url.substring(0, url.indexOf("://"));
            }
        }
        return appName;
    }

    private static String parseDomain(String url) {
        String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
        Pattern pattern = Pattern.compile(regex);
        if (!TextUtils.isEmpty(url) && pattern.matcher(url).matches()) {
            return URI.create(url).getHost();
        } else {
            return url;
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
    public static boolean handleThirdApp(Activity activity, String fromUrl, String backUrl) {
        DebugUtil.error("---backUrl:" + backUrl);
        if (!TextUtils.isEmpty(fromUrl) && fromUrl.contains("jianshu")) {
            // 简书url只能跳简书app，很恶心一直唤起广告
            if (backUrl.startsWith("http")) {
                if (backUrl.contains(".apk")) {
                    startActivity(activity, fromUrl, backUrl);
                    return true;
                } else if (backUrl.contains("vip.com")) {
                    // 不允许重定向，自己处理
                    return true;
                }
                // 允许重定向，WebView处理
                return false;
            }
            if (backUrl.contains("jianshu://")) {
                if (ByWebTools.hasPackage(activity, "com.jianshu.haruki")) {
                    startActivity(activity, fromUrl, backUrl);
                    return true;
                }
            } else if (backUrl.contains("hap://app")) {
                // 快应用 自动打开
                ToastUtil.showToastLong(parseDomain(fromUrl) + " 在强制唤起快应用");
                return true;
            }
            return true;
        }

        /**http开头直接跳过*/
        if (backUrl.startsWith("http")) {
            // 可能有提示下载Apk文件
            if (backUrl.contains(".apk")) {
                startActivity(activity, fromUrl, backUrl);
                return true;
            }
            return false;
        }
        if (backUrl.contains("alipays")) {
            // 网页跳支付宝支付
            if (ByWebTools.hasPackage(activity, "com.eg.android.AlipayGphone")) {
                startActivity(activity, fromUrl, backUrl);
            }

        } else if (backUrl.contains("weixin://wap/pay")) {
            // 微信支付
            if (ByWebTools.hasPackage(activity, "com.tencent.mm")) {
                startActivity(activity, fromUrl, backUrl);
            }
        } else if (backUrl.contains("jianshu://")) {
            // 简书
            if (ByWebTools.hasPackage(activity, "com.jianshu.haruki")) {
                startActivity(activity, fromUrl, backUrl);
            }
        } else if (backUrl.contains("wvhzpj://")) {
            // csdn
            if (ByWebTools.hasPackage(activity, "net.csdn.csdnplus")) {
                startActivity(activity, fromUrl, backUrl);
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
                startActivity(activity, fromUrl, backUrl);
            }
        }
        return true;
    }
}
