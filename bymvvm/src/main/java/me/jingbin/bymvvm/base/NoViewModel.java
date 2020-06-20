package me.jingbin.bymvvm.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author jingbin
 * @data 2018/12/22
 * @Description 没有viewModel的情况
 */

public class NoViewModel extends AndroidViewModel {

    public NoViewModel(@NonNull Application application) {
        super(application);
    }
}
