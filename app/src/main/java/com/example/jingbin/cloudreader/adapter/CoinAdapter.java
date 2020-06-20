package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.CoinLogBean;
import com.example.jingbin.cloudreader.databinding.ItemWanCoinBinding;
import com.example.jingbin.cloudreader.databinding.ItemWanCoinRankBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;

import me.jingbin.bymvvm.adapter.BaseBindingHolder;
import me.jingbin.library.adapter.BaseByRecyclerViewAdapter;
import me.jingbin.library.adapter.BaseByViewHolder;

/**
 * Created by jingbin on 2019/9/26.
 */

public class CoinAdapter extends BaseByRecyclerViewAdapter<CoinLogBean, BaseByViewHolder> {

    private Activity activity;
    private boolean isRank;

    public CoinAdapter(Activity activity, boolean isRank) {
        this.activity = activity;
        this.isRank = isRank;
    }

    @NonNull
    @Override
    public BaseByViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isRank) {
            return new ViewRankHolder(parent, R.layout.item_wan_coin_rank);
        } else {
            return new ViewHolder(parent, R.layout.item_wan_coin);
        }
    }

    private class ViewRankHolder extends BaseBindingHolder<CoinLogBean, ItemWanCoinRankBinding> {

        ViewRankHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void onBindingView(BaseBindingHolder holder, CoinLogBean bean, int position) {
            int adapterPosition = position + 1;
            binding.setBean(bean);
            binding.setPosition(adapterPosition);

            int color = CommonUtils.getColor(R.color.colorSubtitle);
            int size = 18;
            binding.tvRank.setTypeface(Typeface.DEFAULT_BOLD);
            if (adapterPosition == 1) {
                color = Color.parseColor("#FF3C02");
            } else if (adapterPosition == 2) {
                color = Color.parseColor("#FF8002");
            } else if (adapterPosition == 3) {
                color = Color.parseColor("#FFBB02");
            } else {
                size = 14;
                binding.tvRank.setTypeface(Typeface.DEFAULT);
            }
            binding.tvRank.setTextSize(size);
            binding.tvRank.setTextColor(color);
        }
    }

    private class ViewHolder extends BaseBindingHolder<CoinLogBean, ItemWanCoinBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        protected void onBindingView(BaseBindingHolder holder, CoinLogBean bean, int position) {
            binding.setBean(bean);
        }
    }

}
