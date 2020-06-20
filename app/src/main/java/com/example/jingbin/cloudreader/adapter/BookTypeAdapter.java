package com.example.jingbin.cloudreader.adapter;

import android.text.TextUtils;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.databinding.ItemBookTypeBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2019/03/14.
 */

public class BookTypeAdapter extends BaseBindingAdapter<String, ItemBookTypeBinding> {

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public BookTypeAdapter() {
        super(R.layout.item_book_type);
    }

    @Override
    protected void bindView(BaseBindingHolder holder, String bean, ItemBookTypeBinding binding, int position) {
        if (bean != null) {
            binding.setName(bean);
            if (!TextUtils.isEmpty(type)) {
                if (!TextUtils.isEmpty(bean) && bean.equals(type)) {
                    binding.tvTitle.setTextColor(CommonUtils.getColor(R.color.colorTheme));
                } else {
                    binding.tvTitle.setTextColor(CommonUtils.getColor(R.color.select_navi_text));
                }
            }
            binding.tvTitle.setOnClickListener(v -> {
                if (listener != null) {
                    if (!TextUtils.isEmpty(bean)) {
                        listener.onSelected(bean);
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
        void onSelected(String position);
    }

}
