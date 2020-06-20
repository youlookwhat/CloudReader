package com.example.jingbin.cloudreader.viewmodel.gank;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.http.RequestImpl;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jingbin.bymvvm.base.BaseViewModel;


/**
 * @author jingbin
 * @data 2018/1/17
 * @Description 福利页面ViewModel
 */

public class WelfareViewModel extends BaseViewModel {

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
        mModel.setData("Girl", "Girl",mPage, 20);
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
            public void addSubscription(Disposable disposable) {
                addDisposable(disposable);
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
        Observable
                .create(new ObservableOnSubscribe<ArrayList<ArrayList<String>>>() {
                    @Override
                    public void subscribe(ObservableEmitter<ArrayList<ArrayList<String>>> emitter) throws Exception {
                        imgList.clear();
                        imageTitleList.clear();
                        for (int i = 0; i < gankIoDataBean.getResults().size(); i++) {
                            imgList.add(gankIoDataBean.getResults().get(i).getUrl());
                            imageTitleList.add(gankIoDataBean.getResults().get(i).getDesc());
                        }
                        allList.clear();
                        allList.add(imgList);
                        allList.add(imageTitleList);
                        emitter.onNext(allList);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<ArrayList<String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ArrayList<ArrayList<String>> arrayLists) {
                        allListData.setValue(arrayLists);
                    }

                    @Override
                    public void onError(Throwable e) {
                        allListData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

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
