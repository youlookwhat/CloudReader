package com.example.jingbin.cloudreader.bean

/**
 * Created by jingbin on 2020/12/3.
 */
open class UpdateBean {

    var name: String? = null
    // 版本号
    val version: String = "1"
    // 更新日志
    var changelog: String? = null
    // 1显示弹框，不然不显示
    var isShow: String? = "0"
    // 更新时间
    var updated_at: Long = 0
    // 版本名称
    var versionShort: String? = null
    var build: String? = null
    var installUrl: String? = ""
    var update_url: String? = null
    // 是否跳应用市场
    var isJumpMarket: Int = 1
}