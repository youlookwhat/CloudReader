package com.example.jingbin.cloudreader.adapter

import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.bean.FilmDetailNewBean
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailActorBinding
import me.jingbin.bymvvm.adapter.BaseBindingAdapter
import me.jingbin.bymvvm.adapter.BaseBindingHolder

/**
 * Created by jingbin on 2020/12/5.
 */
class FilmDetailActorAdapter : BaseBindingAdapter<FilmDetailNewBean.ActorBean, ItemFilmDetailActorBinding>(R.layout.item_film_detail_actor) {

    override fun bindView(holder: BaseBindingHolder<*, *>?, bean: FilmDetailNewBean.ActorBean?, binding: ItemFilmDetailActorBinding, position: Int) {
        binding.personBean = bean
    }
}