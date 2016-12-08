package com.example.jingbin.yunyue.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.yunyue.R;
import com.example.jingbin.yunyue.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.yunyue.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.yunyue.bean.GankIoDataBean;
import com.example.jingbin.yunyue.databinding.ItemAndroidBinding;

/**
 * Created by jingbin on 2016/12/2.
 */

public class AndroidAdapter extends BaseRecyclerViewAdapter<GankIoDataBean.ResultsBean> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_android);
    }


    private class ViewHolder extends BaseRecyclerViewHolder<GankIoDataBean.ResultsBean, ItemAndroidBinding> {

        ViewHolder(ViewGroup parent, int item_android) {
            super(parent, item_android);
        }

        @Override
        public void onBindViewHolder(GankIoDataBean.ResultsBean object, int position) {

            if (object.getImages() != null
                    && object.getImages().size() > 0
                    && !TextUtils.isEmpty(object.getImages().get(0))) {
                binding.ivAndroidPic.setVisibility(View.GONE);
//                binding.ivAndroidPic.setVisibility(View.VISIBLE);
//                ImgLoadUtil.display(object.getImages().get(0), binding.ivAndroidPic);
            } else {
                binding.ivAndroidPic.setVisibility(View.GONE);
            }

            binding.tvAndroidDes.setText(object.getDesc());
            if (!TextUtils.isEmpty(object.getWho())) {
                binding.tvAndroidWho.setText(object.getWho());
            } else {
                binding.tvAndroidWho.setText("佚名");
            }
            binding.llAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}
