package com.example.jingbin.cloudreader.ui.wan.child

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.lifecycle.Observer
import com.example.jingbin.cloudreader.R
import com.example.jingbin.cloudreader.app.RxCodeConstants
import com.example.jingbin.cloudreader.databinding.ActivityPublishBinding
import com.example.jingbin.cloudreader.ui.WebViewActivity
import com.example.jingbin.cloudreader.utils.BaseTools
import com.example.jingbin.cloudreader.view.MyTextWatch
import com.example.jingbin.cloudreader.viewmodel.wan.PublishViewModel
import me.jingbin.bymvvm.base.BaseActivity
import me.jingbin.bymvvm.rxbus.RxBus
import me.jingbin.bymvvm.rxbus.RxBusBaseMessage

/**
 * 分享文章
 * Created by jingbin on 2021/1/17.
 */
class PublishActivity : BaseActivity<PublishViewModel, ActivityPublishBinding>() {

    private lateinit var clipContent: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)
        title = "分享文章"
        showContentView()
        bindingView.viewModel = viewModel
        initView()
    }

    private fun initView() {
        bindingView.etTitle.postDelayed({
            bindingView.etTitle.requestFocus()
            BaseTools.showSoftKeyBoard(this, bindingView.etTitle)
        }, 150)
        clipContent = BaseTools.getClipContent()
        if (clipContent.isNotBlank()) {
            if (clipContent.contains("http")) {
                bindingView.ivDown.visibility = View.VISIBLE
            } else {
                bindingView.ivUp.visibility = View.VISIBLE
            }
        }
        bindingView.etTitle.addTextChangedListener(object : MyTextWatch() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val title = bindingView.etTitle.text.toString().trim()
                if (title.isNotEmpty()) {
                    bindingView.ivUp.visibility = View.VISIBLE
                    bindingView.ivUp.setImageResource(R.drawable.icon_clear)
                } else {
                    if (clipContent.isNotBlank() && !clipContent.contains("http")) {
                        bindingView.ivUp.visibility = View.VISIBLE
                        bindingView.ivUp.setImageResource(R.drawable.icon_paste)
                    } else {
                        bindingView.ivUp.visibility = View.GONE
                    }
                }
            }
        })
        bindingView.etLink.addTextChangedListener(object : MyTextWatch() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val link = bindingView.etLink.text.toString().trim()
                if (link.isNotBlank()) {
                    bindingView.ivDown.visibility = View.VISIBLE
                    bindingView.ivDown.setImageResource(R.drawable.icon_clear)
                } else {
                    if (clipContent.isNotBlank() && clipContent.contains("http")) {
                        bindingView.ivDown.visibility = View.VISIBLE
                        bindingView.ivDown.setImageResource(R.drawable.icon_paste)
                    } else {
                        bindingView.ivDown.visibility = View.GONE
                    }
                }
            }
        })
        bindingView.ivUp.setOnClickListener {
            val title = bindingView.etTitle.text.toString().trim()
            if (title.isNotBlank()) {
                bindingView.etTitle.text.clear()
            } else {
                bindingView.etTitle.text = Editable.Factory.getInstance().newEditable(clipContent)
                bindingView.etTitle.setSelection(clipContent.length)
            }
        }
        bindingView.ivDown.setOnClickListener {
            val link = bindingView.etLink.text.toString().trim()
            if (link.isNotBlank()) {
                bindingView.etLink.text.clear()
            } else {
                bindingView.etLink.text = Editable.Factory.getInstance().newEditable(clipContent)
                bindingView.etLink.setSelection(clipContent.length)
            }
        }
    }

    /**分享*/
    fun publish(view: View) {
        viewModel.pushArticle().observe(this, Observer {
            if (it != null && it) {
                // 成功
                RxBus.getDefault().post(RxCodeConstants.REFRESH_SQUARE_DATA, RxBusBaseMessage())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        clipContent = BaseTools.getClipContent()
        if (clipContent.isNotBlank()) {
            if (clipContent.contains("http")) {
                bindingView.ivDown.visibility = View.VISIBLE
            } else {
                bindingView.ivUp.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        fun start(context: Activity, view: View) {
            val intent = Intent(context, PublishActivity::class.java)
            context.startActivity(intent)
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view, CommonUtils.getString(R.string.transition_publish_bt))
//            ActivityCompat.startActivity(context, intent, options.toBundle())
        }
    }

    fun goWeb(view: View) {
        WebViewActivity.loadUrl(view.context, "https://www.wanandroid.com/user_article/add", "分享文章")
    }
}