package com.example.jingbin.cloudreader.ui.one;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.MovieDetailAdapter;
import com.example.jingbin.cloudreader.bean.MovieDetailBean;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ActivityMovieDetailBinding;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.StringFormatUtil;
import com.example.jingbin.cloudreader.view.MyNestedScrollView;
import com.example.jingbin.cloudreader.view.statusbar.StatusBarUtil;
import com.example.jingbin.cloudreader.view.test.StatusBarUtils;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.jingbin.cloudreader.view.statusbar.StatusBarUtil.getStatusBarHeight;

/**
 * （已可以使用：OneMovieDetailActivity.java 替代）
 * 思路：
 * 1、透明状态栏（透明titlebar,使背景图上移）
 * 2、titlebar底部增加和背景一样的高斯模糊图，并上移图片的高度-titlebar的高度（为了使背景图的底部作为titlebar的背景）
 * 3、上移，通过scrollview拿到上移的高度，同时（在背景图的高度内） 调整titlebar的颜色使透明变为不透明，调整背景图的颜色，是不透明变为透明
 * 4、下拉，使上面反过来即可
 */
public class MovieDetailActivity extends AppCompatActivity {

    private int slidingDistance;
    private SubjectsBean subjectsBean;
    private ActivityMovieDetailBinding binding;
    private String TAG = "---MovieDetailActivity:";
    // 这个是高斯图背景的高度
    private int imageBgHeight;
    // 更多信息url
    private String mMoreUrl;
    // 影片name
    private String mMovieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        if (getIntent() != null) {
            subjectsBean = (SubjectsBean) getIntent().getSerializableExtra("bean");
        }

        initSlideShapeTheme();

        // 数据配置
        setTitleBar();
        setHeaderData(subjectsBean);

        loadMovieDetail();

    }

    private void loadMovieDetail() {
        // 初始化...
//        binding.include.tvOneCity.setText("制片国家/地区：");
//        binding.include.tvOneDay.setText("上映日期：");
//        binding.tvOneTitle.setText("");
        Subscription get = HttpClient.Builder.getDouBanService().getMovieDetail(subjectsBean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final MovieDetailBean movieDetailBean) {
                        // 上映日期
                        binding.include.tvOneDay.setText("上映日期：" + movieDetailBean.getYear());
                        // 制片国家
                        binding.include.tvOneCity.setText("制片国家/地区：" + StringFormatUtil.formatGenres(movieDetailBean.getCountries()));
                        binding.include.setMovieDetailBean(movieDetailBean);
                        binding.setMovieDetailBean(movieDetailBean);

                        mMoreUrl = movieDetailBean.getAlt();
                        mMovieName = movieDetailBean.getTitle();

                        transformData(movieDetailBean);
                    }
                });

    }

    /**
     * 异步线程转换数据
     */
    private void transformData(final MovieDetailBean movieDetailBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < movieDetailBean.getDirectors().size(); i++) {
                    movieDetailBean.getDirectors().get(i).setType("导演");
                }
                for (int i = 0; i < movieDetailBean.getCasts().size(); i++) {
                    movieDetailBean.getCasts().get(i).setType("演员");
                }

                MovieDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter(movieDetailBean);
                    }
                });
            }
        }).start();
    }

    /**
     * 设置导演&演员adapter
     */
    private void setAdapter(MovieDetailBean movieDetailBean) {
        binding.xrvCast.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.xrvCast.setLayoutManager(mLayoutManager);
        binding.xrvCast.setPullRefreshEnabled(false);
        binding.xrvCast.setLoadingMoreEnabled(false);
        // 需加，不然滑动不流畅
        binding.xrvCast.setNestedScrollingEnabled(false);
        binding.xrvCast.setHasFixedSize(false);

        MovieDetailAdapter mAdapter = new MovieDetailAdapter();
        mAdapter.addAll(movieDetailBean.getDirectors());
        mAdapter.addAll(movieDetailBean.getCasts());
        binding.xrvCast.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_detail, menu);
        return true;
    }

    private void setHeaderData(SubjectsBean positionData) {
        binding.include.setSubjectsBean(positionData);
        // 立即改变UI，防止闪屏
        binding.include.executePendingBindings();
    }

    /**
     * toolbar设置
     */
    private void setTitleBar() {
        setSupportActionBar(binding.titleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
        // 手动设置才有效果
        binding.titleToolBar.setTitleTextAppearance(this, R.style.ToolBar_Title);
        binding.titleToolBar.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);

        binding.titleToolBar.setTitle(subjectsBean.getTitle());
        binding.titleToolBar.setSubtitle(String.format("主演：%s", StringFormatUtil.formatName(subjectsBean.getCasts())));

        binding.titleToolBar.inflateMenu(R.menu.movie_detail);
        binding.titleToolBar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.actionbar_more));
        binding.titleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.titleToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionbar_more:// 更多信息
                        WebViewActivity.loadUrl(MovieDetailActivity.this,mMoreUrl,mMovieName);
                        break;
                }
                return false;
            }
        });
    }


    /**
     * 初始化滑动渐变
     */
    private void initSlideShapeTheme() {
        setImgHeaderBg();

        // toolbar 的高
        int toolbarHeight = binding.titleToolBar.getLayoutParams().height;
        Log.i(TAG, "toolbar height:" + toolbarHeight);
        final int headerBgHeight = toolbarHeight + getStatusBarHeight(this);
        Log.i(TAG, "headerBgHeight:" + headerBgHeight);

        // 使背景图向上移动到图片的最低端，保留（titlebar+statusbar）的高度
        ViewGroup.LayoutParams params = binding.ivTitleHeadBg.getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) binding.ivTitleHeadBg.getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

        binding.ivTitleHeadBg.setImageAlpha(0);
        StatusBarUtils.setTranslucentImageHeader(this, 0, binding.titleToolBar);

        // 上移背景图片，使空白状态栏消失(这样下方就空了状态栏的高度)
        if (binding.include.imgItemBg != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.include.imgItemBg.getLayoutParams();
            layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(this), 0, 0);
        }

        ViewGroup.LayoutParams imgItemBgparams = binding.include.imgItemBg.getLayoutParams();
        // 获得高斯图背景的高度
        imageBgHeight = imgItemBgparams.height;

        // 变色
        initScrollViewListener();

        initNewSlidingParams();
    }


    /**
     * 加载titlebar背景
     */
    private void setImgHeaderBg() {
        if (subjectsBean != null) {

            // 高斯模糊背景 原来 参数：12,5  23,4
            Glide.with(this).load(subjectsBean.getImages().getLarge())
                    .error(R.drawable.stackblur_default)
                    .bitmapTransform(new BlurTransformation(this, 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    binding.titleToolBar.setBackgroundColor(Color.TRANSPARENT);
                    binding.ivTitleHeadBg.setImageAlpha(0);
                    binding.ivTitleHeadBg.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(binding.ivTitleHeadBg);
        }
    }


    private void initScrollViewListener() {
        // 为了兼容23以下
        binding.nsvScrollview.setOnScrollChangeListener(new MyNestedScrollView.ScrollInterface() {
            @Override
            public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollChangeHeader(scrollY);
            }
        });
    }

    private void initNewSlidingParams() {
        int titleBarAndStatusHeight = (int) (CommonUtils.getDimens(R.dimen.nav_bar_height) + getStatusBarHeight(this));
        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (CommonUtils.getDimens(R.dimen.nav_bar_height_more));
    }

    /**
     * 根据页面滑动距离改变Header方法
     */
    private void scrollChangeHeader(int scrolledY) {

        DebugUtil.error("---scrolledY:  " + scrolledY);
        DebugUtil.error("-----slidingDistance: " + slidingDistance);

        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);

        DebugUtil.error("----alpha:  " + alpha);
        Drawable drawable = binding.ivTitleHeadBg.getDrawable();

        if (scrolledY <= slidingDistance) {
            // title部分的渐变
            drawable.mutate().setAlpha((int) (alpha * 255));
            binding.ivTitleHeadBg.setImageDrawable(drawable);
        } else {
            drawable.mutate().setAlpha(255);
            binding.ivTitleHeadBg.setImageDrawable(drawable);
        }
    }

    /**
     * @param context      activity
     * @param positionData bean
     * @param imageView    imageView
     */
    public static void start(Activity context, SubjectsBean positionData, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("bean", positionData);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
}
