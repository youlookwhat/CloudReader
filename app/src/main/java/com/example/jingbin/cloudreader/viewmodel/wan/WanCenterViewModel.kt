package com.example.jingbin.cloudreader.viewmodel.wan

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean
import com.example.jingbin.cloudreader.bean.wanandroid.BaseResultBean
import com.example.jingbin.cloudreader.http.HttpClient
import com.example.jingbin.cloudreader.bean.ShareArticleBean
import com.example.jingbin.cloudreader.utils.ToastUtil
import io.reactivex.Observable
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
    fun getShareList(userId: Int): MutableLiveData<ShareArticleBean?> {
        val data = MutableLiveData<ShareArticleBean?>()
        val userShare: Observable<BaseResultBean<ShareArticleBean>>
        if (userId != 0) {
            userShare = HttpClient.Builder.getWanAndroidServer().getUserShare(userId, page)
        } else {
            userShare = HttpClient.Builder.getWanAndroidServer().getShareList(page)
        }
        val subscribe = userShare
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

    /**
     * 删除分享
     */
    fun deleteShare(position: Int, id: Int): MutableLiveData<DeleteSuccess> {
        val data = MutableLiveData<DeleteSuccess>()
        val subscribe = HttpClient.Builder.getWanAndroidServer().deleteShare(id,null)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bean ->
                    if (bean != null && bean.errorCode == 0) {
                        data.setValue(DeleteSuccess(true, position))
                    } else {
                        bean?.let {
                            ToastUtil.showToast(bean.errorMsg)
                        }
                        data.setValue(DeleteSuccess(false, position))
                    }
                }) { throwable: Throwable? -> data.setValue(DeleteSuccess(false, position)) }
        addDisposable(subscribe)
        return data
    }

    class DeleteSuccess {
        var status: Boolean = false
        var position: Int = 0

        constructor()
        constructor(status: Boolean, position: Int) {
            this.status = status
            this.position = position
        }

    }


}