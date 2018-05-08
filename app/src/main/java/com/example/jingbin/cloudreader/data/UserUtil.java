package com.example.jingbin.cloudreader.data;

import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.data.room.Injection;
import com.example.jingbin.cloudreader.data.room.User;
import com.example.jingbin.cloudreader.data.room.UserDataCallback;
import com.example.jingbin.cloudreader.utils.SPUtils;

/**
 * @author jingbin
 * @data 2018/5/7
 * @Description
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

}
