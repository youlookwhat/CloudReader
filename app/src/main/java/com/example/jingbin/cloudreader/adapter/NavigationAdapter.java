package com.example.jingbin.cloudreader.adapter;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.databinding.ItemNavigationBinding;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2018/10/13.
 */

public class NavigationAdapter extends BaseBindingAdapter<NaviJsonBean.DataBean, ItemNavigationBinding> {

    public NavigationAdapter() {
        super(R.layout.item_navigation);
    }

    @Override
    protected void bindView(BaseBindingHolder holder, NaviJsonBean.DataBean dataBean, ItemNavigationBinding binding, int position) {
        if (dataBean != null) {
            binding.tvTitle.setSelected(dataBean.isSelected());
            binding.setBean(dataBean);
            binding.tvTitle.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSelected(position);
                }
            });
        }
    }

    private OnSelectListener listener;

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public interface OnSelectListener {
        void onSelected(int position);
    }

}
