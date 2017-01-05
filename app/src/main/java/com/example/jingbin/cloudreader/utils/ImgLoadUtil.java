package com.example.jingbin.cloudreader.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jingbin.cloudreader.R;

import java.util.Random;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by jingbin on 2016/11/26.
 */

public class ImgLoadUtil {

    private static ImgLoadUtil instance;

    // 六图的随机图
    private static int[] homeSix = {R.drawable.home_six_1, R.drawable.home_six_2, R.drawable.home_six_3, R.drawable.home_six_4,
            R.drawable.home_six_5, R.drawable.home_six_6, R.drawable.home_six_7, R.drawable.home_six_8, R.drawable.home_six_9,
            R.drawable.home_six_10, R.drawable.home_six_11, R.drawable.home_six_12, R.drawable.home_six_13, R.drawable.home_six_14,
            R.drawable.home_six_15, R.drawable.home_six_16, R.drawable.home_six_17, R.drawable.home_six_18, R.drawable.home_six_19,
            R.drawable.home_six_20, R.drawable.home_six_21, R.drawable.home_six_22, R.drawable.home_six_23
    };

    // 2张图的随机图
    private static int[] homeTwo = {R.drawable.home_two_one, R.drawable.home_two_two, R.drawable.home_two_three, R.drawable.home_two_four,
            R.drawable.home_two_five, R.drawable.home_two_six, R.drawable.home_two_eleven, R.drawable.home_two_eight, R.drawable.home_two_nine};

    // 一张图的随机图
    private static int[] homeOne = {R.drawable.home_one_1, R.drawable.home_one_2, R.drawable.home_one_3,
            R.drawable.home_one_4, R.drawable.home_one_5, R.drawable.home_one_6, R.drawable.home_one_7,
            R.drawable.home_one_9, R.drawable.home_one_10, R.drawable.home_one_11, R.drawable.home_one_12
    };

    private ImgLoadUtil() {
    }

    public static ImgLoadUtil getInstance() {
        if (instance == null) {
            instance = new ImgLoadUtil();
        }
        return instance;
    }


    /**
     * 显示随机的图片(每日推荐)
     *
     * @param imgNumber    有几张图片要显示
     * @param position     第几张图片
     * @param itemPosition 是第几个item，如android是第一个
     * @param imageView    对应图片控件
     */
    public static void displayRandom(int imgNumber, int position, int itemPosition, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(getRandomPic(imgNumber, position, itemPosition))
                .placeholder(getDefaultPic(imgNumber, position))
                .error(getDefaultPic(imgNumber, position))
                .crossFade(1500)
                .into(imageView);
    }

    private static int getDefaultPic(int imgNumber, int position) {
        switch (imgNumber) {
            case 1:
                return R.drawable.img_two_bi_one;
            case 2:
                return R.drawable.img_four_bi_three;
            case 3:
                return R.drawable.img_one_bi_one;
        }
        return R.drawable.img_four_bi_three;
    }

    private static int getRandomPic(int imgNumber, int position, int itemPosition) {
        Random random = new Random();
        int randomInt = 0;
        switch (imgNumber) {
            case 1:
                randomInt = random.nextInt(homeOne.length);
                return homeOne[randomInt];
            case 2:
                randomInt = random.nextInt(homeTwo.length);
                return homeTwo[randomInt];
            case 3:
                randomInt = random.nextInt(homeSix.length);
                return homeSix[randomInt];
        }
        return homeOne[randomInt];
    }

//--------------------------------------

    /**
     * 用于干货item，将gif图转换为静态图
     */
    public static void displayGif(String url, ImageView imageView) {

        Glide.with(imageView.getContext()).load(url)
                .asBitmap()
                .placeholder(R.drawable.img_one_bi_one)
                .error(R.drawable.img_one_bi_one)
//                .skipMemoryCache(true) //跳过内存缓存
//                .crossFade(1000)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)// 缓存图片源文件（解决加载gif内存溢出问题）
//                .into(new GlideDrawableImageViewTarget(imageView, 1));
                .into(imageView);
    }

    /**
     * 书籍、妹子图、电影列表图
     * 默认图区别
     */
    public static void displayEspImage(String url, ImageView imageView, int type) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .placeholder(getDefaultPic(type))
                .error(getDefaultPic(type))
                .into(imageView);
    }

    private static int getDefaultPic(int type) {
        switch (type) {
            case 0:// 电影
                return R.drawable.img_default_movie;
            case 1:// 妹子
                return R.drawable.img_default_meizi;
            case 2:// 书籍
                return R.drawable.img_default_book;
        }
        return R.drawable.img_default_meizi;
    }

    /**
     * 显示高斯模糊效果（电影详情页）
     */
    private static void displayGaussian(Context context, String url, ImageView imageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊
        Glide.with(context)
                .load(url)
                .error(R.drawable.stackblur_default)
                .placeholder(R.drawable.stackblur_default)
                .crossFade(500)
                .bitmapTransform(new BlurTransformation(context, 23, 4))
                .into(imageView);
    }

    /**
     * 加载圆角图
     */
    public static void displayCircle(ImageView imageView, int imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    /**
     * 妹子，电影列表图
     *
     * @param defaultPicType 电影：0；妹子：1； 书籍：2
     */
    @BindingAdapter({"android:displayFadeImage", "android:defaultPicType"})
    public static void displayFadeImage(ImageView imageView, String url, int defaultPicType) {
        displayEspImage(url, imageView, defaultPicType);
    }

    /**
     * 电影详情页显示电影图片(等待被替换)（测试的还在，已可以弃用）
     * 没有加载中的图
     */
    @BindingAdapter("android:showImg")
    public static void showImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .error(getDefaultPic(0))
                .into(imageView);
    }

    /**
     * 电影列表图片
     */
    @BindingAdapter("android:showMovieImg")
    public static void showMovieImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
                .placeholder(getDefaultPic(0))
                .error(getDefaultPic(0))
                .into(imageView);
    }

    /**
     * 书籍列表图片
     */
    @BindingAdapter("android:showBookImg")
    public static void showBookImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .override((int) CommonUtils.getDimens(R.dimen.book_detail_width), (int) CommonUtils.getDimens(R.dimen.book_detail_height))
                .placeholder(getDefaultPic(2))
                .error(getDefaultPic(2))
                .into(imageView);
    }

    /**
     * 电影详情页显示高斯背景图
     */
    @BindingAdapter("android:showImgBg")
    public static void showImgBg(ImageView imageView, String url) {
        displayGaussian(imageView.getContext(), url, imageView);
    }
}
