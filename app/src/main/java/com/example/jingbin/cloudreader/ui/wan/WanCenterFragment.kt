package com.example.jingbin.cloudreader.ui.wan

import androidx.fragment.app.Fragment
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.databinding.FragmentContentBinding
import com.example.jingbin.cloudreader.ui.wan.child.NavigationFragment
import com.example.jingbin.cloudreader.ui.wan.child.TreeFragment
import com.example.jingbin.cloudreader.ui.wan.child.WanFindFragment
import com.example.jingbin.cloudreader.view.CommonTabPagerAdapter
import me.jingbin.bymvvm.base.BaseFragment
import me.jingbin.bymvvm.base.NoViewModel

/**
 * 中间的tab页
 */
class WanCenterFragment : BaseFragment<NoViewModel, FragmentContentBinding>(), CommonTabPagerAdapter.TabPagerListener {

    private var mIsFirst = true

    override fun setContent(): Int = R.layout.fragment_content

    override fun onResume() {
        super.onResume()
        if (mIsFirst) {
            showLoading()
            val pagerAdapter = CommonTabPagerAdapter(childFragmentManager, listOf("发现", "体系", "导航"))
            pagerAdapter.listener = this
            bindingView.vpGank.adapter = pagerAdapter
            bindingView.vpGank.offscreenPageLimit = 2
            pagerAdapter.notifyDataSetChanged()
            bindingView.tabGank.setupWithViewPager(bindingView.vpGank)
            showContentView()
            mIsFirst = false
        }
    }

    override fun getFragment(position: Int): Fragment? =
            when (position) {
                0 -> WanFindFragment.newInstance()
                1 -> TreeFragment.newInstance()
                2 -> NavigationFragment.newInstance()
                else -> NavigationFragment.newInstance()
            }
}