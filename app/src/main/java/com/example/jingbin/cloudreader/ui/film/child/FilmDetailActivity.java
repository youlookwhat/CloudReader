package com.example.jingbin.cloudreader.ui.film.child;

import android.app.Activity;
import android.content.Intent;

import androidx.databinding.ObservableField;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.FilmDetailActorAdapter;
import com.example.jingbin.cloudreader.adapter.FilmDetailImageAdapter;
import com.example.jingbin.cloudreader.base.BaseHeaderActivity;
import com.example.jingbin.cloudreader.bean.FilmDetailBean;
import com.example.jingbin.cloudreader.bean.moviechild.FilmItemBean;
import com.example.jingbin.cloudreader.databinding.ActivityFilmDetailBinding;
import com.example.jingbin.cloudreader.databinding.HeaderFilmDetailBinding;
import com.example.jingbin.cloudreader.http.HttpClient;

import me.jingbin.bymvvm.rxbus.RxBus;

import com.example.jingbin.cloudreader.app.RxCodeConstants;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DataUtil;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.ui.WebViewActivity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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
    public ObservableField<Boolean> isShowBoxOffice = new ObservableField<>();
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
            WebViewActivity.loadUrl(this, mMoreUrl, mMoreTitle);
        } else {
            ToastUtil.showToast("抱歉，暂无更多~");
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
        HttpClient.Builder.getMtimeTicketServer().getFilmDetail(filmItemBean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FilmDetailBean>() {

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
                    public void onNext(final FilmDetailBean bean) {
                        if (bean != null && bean.getData() != null) {
                            if (bean.getData().getBasic() != null) {
                                FilmDetailBean.FilmDetailDataBean.BasicBean basic = bean.getData().getBasic();
                                bindingHeaderView.tvOneRatingRate.setText(String.format("评分：%s", basic.getOverallRating()));
                                bindingHeaderView.tvOneRatingNumber.setText(String.format("%s人评分", basic.getPersonCount()));
                                bindingHeaderView.tvOneDate.setText(String.format("上映日期：%s %s", basic.getReleaseDate(), basic.getReleaseArea()));
                                bindingHeaderView.tvOneTime.setText(String.format("片长：%s", basic.getMins()));
                                bindingContentView.setBean(basic);

                                transformData(bean);
                            }

                            if (bean.getData().getBoxOffice() != null
                                    && !TextUtils.isEmpty(bean.getData().getBoxOffice().getTodayBoxDes())
                                    && !TextUtils.isEmpty(bean.getData().getBoxOffice().getTotalBoxDes())) {
                                isShowBoxOffice.set(true);
                                bindingContentView.setBoxOffice(bean.getData().getBoxOffice());
                            } else {
                                isShowBoxOffice.set(false);
                            }
                            if (bean.getData().getRelated() != null && !TextUtils.isEmpty(bean.getData().getRelated().getRelatedUrl())) {
                                mMoreUrl = bean.getData().getRelated().getRelatedUrl();
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
    private void transformData(final FilmDetailBean bean) {
        if (bean.getData().getBasic().getActors() != null && bean.getData().getBasic().getActors().size() > 0) {
            isShowActor.set(true);
            // 即将上映缺失填充
            String cast = bindingHeaderView.tvOneCasts.getText().toString();
            if (TextUtils.isEmpty(cast)) {
                bindingHeaderView.tvOneCasts.setText(DataUtil.getActorString(bean.getData().getBasic().getActors()));
            }
            FilmDetailBean.ActorsBean director = bean.getData().getBasic().getDirector();
            if (director != null) {
                director.setRoleName("导演");
                bean.getData().getBasic().getActors().add(0, director);
                // 即将上映缺失填充
                String name = bindingHeaderView.tvOneDirectors.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    bindingHeaderView.tvOneDirectors.setText(director.getName());
                }
            }
            setAdapter(bean.getData().getBasic().getActors());
        } else {
            isShowActor.set(false);
        }

        if (bean.getData().getBasic().getVideo() != null
                && !TextUtils.isEmpty(bean.getData().getBasic().getVideo().getUrl())) {
            isShowVideo.set(true);
            FilmDetailBean.FilmDetailDataBean.BasicBean.VideoBean video = bean.getData().getBasic().getVideo();
            bindingContentView.setVideo(video);
            DensityUtil.setWidthHeight(bindingContentView.ivVideo, DensityUtil.getDisplayWidth() - DensityUtil.dip2px(this, 40), (640f / 360));
            DensityUtil.setViewMargin(bindingContentView.ivVideo, true, 20, 20, 10, 10);
            bindingContentView.ivVideo.setOnClickListener(view -> WebViewActivity.loadUrl(this, video.getHightUrl(), video.getTitle(), true));
        } else {
            isShowVideo.set(false);
        }

        if (bean.getData().getBasic().getStageImg() != null
                && bean.getData().getBasic().getStageImg().getList() != null
                && bean.getData().getBasic().getStageImg().getList().size() > 0) {
            setImageAdapter(bean.getData().getBasic().getStageImg().getList());
        }


    }

    /**
     * 演职员
     */
    private void setAdapter(List<FilmDetailBean.ActorsBean> listBeans) {
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
    private void setImageAdapter(List<FilmDetailBean.ImageListBean> listBeans) {
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
