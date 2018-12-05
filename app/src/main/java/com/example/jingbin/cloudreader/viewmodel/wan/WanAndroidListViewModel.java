package com.example.jingbin.cloudreader.viewmodel.wan;

import android.arch.lifecycle.MutableLiveData;

import com.example.jingbin.cloudreader.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.cloudreader.http.HttpClient;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description 玩安卓ViewModel
 */

public class WanAndroidListViewModel extends BaseListViewModel {

    public MutableLiveData<WanAndroidBannerBean> getWanAndroidBanner() {
        final MutableLiveData<WanAndroidBannerBean> data = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getWanAndroidBanner()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WanAndroidBannerBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onNext(WanAndroidBannerBean wanAndroidBannerBean) {
                        ArrayList<String> mBannerImages = new ArrayList<String>();
                        ArrayList<String> mBannerTitles = new ArrayList<String>();
                        if (wanAndroidBannerBean != null
                                && wanAndroidBannerBean.getData() != null
                                && wanAndroidBannerBean.getData().size() > 0) {
                            List<WanAndroidBannerBean.DataBean> result = wanAndroidBannerBean.getData();
                            if (result != null && result.size() > 0) {
                                for (int i = 0; i < result.size(); i++) {
                                    //获取所有图片
                                    mBannerImages.add(result.get(i).getImagePath());
                                    mBannerTitles.add(result.get(i).getTitle());
                                }
                                wanAndroidBannerBean.setmBannerImages(mBannerImages);
                                wanAndroidBannerBean.setmBannerTitles(mBannerTitles);
                                data.setValue(wanAndroidBannerBean);
                            }
                        } else {
                            data.setValue(null);
                        }
                    }
                });
        return data;
    }

    /**
     * @param cid 体系id
     */
    public MutableLiveData<HomeListBean> getHomeList(Integer cid) {
        final MutableLiveData<HomeListBean> listData = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getHomeList(mPage, cid)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listData.setValue(null);
                        if (mPage > 0) {
                            mPage--;
                        }
                    }

                    @Override
                    public void onNext(HomeListBean bean) {
                        if (bean == null
                                || bean.getData() == null
                                || bean.getData().getDatas() == null
                                || bean.getData().getDatas().size() <= 0) {
                            listData.setValue(null);
                        } else {
                            listData.setValue(bean);
                        }
                    }
                });
        return listData;
    }

}
