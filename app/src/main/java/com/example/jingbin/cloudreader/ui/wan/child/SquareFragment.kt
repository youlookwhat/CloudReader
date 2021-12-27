package com.example.jingbin.cloudreader.ui.wan.child

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter
import com.example.jingbin.cloudreader.app.RxCodeConstants
import com.example.jingbin.cloudreader.data.UserUtil
import com.example.jingbin.cloudreader.databinding.FragmentSquareBinding
import com.example.jingbin.cloudreader.utils.RefreshHelper
import com.example.jingbin.cloudreader.viewmodel.wan.WanCenterViewModel
import io.reactivex.functions.Consumer
import me.jingbin.bymvvm.base.BaseFragment
import me.jingbin.bymvvm.rxbus.RxBus
import me.jingbin.bymvvm.rxbus.RxBusBaseMessage

/**
 * 广场
 */
class SquareFragment : BaseFragment<WanCenterViewModel, FragmentSquareBinding>() {

    private var isFirst = true
    private lateinit var activity: Activity
    private lateinit var mAdapter: WanAndroidAdapter

    override fun setContent(): Int = R.layout.fragment_square

    companion object {
        fun newInstance(): SquareFragment {
            return SquareFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.mPage = 0
    }

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            showLoading()
            initRv()
            getWendaData()
            initRxBus()
            isFirst = false
        }
    }

    private fun initRv() {
        RefreshHelper.initLinear(bindingView.xrvAndroid, true, 1)
        mAdapter = WanAndroidAdapter(activity)
        mAdapter.isNoShowChapterName = true
        mAdapter.isNoImage = false
        bindingView.xrvAndroid.adapter = mAdapter
        bindingView.xrvAndroid.setOnRefreshListener {
            viewModel.mPage = 0
            getWendaData()
        }
        bindingView.xrvAndroid.setOnLoadMoreListener(true) {
            ++viewModel.mPage
            getWendaData()
        }
        bindingView.tvPublish.setOnClickListener {
            if (UserUtil.isLogin(activity)) {
                PublishActivity.start(activity)
            }
        }
    }

    private fun getWendaData() {
        viewModel.getUserArticleList().observe(this, Observer {
            bindingView.xrvAndroid.isRefreshing = false
            if (it != null && it.isNotEmpty()) {
                showContentView()
                if (viewModel.mPage == 0) {
                    mAdapter.setNewData(it)
                } else {
                    mAdapter.addData(it)
                    bindingView.xrvAndroid.loadMoreComplete()
                }

            } else {
                if (viewModel.mPage == 0) {
                    if (it != null) {
                        showEmptyView("没找到广场里的内容~")
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

    private fun initRxBus() {
        val subscribe = RxBus.getDefault().toObservable(RxCodeConstants.REFRESH_SQUARE_DATA, RxBusBaseMessage::class.java)
                .subscribe(Consumer {
                    showLoading()
                    viewModel.mPage = 0
                    getWendaData()
                })
        addSubscription(subscribe)
    }
}