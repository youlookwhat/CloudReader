package com.example.jingbin.cloudreader.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ItemOneBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by jingbin on 2019/01/14.
 */

public class UpcomingAdapter extends BaseRecyclerViewAdapter<SubjectsBean> {

    private int width;

    public UpcomingAdapter() {
        int px = DensityUtil.dip2px(36);
        width = (DensityUtil.getDisplayWidth() - px) / 3;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item_douban_top 宫格
        return new ViewHolder(parent, R.layout.item_one);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<SubjectsBean, ItemOneBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final SubjectsBean positionData, final int position) {
            if (positionData != null) {
                binding.setSubjectsBean(positionData);
                binding.llOneItem.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        if (listener != null) {
                            listener.onClick(positionData, binding.ivOnePhoto);
                        }
                    }
                });

//                ViewHelper.setScaleX(itemView, 0.8f);
//                ViewHelper.setScaleY(itemView, 0.8f);
//                ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
//                ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

            }
        }
    }

    private OnClickInterface listener;

    public interface OnClickInterface {
        void onClick(SubjectsBean bean, ImageView view);
    }

    public void setListener(OnClickInterface listener) {
        this.listener = listener;
    }
}
