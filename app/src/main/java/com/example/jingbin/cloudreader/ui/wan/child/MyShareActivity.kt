package com.example.jingbin.cloudreader.ui.wan.child

import android.os.Bundle
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding
import com.example.jingbin.cloudreader.utils.CommonUtils
import com.example.jingbin.cloudreader.utils.RefreshHelper
import me.jingbin.bymvvm.base.BaseActivity
import me.jingbin.bymvvm.base.NoViewModel

/**
 * 我分享的文章
 */
class MyShareActivity : BaseActivity<NoViewModel, FragmentWanAndroidBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_wan_android)
        title = "我的分享"
        initRefreshView()

    }

    private fun initRefreshView() {
        RefreshHelper.initLinear(bindingView.xrvWan, false)
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme))
        var mAdapter = WanAndroidAdapter(this)
        bindingView.xrvWan.adapter = mAdapter
        bindingView.srlWan.setOnRefreshListener {
            bindingView.srlWan.postDelayed({
//                viewModel.setPage(0)
//                loadData()
            }, 500)
        }
        bindingView.xrvWan.setOnLoadMoreListener {
//            var page: Int = viewModel.getPage()
//            viewModel.setPage(++page)
//            loadData()
        }
    }

}