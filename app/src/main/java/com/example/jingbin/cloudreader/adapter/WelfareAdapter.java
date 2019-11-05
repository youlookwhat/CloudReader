package com.example.jingbin.cloudreader.adapter;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.ItemWelfareBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;

/**
 * @author jingbin
 * @date 2016/12/1
 */
public class WelfareAdapter extends BaseBindingAdapter<GankIoDataBean.ResultBean, ItemWelfareBinding> {

    public WelfareAdapter() {
        super(R.layout.item_welfare);
    }

    @Override
    protected void bindView(GankIoDataBean.ResultBean bean, ItemWelfareBinding binding, int position) {
        /**
         * 注意：DensityUtil.setViewMargin(itemView,true,5,3,5,0);
         * 如果这样使用，则每个item的左右边距是不一样的，
         * 这样item不能复用，所以下拉刷新成功后显示会闪一下
         * 换成每个item设置上下左右边距是一样的话，系统就会复用，就消除了图片不能复用 闪跳的情况
         */
        if (position % 2 == 0) {
            DensityUtil.setViewMargin(binding.ivWelfare, false, 12, 6, 12, 0);
        } else {
            DensityUtil.setViewMargin(binding.ivWelfare, false, 6, 12, 12, 0);
        }
        binding.setBean(bean);
    }

}
