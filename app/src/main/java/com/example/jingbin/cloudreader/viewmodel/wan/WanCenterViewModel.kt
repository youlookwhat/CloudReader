package com.example.jingbin.cloudreader.viewmodel.wan

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean
import com.example.jingbin.cloudreader.http.HttpClient
import com.example.jingbin.cloudreader.ui.wan.child.ShareArticleBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jingbin.bymvvm.base.BaseListViewModel

class WanCenterViewModel(application: Application) : BaseListViewModel(application) {

    init {
        mPage = 1
    }

    /**
     * 问答列表
     */
    fun getWendaList(): MutableLiveData<List<ArticlesBean>?> {
        val data = MutableLiveData<List<ArticlesBean>?>()
        val subscribe = HttpClient.Builder.getWanAndroidServer().getWendaList(page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bean ->
                    if (bean != null
                            && bean.data != null
                            && bean.data.datas != null
                            && bean.data.datas.size > 0) {
                        data.setValue(bean.data.datas)
                    } else {
                        data.setValue(ArrayList())
                    }
                }) { throwable: Throwable? -> data.setValue(null) }
        addDisposable(subscribe)
        return data
    }

    /**
     * 广场列表数据
     */
    fun getUserArticleList(): MutableLiveData<List<ArticlesBean>?> {
        val data = MutableLiveData<List<ArticlesBean>?>()
        val subscribe = HttpClient.Builder.getWanAndroidServer().getUserArticleList(page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bean ->
                    if (bean != null
                            && bean.data != null
                            && bean.data.datas != null
                            && bean.data.datas.size > 0) {
                        data.setValue(bean.data.datas)
                    } else {
                        data.setValue(ArrayList())
                    }
                }) { throwable: Throwable? -> data.setValue(null) }
        addDisposable(subscribe)
        return data
    }

    /**
     * 自己的分享的文章列表
     */
    fun getShareList(): MutableLiveData<ShareArticleBean?> {
        val data = MutableLiveData<ShareArticleBean?>()
        val subscribe = HttpClient.Builder.getWanAndroidServer().getShareList(page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bean ->
                    if (bean != null) {
                        data.setValue(bean.data)
                    } else {
                        data.setValue(ShareArticleBean())
                    }
                }) { throwable: Throwable? -> data.setValue(null) }
        addDisposable(subscribe)
        return data
    }


}