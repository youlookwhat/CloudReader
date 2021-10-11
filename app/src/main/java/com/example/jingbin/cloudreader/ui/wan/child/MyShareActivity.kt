package com.example.jingbin.cloudreader.ui.wan.child

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.adapter.WanAndroidAdapter
import com.example.jingbin.cloudreader.app.RxCodeConstants
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding
import com.example.jingbin.cloudreader.databinding.HeaderShareArticleBinding
import com.example.jingbin.cloudreader.ui.WebViewActivity
import com.example.jingbin.cloudreader.utils.*
import com.example.jingbin.cloudreader.view.OnShareDialogListener
import com.example.jingbin.cloudreader.viewmodel.wan.WanCenterViewModel
import io.reactivex.functions.Consumer
import me.jingbin.bymvvm.base.BaseActivity
import me.jingbin.bymvvm.databinding.LayoutLoadingEmptyBinding
import me.jingbin.bymvvm.rxbus.RxBus
import me.jingbin.bymvvm.rxbus.RxBusBaseMessage
import me.jingbin.bymvvm.utils.CommonUtils
import me.jingbin.library.ByRecyclerView

/**
 * 我分享的文章
 */
open class MyShareActivity : BaseActivity<WanCenterViewModel, FragmentWanAndroidBinding>() {

    private lateinit var headerBinding: HeaderShareArticleBinding
    private lateinit var mAdapter: WanAndroidAdapter
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_wan_android)
        getIntentData()
        initRefreshView()
        loadData()
    }

    private fun getIntentData() {
        val username = intent.getStringExtra("username")
        userId = intent.getIntExtra("userId", 0)
        title = if (userId > 0) username else "我的分享"
    }

    private fun initRefreshView() {
        headerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.header_share_article, bindingView.xrvWan.parent as ViewGroup?, false)
        RefreshHelper.initLinear(bindingView.xrvWan, true)
        RefreshHelper.setSwipeRefreshView(bindingView.srlWan);
        mAdapter = WanAndroidAdapter(this)
        mAdapter.isMyShare = true
        bindingView.xrvWan.adapter = mAdapter
        bindingView.xrvWan.addHeaderView(headerBinding.root)
        // 目的是修复系统切换深色模式时page的值不变
        viewModel.mPage = 1
        bindingView.srlWan.setOnRefreshListener {
            viewModel.mPage = 1
            loadData()
        }
        bindingView.xrvWan.setOnLoadMoreListener {
            viewModel.mPage++
            loadData()
        }
        bindingView.xrvWan.setOnItemClickListener(ByRecyclerView.OnItemClickListener { v, position ->
            if (userId > 0) {
                val itemData = mAdapter.getItemData(position)
                WebViewActivity.loadUrl(v.context, itemData.link, itemData.title)
            } else {
                DialogBuild.showItemDialog(v, object : OnShareDialogListener {
                    override fun look() {
                        val itemData = mAdapter.getItemData(position)
                        WebViewActivity.loadUrl(v.context, itemData.link, itemData.title)
                    }

                    override fun delete() {
                        val itemData = mAdapter.getItemData(position)
                        handleDelete(position, itemData.id)
                    }

                })
            }
        })
    }

    private fun handleDelete(position: Int, id: Int) {
        DialogBuild.show(this, "确定要删除吗？", "确定", "取消") { _, _ ->
            viewModel.deleteShare(position, id).observe(this@MyShareActivity, Observer {
                if (it.status) {
                    ToastUtil.showToast("删除成功")
                    viewModel.mPage = 1
                    loadData()
                    RxBus.getDefault().post(RxCodeConstants.LOGIN, true)
                }
            })
        }
    }

    private fun loadData() {
        viewModel.getShareList(userId).observe(this, Observer {
            showContentView()
            bindingView.srlWan.isRefreshing = false
            bindingView.xrvWan.isLoadMoreEnabled = true
            if (viewModel.mPage == 1) {
                it?.coinInfo?.let { coinInfo ->
                    if (userId > 0) title = DataUtil.getName(coinInfo.username, coinInfo.nickname)
                    headerBinding.bean = coinInfo
                }
            }
            if (it?.shareArticles != null && it.shareArticles?.datas != null && it.shareArticles?.datas?.size!! > 0) {
                showContentView()
                if (viewModel.mPage == 1) {
                    bindingView.xrvWan.isStateViewEnabled = false
                    headerBinding.totalNum = "分享了" + it.shareArticles?.total + "篇文章"
                    mAdapter.setNewData(it.shareArticles?.datas)
                    bindingView.xrvWan.scrollToPosition(0)
                } else {
                    mAdapter.addData(it.shareArticles?.datas)
                    bindingView.xrvWan.loadMoreComplete()
                }

            } else {
                if (viewModel.mPage == 1) {
                    if (it != null) {
                        val bindingEmpty = DataBindingUtil.inflate<LayoutLoadingEmptyBinding>(layoutInflater, R.layout.layout_loading_empty, bindingView.xrvWan.parent as ViewGroup?, false)
                        if (userId != 0) {
                            bindingEmpty.tvTipEmpty.text = "未找到相关内容"
                        } else {
                            bindingEmpty.tvTipEmpty.text = "未找到相关内容\n点击分享一篇文章吧~"
                            bindingEmpty.tvTipEmpty.setTextColor(CommonUtils.getColor(this, R.color.colorTheme))
                            bindingEmpty.tvTipEmpty.setOnClickListener { PublishActivity.start(this) }
                            initRxBus()
                        }
                        bindingView.xrvWan.setEmptyView(bindingEmpty.root)
                        headerBinding.totalNum = "分享了0篇文章 "
                        bindingView.xrvWan.isLoadMoreEnabled = false
                        mAdapter.setNewData(null)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (userId == 0) {
            menuInflater.inflate(R.menu.menu_publish, menu)
            return true
        } else {
            return false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionbar_publish) {
            PublishActivity.start(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        loadData()
    }

    private fun initRxBus() {
        val subscribe = RxBus.getDefault().toObservable(RxCodeConstants.REFRESH_SQUARE_DATA, RxBusBaseMessage::class.java)
                .subscribe(Consumer {
                    showLoading()
                    viewModel.mPage = 1
                    loadData()
                })
        addSubscription(subscribe)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, MyShareActivity::class.java)
            context.startActivity(intent)
        }

        @JvmStatic
        fun start(context: Context, name: String?, userId: Int) {
            val intent = Intent(context, MyShareActivity::class.java)
            intent.putExtra("username", name)
            intent.putExtra("userId", userId)
            context.startActivity(intent)
        }
    }

}