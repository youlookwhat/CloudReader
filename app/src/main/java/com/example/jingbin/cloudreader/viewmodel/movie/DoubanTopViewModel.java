package com.example.jingbin.cloudreader.viewmodel.movie;

import android.arch.lifecycle.ViewModel;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.data.remote.OneRepository;

/**
 * @author jingbin
 * @data 2017/12/15
 * @Description 依赖注入：依赖注入允许类在不构造它们的情况下定义它们的依赖关系。在运行时，另一个类负责提供这些依赖关系。
 * 我们推荐Google的Dagger 2库在Android应用程序中实现依赖注入。Dagger 2通过遍历依赖关系树来自动构造对象，并为依赖关系提供编译时间保证。
 */

public class DoubanTopViewModel extends ViewModel {

    private OneRepository oneRepo;
    private OnMovieLoadListener loadListener;

    public void setOnMovieLoadListener(OnMovieLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public void onDestroy() {
        loadListener = null;
    }

    public DoubanTopViewModel() {
        this.oneRepo = new OneRepository();
    }

    public void getHotMovie(int start, int count) {
        oneRepo.getMovieTop250(start, count, new OnMovieLoadListener() {
            @Override
            public void onSuccess(HotMovieBean hotMovieBean) {
                if (loadListener != null) {
                    loadListener.onSuccess(hotMovieBean);
                }
            }

            @Override
            public void onFailure() {
                if (loadListener != null) {
                    loadListener.onFailure();
                }
            }
        });
    }
}
