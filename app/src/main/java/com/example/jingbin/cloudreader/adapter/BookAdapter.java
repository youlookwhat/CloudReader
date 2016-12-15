package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.book.BooksBean;
import com.example.jingbin.cloudreader.databinding.ItemBookBinding;

/**
 * Created by jingbin on 2016/12/15.
 */

public class BookAdapter extends BaseRecyclerViewAdapter<BooksBean> {

    private Activity mActivity;

    public BookAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_book);
    }


    private class ViewHolder extends BaseRecyclerViewHolder<BooksBean, ItemBookBinding> {
        public ViewHolder(ViewGroup parent, int layout) {
            super(parent, layout);
        }

        @Override
        public void onBindViewHolder(BooksBean bean, int position) {
            binding.setBean(bean);
            binding.executePendingBindings();
        }
    }


}
