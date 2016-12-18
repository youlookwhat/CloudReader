package com.example.jingbin.cloudreader.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.ItemAndroidBinding;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

/**
 * Created by jingbin on 2016/12/2.
 */

public class AndroidAdapter extends BaseRecyclerViewAdapter<GankIoDataBean.ResultBean> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_android);
    }


    private class ViewHolder extends BaseRecyclerViewHolder<GankIoDataBean.ResultBean, ItemAndroidBinding> {

        ViewHolder(ViewGroup parent, int item_android) {
            super(parent, item_android);
        }

        @Override
        public void onBindViewHolder(final GankIoDataBean.ResultBean object, int position) {

            binding.setResultsBean(object);
            binding.executePendingBindings();

            // 显示图片会很耗内存
            if (object.getImages() != null
                    && object.getImages().size() > 0
                    && !TextUtils.isEmpty(object.getImages().get(0))) {
                binding.ivAndroidPic.setVisibility(View.GONE);
//                binding.ivAndroidPic.setVisibility(View.VISIBLE);
//                ImgLoadUtil.display(object.getImages().get(0), binding.ivAndroidPic);
            } else {
                binding.ivAndroidPic.setVisibility(View.GONE);
            }

            binding.llAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.loadUrl(v.getContext(), object.getUrl(), "详情");
                }
            });
        }

    }
}
