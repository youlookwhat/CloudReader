package com.example.jingbin.yunyue.http;

import com.example.jingbin.yunyue.bean.FrontpageBean;
import com.example.jingbin.yunyue.bean.GankIoDataBean;
import com.example.jingbin.yunyue.bean.GankIoDayBean;
import com.example.jingbin.yunyue.bean.HotMovieBean;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by hsl on 2015/6/24.
 * 网络请求类（一个接口一个方法）
 */
public interface RetrofitHttpClient {

//    @FormUrlEncoded
//    @POST("/services/application")
//    void submitServicesForm(@Header("a") String a, @Field("item_pk") String item_pk, @Field("type") Integer type, @Field("userkey") String userkey,
//                            @Field("real_name") String real_name, @Field("sex") String sex, @Field("age") Integer age, @Field("place") String place,
//                            @Field("diseased_state_id") String diseased_state_id,
//                            @Field("mobile") String mobile, @Field("apply_place") String apply_place, @Field("reason") String reason, Callback<String> response);


//    /*抗癌广场-获取分类标签*/
//    @GET("/tags")
//    void getCommunityTags(@Header("a") String a, Callback<List<TagBean>> reponse);


    /*修改我的病历本的"其他资料"的详情*/
//    @FormUrlEncoded
//    @PUT("/p/medical_records/{m_r_id}/items/{id}")
//    void modifyIllNoteOthersDetail(@Header("a") String a, @Path("id") int id, @Field("userkey")
//            String userkey, @Path("m_r_id") String medical_record_item_id, @Field("record_date") String
//                                           record_date, @Field("content") String content, @Field
//                                           ("image_ids[]") List<Integer>
//                                           image_ids, Callback<ErrorBean> response);


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
     * 首页轮播图
     */
    @GET("/frontpage/frontpage")
    Observable<FrontpageBean> getFrontpage();

}