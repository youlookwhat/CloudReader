package com.example.jingbin.cloudreader.viewmodel.gank;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.bean.CollectUrlBean;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/1/17
 * @Description 福利页面ViewModel
 */

public class WelfareViewModel extends AndroidViewModel {

    private final GankOtherModel mModel;
    private int mPage = 1;

    /**
     * 图片url集合
     */
    private ArrayList<String> imgList = new ArrayList<>();
    /**
     * 图片标题集合，用于保存图片时使用
     */
    private ArrayList<String> imageTitleList = new ArrayList<>();
    /**
     * 传递给Activity数据集合
     */
    private ArrayList<ArrayList<String>> allList = new ArrayList<>();
    private final MutableLiveData<ArrayList<ArrayList<String>>> allListData = new MutableLiveData<>();

    public WelfareViewModel(@NonNull Application application) {
        super(application);
        mModel = new GankOtherModel();
    }

    public MutableLiveData<ArrayList<ArrayList<String>>> getImageAndTitle() {
        return allListData;
    }

    public MutableLiveData<GankIoDataBean> loadWelfareData() {
        final MutableLiveData<GankIoDataBean> data = new MutableLiveData<>();
        mModel.setData("福利", mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                handleImageList(gankIoDataBean);
                data.setValue(gankIoDataBean);
            }

            @Override
            public void loadFailed() {
                if (mPage > 1) {
                    mPage--;
                }
                data.setValue(null);
            }

            @Override
            public void addSubscription(Subscription subscription) {
            }
        });
        return data;
    }

    /**
     * 异步处理用于图片详情显示的数据
     *
     * @param gankIoDataBean 原数据
     */
    private void handleImageList(GankIoDataBean gankIoDataBean) {
        Subscription subscribe = Observable.just(gankIoDataBean)
                .map(new Func1<GankIoDataBean, ArrayList<ArrayList<String>>>() {
                    @Override
                    public ArrayList<ArrayList<String>> call(GankIoDataBean gankIoDataBean) {
                        imgList.clear();
                        imageTitleList.clear();
                        for (int i = 0; i < gankIoDataBean.getResults().size(); i++) {
                            imgList.add(gankIoDataBean.getResults().get(i).getUrl());
                            imageTitleList.add(gankIoDataBean.getResults().get(i).getDesc());
                        }
                        allList.clear();
                        allList.add(imgList);
                        allList.add(imageTitleList);
                        return allList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<ArrayList<String>>>() {
                    @Override
                    public void call(ArrayList<ArrayList<String>> arrayLists) {
                        allListData.setValue(arrayLists);
                    }
                });
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public void onDestroy() {
        imgList.clear();
        imgList = null;
        imageTitleList.clear();
        imageTitleList = null;
        allList.clear();
        allList = null;
    }
}
