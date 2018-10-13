package com.example.jingbin.cloudreader.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.databinding.ItemNaviBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;

import java.util.List;

/**
 * Created by jingbin on 2018/10/13.
 */

public class NaviAdapter extends BaseRecyclerViewAdapter<NaviJsonBean.DataBean> {


    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_navi);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<NaviJsonBean.DataBean, ItemNaviBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final NaviJsonBean.DataBean dataBean, final int position) {
            if (dataBean != null) {
                if (dataBean.isSelected()) {
                    binding.llItem.setBackgroundColor(CommonUtils.getColor(R.color.tabBackground));
                } else {
                    binding.llItem.setBackgroundColor(CommonUtils.getColor(R.color.colorLine));
                }
                binding.setBean(dataBean);
                binding.llItem.setOnClickListener(v -> {
                    setSelected(position);
                    if (listener != null) {
                        listener.onSelected(position);
                    }
                });
            }
        }
    }

    public void setSelected(int position) {
        List<NaviJsonBean.DataBean> data = getData();
        for (int i = 0; i < data.size(); i++) {
            if (i == position) {
                data.get(i).setSelected(true);
            } else {
                data.get(i).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    private OnSelectListener listener;

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public interface OnSelectListener {
        void onSelected(int position);
    }

}
