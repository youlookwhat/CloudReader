package com.example.jingbin.cloudreader.base;

import android.annotation.SuppressLint;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.ArcMotion;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.databinding.BaseHeaderTitleBarBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.view.CustomChangeBounds;
import com.example.jingbin.cloudreader.view.MyNestedScrollView;
import me.jingbin.bymvvm.utils.StatusBarUtil;
import com.example.jingbin.cloudreader.view.statue.StatusBarUtils;

import java.lang.reflect.Method;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * Created by jingbin on 16/12/12.
 * 电影
 * 书籍等详情页
 * 根布局：fitsSystemWindows 惹的祸
 */
public abstract class BaseHeaderActivity<HV extends ViewDataBinding, SV extends ViewDataBinding> extends AppCompatActivity {

    // 标题
    protected BaseHeaderTitleBarBinding bindingTitleView;
    // 内容布局头部
    protected HV bindingHeaderView;
    // 内容布局view
    protected SV bindingContentView;
    private View loadingView;
    private View errorView;
    // 滑动多少距离后标题不透明
    private int slidingDistance;
    // 这个是高斯图背景的高度
    private int imageBgHeight;
    // 清除动画，防止内存泄漏
    private CustomChangeBounds changeBounds;
    private AnimationDrawable mAnimationDrawable;
    private CompositeDisposable mCompositeDisposable;

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View ll = getLayoutInflater().inflate(R.layout.activity_header_base, null);

        // 内容
        bindingContentView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        // 头部
        bindingHeaderView = DataBindingUtil.inflate(getLayoutInflater(), setHeaderLayout(), null, false);
        // 标题
        bindingTitleView = DataBindingUtil.inflate(getLayoutInflater(), R.layout.base_header_title_bar, null, false);

        // title (如自定义很强可以拿出去)
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bindingTitleView.getRoot().setLayoutParams(titleParams);
        RelativeLayout mTitleContainer = (RelativeLayout) ll.findViewById(R.id.title_container);
        mTitleContainer.addView(bindingTitleView.getRoot());

        // header
        RelativeLayout.LayoutParams headerParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bindingHeaderView.getRoot().setLayoutParams(headerParams);
        RelativeLayout mHeaderContainer = (RelativeLayout) ll.findViewById(R.id.header_container);
        mHeaderContainer.addView(bindingHeaderView.getRoot());

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingContentView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = ll.findViewById(R.id.container);
        mContainer.addView(bindingContentView.getRoot());
        getWindow().setContentView(ll);

        loadingView = ((ViewStub) getView(R.id.vs_loading)).inflate();

        // 设置自定义元素共享切换动画
//        setMotion(setHeaderPicView(), false);

        // 初始化滑动渐变
//        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());

        // 设置toolbar
        setToolBar();

        ImageView img = loadingView.findViewById(R.id.img_progress);
        // 加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        bindingContentView.getRoot().setVisibility(View.GONE);
    }


    /**
     * a. 布局高斯透明图 header布局
     */
    protected abstract int setHeaderLayout();

    /**
     * b. 设置头部header高斯背景 imgUrl
     */
    protected abstract String setHeaderImgUrl();

    /**
     * c. 设置头部header布局 高斯背景ImageView控件
     */
    protected abstract ImageView setHeaderImageView();

    /**
     * 设置头部header布局 左侧的图片(需要设置曲线路径切换动画时重写)
     */
    protected ImageView setHeaderPicView() {
        return new ImageView(this);
    }

    /**
     * 1. 标题
     */
    @Override
    public void setTitle(CharSequence text) {
        bindingTitleView.tbBaseTitle.setTitle(text);
    }

    /**
     * 2. 副标题
     */
    protected void setSubTitle(CharSequence text) {
        bindingTitleView.tbBaseTitle.setSubtitle(text);
    }

    /**
     * 3. toolbar 单击"更多信息"
     */
    protected void setTitleClickMore() {
    }

    /**
     * 设置自定义 Shared Element切换动画
     * 默认不开启曲线路径切换动画，
     * 开启需要重写setHeaderPicView()，和调用此方法并将isShow值设为true
     *
     * @param imageView 共享的图片
     * @param isShow    是否显示曲线动画
     */
    protected void setMotion(ImageView imageView, boolean isShow) {
        if (!isShow) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 定义ArcMotion
            ArcMotion arcMotion = new ArcMotion();
            // 设置曲线幅度
            arcMotion.setMinimumHorizontalAngle(10f);
            arcMotion.setMinimumVerticalAngle(10f);
            //插值器，控制速度
            Interpolator interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

            // 实例化自定义的ChangeBounds
            changeBounds = new CustomChangeBounds();
            changeBounds.setPathMotion(arcMotion);
            changeBounds.setInterpolator(interpolator);
            changeBounds.addTarget(imageView);
            // 将切换动画应用到当前的Activity的进入和返回
            getWindow().setSharedElementEnterTransition(changeBounds);
            getWindow().setSharedElementReturnTransition(changeBounds);
        }
    }

    /**
     * 设置toolbar
     */
    protected void setToolBar() {
        setSupportActionBar(bindingTitleView.tbBaseTitle);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
        bindingTitleView.tbBaseTitle.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.actionbar_more));
        bindingTitleView.tbBaseTitle.setNavigationOnClickListener(v -> {
            supportFinishAfterTransition();
        });
        bindingTitleView.tbBaseTitle.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.actionbar_more) {
                // 更多信息
                setTitleClickMore();
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_header_menu, menu);
        return true;
    }

    /**
     * 显示popu内的图片
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    /**
     * *** 初始化滑动渐变 一定要实现 ******
     *
     * @param imgUrl    header头部的高斯背景imageUrl
     * @param mHeaderBg header头部高斯背景ImageView控件
     */
    protected void initSlideShapeTheme(String imgUrl, ImageView mHeaderBg) {
        setImgHeaderBg(imgUrl);

        // toolbar 的高
        int toolbarHeight = bindingTitleView.tbBaseTitle.getLayoutParams().height;
        final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(this);

        // 使背景图向上移动到图片的最低端，保留（titlebar+statusbar）的高度
        ViewGroup.LayoutParams params = bindingTitleView.ivBaseTitlebarBg.getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) bindingTitleView.ivBaseTitlebarBg.getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

        bindingTitleView.ivBaseTitlebarBg.setImageAlpha(0);
        StatusBarUtils.setTranslucentImageHeader(this, 0, bindingTitleView.tbBaseTitle);

        // 上移背景图片，使空白状态栏消失(这样下方就空了状态栏的高度)
        if (mHeaderBg != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mHeaderBg.getLayoutParams();
            layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(this), 0, 0);

            ViewGroup.LayoutParams imgItemBgparams = mHeaderBg.getLayoutParams();
            // 获得高斯图背景的高度
            imageBgHeight = imgItemBgparams.height;
        }

        // 变色
        initScrollViewListener();
        initNewSlidingParams();
    }


    /**
     * 加载titlebar背景
     */
    private void setImgHeaderBg(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {

            // 高斯模糊背景 原来 参数：12,5  23,4
            Glide.with(this).load(imgUrl)
                    .error(R.drawable.stackblur_default)
                    .transform(new BlurTransformation(40, 8))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            bindingTitleView.tbBaseTitle.setBackgroundColor(Color.TRANSPARENT);
                            bindingTitleView.ivBaseTitlebarBg.setImageAlpha(0);
                            bindingTitleView.ivBaseTitlebarBg.setVisibility(View.VISIBLE);
                            return false;
                        }
                    }).into(bindingTitleView.ivBaseTitlebarBg);
        }
    }


    private void initScrollViewListener() {
        // 为了兼容23以下
        ((MyNestedScrollView) findViewById(R.id.mns_base)).setOnScrollChangeListener(new MyNestedScrollView.ScrollInterface() {
            @Override
            public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollChangeHeader(scrollY);
            }
        });
    }

    private void initNewSlidingParams() {
        int titleBarAndStatusHeight = (int) (CommonUtils.getDimens(R.dimen.nav_bar_height) + StatusBarUtil.getStatusBarHeight(this));
        // 减掉后，没到顶部就不透明了
        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (CommonUtils.getDimens(R.dimen.base_header_activity_slide_more));
    }

    /**
     * 根据页面滑动距离改变Header方法
     */
    private void scrollChangeHeader(int scrolledY) {
        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);

        Drawable drawable = bindingTitleView.ivBaseTitlebarBg.getDrawable();

        if (drawable == null) {
            return;
        }
        if (scrolledY <= slidingDistance) {
            // title部分的渐变
            drawable.mutate().setAlpha((int) (alpha * 255));
            bindingTitleView.ivBaseTitlebarBg.setImageDrawable(drawable);
        } else {
            drawable.mutate().setAlpha(255);
            bindingTitleView.ivBaseTitlebarBg.setImageDrawable(drawable);
        }
    }

    protected void showLoading() {
        if (loadingView != null && loadingView.getVisibility() != View.VISIBLE) {
            loadingView.setVisibility(View.VISIBLE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (bindingContentView.getRoot().getVisibility() != View.GONE) {
            bindingContentView.getRoot().setVisibility(View.GONE);
        }
        if (errorView != null && errorView.getVisibility() != View.GONE) {
            errorView.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if (loadingView != null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (errorView != null && errorView.getVisibility() != View.GONE) {
            errorView.setVisibility(View.GONE);
        }
        if (bindingContentView.getRoot().getVisibility() != View.VISIBLE) {
            bindingContentView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showError() {
        if (loadingView != null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (errorView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.vs_error_refresh);
            errorView = viewStub.inflate();
            // 点击加载失败布局
            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    onRefresh();
                }
            });
        } else {
            errorView.setVisibility(View.VISIBLE);
        }
        if (bindingContentView.getRoot().getVisibility() != View.GONE) {
            bindingContentView.getRoot().setVisibility(View.GONE);
        }
    }

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }

    public void addSubscription(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }

    @Override
    public void onDestroy() {
        if (this.mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            this.mCompositeDisposable.clear();
        }
        if (changeBounds != null) {
            changeBounds.addListener(null);
            changeBounds.removeTarget(setHeaderPicView());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setSharedElementEnterTransition(null);
                getWindow().setSharedElementReturnTransition(null);
            }
        }
        if (mAnimationDrawable != null) {
            mAnimationDrawable.stop();
            mAnimationDrawable = null;
        }
        super.onDestroy();
    }

    public void removeDisposable() {
        if (this.mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            this.mCompositeDisposable.dispose();
        }
    }
}
