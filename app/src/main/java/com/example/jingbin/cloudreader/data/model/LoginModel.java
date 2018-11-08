package com.example.jingbin.cloudreader.data.model;

import com.example.jingbin.cloudreader.bean.wanandroid.LoginBean;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.room.Injection;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.ToastUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToastLong("退出失败");
                    }

                    @Override
                    public void onNext(LoginBean bean) {
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
                });
    }
}
