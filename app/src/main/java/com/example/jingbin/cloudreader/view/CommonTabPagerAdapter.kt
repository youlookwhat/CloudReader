package com.example.jingbin.cloudreader.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.jingbin.cloudreader.utils.DataUtil

class CommonTabPagerAdapter : FragmentPagerAdapter {

    private var pageCount = 0
    private var mList: List<String>? = null
    var listener: TabPagerListener? = null

    constructor(fm: FragmentManager, list: List<String>?) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        initData(list)
    }

    private fun initData(list: List<String>?) {
        if (list == null || list.isEmpty()) {
            throw ExceptionInInitializerError("list can't be null or empty")
        }
        mList = list
        this.pageCount = list.size
    }

    override fun getItem(position: Int): Fragment = listener?.getFragment(position)!!

    override fun getCount(): Int = pageCount

    override fun getPageTitle(position: Int): CharSequence? = DataUtil.getHtmlString(mList?.get(position))!!

    interface TabPagerListener {
        fun getFragment(position: Int): Fragment?
    }
}