package com.example.jingbin.cloudreader.adapter;

import android.text.TextUtils;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.ItemAndroidBinding;
import com.example.jingbin.cloudreader.utils.GlideUtil;
import com.example.jingbin.cloudreader.ui.WebViewActivity;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2016/12/2.
 */

public class GankAndroidAdapter extends BaseBindingAdapter<GankIoDataBean.ResultBean, ItemAndroidBinding> {

    private boolean isAll = false;

    public GankAndroidAdapter() {
        super(R.layout.item_android);
    }

    public void setAllType(boolean isAll) {
        this.isAll = isAll;
    }

    @Override
    protected void bindView(BaseBindingHolder holder, GankIoDataBean.ResultBean object, ItemAndroidBinding binding, int position) {
        if (isAll && "福利".equals(object.getType())) {
            binding.ivAllWelfare.setVisibility(View.VISIBLE);
            binding.llWelfareOther.setVisibility(View.GONE);
            GlideUtil.displayEspImage(object.getUrl(), binding.ivAllWelfare, 1);
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

        // 显示gif图片会很耗内存
        if (object.getImages() != null
                && object.getImages().size() > 0
                && !TextUtils.isEmpty(object.getImages().get(0))) {
            binding.ivAndroidPic.setVisibility(View.VISIBLE);
            GlideUtil.displayGif(object.getImages().get(0), binding.ivAndroidPic);
            //                Glide.with(context).load(object.getImages().get(0))
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .placeholder(R.drawable.img_one_bi_one)
//                        .error(R.drawable.img_one_bi_one)
//                        .into(binding.ivAndroidPic);
        } else {
            binding.ivAndroidPic.setVisibility(View.GONE);
        }
        binding.llAll.setOnClickListener(v -> WebViewActivity.loadUrl(v.getContext(), object.getUrl(), object.getDesc()));

        binding.setResultsBean(object);
        binding.setCommand(GankAndroidAdapter.this);
        binding.executePendingBindings();
    }

}
