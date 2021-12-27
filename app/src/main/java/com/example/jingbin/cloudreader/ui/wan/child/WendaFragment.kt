package com.example.jingbin.cloudreader.ui.wan.child

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter
import com.example.jingbin.cloudreader.databinding.FragmentAndroidBinding
import com.example.jingbin.cloudreader.utils.RefreshHelper
import com.example.jingbin.cloudreader.viewmodel.wan.WanCenterViewModel
import me.jingbin.bymvvm.base.BaseFragment

/**
 * 问答
 */
class WendaFragment : BaseFragment<WanCenterViewModel, FragmentAndroidBinding>() {

    private var isFirst = true
    private lateinit var activity: Activity
    private lateinit var mAdapter: WanAndroidAdapter

    override fun setContent(): Int = R.layout.fragment_android

    companion object {
        fun newInstance(): WendaFragment {
            return WendaFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            showLoading()
            initRv()
            getWendaData()
            isFirst = false
        }
    }

    private fun initRv() {
        RefreshHelper.initLinear(bindingView.xrvAndroid, true, 1)
        mAdapter = WanAndroidAdapter(activity)
        mAdapter.isNoShowChapterName = true
        mAdapter.isNoImage = false
        mAdapter.isShowDesc = true
        bindingView.xrvAndroid.adapter = mAdapter
        bindingView.xrvAndroid.setOnRefreshListener {
            viewModel.mPage = 1
            getWendaData()
        }
        bindingView.xrvAndroid.setOnLoadMoreListener(true) {
            ++viewModel.mPage
            getWendaData()
        }
    }

    private fun getWendaData() {
        viewModel.getWendaList().observe(this, Observer {
            bindingView.xrvAndroid.isRefreshing = false
            if (it != null && it.isNotEmpty()) {
                showContentView()
                if (viewModel.mPage == 1) {
                    mAdapter.setNewData(it)
                } else {
                    mAdapter.addData(it)
                    bindingView.xrvAndroid.loadMoreComplete()
                }

            } else {
                if (viewModel.mPage == 1) {
                    if (it != null) {
                        showEmptyView("没找到问答的内容~")
                    } else {
                        showError()
                        if (viewModel.mPage > 1) viewModel.mPage--
                    }
                } else {
                    bindingView.xrvAndroid.loadMoreEnd()
                }
            }
        })
    }

    override fun onRefresh() {
        getWendaData()
    }
}