package com.example.jingbin.cloudreader.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import me.jingbin.bymvvm.base.BaseActivity;
import com.example.jingbin.cloudreader.databinding.ActivityNavDeedBackBinding;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import me.jingbin.bymvvm.base.NoViewModel;

/**
 * @author jingbin
 */
public class NavDeedBackActivity extends BaseActivity<NoViewModel, ActivityNavDeedBackBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_deed_back);
        setTitle("问题反馈");
        showContentView();

        bindingView.tvIssues.setOnClickListener(listener);
        bindingView.tvJianshu.setOnClickListener(listener);
        bindingView.tvQq.setOnClickListener(listener);
        bindingView.tvEmail.setOnClickListener(listener);
        bindingView.tvFaq.setOnClickListener(listener);
        bindingView.tvQqGroup.setOnClickListener(listener);
        bindingView.tvQqGroupNum.setOnClickListener(listener);
    }

    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            switch (v.getId()) {
                case R.id.tv_issues:
                    WebViewActivity.loadUrl(v.getContext(), CommonUtils.getString(R.string.string_url_issues), "Issues");
                    break;
                case R.id.tv_qq:
                    BaseTools.joinQQChat(NavDeedBackActivity.this, "770413277");
                    break;
                case R.id.tv_qq_group:
                    BaseTools.joinQQGroup(NavDeedBackActivity.this, "jSdY9xxzZ7xXG55_V8OUb8ds_YT6JjAn");
                    break;
                case R.id.tv_email:
                    try {
                        Intent data = new Intent(Intent.ACTION_SENDTO);
                        data.setData(Uri.parse("mailto:jingbin127@163.com"));
                        startActivity(data);
                    } catch (Exception e) {
                        ToastUtil.showToastLong("请先安装邮箱~");
                    }
                    break;
                case R.id.tv_jianshu:
                    WebViewActivity.loadUrl(v.getContext(), CommonUtils.getString(R.string.string_url_jianshu), "简书");
                    break;
                case R.id.tv_faq:
                    WebViewActivity.loadUrl(v.getContext(), CommonUtils.getString(R.string.string_url_faq), "常见问题归纳");
                    break;
                case R.id.tv_qq_group_num:
                    BaseTools.copy(bindingView.tvQqGroupNum.getText().toString());
                    break;
                default:
                    break;
            }
        }
    };

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDeedBackActivity.class);
        mContext.startActivity(intent);
    }
}
