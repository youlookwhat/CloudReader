package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.animation.OvershootInterpolator;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.moviechild.FilmItemBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmBinding;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * @author jingbin
 */
public class FilmAdapter extends BaseBindingAdapter<FilmItemBean, ItemFilmBinding> {

    private Activity activity;

    public FilmAdapter(Activity activity) {
        super(R.layout.item_film);
        this.activity = activity;
    }

    @Override
    protected void bindView(BaseBindingHolder holder, FilmItemBean positionData, ItemFilmBinding binding, int position) {
        if (positionData != null) {
            binding.setSubjectsBean(positionData);
            ViewHelper.setScaleX(binding.llOneItem, 0.8f);
            ViewHelper.setScaleY(binding.llOneItem, 0.8f);
            ViewPropertyAnimator.animate(binding.llOneItem).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(binding.llOneItem).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
        }
    }
}
