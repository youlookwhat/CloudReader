package com.example.jingbin.cloudreader.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.bean.ComingFilmBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmComingBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;

/**
 * Created by jingbin on 2016/12/10.
 */

public class FilmComingAdapter extends BaseBindingAdapter<ComingFilmBean.MoviecomingsBean, ItemFilmComingBinding> {

    private int width;

    public FilmComingAdapter(Context context) {
        super(R.layout.item_film_coming);
        int px = DensityUtil.dip2px(context, 36);
        width = (DensityUtil.getDisplayWidth() - px) / 3;
    }

    @Override
    protected void bindView(ComingFilmBean.MoviecomingsBean bean, ItemFilmComingBinding binding, int position) {
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

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(ComingFilmBean.MoviecomingsBean bean, ImageView imageView);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }
}
