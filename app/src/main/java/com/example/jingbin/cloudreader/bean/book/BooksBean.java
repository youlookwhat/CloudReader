package com.example.jingbin.cloudreader.bean.book;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.http.ParamNames;
import com.example.jingbin.cloudreader.BR;
import com.example.jingbin.cloudreader.bean.moviechild.ImagesBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/12/15.
 */

public class BooksBean extends BaseObservable implements Serializable{

    /**
     * rating : {"max":10,"numRaters":116375,"average":"7.9","min":0}
     * subtitle :
     * author : ["韩寒"]
     * pubdate : 2010-9
     * tags : [{"count":38955,"name":"韩寒","title":"韩寒"},{"count":16343,"name":"小说","title":"小说"},{"count":12037,"name":"我想和这个世界谈谈","title":"我想和这个世界谈谈"},{"count":10674,"name":"公路小说","title":"公路小说"},{"count":6392,"name":"1988","title":"1988"},{"count":5868,"name":"中国文学","title":"中国文学"},{"count":5260,"name":"中国","title":"中国"},{"count":4730,"name":"文学","title":"文学"}]
     * origin_title :
     * image : https://img5.doubanio.com/mpic/s4477716.jpg
     * binding : 平装
     * translator : []
     * catalog : 曹操与刘备的一生
     * pages : 215
     * images : {"small":"https://img5.doubanio.com/spic/s4477716.jpg","large":"https://img5.doubanio.com/lpic/s4477716.jpg","medium":"https://img5.doubanio.com/mpic/s4477716.jpg"}
     * alt : https://book.douban.com/subject/5275059/
     * id : 5275059
     * publisher : 国际文化出版公司
     * isbn10 : 751250098X
     * isbn13 : 9787512500983
     * title : 1988：我想和这个世界谈谈
     * url : https://api.douban.com/v2/book/5275059
     * alt_title :
     * author_intro : 韩寒 1982年9月23日出生。作家，赛车手。已出版作品：《三重门》、《零下一度》、《像少年啦飞驰》、《通稿2003》、《毒》、《韩寒五年文集》、《长安乱》、《就这么漂来漂去》、《一座城池》、《寒》、《光荣日》、《杂的文》或有其他。
     * summary : 系列主题：《我想和这个世界谈谈》
     * 目前在韩寒主编的杂志《独唱团》中首度连载，这是韩寒预谋已久的一个系列，也是国内首度实际尝试“公路小说”这一概念的第一本——《1988》。
     * 所谓“公路小说”就是以路途为载体反应人生观，现实观的小说。
     * 如果说一件真正的艺术品的面世具有任何重大意义的话，韩寒新书的出版将会在中国创造一个历史事件，文章开头“空气越来越差，我必须上路了。我开着一台1988年出厂的旅行车，在说不清是迷雾还是毒气的夜色里拐上了318国道。”用一部旅行车为载体，通过在路上的见闻、过去的回忆、扑朔迷离的人物关系等各种现实场景，以韩寒本人对路上所见、所闻引发自己的观点，这场真正的旅途在精神层面；如果说似乎逾越了部分法律和道德的界限，但出发点也仅仅是希望在另一侧找到信仰。韩寒是“叛逆的”，他“试图用能给世界一些新意的眼光来看世界。试图寻找令人信服的价值”。他认为这一切通过文学都可以实现，产生了要创造一种批判现有一切社会习俗的“新幻象”的念头——《1988》就此问世。
     * 目前“公路小说”的系列已经开始策划，韩寒的作品首当其冲，韩寒表示将会撰写三部作品与聚石文华联合打造“公路小说”这一品牌
     * price : 25.00元
     */
    @ParamNames("rating")
    private RatingBean rating;
    @ParamNames("subtitle")
    private String subtitle;
    @ParamNames("pubdate")
    private String pubdate;
    @ParamNames("origin_title")
    private String origin_title;
    @ParamNames("image")
    private String image;
    @ParamNames("binding")
    private String binding;
    @ParamNames("catalog")
    private String catalog;
    @ParamNames("pages")
    private String pages;
    @ParamNames("images")
    private ImagesBean images;
    @ParamNames("alt")
    private String alt;
    @ParamNames("id")
    private String id;
    @ParamNames("publisher")
    private String publisher;
    @ParamNames("isbn10")
    private String isbn10;
    @ParamNames("isbn13")
    private String isbn13;
    @ParamNames("title")
    private String title;
    @ParamNames("url")
    private String url;
    @ParamNames("alt_title")
    private String alt_title;
    @ParamNames("author_intro")
    private String author_intro;
    @ParamNames("summary")
    private String summary;
    @ParamNames("price")
    private String price;
    @ParamNames("author")
    private List<String> author;
    @ParamNames("tags")
    private List<TagsBean> tags;
    @ParamNames("translator")
    private List<String> translator;// 翻译


    public static class RatingBean extends BaseObservable implements Serializable{
        /**
         * max : 10
         * numRaters : 116375
         * average : 7.9
         * min : 0
         */
        @ParamNames("max")
        private int max;
        @ParamNames("numRaters")
        private int numRaters;
        @ParamNames("average")
        private String average;
        @ParamNames("min")
        private int min;

        @Bindable
        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
            notifyPropertyChanged(BR.max);
        }

        @Bindable
        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
            notifyPropertyChanged(BR.numRaters);
        }

        @Bindable
        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
            notifyPropertyChanged(BR.average);
        }

        @Bindable
        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
            notifyPropertyChanged(BR.min);
        }
    }


    public static class TagsBean extends BaseObservable implements Serializable{
        /**
         * count : 38955
         * name : 韩寒
         * title : 韩寒
         */
        @ParamNames("count")
        private int count;
        @ParamNames("name")
        private String name;
        @ParamNames("title")
        private String title;

        @Bindable
        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
            notifyPropertyChanged(BR.count);
        }

        @Bindable
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
            notifyPropertyChanged(BR.name);
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
            notifyPropertyChanged(BR.title);
        }
    }

    @Bindable
    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
        notifyPropertyChanged(BR.rating);
    }

    @Bindable
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        notifyPropertyChanged(BR.subtitle);
    }

    @Bindable
    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
        notifyPropertyChanged(BR.pubdate);
    }

    @Bindable
    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
        notifyPropertyChanged(BR.origin_title);
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
        notifyPropertyChanged(BR.binding);
    }

    @Bindable
    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
        notifyPropertyChanged(BR.catalog);
    }

    @Bindable
    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
        notifyPropertyChanged(BR.pages);
    }

    @Bindable
    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
        notifyPropertyChanged(BR.images);
    }

    @Bindable
    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
        notifyPropertyChanged(BR.alt);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
        notifyPropertyChanged(BR.publisher);
    }

    @Bindable
    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
        notifyPropertyChanged(BR.isbn10);
    }

    @Bindable
    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
        notifyPropertyChanged(BR.isbn13);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
        notifyPropertyChanged(BR.alt_title);
    }

    @Bindable
    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
        notifyPropertyChanged(BR.author_intro);
    }

    @Bindable
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
        notifyPropertyChanged(BR.summary);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
        notifyPropertyChanged(BR.author);
    }

    @Bindable
    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
        notifyPropertyChanged(BR.tags);
    }

    @Bindable
    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
        notifyPropertyChanged(BR.translator);
    }
}
