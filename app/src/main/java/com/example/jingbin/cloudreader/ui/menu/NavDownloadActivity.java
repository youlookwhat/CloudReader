package com.example.jingbin.cloudreader.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.app.Constants;
import me.jingbin.bymvvm.base.BaseActivity;
import com.example.jingbin.cloudreader.databinding.ActivityNavDownloadBinding;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.QRCodeUtil;
import com.example.jingbin.cloudreader.utils.ShareUtils;
import me.jingbin.bymvvm.base.NoViewModel;

public class NavDownloadActivity extends BaseActivity<NoViewModel, ActivityNavDownloadBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_download);
        showContentView();

        setTitle("扫码下载");
        QRCodeUtil.showThreadImage(this, Constants.DOWNLOAD_URL, bindingView.ivErweima, R.drawable.ic_cloudreader_mip);
        bindingView.tvShare.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDownloadActivity.class);
        mContext.startActivity(intent);
    }
}
