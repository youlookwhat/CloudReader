package com.example.jingbin.cloudreader.adapter;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.wanandroid.WxarticleItemBean;
import com.example.jingbin.cloudreader.databinding.ItemWxarticleBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2019/9/29.
 */

public class WxArticleAdapter extends BaseBindingAdapter<WxarticleItemBean, ItemWxarticleBinding> {

    private int id;
    private int selectPosition = 0;
    private int lastPosition = 0;

    public WxArticleAdapter() {
        super(R.layout.item_wxarticle);
    }

    @Override
    protected void bindView(BaseBindingHolder holder, WxarticleItemBean dataBean, ItemWxarticleBinding binding, int position) {
        if (dataBean != null) {

            if (dataBean.getId() == id) {
                binding.tvTitle.setTextColor(CommonUtils.getColor(R.color.colorTheme));
                binding.viewLine.setBackgroundColor(CommonUtils.getColor(R.color.colorTheme));
            } else {
                binding.tvTitle.setTextColor(CommonUtils.getColor(R.color.select_navi_text));
                binding.viewLine.setBackgroundColor(CommonUtils.getColor(R.color.colorSubtitle));
            }
            binding.setBean(dataBean);
            binding.clWxarticle.setOnClickListener(v -> {
                if (selectPosition != position) {
                    lastPosition = selectPosition;

                    id = dataBean.getId();
                    selectPosition = position;

                    notifyItemChanged(lastPosition);
                    notifyItemChanged(selectPosition);

                    if (listener != null) {
                        listener.onSelected(position);
                    }
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

    public void setId(int id) {
        this.id = id;
    }
}
