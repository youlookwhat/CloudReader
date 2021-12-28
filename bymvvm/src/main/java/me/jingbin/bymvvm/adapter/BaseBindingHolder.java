package me.jingbin.bymvvm.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.List;

import me.jingbin.library.adapter.BaseByViewHolder;


/**
 * https://github.com/youlookwhat/ByRecyclerView
 */
public abstract class BaseBindingHolder<T, B extends ViewDataBinding> extends BaseByViewHolder<T> {

    public final B binding;

    public BaseBindingHolder(ViewGroup viewGroup, int layoutId) {
        super(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), layoutId, viewGroup, false).getRoot());
        binding = DataBindingUtil.getBinding(this.itemView);
    }

    @Override
    protected void onBaseBindView(BaseByViewHolder<T> holder, T bean, int position) {
        onBindingView(this, bean, position);
        binding.executePendingBindings();
    }

    @Override
    protected void onBaseBindViewPayloads(BaseByViewHolder<T> holder, T bean, int position, @NonNull List<Object> payloads) {
        onBindingViewPayloads(this, bean, position, payloads);
        binding.executePendingBindings();
    }

    protected abstract void onBindingView(BaseBindingHolder holder, T bean, int position);

    protected void onBindingViewPayloads(BaseBindingHolder holder, T bean, int position, @NonNull List<Object> payloads) {
        /*
         * fallback to onBindingViewPayloads(holder, bean,position) if app does not override this method.
         * 如果不覆盖 onBindingViewPayloads() 方法，就走 onBindingView()
         */
        onBindingView(holder, bean, position);
    }
}
