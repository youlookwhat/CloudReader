package com.example.jingbin.cloudreader.bean.moviechild;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.http.ParamNames;
import com.example.jingbin.cloudreader.BR;

import java.io.Serializable;

/**
 * Created by jingbin on 2016/11/25.
 */

public class RatingBean extends BaseObservable implements Serializable {
    /**
     * max : 10
     * average : 6.9
     * stars : 35
     * min : 0
     */
    @ParamNames("max")
    private int max;
    @ParamNames("average")
    private double average;
    @ParamNames("stars")
    private String stars;
    @ParamNames("min")
    private int min;

    @Bindable
    public int getMax() {
        return max;
    }

    @Bindable
    public double getAverage() {
        return average;
    }

    @Bindable
    public String getStars() {
        return stars;
    }

    @Bindable
    public int getMin() {
        return min;
    }

    public void setMax(int max) {
        this.max = max;
        notifyPropertyChanged(BR.max);
    }

    public void setAverage(double average) {
        this.average = average;
        notifyPropertyChanged(BR.average);
    }

    public void setStars(String stars) {
        this.stars = stars;
        notifyPropertyChanged(BR.stars);
    }

    public void setMin(int min) {
        this.min = min;
        notifyPropertyChanged(BR.min);
    }
}
