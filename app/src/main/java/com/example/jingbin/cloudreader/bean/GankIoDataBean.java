package com.example.jingbin.cloudreader.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.jingbin.cloudreader.BR;

import java.io.Serializable;
import java.util.List;

import me.jingbin.bymvvm.http.ParamNames;

/**
 * Created by jingbin on 2016/11/24.
 */

public class GankIoDataBean extends BaseObservable implements Serializable {

    @ParamNames("error")
    private int status;
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

    @ParamNames("data")
    private List<ResultBean> data;

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
        @ParamNames("category")
        private String category;
        @ParamNames("url")
        private String url;
        @ParamNames("used")
        private boolean used;
        @ParamNames("author")
        private String author;
        @ParamNames("images")
        private List<String> images;
        @ParamNames("image")
        private String image;
        @ParamNames("title")
        private String title;

        @Bindable
        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
            notifyPropertyChanged(BR.image);
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
            notifyPropertyChanged(BR.title);
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

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
        public String getAuthor() {
            return author;
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

        public void setAuthor(String author) {
            this.author = author;
            notifyPropertyChanged(BR.author);
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

    public int getStatus() {
        return status;
    }

    @Bindable
    public List<ResultBean> getResults() {
        return data;
    }

    public void setResults(List<ResultBean> results) {
        this.data = results;
        notifyPropertyChanged(BR.results);
    }
}
