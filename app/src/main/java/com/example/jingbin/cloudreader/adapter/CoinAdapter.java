package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.CoinLogBean;
import com.example.jingbin.cloudreader.databinding.ItemWanCoinBinding;
import com.example.jingbin.cloudreader.databinding.ItemWanCoinRankBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
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
            // 其实是 —2+1 两个header  +1是为了得到序号
            int adapterPosition = getAdapterPosition() - 1;
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

    private class ViewHolder extends BaseRecyclerViewHolder<CoinLogBean, ItemWanCoinBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final CoinLogBean bean, final int position) {
            binding.setBean(bean);
        }
    }

}
