package com.example.jingbin.cloudreader.viewmodel.movie;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import me.jingbin.bymvvm.base.BaseViewModel;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.data.model.OneRepository;

/**
 * @author jingbin
 * @data 2017/12/15
 * @Description 依赖注入：依赖注入允许类在不构造它们的情况下定义它们的依赖关系。在运行时，另一个类负责提供这些依赖关系。
 * 我们推荐Google的Dagger 2库在Android应用程序中实现依赖注入。Dagger 2通过遍历依赖关系树来自动构造对象，并为依赖关系提供编译时间保证。
 */

public class DoubanTopViewModel extends BaseViewModel {

    private int mStart = 0;
    private int mCount = 21;
    private OneRepository oneRepo;

    public DoubanTopViewModel(@NonNull Application application) {
        super(application);
        this.oneRepo = new OneRepository();
    }

    public MutableLiveData<HotMovieBean> getHotMovie() {
        final MutableLiveData<HotMovieBean> data = new MutableLiveData<>();
        oneRepo.getMovieTop250(mStart, mCount, new OnMovieLoadListener() {
            @Override
            public void onSuccess(HotMovieBean hotMovieBean) {
                data.setValue(hotMovieBean);
            }

            @Override
            public void onFailure() {
                data.setValue(null);
            }
        });
        return data;
    }

    public int getStart() {
        return mStart;
    }

    public void handleNextStart() {
        mStart += mCount;
    }
}
