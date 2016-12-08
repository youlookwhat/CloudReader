package com.example.jingbin.yunyue.base.list;

import android.view.View;

/**
 * Created by yangcai on 16/11/7.
 */

public interface BaseHolder<T extends Object> {

    public void onBindView(T positionData, int position);

    public View getItemView();
}
