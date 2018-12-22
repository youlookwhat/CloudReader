package com.example.jingbin.cloudreader.viewmodel.movie;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.data.model.OneRepository;

/**
 * @author jingbin
 * @data 2018/12/22
 */

public class OneViewModel extends AndroidViewModel {

    private MutableLiveData<HotMovieBean> hotMovieBean;
    private OneRepository oneRepo;

    public OneViewModel(@NonNull Application application) {
        super(application);
        this.oneRepo = new OneRepository();
    }

    private void setHotMovieBean(MutableLiveData<HotMovieBean> hotMovieBean) {
        this.hotMovieBean = hotMovieBean;
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
