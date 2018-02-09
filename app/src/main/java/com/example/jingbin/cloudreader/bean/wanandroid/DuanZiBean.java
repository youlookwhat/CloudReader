package com.example.jingbin.cloudreader.bean.wanandroid;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.jingbin.cloudreader.BR;

/**
 * @author jingbin
 * @data 2018/2/9
 * @Description 段子Bean
 */

public class DuanZiBean extends BaseObservable {

    private String name;
    private String avatarUrl;
    private long createTime;
    private String content;
    private String categoryName;


    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        notifyPropertyChanged(BR.avatarUrl);
    }

    @Bindable
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
        notifyPropertyChanged(BR.createTime);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        notifyPropertyChanged(BR.categoryName);
    }
}
