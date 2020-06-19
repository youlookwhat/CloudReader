package com.example.jingbin.cloudreader.bean.wanandroid;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.jingbin.cloudreader.BR;

public class BaseResultBean<T> extends BaseObservable {

    private T data;
    private int errorCode;
    private String errorMsg;

    @Bindable
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        notifyPropertyChanged(BR.data);
    }

    @Bindable
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        notifyPropertyChanged(BR.errorCode);
    }

    @Bindable
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        notifyPropertyChanged(BR.errorMsg);
    }
}
