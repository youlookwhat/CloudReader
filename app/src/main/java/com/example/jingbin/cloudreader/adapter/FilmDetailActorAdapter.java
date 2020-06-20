package com.example.jingbin.cloudreader.adapter;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.FilmDetailBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailActorBinding;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2016/12/10.
 */

public class FilmDetailActorAdapter extends BaseBindingAdapter<FilmDetailBean.ActorsBean, ItemFilmDetailActorBinding> {

    public FilmDetailActorAdapter() {
        super(R.layout.item_film_detail_actor);
    }

    @Override
    protected void bindView(BaseBindingHolder holder, FilmDetailBean.ActorsBean bean, ItemFilmDetailActorBinding binding, int position) {
        binding.setPersonBean(bean);
    }
}
