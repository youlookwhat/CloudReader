package com.example.jingbin.cloudreader.viewmodel.movie;

import com.example.jingbin.cloudreader.bean.HotMovieBean;

/**
 * @author jingbin
 * @data 2017/12/26
 * @Description
 */

public interface OnMovieLoadListener {

    void onSuccess(HotMovieBean bean);

    void onFailure();
}
