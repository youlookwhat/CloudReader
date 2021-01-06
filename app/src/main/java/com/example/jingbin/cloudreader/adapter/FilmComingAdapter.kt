package com.example.jingbin.cloudreader.adapter

import android.content.Context
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.bean.ComingFilmBean
import com.example.jingbin.cloudreader.databinding.ItemFilmComingBinding
import com.example.jingbin.cloudreader.utils.DensityUtil
import me.jingbin.bymvvm.adapter.BaseBindingAdapter
import me.jingbin.bymvvm.adapter.BaseBindingHolder

/**
 * Created by jingbin on 2020/12/5.
 */
class FilmComingAdapter(context: Context) : BaseBindingAdapter<ComingFilmBean.MoviecomingsBean, ItemFilmComingBinding>(R.layout.item_film_coming) {

    private val width: Int

    init {
        val px = DensityUtil.dip2px(context, 36f)
        width = (DensityUtil.getDisplayWidth() - px) / 3
    }

    override fun bindView(holder: BaseBindingHolder<*, *>?, bean: ComingFilmBean.MoviecomingsBean?, binding: ItemFilmComingBinding, position: Int) {
        binding.bean = bean
        DensityUtil.setWidthHeight(binding.ivTopPhoto, width, 0.758f)
    }
}