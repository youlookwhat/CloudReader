package com.example.jingbin.cloudreader.adapter

import android.app.Activity
import android.view.animation.OvershootInterpolator
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.bean.moviechild.FilmItemBean
import com.example.jingbin.cloudreader.databinding.ItemFilmBinding
import com.nineoldandroids.view.ViewHelper
import com.nineoldandroids.view.ViewPropertyAnimator
import me.jingbin.bymvvm.adapter.BaseBindingAdapter
import me.jingbin.bymvvm.adapter.BaseBindingHolder

/**
 * Created by jingbin on 2020/12/5.
 */
class FilmAdapter(private val activity: Activity) : BaseBindingAdapter<FilmItemBean, ItemFilmBinding>(R.layout.item_film) {

    override fun bindView(holder: BaseBindingHolder<*, *>?, bean: FilmItemBean?, binding: ItemFilmBinding, position: Int) {
        if (bean != null) {
            binding.subjectsBean = bean
            ViewHelper.setScaleX(binding.llOneItem, 0.8f)
            ViewHelper.setScaleY(binding.llOneItem, 0.8f)
            ViewPropertyAnimator.animate(binding.llOneItem).scaleX(1f).setDuration(350).setInterpolator(OvershootInterpolator()).start()
            ViewPropertyAnimator.animate(binding.llOneItem).scaleY(1f).setDuration(350).setInterpolator(OvershootInterpolator()).start()
        }
    }
}