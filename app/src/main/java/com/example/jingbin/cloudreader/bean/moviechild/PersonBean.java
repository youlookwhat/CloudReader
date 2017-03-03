package com.example.jingbin.cloudreader.bean.moviechild;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.http.ParamNames;
import com.example.jingbin.cloudreader.BR;

import java.io.Serializable;

/**
 * Created by jingbin on 2016/11/25.
 */

public class PersonBean extends BaseObservable implements Serializable {
    /**
     * alt : https://movie.douban.com/celebrity/1050059/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/1691.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1691.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1691.jpg"}
     * name : 范冰冰  or
     * name : 冯小刚
     * id : 1050059
     * type: 导演 或 演员
     */
    @ParamNames("alt")
    private String alt;

    // 导演或演员
    @ParamNames("type")
    private String type;

    @ParamNames("avatars")
    private ImagesBean avatars;
    @ParamNames("name")
    private String name;
    @ParamNames("id")
    private String id;

    @Bindable
    public String getAlt() {
        return alt;
    }

    @Bindable
    public ImagesBean getAvatars() {
        return avatars;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setAlt(String alt) {
        this.alt = alt;
        notifyPropertyChanged(BR.alt);
    }

    public void setAvatars(ImagesBean avatars) {
        this.avatars = avatars;
        notifyPropertyChanged(BR.avatars);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
}
