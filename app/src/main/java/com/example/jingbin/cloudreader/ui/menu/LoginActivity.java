package com.example.jingbin.cloudreader.ui.menu;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.databinding.ActivityLoginBinding;
import com.example.jingbin.cloudreader.viewmodel.menu.LoginViewModel;

/**
 * 玩安卓登录
 *
 * @author jingbin
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("玩安卓登录");
        showContentView();
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        bindingView.setViewModel(loginViewModel);
    }

    public void register(View view) {
        loginViewModel.register().observe(this, this::loadSuccess);
    }

    public void login(View view) {
        loginViewModel.login().observe(this, this::loadSuccess);
    }

    /**
     * 注册或登录成功
     */
    public void loadSuccess(Boolean aBoolean) {
        if (aBoolean != null && aBoolean) {
            finish();
        }
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }
}
