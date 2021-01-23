package com.example.jingbin.cloudreader.bean

import com.example.jingbin.cloudreader.bean.wanandroid.WxarticleDetailItemBean

/**
 * Created by jingbin on 2021/1/22.
 */
class ShareArticleBean {

    var shareArticles: WxarticleDetailItemBean? = null
    var coinInfo: CoinInfoBean? = null

    class CoinInfoBean {
        val coinCount: Int = 0
        val userId: Int = 0
        val level: Int = 0
        val nickname: String = ""
        val rank: String = ""
        val username: String = ""
    }
}