package com.example.jingbin.cloudreader.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.ItemWelfareBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;

/**
 * Created by jingbin on 2016/12/1.
 */

public class WelfareAdapter extends BaseRecyclerViewAdapter<GankIoDataBean.ResultBean> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_welfare);
    }


    private class ViewHolder extends BaseRecyclerViewHolder<GankIoDataBean.ResultBean, ItemWelfareBinding> {


        ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final GankIoDataBean.ResultBean resultsBean, final int position) {
            /**
             * 注意：DensityUtil.setViewMargin(itemView,true,5,3,5,0);
             * 如果这样使用，则每个item的左右边距是不一样的，
             * 这样item不能复用，所以下拉刷新成功后显示会闪一下
             * 换成每个item设置上下左右边距是一样的话，系统就会复用，就消除了图片不能复用 闪跳的情况
             */
            if (position % 2 == 0) {
                DensityUtil.setViewMargin(itemView, false, 12, 6, 12, 0);
            } else {
                DensityUtil.setViewMargin(itemView, false, 6, 12, 12, 0);
            }
            binding.setBean(resultsBean);
            // 仿抖动
            binding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(resultsBean, position);
                    }
                }
            });

//            binding.ivWelfare.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ArrayList<String> imageuri = new ArrayList<String>();
//                    imageuri.add(resultsBean.getUrl());
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("selet", 1);// 2,大图显示当前页数，1,头像，不显示页数
//                    bundle.putInt("code", 0);//第几张
//                    bundle.putStringArrayList("imageuri", imageuri);
//                    Intent intent = new Intent(v.getContext(), ViewBigImageActivity.class);
//                    intent.putExtras(bundle);
//                    v.getContext().startActivity(intent);
//                }
//            });
        }
    }
}
