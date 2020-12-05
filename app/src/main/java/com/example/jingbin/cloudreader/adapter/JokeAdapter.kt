package com.example.jingbin.cloudreader.adapter

import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean
import com.example.jingbin.cloudreader.databinding.ItemJokeBinding
import com.example.jingbin.cloudreader.utils.TimeUtil
import me.jingbin.bymvvm.adapter.BaseBindingAdapter
import me.jingbin.bymvvm.adapter.BaseBindingHolder

/**
 * Created by jingbin on 2016/11/25.update 2020/12/5
 */
class JokeAdapter : BaseBindingAdapter<DuanZiBean, ItemJokeBinding>(R.layout.item_joke) {

    override fun bindView(holder: BaseBindingHolder<*, *>?, bean: DuanZiBean, binding: ItemJokeBinding, position: Int) {
        binding.bean = bean
        binding.time = TimeUtil.formatDataTime((bean.createTime.toString() + "000").toLong())
    }
}