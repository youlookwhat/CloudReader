package com.example.jingbin.cloudreader.app

/**
 * Created by jingbin on 2016/11/26.
 * 固定参数
 */

open class Constants {

    companion object {
        const val DOWNLOAD_URL = "https://www.coolapk.com/apk/127875"

        const val NIGHT_SKIN = "night.skin"
        const val KEY_MODE_NIGHT = "mode-night"
        const val SHOW_MARKET = "show_market"
        // 酷安包名
        const val COOLAPK_PACKAGE = "com.coolapk.market"
        // 热映缓存
        const val ONE_HOT_MOVIE = "one_hot_movie"
        // 保存每日推荐轮播图url
        const val BANNER_PIC = "gank_banner_pic"
        // 保存每日推荐轮播图的跳转数据
        const val BANNER_PIC_DATA = "gank_banner_data"
        // 保存每日推荐recyclerview内容
        const val EVERYDAY_CONTENT = "everyday_content"
        // 干货订制类别
        const val GANK_TYPE = "gank_type"
        // 是否登录
        const val IS_LOGIN = "is_login"
        // 是否第一次收藏网址
        const val IS_FIRST_COLLECTURL = "isFirstCollectUrl"
        // 问题反馈消息提示
        const val MESSAGE_READ_TIP = "message_read_tip"
        // 知识体系里上一次选中的position
        const val TREE_POSITION = "tree_position"
        // 发现页内容角标
        const val FIND_POSITION = "find_position"
    }
}