package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import me.jingbin.bymvvm.base.BaseViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/12/3
 * @Description wanandroid导航数据的ViewModel
 */

public class NavigationViewModel extends BaseViewModel {

    // content数据
    private final MutableLiveData<List<ArticlesBean>> data = new MutableLiveData<>();
    // title数据
    private final MutableLiveData<List<NaviJsonBean.DataBean>> dataTitle = new MutableLiveData<>();
    // content 的 title position  外面的i对应的 titlePositions.get(i)
    private final MutableLiveData<List<Integer>> titlePositions = new MutableLiveData<>();
    // 用来滑动定位 第一个Integer为内容的position，第二个Integer为对应的分类position
    private HashMap<Integer, Integer> hashMap;

    public MutableLiveData<List<ArticlesBean>> getData() {
        return data;
    }

    public MutableLiveData<List<NaviJsonBean.DataBean>> getDataTitle() {
        return dataTitle;
    }

    public MutableLiveData<List<Integer>> getTitlePositions() {
        return titlePositions;
    }

    public HashMap<Integer, Integer> getHashMap() {
        return hashMap;
    }

    public NavigationViewModel(@NonNull Application application) {
        super(application);
        hashMap = new HashMap<>();
    }

    public void getNavigationJson() {
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().getNaviJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NaviJsonBean>() {
                    @Override
                    public void accept(NaviJsonBean naviJsonBean) throws Exception {
                        if (naviJsonBean != null
                                && naviJsonBean.getData() != null
                                && naviJsonBean.getData().size() > 0) {

                            // title
                            dataTitle.setValue(naviJsonBean.getData());
                            // content
                            ArrayList<ArticlesBean> list = new ArrayList<>();
                            // content部分对应分类的position
                            ArrayList<Integer> positions = new ArrayList<>();
                            for (int i = 0; i < naviJsonBean.getData().size(); i++) {
                                NaviJsonBean.DataBean dataBean = naviJsonBean.getData().get(i);
                                ArticlesBean bean = new ArticlesBean();
                                bean.setNavigationName(dataBean.getName());
                                positions.add(list.size());
                                if (i != 0) {
                                    // 最后一个item可能有一个或两个
                                    hashMap.put(list.size() - 1, i - 1);
                                    hashMap.put(list.size() - 2, i - 1);
                                }
                                hashMap.put(list.size(), i);
                                list.add(bean);
                                list.addAll(dataBean.getArticles());
                            }
                            data.setValue(list);
                            titlePositions.setValue(positions);
                        } else {
                            data.setValue(null);
                            dataTitle.setValue(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        data.setValue(null);
                        dataTitle.setValue(null);
                    }
                });
        addDisposable(subscribe);
    }
}
