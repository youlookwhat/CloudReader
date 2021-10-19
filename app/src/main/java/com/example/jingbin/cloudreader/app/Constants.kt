package com.example.jingbin.cloudreader.app

/**
 * Created by jingbin on 2016/11/26.
 * 固定参数
 */
open class Constants {

    companion object {
        // 下载的链接
        const val DOWNLOAD_URL = "https://www.coolapk.com/apk/127875"
        // 隐私政策
        const val PRIVATE_URL = "https://jingbin127.gitee.io/apiserver/privacy.html"

        // 深色模式
        const val KEY_MODE_NIGHT = "mode-night"
        // 跟随系统
        const val KEY_MODE_SYSTEM = "mode-system"
        // 是否打开过酷安应用市场
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
        // 深色模式消息提示
        const val MESSAGE_READ_NIGHT_TIP = "message_read_night_tip"
        // 发现页内容角标
        const val FIND_POSITION = "find_position"
        // 是否同意隐私政策
        const val IS_AGREE_PRIVATE = "is_agree_private"
    }
}