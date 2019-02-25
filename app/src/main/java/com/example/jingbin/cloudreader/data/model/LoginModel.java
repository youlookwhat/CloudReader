package com.example.jingbin.cloudreader.data.model;

import com.example.jingbin.cloudreader.bean.wanandroid.LoginBean;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by jingbin on 2018/11/8.
 * 登出的model
 */

public class LoginModel {

    public interface OnLogoutListener {
        void logout();
    }

    public void logout(OnLogoutListener listener) {
        HttpClient.Builder.getWanAndroidServer().logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean bean) throws Exception {
                        if (bean != null && bean.getErrorCode() == 0) {
                            if (listener != null) {
                                listener.logout();
                            }
                        } else {
                            if (bean != null) {
                                ToastUtil.showToastLong(bean.getErrorMsg());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.showToastLong("退出失败");
                    }
                });
    }
}
