package com.example.jingbin.cloudreader.data;

import android.content.Context;

import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.data.room.Injection;
import com.example.jingbin.cloudreader.data.room.User;
import com.example.jingbin.cloudreader.data.room.UserDataCallback;
import com.example.jingbin.cloudreader.ui.menu.LoginActivity;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.ToastUtil;

/**
 * @author jingbin
 * @data 2018/5/7
 * @Description 处理用户登录问题
 */

public class UserUtil {

    /**
     * 初始化登录状态
     */
    public static void getLoginStatus() {
        Injection.get().getSingleBean(new UserDataCallback() {
            @Override
            public void onDataNotAvailable() {
                SPUtils.putBoolean(Constants.IS_LOGIN, false);
            }

            @Override
            public void getData(User bean) {
                SPUtils.putBoolean(Constants.IS_LOGIN, true);
            }
        });
    }

    public static void handleLoginSuccess() {
        SPUtils.putBoolean(Constants.IS_LOGIN, true);
    }

    public static void handleLoginFailure() {
        SPUtils.putBoolean(Constants.IS_LOGIN, false);
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
}
