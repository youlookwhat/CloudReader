package com.example.jingbin.cloudreader.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.ComingFilmBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmComingBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;

/**
 * Created by jingbin on 2016/12/10.
 */

public class FilmComingAdapter extends BaseRecyclerViewAdapter<ComingFilmBean.MoviecomingsBean> {

    private int width;

    public FilmComingAdapter() {
        int px = DensityUtil.dip2px(36);
        width = (DensityUtil.getDisplayWidth() - px) / 3;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_film_coming);
    }

    class ViewHolder extends BaseRecyclerViewHolder<ComingFilmBean.MoviecomingsBean, ItemFilmComingBinding> {

        ViewHolder(ViewGroup parent, int layout) {
            super(parent, layout);
        }

        @Override
        public void onBindViewHolder(final ComingFilmBean.MoviecomingsBean bean, final int position) {
            binding.setBean(bean);
            DensityUtil.formatHeight(binding.ivTopPhoto, width, 0.758f, 1);
            binding.cvTopMovie.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (listener != null) {
                        listener.onClick(bean, binding.ivTopPhoto);
                    }
                }
            });
        }
    }

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(ComingFilmBean.MoviecomingsBean bean, ImageView imageView);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }
}
