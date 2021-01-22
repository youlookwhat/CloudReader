package com.example.jingbin.cloudreader.ui.wan.child

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding
import com.example.jingbin.cloudreader.utils.CommonUtils
import com.example.jingbin.cloudreader.utils.RefreshHelper
import com.example.jingbin.cloudreader.viewmodel.wan.WanCenterViewModel
import me.jingbin.bymvvm.base.BaseActivity
import me.jingbin.bymvvm.databinding.LayoutLoadingEmptyBinding

/**
 * 我分享的文章
 */
open class MyShareActivity : BaseActivity<WanCenterViewModel, FragmentWanAndroidBinding>() {

    private lateinit var mAdapter: WanAndroidAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_wan_android)
        title = "我的分享"
        initRefreshView()
        loadData()
    }

    private fun initRefreshView() {
        RefreshHelper.initLinear(bindingView.xrvWan, true)
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme))
        mAdapter = WanAndroidAdapter(this)
        bindingView.xrvWan.adapter = mAdapter
        bindingView.srlWan.setOnRefreshListener {
            viewModel.page = 1
            loadData()
        }
        bindingView.xrvWan.setOnLoadMoreListener {
            viewModel.mPage++
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getShareList().observe(this, Observer {
            showContentView()
            bindingView.srlWan.isRefreshing = false
            if (it?.shareArticles != null && it.shareArticles?.datas != null && it.shareArticles?.datas?.size!! > 0) {
                showContentView()
                if (viewModel.mPage == 1) {
                    mAdapter.setNewData(it.shareArticles?.datas)
                } else {
                    mAdapter.addData(it.shareArticles?.datas)
                    bindingView.xrvWan.loadMoreComplete()
                }

            } else {
                if (viewModel.mPage == 1) {
                    if (it != null) {
                        val bindingEmpty = DataBindingUtil.inflate<LayoutLoadingEmptyBinding>(layoutInflater, R.layout.layout_loading_empty, bindingView.xrvWan.parent as ViewGroup?, false)
                        bindingEmpty.tvTipEmpty.text = "未找到相关内容\n快去分享一篇文章吧~"
                        bindingView.xrvWan.setEmptyView(bindingEmpty.root)
                    } else {
                        showError()
                        if (viewModel.mPage > 1) viewModel.mPage--
                    }
                } else {
                    bindingView.xrvWan.loadMoreEnd()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, MyShareActivity::class.java)
            context.startActivity(intent)
        }
    }

}