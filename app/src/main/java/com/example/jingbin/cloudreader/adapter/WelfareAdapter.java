package com.example.jingbin.cloudreader.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.ItemWelfareBinding;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.ImgLoadUtil;

/**
 * Created by jingbin on 2016/12/1.
 */

public class WelfareAdapter extends BaseRecyclerViewAdapter<GankIoDataBean.ResultsBean> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_welfare);
    }


    private class ViewHolder extends BaseRecyclerViewHolder<GankIoDataBean.ResultsBean, ItemWelfareBinding> {


        ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final GankIoDataBean.ResultsBean resultsBean, final int position) {

//            binding.setBean(resultsBean);
            ImgLoadUtil.displayEspImage(resultsBean.getUrl(), binding.ivWelfare, 1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null) {
                        listener.onClick(resultsBean,position);
                    }
                }
            });

            DebugUtil.error("------position:  "+position);
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
