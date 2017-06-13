package com.example.jingbin.cloudreader.bean.book;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.example.http.ParamNames;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/12/15.
 */

public class BookBean extends BaseObservable implements Serializable{

    /**
     * count : 1
     * start : 0
     * total : 200
     * books : [{"rating":{"max":10,"numRaters":116375,"average":"7.9","min":0},"subtitle":"","author":["韩寒"],"pubdate":"2010-9","tags":[{"count":38955,"name":"韩寒","title":"韩寒"},{"count":16343,"name":"小说","title":"小说"},{"count":12037,"name":"我想和这个世界谈谈","title":"我想和这个世界谈谈"},{"count":10674,"name":"公路小说","title":"公路小说"},{"count":6392,"name":"1988","title":"1988"},{"count":5868,"name":"中国文学","title":"中国文学"},{"count":5260,"name":"中国","title":"中国"},{"count":4730,"name":"文学","title":"文学"}],"origin_title":"","image":"https://img5.doubanio.com/mpic/s4477716.jpg","binding":"平装","translator":[],"catalog":"曹操与刘备的一生","pages":"215","images":{"small":"https://img5.doubanio.com/spic/s4477716.jpg","large":"https://img5.doubanio.com/lpic/s4477716.jpg","medium":"https://img5.doubanio.com/mpic/s4477716.jpg"},"alt":"https://book.douban.com/subject/5275059/","id":"5275059","publisher":"国际文化出版公司","isbn10":"751250098X","isbn13":"9787512500983","title":"1988：我想和这个世界谈谈","url":"https://api.douban.com/v2/book/5275059","alt_title":"","author_intro":"韩寒 1982年9月23日出生。作家，赛车手。已出版作品：《三重门》、《零下一度》、《像少年啦飞驰》、《通稿2003》、《毒》、《韩寒五年文集》、《长安乱》、《就这么漂来漂去》、《一座城池》、《寒》、《光荣日》、《杂的文》或有其他。","summary":"系列主题：《我想和这个世界谈谈》\n目前在韩寒主编的杂志《独唱团》中首度连载，这是韩寒预谋已久的一个系列，也是国内首度实际尝试\u201c公路小说\u201d这一概念的第一本\u2014\u2014《1988》。\n所谓\u201c公路小说\u201d就是以路途为载体反应人生观，现实观的小说。\n如果说一件真正的艺术品的面世具有任何重大意义的话，韩寒新书的出版将会在中国创造一个历史事件，文章开头\u201c空气越来越差，我必须上路了。我开着一台1988年出厂的旅行车，在说不清是迷雾还是毒气的夜色里拐上了318国道。\u201d用一部旅行车为载体，通过在路上的见闻、过去的回忆、扑朔迷离的人物关系等各种现实场景，以韩寒本人对路上所见、所闻引发自己的观点，这场真正的旅途在精神层面；如果说似乎逾越了部分法律和道德的界限，但出发点也仅仅是希望在另一侧找到信仰。韩寒是\u201c叛逆的\u201d，他\u201c试图用能给世界一些新意的眼光来看世界。试图寻找令人信服的价值\u201d。他认为这一切通过文学都可以实现，产生了要创造一种批判现有一切社会习俗的\u201c新幻象\u201d的念头\u2014\u2014《1988》就此问世。\n目前\u201c公路小说\u201d的系列已经开始策划，韩寒的作品首当其冲，韩寒表示将会撰写三部作品与聚石文华联合打造\u201c公路小说\u201d这一品牌","price":"25.00元"}]
     */
    @ParamNames("count")
    private int count;
    @ParamNames("start")
    private int start;
    @ParamNames("total")
    private int total;
    @ParamNames("books")
    private List<BooksBean> books;

    @Bindable
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }

    @Bindable
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
        notifyPropertyChanged(BR.start);
    }

    @Bindable
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        notifyPropertyChanged(BR.total);
    }

    @Bindable
    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
        notifyPropertyChanged(BR.books);
    }
}
