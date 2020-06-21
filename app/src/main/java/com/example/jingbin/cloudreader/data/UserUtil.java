package com.example.jingbin.cloudreader.data;

import android.content.Context;

import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.ui.wan.child.LoginActivity;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.ToastUtil;

import me.jingbin.bymvvm.room.Injection;
import me.jingbin.bymvvm.room.User;
import me.jingbin.bymvvm.room.UserDataCallback;

/**
 * @author jingbin
 * @data 2018/5/7
 * @Description 处理用户登录问题
 */

public class UserUtil {

    /**
     * 得到用户信息
     */
    public static void getUserInfo(OnUserInfoListener listener) {
        Injection.get().getSingleBean(new UserDataCallback() {
            @Override
            public void onDataNotAvailable() {
                if (listener != null) {
                    listener.onSuccess(null);
                }
            }

            @Override
            public void getData(User bean) {
                if (listener != null) {
                    listener.onSuccess(bean);
                }
            }
        });
    }

    /**
     * 更新用户信息
     */
    public static void setUserInfo(User bean) {
        Injection.get().addData(bean);
    }

    public static void handleLoginSuccess() {
        SPUtils.putBoolean(Constants.IS_LOGIN, true);
    }

    public static void handleLoginFailure() {
        SPUtils.putBoolean(Constants.IS_LOGIN, false);
        SPUtils.putString("cookie", "");
        SPUtils.remove("cookie");
    }

    /**
     * 是否登录，没有进入登录页面
     */
    public static boolean isLogin(Context context) {
        boolean isLogin = SPUtils.getBoolean(Constants.IS_LOGIN, false);
        if (!isLogin) {
            ToastUtil.showToastLong("请先登录~");
            LoginActivity.start(context);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否登录
     */
    public static boolean isLogin() {
        return SPUtils.getBoolean(Constants.IS_LOGIN, false);
    }

    public static String getLevel(int coinCount) {
        return String.valueOf(coinCount / 100 + 1);
    }
}
