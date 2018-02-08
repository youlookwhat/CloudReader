package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.databinding.ItemWanAndroidBinding;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

/**
 * Created by jingbin on 2016/11/25.
 */

public class WanAndroidAdapter extends BaseRecyclerViewAdapter<HomeListBean.DataBean.DatasBean> {

    private Activity activity;

    public WanAndroidAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_wan_android);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<HomeListBean.DataBean.DatasBean, ItemWanAndroidBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final HomeListBean.DataBean.DatasBean bean, final int position) {
            if (bean != null) {
                binding.setBean(bean);
                binding.setAdapter(WanAndroidAdapter.this);
                binding.executePendingBindings();

//                binding.llItemTop.setOnClickListener(new PerfectClickListener() {
//                    @Override
//                    protected void onNoDoubleClick(View v) {
//                        WebViewActivity.loadUrl(v.getContext(),bean.getLink(),bean.getTitle());
//                    }
//                });
            }
        }
    }

    public void openDetail(HomeListBean.DataBean.DatasBean bean) {
        WebViewActivity.loadUrl(activity, bean.getLink(), bean.getTitle());
    }
}
