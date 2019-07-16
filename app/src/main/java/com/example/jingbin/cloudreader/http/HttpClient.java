package com.example.jingbin.cloudreader.http;

import com.example.http.HttpUtils;
import com.example.http.utils.BuildFactory;
import com.example.jingbin.cloudreader.bean.CollectUrlBean;
import com.example.jingbin.cloudreader.bean.ComingFilmBean;
import com.example.jingbin.cloudreader.bean.FilmDetailBasicBean;
import com.example.jingbin.cloudreader.bean.FilmDetailBean;
import com.example.jingbin.cloudreader.bean.FrontpageBean;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.bean.GankIoDayBean;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.bean.MovieDetailBean;
import com.example.jingbin.cloudreader.bean.MtimeFilmeBean;
import com.example.jingbin.cloudreader.bean.UpdateBean;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.bean.book.BookDetailBean;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.LoginBean;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.bean.wanandroid.QsbkListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.SearchTagBean;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WanAndroidBannerBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author jingbin
 * @date 16/11/21
 * 网络请求类（一个接口一个方法）
 */
public interface HttpClient {

    class Builder {
        public static HttpClient getDouBanService() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_DOUBAN);
        }

        public static HttpClient getTingServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_TING);
        }

        public static HttpClient getGankIOServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_GANKIO);
        }

        public static HttpClient getFirServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_FIR);
        }

        public static HttpClient getWanAndroidServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_WAN_ANDROID);
        }

        public static HttpClient getQSBKServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_QSBK);
        }

        public static HttpClient getMtimeServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_MTIME);
        }

        public static HttpClient getMtimeTicketServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_MTIME_TICKET);
        }
    }

    /**
     * 首页轮播图
     */
    @GET("ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14")
    Observable<FrontpageBean> getFrontpage();

    /**
     * 玩安卓轮播图
     */
    @GET("banner/json")
    Observable<WanAndroidBannerBean> getWanAndroidBanner();

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    Observable<GankIoDataBean> getGankIoData(@Path("type") String id, @Path("page") int page, @Path("pre_page") int pre_page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2015/08/06
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankIoDayBean> getGankIoDay(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();

    /**
     * 豆瓣即将上映电影
     */
    @GET("v2/movie/coming_soon")
    Flowable<HotMovieBean> getComingSoon(@Query("start") int start, @Query("count") int count);

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @GET("v2/movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id);

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("v2/movie/top250")
    Observable<HotMovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 根据tag获取图书
     *
     * @param tag   搜索关键字
     * @param count 一次请求的数目 最多100
     */
    @GET("v2/book/search")
    Observable<BookBean> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    @GET("v2/book/{id}")
    Observable<BookDetailBean> getBookDetail(@Path("id") String id);

    /**
     * @param id       应用id
     * @param apiToken token
     */
    @GET("latest/{id}")
    Observable<UpdateBean> checkUpdate(@Path("id") String id, @Query("api_token") String apiToken);

    /**
     * 糗事百科
     *
     * @param page 页码，从1开始
     */
    @GET("article/list/text")
    Observable<QsbkListBean> getQsbkList(@Query("page") int page);

    /**
     * 玩安卓，文章列表、知识体系下的文章
     *
     * @param page 页码，从0开始
     * @param cid  体系id
     */
    @GET("article/list/{page}/json")
    Observable<HomeListBean> getHomeList(@Path("page") int page, @Query("cid") Integer cid);

    /**
     * 玩安卓登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginBean> login(@Field("username") String username, @Field("password") String password);

    /**
     * 玩安卓注册
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<LoginBean> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 退出
     */
    @GET("user/logout/json")
    Flowable<LoginBean> logout();


    /**
     * 收藏文章列表
     *
     * @param page 页码
     */
    @GET("lg/collect/list/{page}/json")
    Flowable<HomeListBean> getCollectList(@Path("page") int page);

    /**
     * 收藏本站文章，errorCode返回0为成功
     *
     * @param id 文章id
     */
    @POST("lg/collect/{id}/json")
    Flowable<HomeListBean> collect(@Path("id") int id);

    /**
     * 取消收藏(首页文章列表)
     *
     * @param id 文章id
     */
    @POST("lg/uncollect_originId/{id}/json")
    Flowable<HomeListBean> unCollectOrigin(@Path("id") int id);

    /**
     * 取消收藏，我的收藏页面（该页面包含自己录入的内容）
     *
     * @param id       文章id
     * @param originId 列表页下发，无则为-1
     *                 (代表的是你收藏之前的那篇文章本身的id；
     *                 但是收藏支持主动添加，这种情况下，没有originId则为-1)
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Flowable<HomeListBean> unCollect(@Path("id") int id, @Field("originId") int originId);

    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<TreeBean> getTree();

    /**
     * 收藏网址
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/addtool/json")
    Flowable<HomeListBean> collectUrl(@Field("name") String name, @Field("link") String link);

    /**
     * 编辑收藏网站
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/updatetool/json")
    Flowable<HomeListBean> updateUrl(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    /**
     * 删除收藏网站
     *
     * @param id 收藏网址id
     */
    @FormUrlEncoded
    @POST("lg/collect/deletetool/json")
    Flowable<HomeListBean> unCollectUrl(@Field("id") int id);

    /**
     * 收藏网站列表
     */
    @GET("lg/collect/usertools/json")
    Flowable<CollectUrlBean> getCollectUrlList();

    /**
     * 导航数据
     */
    @GET("navi/json")
    Flowable<NaviJsonBean> getNaviJson();

    /**
     * 玩安卓 搜索
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    Flowable<HomeListBean> searchWan(@Path("page") int page, @Field("k") String k);

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    Flowable<SearchTagBean> getHotkey();

    /**
     * 干货集中营 搜索
     */
    @GET("search/query/{keyWord}/category/{type}/count/20/page/{p}")
    Flowable<GankIoDataBean> searchGank(@Path("p") int p, @Path("type") String type, @Path("keyWord") String keyWord);

    /**
     * 时光网热映电影
     */
    @GET("Showtime/LocationMovies.api?locationId=561")
    Observable<MtimeFilmeBean> getHotFilm();

    /**
     * 时光网即将上映电影
     */
    @GET("Movie/MovieComingNew.api?locationId=561")
    Observable<ComingFilmBean> getComingFilm();

    /**
     * 获取电影详情
     * FilmDetailBasicBean 561为武汉地区
     *
     * @param movieId 电影bean里的id
     */
    @GET("movie/detail.api?locationId=561")
    Observable<FilmDetailBean> getFilmDetail(@Query("movieId") int movieId);

    /**
     * 玩安卓，首页第二tab 项目；列表
     *
     * @param page 页码，从0开始
     */
    @GET("article/listproject/{page}/json")
    Observable<HomeListBean> getProjectList(@Path("page") int page);

}