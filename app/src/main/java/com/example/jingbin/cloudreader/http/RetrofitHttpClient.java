package com.example.jingbin.cloudreader.http;

import com.example.jingbin.cloudreader.bean.FrontpageBean;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.bean.GankIoDayBean;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.bean.MovieDetailBean;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.bean.book.BookDetailBean;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by jingbin on 16/11/21.
 * 网络请求类（一个接口一个方法）
 */
public interface RetrofitHttpClient {

    /**
     * 首页轮播图
     */
    @GET("/frontpage/frontpage")
    Observable<FrontpageBean> getFrontpage();

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("/data/{type}/{pre_page}/{page}")
    Observable<GankIoDataBean> getGankIoData(@Path("type") String id, @Path("page") int page, @Path("pre_page") int pre_page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2015/08/06
     */
    @GET("/day/{year}/{month}/{day}")
    Observable<GankIoDayBean> getGankIoDay(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("/v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @GET("/v2/movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id);

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("/v2/movie/top250")
    Observable<HotMovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 根据tag获取图书
     *
     * @param tag   搜索关键字
     * @param count 一次请求的数目 最多100
     */

    @GET("/v2/book/search")
    Observable<BookBean> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    @GET("/v2/book/{id}")
    Observable<BookDetailBean> getBookDetail(@Path("id") String id);

    /**
     * 根据tag获取music
     * @param tag
     * @return
     */

//    @GET("/v2/music/search")
//    Observable<MusicRoot> searchMusicByTag(@Query("tag")String tag);

//    @GET("/v2/music/{id}")
//    Observable<Musics> getMusicDetail(@Path("id") String id);
}