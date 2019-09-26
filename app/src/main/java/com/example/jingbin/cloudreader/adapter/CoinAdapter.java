package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.CoinLogBean;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.model.CollectModel;
import com.example.jingbin.cloudreader.databinding.ItemWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.ItemWanCoinBinding;
import com.example.jingbin.cloudreader.ui.wan.child.ArticleListActivity;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.TimeUtil;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;

/**
 * Created by jingbin on 2019/9/26.
 */

public class CoinAdapter extends BaseRecyclerViewAdapter<CoinLogBean> {

    private Activity activity;

    public CoinAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_wan_coin);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<CoinLogBean, ItemWanCoinBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final CoinLogBean bean, final int position) {
            binding.setBean(bean);
            String time = TimeUtil.getTime(bean.getDate());
            binding.tvTitle.setText(bean.getDesc().replace(time, "").trim());
            binding.tvLogCoin.setText("+" + bean.getCoinCount());
            binding.tvTime.setText(time);
        }
    }

}
