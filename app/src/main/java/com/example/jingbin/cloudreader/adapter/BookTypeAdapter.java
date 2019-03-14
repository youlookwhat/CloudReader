package com.example.jingbin.cloudreader.adapter;

import android.text.TextUtils;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.refreshadapter.JQuickAdapter;
import com.example.jingbin.cloudreader.base.refreshadapter.JViewHolder;
import com.example.jingbin.cloudreader.databinding.ItemBookTypeBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;

/**
 * Created by jingbin on 2019/03/14.
 */

public class BookTypeAdapter extends JQuickAdapter<String, ItemBookTypeBinding> {

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public BookTypeAdapter() {
        super(R.layout.item_book_type);
    }

    @Override
    protected void onBinding(ItemBookTypeBinding binding) {
    }

    @Override
    protected void convert(JViewHolder<ItemBookTypeBinding> helper, String bean) {
        if (bean != null) {
            helper.binding.setName(bean);
            if (!TextUtils.isEmpty(type)) {
                if (!TextUtils.isEmpty(bean) && bean.equals(type)) {
                    helper.binding.tvTitle.setTextColor(CommonUtils.getColor(R.color.colorTheme));
                } else {
                    helper.binding.tvTitle.setTextColor(CommonUtils.getColor(R.color.select_navi_text));
                }
            }
            helper.binding.tvTitle.setOnClickListener(v -> {
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
