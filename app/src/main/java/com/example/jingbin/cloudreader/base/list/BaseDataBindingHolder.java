package com.example.jingbin.cloudreader.base.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by yangcai on 16/11/7.
 */

public abstract class BaseDataBindingHolder<T ,DB extends ViewDataBinding> implements BaseHolder<T> {

    protected DB binding;

    public BaseDataBindingHolder(Context context, int layoutID) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutID, null, false);
    }

    @Override
    public View getItemView() {
        return binding.getRoot();
    }




}
