package com.example.http;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * Created by jingbin on 2017/2/14.
 */

class HttpHead {
    private static final String CLIENT_TYPE = "4";

    private static Context context;

    public static void init(Context context) {
        HttpHead.context = context;
    }

    public static String getHeader(String httpMethod) {
        long sen = System.currentTimeMillis() / 1000;
        String h = getUuid() + getdevice() + CLIENT_TYPE + httpMethod + "0" + getVersion() + sen;
        return h;
    }

    /**
     * * 获取版本号
     * * @return 当前应用的版本号
     */
    private static String getVersion() {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        return info.versionName;
    }

    private static String getUuid() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, androidId;
        tmDevice = tm.getDeviceId().toString();
        tmSerial = "ANDROID_ID";
        androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID).toString();
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;

    }

    /*
     * 获取机型
     */
    private static String getdevice() {
        String s = android.os.Build.MODEL;
        String t = s.replaceAll(" ", "");
        return t;
    }
}
