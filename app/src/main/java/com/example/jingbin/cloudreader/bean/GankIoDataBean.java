package com.example.jingbin.cloudreader.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.http.ParamNames;
import com.example.jingbin.cloudreader.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/11/24.
 */

public class GankIoDataBean extends BaseObservable implements Serializable {

    @ParamNames("error")
    private boolean error;
    /**
     * _id : 5832662b421aa929b0f34e99
     * createdAt : 2016-11-21T11:12:43.567Z
     * desc :  深入Android渲染机制
     * publishedAt : 2016-11-24T11:40:53.615Z
     * source : web
     * type : Android
     * url : http://blog.csdn.net/ccj659/article/details/53219288
     * used : true
     * who : Chauncey
     */

    @ParamNames("results")
    private List<ResultBean> results;

    public static class ResultBean extends BaseObservable implements Serializable {

        @ParamNames("createdAt")
        private String createdAt;
        @ParamNames("desc")
        private String desc;
        @ParamNames("publishedAt")
        private String publishedAt;
        @ParamNames("source")
        private String source;
        @ParamNames("type")
        private String type;
        @ParamNames("url")
        private String url;
        @ParamNames("used")
        private boolean used;
        @ParamNames("who")
        private String who;
        @ParamNames("images")
        private List<String> images;

        @Bindable
        public String getCreatedAt() {
            return createdAt;
        }

        @Bindable
        public String getDesc() {
            return desc;
        }

        @Bindable
        public String getPublishedAt() {
            return publishedAt;
        }

        @Bindable
        public String getSource() {
            return source;
        }

        @Bindable
        public String getType() {
            return type;
        }

        @Bindable
        public String getUrl() {
            return url;
        }

        @Bindable
        public String getWho() {
            return who;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            notifyPropertyChanged(BR.createdAt);
        }

        public void setDesc(String desc) {
            this.desc = desc;
            notifyPropertyChanged(BR.desc);
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
            notifyPropertyChanged(BR.publishedAt);
        }

        public void setSource(String source) {
            this.source = source;
            notifyPropertyChanged(BR.source);
        }

        public void setType(String type) {
            this.type = type;
            notifyPropertyChanged(BR.type);
        }

        public void setUrl(String url) {
            this.url = url;
            notifyPropertyChanged(BR.url);
        }

        public void setWho(String who) {
            this.who = who;
            notifyPropertyChanged(BR.who);
        }

        public void setImages(List<String> images) {
            this.images = images;
            notifyPropertyChanged(BR.images);
        }

        @Bindable
        public List<String> getImages() {
            return images;
        }
    }

    public boolean isError() {
        return error;
    }

    @Bindable
    public List<ResultBean> getResults() {
        return results;
    }

    public void setResults(List<ResultBean> results) {
        this.results = results;
        notifyPropertyChanged(BR.results);
    }
}
