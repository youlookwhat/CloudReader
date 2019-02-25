package com.example.jingbin.cloudreader.viewmodel.movie;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import com.example.http.HttpUtils;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.data.model.GankOtherModel;
import com.example.jingbin.cloudreader.data.model.OneRepository;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.http.RequestImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jingbin
 * @data 2018/12/23
 */

public class BookListViewModel extends AndroidViewModel {

    // 开始请求的角标
    private int mStart = 0;
    // 一次请求的数量
    private int mCount = 18;

    public final ObservableField<String> bookType = new ObservableField<>();

    public BookListViewModel(@NonNull Application application) {
        super(application);
    }

    public void setStart(int mStart) {
        this.mStart = mStart;
    }

    public int getStart() {
        return mStart;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<BookBean> getBook() {
        final MutableLiveData<BookBean> data = new MutableLiveData<>();
        HttpClient.Builder.getDouBanService().getBook(bookType.get(), mStart, mCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BookBean>() {
                    @Override
                    public void accept(BookBean bookBean) throws Exception {
                        data.setValue(bookBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public void handleNextStart() {
        mStart += mCount;
    }

    @BindingAdapter("android:textSelection")
    public static void textSelection(AppCompatEditText tv, ObservableField<String> value) {
        if (!TextUtils.isEmpty(tv.getText())) {
//            tv.setText(value.get());
            // Set the cursor to the end of the text
            tv.setSelection(tv.getText().length());
        }
    }
}
