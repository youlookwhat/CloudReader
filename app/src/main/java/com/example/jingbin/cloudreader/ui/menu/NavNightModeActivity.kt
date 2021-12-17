package com.example.jingbin.cloudreader.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.databinding.ActivityNavNightModeBinding
import com.example.jingbin.cloudreader.utils.BaseTools
import com.example.jingbin.cloudreader.utils.DialogBuild
import com.example.jingbin.cloudreader.utils.NightModeUtil
import me.jingbin.bymvvm.base.BaseActivity
import me.jingbin.bymvvm.base.NoViewModel

/**
 * @author jingbin
 */
class NavNightModeActivity : BaseActivity<NoViewModel, ActivityNavNightModeBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_night_mode)
        showContentView()
        title = "深色模式"
        initView()
    }

    private fun initView() {
        // 设置toolbar的dark模式，为了使"完成"文字颜色显示白色
        supportActionBar?.themedContext?.setTheme(R.style.ToolBarDarkActionBar)
        if (NightModeUtil.getSystemMode()) {
            bindingView.dayNightSwitch.isChecked = true
            bindingView.llChoose.visibility = View.GONE
        } else {
            bindingView.dayNightSwitch.isChecked = false
            bindingView.llChoose.visibility = View.VISIBLE
            if (NightModeUtil.getNightMode()) {
                bindingView.ctvCheckNight.isChecked = true
                bindingView.ctvCheckNormal.isChecked = false
            } else {
                bindingView.ctvCheckNight.isChecked = false
                bindingView.ctvCheckNormal.isChecked = true
            }
        }
        bindingView.dayNightSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            bindingView.llChoose.visibility = if (!isChecked) View.VISIBLE else View.GONE
            if (!isChecked) {
                bindingView.ctvCheckNight.isChecked = false
                bindingView.ctvCheckNormal.isChecked = true
            }
        })
        bindingView.ctvCheckNormal.setOnClickListener(View.OnClickListener {
            if (!bindingView.ctvCheckNormal.isChecked) {
                bindingView.ctvCheckNormal.toggle()
                bindingView.ctvCheckNight.isChecked = !bindingView.ctvCheckNight.isChecked()
            }
        })
        bindingView.ctvCheckNight.setOnClickListener(View.OnClickListener {
            if (!bindingView.ctvCheckNight.isChecked) {
                bindingView.ctvCheckNight.toggle()
                bindingView.ctvCheckNormal.isChecked = !bindingView.ctvCheckNormal.isChecked()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_night_mode, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionbar_ok -> DialogBuild.showCustom(bindingView.activityNavAbout, 0, "新的设置需要重启云阅才能生效", "确认", "取消") { _, _ ->
                when {
                    bindingView.dayNightSwitch.isChecked -> {
                        NightModeUtil.setSystemMode(true)
                        NightModeUtil.setNightMode(false)
                    }
                    bindingView.ctvCheckNight.isChecked -> {
                        NightModeUtil.setSystemMode(false)
                        NightModeUtil.setNightMode(true)
                    }
                    else -> {
                        NightModeUtil.setSystemMode(false)
                        NightModeUtil.setNightMode(false)
                    }
                }
                bindingView.ctvCheckNormal.postDelayed(Runnable {
                    NightModeUtil.initNightMode(bindingView.dayNightSwitch.isChecked, bindingView.ctvCheckNight.isChecked)
                    NightModeUtil.restartApp(activity)
                }, 300)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun start(mContext: Context) {
            val intent = Intent(mContext, NavNightModeActivity::class.java)
            mContext.startActivity(intent)
        }
    }
}