package com.example.jingbin.cloudreader.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.ItemAndroidBinding;
import com.example.jingbin.cloudreader.utils.ImgLoadUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

/**
 * Created by jingbin on 2016/12/2.
 */

public class AndroidAdapter extends BaseRecyclerViewAdapter<GankIoDataBean.ResultBean> {

    private boolean isAll = false;

    public void setAllType(boolean isAll) {
        this.isAll = isAll;
    }

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

            if (isAll && "福利".equals(object.getType())) {
                binding.ivAllWelfare.setVisibility(View.VISIBLE);
                binding.llWelfareOther.setVisibility(View.GONE);
                ImgLoadUtil.displayEspImage(object.getUrl(), binding.ivAllWelfare, 1);
            } else {
                binding.ivAllWelfare.setVisibility(View.GONE);
                binding.llWelfareOther.setVisibility(View.VISIBLE);
            }

            if (isAll) {
                binding.tvContentType.setVisibility(View.VISIBLE);
                binding.tvContentType.setText(" · " + object.getType());
            } else {
                binding.tvContentType.setVisibility(View.GONE);

            }
            binding.setResultsBean(object);
            binding.executePendingBindings();

            // 显示gif图片会很耗内存
            if (object.getImages() != null
                    && object.getImages().size() > 0
                    && !TextUtils.isEmpty(object.getImages().get(0))) {
//                binding.ivAndroidPic.setVisibility(View.GONE);
                binding.ivAndroidPic.setVisibility(View.VISIBLE);
                ImgLoadUtil.displayGif(object.getImages().get(0), binding.ivAndroidPic);
            } else {
                binding.ivAndroidPic.setVisibility(View.GONE);
            }

            binding.llAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.loadUrl(v.getContext(), object.getUrl(), "加载中...");
                }
            });
        }

    }
}
