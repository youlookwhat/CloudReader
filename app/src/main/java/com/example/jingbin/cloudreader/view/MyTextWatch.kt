package com.example.jingbin.cloudreader.view

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by jingbin on 2021/1/17.
 */
abstract class MyTextWatch : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }
}