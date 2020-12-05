package com.example.jingbin.cloudreader.adapter

import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.bean.moviechild.PersonBean
import com.example.jingbin.cloudreader.databinding.ItemMovieDetailPersonBinding
import com.example.jingbin.cloudreader.ui.WebViewActivity
import me.jingbin.bymvvm.adapter.BaseBindingAdapter
import me.jingbin.bymvvm.adapter.BaseBindingHolder

/**
 * Created by jingbin on 2020/12/5.
 */
class MovieDetailAdapter : BaseBindingAdapter<PersonBean, ItemMovieDetailPersonBinding>(R.layout.item_movie_detail_person) {

    override fun bindView(holder: BaseBindingHolder<*, *>?, bean: PersonBean?, binding: ItemMovieDetailPersonBinding, position: Int) {
        if (bean != null) {
            binding.personBean = bean
            binding.llItem.setOnClickListener {
                if (!bean.alt.isNullOrEmpty()) {
                    WebViewActivity.loadUrl(it.context, bean.alt, bean.name)
                }
            }
        }
    }
}