package com.example.jingbin.cloudreader.adapter;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.ItemWelfareBinding;

/**
 * @author jingbin
 * @date 2019/11/7
 */
public class WelfareAdapter extends BaseBindingAdapter<GankIoDataBean.ResultBean, ItemWelfareBinding> {

    public WelfareAdapter() {
        super(R.layout.item_welfare);
    }

    @Override
    protected void bindView(GankIoDataBean.ResultBean bean, ItemWelfareBinding binding, int position) {
        binding.setBean(bean);
    }

}
