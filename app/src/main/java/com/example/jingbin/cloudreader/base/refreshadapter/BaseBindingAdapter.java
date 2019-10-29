package com.example.jingbin.cloudreader.base.refreshadapter;


import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

import me.jingbin.library.adapter.BaseByRecyclerViewAdapter;


/**
 * 【databinding】单一 item 类型 adapter
 * https://github.com/youlookwhat/ByRecyclerView
 */
public abstract class BaseBindingAdapter<T, B extends ViewDataBinding> extends BaseByRecyclerViewAdapter<T, BaseBindingHolder<T, B>> {

    private int mLayoutId;

    public BaseBindingAdapter(@LayoutRes int layoutId) {
        mLayoutId = layoutId;
    }

    public BaseBindingAdapter(@LayoutRes int layoutId, List<T> data) {
        super(data);
        mLayoutId = layoutId;
    }

    @NonNull
    @Override
    public BaseBindingHolder<T, B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, mLayoutId);
    }

    private class ViewHolder extends BaseBindingHolder<T, B> {
        ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        protected void onBindingView(B binding, T bean, int position) {
            bindView(bean, binding, position);
        }
    }

    protected abstract void bindView(T bean, B binding, int position);
}

