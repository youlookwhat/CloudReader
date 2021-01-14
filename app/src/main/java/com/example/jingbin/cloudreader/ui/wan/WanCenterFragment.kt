package com.example.jingbin.cloudreader.ui.wan

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.databinding.FragmentContentBinding
import com.example.jingbin.cloudreader.ui.wan.child.NavigationFragment
import com.example.jingbin.cloudreader.ui.wan.child.WendaFragment
import com.example.jingbin.cloudreader.view.CommonTabPagerAdapter
import com.example.jingbin.cloudreader.viewmodel.wan.WanCenterViewModel
import me.jingbin.bymvvm.base.BaseFragment
import me.jingbin.bymvvm.base.NoViewModel

/**
 * 玩安卓中间的tab页
 */
class WanCenterFragment : BaseFragment<NoViewModel, FragmentContentBinding>(), CommonTabPagerAdapter.TabPagerListener {

    private var mIsFirst = true
    private var mIsPrepared = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mIsPrepared = true
    }

    override fun loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return
        }
        showLoading()
        bindingView.tabGank.postDelayed({
            val pagerAdapter = CommonTabPagerAdapter(childFragmentManager, listOf("问答", "广场", "导航"))
            pagerAdapter.listener = this
            bindingView.vpGank.adapter = pagerAdapter
            bindingView.vpGank.offscreenPageLimit = 2
            pagerAdapter.notifyDataSetChanged()
            bindingView.tabGank.setupWithViewPager(bindingView.vpGank)
            showContentView()
            mIsFirst = false
        }, 120)
    }

    override fun setContent(): Int = R.layout.fragment_content

    override fun getFragment(position: Int): Fragment? =
            when (position) {
                0 -> WendaFragment.newInstance()
                1 -> NavigationFragment.newInstance()
                2 -> NavigationFragment.newInstance()
                else -> NavigationFragment.newInstance()
            }
}