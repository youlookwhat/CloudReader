package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.base.binding.BaseBindingHolder;
import com.example.jingbin.cloudreader.bean.moviechild.FilmItemBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmBinding;
import com.example.jingbin.cloudreader.ui.film.child.FilmDetailActivity;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

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
            binding.llOneItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    FilmDetailActivity.start(activity, positionData, binding.ivOnePhoto);
                }
            });

            DebugUtil.error("position:"+position);
            ViewHelper.setScaleX(binding.llOneItem, 0.8f);
            ViewHelper.setScaleY(binding.llOneItem, 0.8f);
            ViewPropertyAnimator.animate(binding.llOneItem).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(binding.llOneItem).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
        }
    }
}
