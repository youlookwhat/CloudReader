package com.example.jingbin.cloudreader.ui.film.child;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.FilmDetailActorAdapter;
import com.example.jingbin.cloudreader.adapter.FilmDetailImageAdapter;
import com.example.jingbin.cloudreader.app.RxCodeConstants;
import com.example.jingbin.cloudreader.base.BaseHeaderActivity;
import com.example.jingbin.cloudreader.bean.FilmDetailNewBean;
import com.example.jingbin.cloudreader.bean.moviechild.FilmItemBean;
import com.example.jingbin.cloudreader.databinding.ActivityFilmDetailBinding;
import com.example.jingbin.cloudreader.databinding.HeaderFilmDetailBinding;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.utils.DataUtil;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.view.viewbigimage.ViewBigImageActivity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jingbin.bymvvm.rxbus.RxBus;
import me.jingbin.bymvvm.utils.CommonUtils;


/**
 * 时光网电影详情页：
 *
 * @author jingbin 2019-05-14
 */
public class FilmDetailActivity extends BaseHeaderActivity<HeaderFilmDetailBinding, ActivityFilmDetailBinding> {

    private FilmItemBean filmItemBean;
    private String mMoreUrl;
    private String mMoreTitle;
    public ObservableField<Boolean> isShowActor = new ObservableField<>();
    public ObservableField<Boolean> isShowVideo = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        if (getIntent() != null) {
            filmItemBean = (FilmItemBean) getIntent().getSerializableExtra("bean");
        }

        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());

        setTitle(filmItemBean.getTCn());
        setSubTitle(filmItemBean.getTEn());
        bindingHeaderView.setSubjectsBean(filmItemBean);
        bindingContentView.setListener(this);
        bindingHeaderView.executePendingBindings();

        bindingContentView.tvOneTitle.postDelayed(this::loadMovieDetail, 450);
    }

    @Override
    protected void setTitleClickMore() {
        if (!TextUtils.isEmpty(mMoreUrl)) {
            ViewBigImageActivity.start(this, mMoreUrl, mMoreUrl);
        } else {
            ToastUtil.showToast("抱歉，暂无大图");
        }
    }

    @Override
    protected int setHeaderLayout() {
        return R.layout.header_film_detail;
    }

    @Override
    protected String setHeaderImgUrl() {
        if (filmItemBean == null) {
            return "";
        }
        return filmItemBean.getImg();
    }

    @Override
    protected ImageView setHeaderImageView() {
        return bindingHeaderView.imgItemBg;
    }

    private void loadMovieDetail() {
        HttpClient.Builder.getMtimeTicketServer().getFilmDetail(561, filmItemBean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FilmDetailNewBean>() {

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onComplete() {
                        showContentView();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(final FilmDetailNewBean bean) {
                        if (bean != null) {
                            bindingHeaderView.tvOneRatingRate.setText(String.format("评分：%s", bean.getRating()));
                            bindingHeaderView.tvOneRatingNumber.setText(String.format("%s人评分", bean.getScoreCount()));
                            if (bean.getRelease() != null) {
                                bindingHeaderView.tvOneDate.setText(String.format("上映日期：%s %s", bean.getRelease().getDate(), bean.getRelease().getLocation()));
                            } else {
                                bindingHeaderView.tvOneDate.setText("上映日期：暂无");
                            }
                            bindingHeaderView.tvOneTime.setText(String.format("片长：%s", bean.getRunTime()));
                            bindingContentView.setBean(bean);

                            transformData(bean);

                            if (!TextUtils.isEmpty(bean.getImage())) {
                                mMoreUrl = bean.getImage();
                                mMoreTitle = "加载中...";
                            }
                            bindingContentView.executePendingBindings();
                        }
                    }
                });
    }

    /**
     * 异步线程转换数据
     */
    private void transformData(final FilmDetailNewBean bean) {
        if (bean.getActorList() != null && bean.getActorList().size() > 0) {
            isShowActor.set(true);
            // 即将上映缺失填充
            String cast = bindingHeaderView.tvOneCasts.getText().toString();
            if (TextUtils.isEmpty(cast)) {
                bindingHeaderView.tvOneCasts.setText(DataUtil.getActorString(bean.getActorList()));
            }
            FilmDetailNewBean.ActorBean actorBean = new FilmDetailNewBean.ActorBean();

            FilmDetailNewBean.DirectorBean director = bean.getDirector();
            if (director != null) {
                actorBean.setRoleName("导演");
                actorBean.setActor(director.getDirectorName());
                actorBean.setActorEn(director.getDirectorNameEn());
                actorBean.setActorImg(director.getDirectorImg());

                bean.getActorList().add(0, actorBean);
                // 即将上映缺失填充
                String name = bindingHeaderView.tvOneDirectors.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    bindingHeaderView.tvOneDirectors.setText(director.getDirectorName() != null ? director.getDirectorName() : director.getDirectorNameEn());
                }
            }
            setAdapter(bean.getActorList());
        } else {
            isShowActor.set(false);
        }

        if (bean.getVideos() != null
                && bean.getVideos().size() > 0
                && bean.getVideos().get(0) != null
                && !TextUtils.isEmpty(bean.getVideos().get(0).getUrl())) {
            isShowVideo.set(true);
            FilmDetailNewBean.VideoBean videoBean = bean.getVideos().get(0);
            bindingContentView.setVideo(videoBean);
            DensityUtil.setWidthHeight(bindingContentView.ivVideo, DensityUtil.getDisplayWidth() - DensityUtil.dip2px(this, 40), (640f / 360));
            DensityUtil.setViewMargin(bindingContentView.ivVideo, true, 20, 20, 10, 10);
            bindingContentView.ivVideo.setOnClickListener(view -> WebViewActivity.loadUrl(this, videoBean.getUrl(), videoBean.getTitle(), true));
        } else {
            isShowVideo.set(false);
        }

        if (bean.getImages() != null && bean.getImages().size() > 0) {
            setImageAdapter(bean.getImages());
        }


    }

    /**
     * 演职员
     */
    private void setAdapter(List<FilmDetailNewBean.ActorBean> listBeans) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(FilmDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bindingContentView.xrvCast.setLayoutManager(mLayoutManager);
        // 需加，不然滑动不流畅
        bindingContentView.xrvCast.setNestedScrollingEnabled(false);
        bindingContentView.xrvCast.setHasFixedSize(false);

        FilmDetailActorAdapter mAdapter = new FilmDetailActorAdapter();
        mAdapter.addAll(listBeans);
        bindingContentView.xrvCast.setAdapter(mAdapter);
        bindingContentView.xrvCast.setFocusable(false);
        bindingContentView.xrvCast.setFocusableInTouchMode(false);
    }

    /**
     * 剧照
     */
    private void setImageAdapter(List<String> listBeans) {
        bindingContentView.xrvImages.setVisibility(View.VISIBLE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(FilmDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bindingContentView.xrvImages.setLayoutManager(mLayoutManager);
        // 需加，不然滑动不流畅
        bindingContentView.xrvImages.setNestedScrollingEnabled(false);
        bindingContentView.xrvImages.setHasFixedSize(false);

        FilmDetailImageAdapter mAdapter = new FilmDetailImageAdapter(this, listBeans);
        mAdapter.addAll(listBeans);
        bindingContentView.xrvImages.setAdapter(mAdapter);
        bindingContentView.xrvImages.setFocusable(false);
        bindingContentView.xrvImages.setFocusableInTouchMode(false);
        initRxBus();
    }

    @Override
    protected void onRefresh() {
        loadMovieDetail();
    }

    private void initRxBus() {
        Disposable subscribe = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_CURRENT_POSITION, Integer.class)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        bindingContentView.xrvImages.smoothScrollToPosition(integer);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (filmItemBean != null) {
            filmItemBean = null;
        }
    }

    /**
     * @param context      activity
     * @param positionData bean
     * @param imageView    imageView
     */
    public static void start(Activity context, FilmItemBean positionData, ImageView imageView) {
        Intent intent = new Intent(context, FilmDetailActivity.class);
        intent.putExtra("bean", positionData);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context, imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

}
