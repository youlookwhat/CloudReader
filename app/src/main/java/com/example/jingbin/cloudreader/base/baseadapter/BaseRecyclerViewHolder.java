package com.example.jingbin.cloudreader.base.baseadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by jingbin on 2016/11/25
 */
public abstract class BaseRecyclerViewHolder<T, D extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public D binding;

    public BaseRecyclerViewHolder(ViewGroup viewGroup, int layoutId) {
        // 注意要依附 viewGroup，不然显示item不全!!
        super(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), layoutId, viewGroup, false).getRoot());
        // 得到这个View绑定的Binding
        binding = DataBindingUtil.getBinding(this.itemView);
    }

    /**
     * @param object   the data of bind
     * @param position the item position of recyclerView
     */
    public abstract void onBindViewHolder(T object, final int position);

    /**
     * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
     */
    void onBaseBindViewHolder(T object, final int position) {
        onBindViewHolder(object, position);
        binding.executePendingBindings();
    }
}
