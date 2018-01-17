package com.example.jingbin.cloudreader.viewmodel.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.data.remote.OneRepository;

/**
 * @author jingbin
 * @data 2017/12/15
 * @Description 依赖注入：依赖注入允许类在不构造它们的情况下定义它们的依赖关系。在运行时，另一个类负责提供这些依赖关系。
 * 我们推荐Google的Dagger 2库在Android应用程序中实现依赖注入。Dagger 2通过遍历依赖关系树来自动构造对象，并为依赖关系提供编译时间保证。
 */

public class OneViewModel extends ViewModel {

    private MutableLiveData<HotMovieBean> hotMovieBean;
    private OneRepository oneRepo;

    private void setHotMovieBean(MutableLiveData<HotMovieBean> hotMovieBean) {
        this.hotMovieBean = hotMovieBean;
    }

    /**
     * UserRepository parameter is provided by Dagger 2
     * public 必须，不然报错
     */
    public OneViewModel() {
        this.oneRepo = new OneRepository();
    }

    public LiveData<HotMovieBean> getHotMovie() {
        if (hotMovieBean == null
                || hotMovieBean.getValue() == null
                || hotMovieBean.getValue().getSubjects() == null
                || hotMovieBean.getValue().getSubjects().size() == 0) {
            hotMovieBean = new MutableLiveData<>();
            return loadHotMovie();
        } else {
            return hotMovieBean;
        }
    }

    private MutableLiveData<HotMovieBean> loadHotMovie() {
        MutableLiveData<HotMovieBean> hotMovie = oneRepo.getHotMovie();
        setHotMovieBean(hotMovie);
        return hotMovie;
    }
}
