package com.example.jingbin.cloudreader.adapter;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import com.example.jingbin.cloudreader.databinding.ItemJokeBinding;
import com.example.jingbin.cloudreader.utils.TimeUtil;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2016/11/25.
 */

public class JokeAdapter extends BaseBindingAdapter<DuanZiBean, ItemJokeBinding> {

    public JokeAdapter() {
        super(R.layout.item_joke);
    }

    @Override
    protected void bindView(BaseBindingHolder holder, DuanZiBean bean, ItemJokeBinding binding, int position) {
        binding.setBean(bean);
        String time = TimeUtil.formatDataTime(Long.valueOf(bean.getCreateTime() + "000"));
        binding.setTime(time);
    }
}
