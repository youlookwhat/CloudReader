package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.CoinLogBean;
import com.example.jingbin.cloudreader.databinding.ItemWanCoinBinding;
import com.example.jingbin.cloudreader.databinding.ItemWanCoinRankBinding;
import com.example.jingbin.cloudreader.utils.TimeUtil;

/**
 * Created by jingbin on 2019/9/26.
 */

public class CoinAdapter extends BaseRecyclerViewAdapter<CoinLogBean> {

    private Activity activity;
    private boolean isRank;

    public CoinAdapter(Activity activity, boolean isRank) {
        this.activity = activity;
        this.isRank = isRank;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isRank) {
            return new ViewRankHolder(parent, R.layout.item_wan_coin_rank);
        } else {
            return new ViewHolder(parent, R.layout.item_wan_coin);
        }
    }

    private class ViewRankHolder extends BaseRecyclerViewHolder<CoinLogBean, ItemWanCoinRankBinding> {

        ViewRankHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final CoinLogBean bean, final int position) {
            binding.setBean(bean);
            binding.tvRank.setText(getAdapterPosition() + "");
            binding.tvName.setText(bean.getUsername());
            binding.tvCoin.setText(bean.getCoinCount() + "");
        }
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
