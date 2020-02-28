package com.example.jingbin.cloudreader.adapter;

import android.content.Context;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.base.binding.BaseBindingHolder;
import com.example.jingbin.cloudreader.bean.ComingFilmBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmComingBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;

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
    protected void bindView(BaseBindingHolder holder, ComingFilmBean.MoviecomingsBean bean, ItemFilmComingBinding binding, int position) {
        binding.setBean(bean);
        DensityUtil.setWidthHeight(binding.ivTopPhoto, width, 0.758f);
    }
}
