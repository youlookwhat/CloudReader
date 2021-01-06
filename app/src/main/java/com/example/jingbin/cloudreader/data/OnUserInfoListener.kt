package com.example.jingbin.cloudreader.data

import me.jingbin.bymvvm.room.User

/**
 * Created by jingbin on 2020/12/5.
 */
interface OnUserInfoListener {

    fun onSuccess(user: User?)
}