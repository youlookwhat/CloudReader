package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.example.jingbin.cloudreader.app.Constants;
import me.jingbin.bymvvm.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.bean.wanandroid.BaseResultBean;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WxarticleDetailItemBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WxarticleItemBean;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.SPUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2019/9/29
 * @Description wanandroid公众号的ViewModel
 */

public class WanFindViewModel extends BaseListViewModel {

    // content数据
    private final MutableLiveData<List<ArticlesBean>> data = new MutableLiveData<>();
    // title数据
    private final MutableLiveData<List<WxarticleItemBean>> dataTitle = new MutableLiveData<>();

    public MutableLiveData<List<ArticlesBean>> getData() {
        return data;
    }

    public MutableLiveData<List<WxarticleItemBean>> getDataTitle() {
        return dataTitle;
    }

    public WanFindViewModel(@NonNull Application application) {
        super(application);
        mPage = 1;
    }

    public void getWxArticle() {
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().getWxarticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResultBean<List<WxarticleItemBean>>>() {
                    @Override
                    public void accept(BaseResultBean<List<WxarticleItemBean>> naviJsonBean) throws Exception {
                        if (naviJsonBean != null
                                && naviJsonBean.getData() != null
                                && naviJsonBean.getData().size() > 0) {

                            // title
                            dataTitle.setValue(naviJsonBean.getData());
                        } else {
                            dataTitle.setValue(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dataTitle.setValue(null);
                    }
                });
        addDisposable(subscribe);
    }

    public void getWxarticleDetail(int id) {
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().getWxarticleDetail(id, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResultBean<WxarticleDetailItemBean>>() {
                    @Override
                    public void accept(BaseResultBean<WxarticleDetailItemBean> naviJsonBean) throws Exception {
                        if (naviJsonBean != null
                                && naviJsonBean.getData() != null
                                && naviJsonBean.getData().getDatas() != null
                                && naviJsonBean.getData().getDatas().size() > 0
                        ) {

                            // detail
                            data.setValue(naviJsonBean.getData().getDatas());
                        } else {
                            data.setValue(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        data.setValue(null);
                    }
                });
        addDisposable(subscribe);
    }

    /**
     * 处理订制页内容
     */
    public boolean handleCustomData(TreeBean treeBean, int position) {
        if (treeBean != null
                && treeBean.getData() != null
                && treeBean.getData().size() > 0) {
            SPUtils.putInt(Constants.FIND_POSITION, position);
            mPage = 1;
            dataTitle.setValue(treeBean.getData().get(position).getChildren());
            return true;
        } else {
            // 如果缓存失效清掉position值
            SPUtils.remove(Constants.FIND_POSITION);
            return false;
        }
    }
}
