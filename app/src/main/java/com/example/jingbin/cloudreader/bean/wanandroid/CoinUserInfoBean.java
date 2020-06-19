package com.example.jingbin.cloudreader.bean.wanandroid;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.jingbin.cloudreader.BR;

public class CoinUserInfoBean extends BaseObservable {

    /**
     * coinCount : 451
     * rank : 7
     * userId : 2
     * username : x**oyang
     */

    private int coinCount;
    private int rank;
    private int userId;
    // 有 **
    private String username;

    @Bindable
    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
        notifyPropertyChanged(BR.coinCount);
    }

    @Bindable
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
        notifyPropertyChanged(BR.rank);
    }

    @Bindable
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }
}
