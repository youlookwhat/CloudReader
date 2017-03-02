package com.example.jingbin.cloudreader.bean.moviechild;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.http.ParamNames;
import com.example.jingbin.cloudreader.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/11/25.
 * 豆瓣热映item详情
 */

public class SubjectsBean extends BaseObservable implements Serializable{
    /**
     * rating : {"max":10,"average":6.9,"stars":"35","min":0}
     * genres : ["剧情","喜剧"]
     * title : 我不是潘金莲
     * casts : [{"alt":"https://movie.douban.com/celebrity/1050059/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1691.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1691.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1691.jpg"},"name":"范冰冰","id":"1050059"},{"alt":"https://movie.douban.com/celebrity/1274274/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/39703.jpg","large":"https://img3.doubanio.com/img/celebrity/large/39703.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/39703.jpg"},"name":"郭涛","id":"1274274"},{"alt":"https://movie.douban.com/celebrity/1324043/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/58870.jpg","large":"https://img3.doubanio.com/img/celebrity/large/58870.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/58870.jpg"},"name":"大鹏","id":"1324043"}]
     * （多少人评分）collect_count : 56325
     * （原名）original_title : 我不是潘金莲
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1274255/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/45667.jpg","large":"https://img1.doubanio.com/img/celebrity/large/45667.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/45667.jpg"},"name":"冯小刚","id":"1274255"}]
     * year : 2016
     * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2378133884.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2378133884.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2378133884.jpg"}
     * （更多信息）alt : https://movie.douban.com/subject/26630781/
     * id : 26630781
     */
    @ParamNames("rating")
    private RatingBean rating;
    @ParamNames("title")
    private String title;
    @ParamNames("collect_count")
    private int collect_count;
    @ParamNames("original_title")
    private String original_title;
    @ParamNames("subtype")
    private String subtype;
    @ParamNames("year")
    private String year;
    @ParamNames("images")
    private ImagesBean images;
    @ParamNames("alt")
    private String alt;
    @ParamNames("id")
    private String id;
    @ParamNames("genres")
    private List<String> genres;
    @ParamNames("casts")
    private List<PersonBean> casts;
    @ParamNames("directors")
    private List<PersonBean> directors;


    @Bindable
    public RatingBean getRating() {
        return rating;
    }

    @Bindable
    public String getTitle() {
        return this.title;
    }

    @Bindable
    public int getCollect_count() {
        return collect_count;
    }

    @Bindable
    public String getOriginal_title() {
        return original_title;
    }

    @Bindable
    public String getSubtype() {
        return subtype;
    }

    @Bindable
    public String getYear() {
        return year;
    }

    @Bindable
    public ImagesBean getImages() {
        return images;
    }

    @Bindable
    public String getAlt() {
        return alt;
    }

    @Bindable
    public String getId() {
        return id;
    }

    @Bindable
    public List<String> getGenres() {
        return genres;
    }

    @Bindable
    public List<PersonBean> getCasts() {
        return casts;
    }

    @Bindable
    public List<PersonBean> getDirectors() {
        return directors;
    }


    public void setRating(RatingBean rating) {
        this.rating = rating;
        notifyPropertyChanged(BR.rating);
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
        notifyPropertyChanged(BR.collect_count);
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
        notifyPropertyChanged(BR.original_title);
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
        notifyPropertyChanged(BR.subtype);
    }

    public void setYear(String year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }

    public void setImages(ImagesBean images) {
        this.images = images;
        notifyPropertyChanged(BR.images);
    }

    public void setAlt(String alt) {
        this.alt = alt;
        notifyPropertyChanged(BR.alt);
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
        notifyPropertyChanged(BR.genres);
    }

    public void setCasts(List<PersonBean> casts) {
        this.casts = casts;
        notifyPropertyChanged(BR.casts);
    }

    public void setDirectors(List<PersonBean> directors) {
        this.directors = directors;
        notifyPropertyChanged(BR.directors);
    }

    @Override
    public String toString() {
        return "SubjectsBean{" +
                "directors=" + directors +
                ", casts=" + casts +
                ", genres=" + genres +
                ", id='" + id + '\'' +
                ", alt='" + alt + '\'' +
                ", images=" + images +
                ", year='" + year + '\'' +
                ", subtype='" + subtype + '\'' +
                ", original_title='" + original_title + '\'' +
                ", collect_count=" + collect_count +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                '}';
    }
}
