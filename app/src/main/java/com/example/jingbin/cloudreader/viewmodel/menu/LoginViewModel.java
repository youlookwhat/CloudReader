package com.example.jingbin.cloudreader.viewmodel.menu;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.bean.wanandroid.LoginBean;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.room.Injection;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.ToastUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/5/7
 * @Description wanandroid登录的ViewModel
 */

public class LoginViewModel extends ViewModel {

    public final ObservableField<String> username = new ObservableField<>();

    public final ObservableField<String> password = new ObservableField<>();

    public MutableLiveData<Boolean> register() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        if (!verifyData()) {
            data.setValue(false);
            return data;
        }
        HttpClient.Builder.getWanAndroidServer().register(username.get(), password.get(), password.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(LoginBean bean) {
                        if (bean != null && bean.getData() != null) {
                            // 存入数据库
                            Injection.get().addData(bean.getData());
                            UserUtil.handleLoginSuccess();
                            data.setValue(true);
                        } else {
                            if (bean != null) {
                                ToastUtil.showToastLong(bean.getErrorMsg());
                            }
                            data.setValue(false);
                        }
                    }
                });
        return data;
    }

    public MutableLiveData<Boolean> login() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        if (!verifyData()) {
            data.setValue(false);
            return data;
        }
        HttpClient.Builder.getWanAndroidServer().login(username.get(), password.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(LoginBean bean) {
                        if (bean != null && bean.getData() != null) {
                            Injection.get().addData(bean.getData());
                            UserUtil.handleLoginSuccess();
                            data.setValue(true);
                        } else {
                            if (bean != null) {
                                ToastUtil.showToastLong(bean.getErrorMsg());
                            }
                            data.setValue(false);
                        }
                    }
                });
        return data;
    }

    private boolean verifyData() {
        if (TextUtils.isEmpty(username.get())) {
            ToastUtil.showToast("请输入用户名");
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtil.showToast("请输入密码");
            return false;
        }
        return true;
    }
}
