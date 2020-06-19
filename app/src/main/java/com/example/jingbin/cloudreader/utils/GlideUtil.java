package com.example.jingbin.cloudreader.utils;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.jingbin.cloudreader.R;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * @author jingbin
 * @date 2019/04/29
 */

public class GlideUtil {

    private static GlideUtil instance;

    private GlideUtil() {
    }

    public static GlideUtil getInstance() {
        if (instance == null) {
            instance = new GlideUtil();
        }
        return instance;
    }


    /**
     * 显示随机的图片(每日推荐)
     *
     * @param imgNumber 有几张图片要显示,对应默认图
     * @param imageUrl  显示图片的url
     * @param imageView 对应图片控件
     */
    public static void displayRandom(int imgNumber, String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(getMusicDefaultPic(imgNumber))
                .error(getMusicDefaultPic(imgNumber))
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(imageView);
    }

    private static int getMusicDefaultPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                return R.drawable.img_two_bi_one;
            case 2:
                return R.drawable.img_four_bi_three;
            case 3:
                return R.drawable.img_one_bi_one;
            case 4:
                return R.drawable.shape_bg_loading;
            default:
                break;
        }
        return R.drawable.img_four_bi_three;
    }

//--------------------------------------

    /**
     * 用于干货item，将gif图转换为静态图
     */
    public static void displayGif(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
//                .asBitmap()
                .load(url)
                .override(DensityUtil.dip2px(imageView.getContext(), 60), DensityUtil.dip2px(imageView.getContext(), 80))
                .placeholder(R.drawable.shape_bg_loading)
                .error(R.drawable.shape_bg_loading)
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
                .transition(DrawableTransitionOptions.withCrossFade(500))
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
            case 3:
                return R.drawable.shape_bg_loading;
            default:
                break;
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
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.stackblur_default)
                .placeholder(R.drawable.stackblur_default)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .transform(new BlurTransformation(50, 8))
                .into(imageView);
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    @BindingAdapter("android:displayCircle")
    public static void displayCircle(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .error(R.drawable.ic_avatar_default)
                .transform(new CircleCrop())
//                .apply(bitmapTransform(new CircleCrop()))
//                .transform(new GlideCircleTransform())
//                .transform(new RoundedCorners(20))
//                .transform(new CenterCrop(), new RoundedCorners(20))
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
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .error(getDefaultPic(3))
                .into(imageView);
    }

    /**
     * 电影列表图片
     */
    @BindingAdapter("android:showMovieImg")
    public static void showMovieImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
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
                .transition(DrawableTransitionOptions.withCrossFade(500))
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


    /**
     * 热门电影头部图片
     */
    @BindingAdapter({"android:displayRandom", "android:imgType"})
    public static void displayRandom(ImageView imageView, int imageUrl, int imgType) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(getMusicDefaultPic(imgType))
                .error(getMusicDefaultPic(imgType))
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(imageView);
    }

    /**
     * 加载固定宽高图片
     */
    @BindingAdapter({"android:imageUrl", "android:imageWidthDp", "android:imageHeightDp"})
    public static void imageUrl(ImageView imageView, String url, int imageWidthDp, int imageHeightDp) {
        Glide.with(imageView.getContext())
                .load(url)
                .override(DensityUtil.dip2px(imageView.getContext(), imageWidthDp), DensityUtil.dip2px(imageView.getContext(), imageHeightDp))
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(getMusicDefaultPic(4))
                .centerCrop()
                .error(getDefaultPic(0))
                .into(imageView);
    }
}
