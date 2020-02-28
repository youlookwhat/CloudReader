package com.example.jingbin.cloudreader.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.base.binding.BaseBindingHolder;
import com.example.jingbin.cloudreader.bean.book.BooksBean;
import com.example.jingbin.cloudreader.databinding.ItemBookBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;

/**
 * Created by jingbin on 2016/11/25.
 */

public class DouBookAdapter extends BaseBindingAdapter<BooksBean, ItemBookBinding> {

    private int width;

    public DouBookAdapter(Context context) {
        super(R.layout.item_book);
        int px = DensityUtil.dip2px(context, 48);
        width = (DensityUtil.getDisplayWidth() - px) / 3;
    }

    @Override
    protected void bindView(BaseBindingHolder holder, BooksBean book, ItemBookBinding binding, int position) {
        if (book != null) {
            binding.setBean(book);
            DensityUtil.setWidthHeight(binding.ivTopPhoto, width, 0.703f);
            binding.cvTopBook.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (listener != null) {
                        listener.onClick(book, binding.ivTopPhoto);
                    }
                }
            });
        }
    }

    private OnClickInterface listener;

    public interface OnClickInterface {
        void onClick(BooksBean bean, ImageView view);
    }

    public void setOnClickListener(OnClickInterface listener) {
        this.listener = listener;
    }
}
