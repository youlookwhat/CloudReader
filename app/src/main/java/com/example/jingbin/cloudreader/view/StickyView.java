package com.example.jingbin.cloudreader.view;

import android.view.View;

/**
 * 获取吸附View相关的信息
 *
 * @author jingbin
 */
public interface StickyView {


    /**
     * StickyView的 ItemViewType
     */
    int TYPE_STICKY_VIEW = 0x001;

    /**
     * 是否是吸附view
     *
     * @param view itemView
     */
    boolean isStickyView(View view);

    /**
     * 得到吸附view的itemType
     */
    int getStickViewType();
}
