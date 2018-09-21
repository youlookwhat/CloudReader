package com.example.jingbin.cloudreader.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.book.BooksBean;
import com.example.jingbin.cloudreader.databinding.ItemBookBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;

/**
 * Created by jingbin on 2016/11/25.
 */

public class WanBookAdapter extends BaseRecyclerViewAdapter<BooksBean> {

    private int width;

    public WanBookAdapter() {
        int px = DensityUtil.dip2px(48);
        width = (DensityUtil.getDisplayWidth() - px) / 3;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_book);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<BooksBean, ItemBookBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final BooksBean book, final int position) {
            if (book != null) {
                binding.setBean(book);
                DensityUtil.formatHeight(binding.ivTopPhoto, width, 0.703f, 1);
                binding.llItemTop.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        if (listener != null) {
                            listener.onClick(book, binding.ivTopPhoto);
                        }
                    }
                });
            }
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
