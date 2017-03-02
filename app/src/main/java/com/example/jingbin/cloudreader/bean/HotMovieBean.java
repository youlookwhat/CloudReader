package com.example.jingbin.cloudreader.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.http.ParamNames;
import com.example.jingbin.cloudreader.BR;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/11/25.
 */

public class HotMovieBean extends BaseObservable implements Serializable {

    @ParamNames("count")
    private int count;
    @ParamNames("start")
    private int start;
    @ParamNames("total")
    private int total;
    @ParamNames("title")
    private String title;
    @ParamNames("subjects")
    private List<SubjectsBean> subjects;

    @Bindable
    public int getCount() {
        return count;
    }
    @Bindable
    public int getStart() {
        return start;
    }
    @Bindable
    public int getTotal() {
        return total;
    }
    @Bindable
    public String getTitle() {
        return title;
    }
    @Bindable
    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setStart(int start) {
        this.start = start;
        notifyPropertyChanged(BR.start);
    }

    public void setTotal(int total) {
        this.total = total;
        notifyPropertyChanged(BR.total);
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
        notifyPropertyChanged(BR.subjects);
    }
}
