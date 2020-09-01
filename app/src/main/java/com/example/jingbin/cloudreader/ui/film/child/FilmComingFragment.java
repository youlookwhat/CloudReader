package com.example.jingbin.cloudreader.ui.film.child;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.FilmComingAdapter;

import me.jingbin.bymvvm.base.BaseFragment;

import com.example.jingbin.cloudreader.bean.ComingFilmBean;
import com.example.jingbin.cloudreader.bean.moviechild.FilmItemBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.viewmodel.movie.FilmViewModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import me.jingbin.library.ByRecyclerView;
import me.jingbin.library.view.OnItemFilterClickListener;

/**
 * @author jingbin
 * Updated by jingbin on 19/05/15.
 */
public class FilmComingFragment extends BaseFragment<FilmViewModel, FragmentWanAndroidBinding> {

    private static final String TYPE = "param1";
    private String mType = "综合";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FilmComingAdapter adapter;
    private FragmentActivity activity;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static FilmComingFragment newInstance(String param1) {
        FilmComingFragment fragment = new FilmComingFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();
        // 准备就绪
        mIsPrepared = true;
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        adapter = new FilmComingAdapter(activity);
        bindingView.xrvWan.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        bindingView.xrvWan.setItemAnimator(null);
        bindingView.xrvWan.setHasFixedSize(true);
        bindingView.xrvWan.setLoadMoreEnabled(true);
        viewModel.bookType.set(mType);
        bindingView.xrvWan.setAdapter(adapter);
        bindingView.xrvWan.setOnItemClickListener(new OnItemFilterClickListener() {
            @Override
            public void onSingleClick(View v, int position) {
                ImageView imageView = v.findViewById(R.id.iv_top_photo);
                ComingFilmBean.MoviecomingsBean bean = adapter.getItemData(position);

                FilmItemBean filmItemBean = new FilmItemBean();
                filmItemBean.setId(bean.getId());
                filmItemBean.setDN(bean.getDirector());
                filmItemBean.setTCn(bean.getTitle());
                filmItemBean.setTEn(bean.getReleaseDate());
                filmItemBean.setMovieType(bean.getType());
                filmItemBean.setImg(bean.getImage());
                filmItemBean.setLocationName(bean.getLocationName());
                String actor1 = bean.getActor1();
                String actor2 = bean.getActor2();
                if (!TextUtils.isEmpty(actor2)) {
                    actor1 = actor1 + " / " + actor2;
                }
                filmItemBean.setActors(actor1);
                FilmDetailActivity.start(activity, filmItemBean, imageView);

            }
        });
        bindingView.srlWan.setOnRefreshListener(this::getHotFilm);
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getHotFilm, 150);
        mIsFirst = false;
    }

    private void getHotFilm() {
        viewModel.getComingFilm().observe(this, new androidx.lifecycle.Observer<ComingFilmBean>() {
            @Override
            public void onChanged(@Nullable ComingFilmBean bookBean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                boolean isMovieComings = bookBean != null
                        && bookBean.getData() != null
                        && bookBean.getData().getMoviecomings() != null
                        && bookBean.getData().getMoviecomings().size() > 0;
                boolean isRecommends = bookBean != null
                        && bookBean.getData() != null
                        && bookBean.getData().getRecommends() != null
                        && bookBean.getData().getRecommends().size() > 0
                        && bookBean.getData().getRecommends().get(0) != null
                        && bookBean.getData().getRecommends().get(0).getMovies() != null
                        && bookBean.getData().getRecommends().get(0).getMovies().size() > 0;

                if (isMovieComings || isRecommends) {
                    showContentView();

                    ArrayList<ComingFilmBean.MoviecomingsBean> beans = new ArrayList<>();
                    if (isRecommends) {
                        beans.addAll(bookBean.getData().getRecommends().get(0).getMovies());
                    }
                    beans.addAll(bookBean.getData().getMoviecomings());
                    // 用HashSet 根据id去重 https://www.cnblogs.com/woshimrf/p/java-list-distinct.html
                    Set<ComingFilmBean.MoviecomingsBean> set = new LinkedHashSet<>(beans);
                    beans.clear();
                    beans.addAll(set);

                    adapter.setNewData(beans);
                    bindingView.xrvWan.loadMoreEnd();
                } else {
                    if (adapter.getItemCount() == 0) {
                        if (bookBean != null) {
                            showEmptyView("暂未发现即将上映的电影");
                        } else {
                            showError();
                        }
                    } else {
                        bindingView.xrvWan.loadMoreEnd();
                    }
                }
            }
        });
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getHotFilm();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.clear();
            adapter = null;
        }
    }
}
