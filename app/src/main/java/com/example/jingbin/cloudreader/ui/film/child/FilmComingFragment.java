package com.example.jingbin.cloudreader.ui.film.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.FilmComingAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.ComingFilmBean;
import com.example.jingbin.cloudreader.bean.moviechild.FilmItemBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.viewmodel.movie.FilmViewModel;
import com.example.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

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
//        loadData();
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        adapter = new FilmComingAdapter();
        bindingView.xrvWan.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.setItemAnimator(null);
        bindingView.xrvWan.clearHeader();
        bindingView.xrvWan.setHasFixedSize(true);
        viewModel.bookType.set(mType);
        bindingView.xrvWan.setAdapter(adapter);
        adapter.setListener(new FilmComingAdapter.OnClickListener() {
            @Override
            public void onClick(ComingFilmBean.MoviecomingsBean bean, ImageView imageView) {
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
        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!bindingView.xrvWan.isLoadingData()) {
                    bindingView.srlWan.postDelayed(() -> {
                        bindingView.xrvWan.reset();
                        getHotFilm();
                    }, 150);
                }

            }
        });
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                bindingView.xrvWan.noMoreLoading();
            }
        });
    }

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getHotFilm, 150);
        DebugUtil.error("-----setRefreshing");
    }

    private void getHotFilm() {
        viewModel.getComingFilm().observe(this, new android.arch.lifecycle.Observer<ComingFilmBean>() {
            @Override
            public void onChanged(@Nullable ComingFilmBean bookBean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (bookBean != null && bookBean.getMoviecomings() != null && bookBean.getMoviecomings().size() > 0) {
                    if (viewModel.getStart() == 0) {
                        showContentView();
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                    }
                    // +1 一个刷新头布局
                    int positionStart = adapter.getItemCount() + 1;

                    ArrayList<ComingFilmBean.MoviecomingsBean> beans = new ArrayList<>();
                    beans.addAll(bookBean.getAttention());
                    beans.addAll(bookBean.getMoviecomings());
                    // 用HashSet 根据id去重 https://www.cnblogs.com/woshimrf/p/java-list-distinct.html
                    Set<ComingFilmBean.MoviecomingsBean> set = new LinkedHashSet<>(beans);
                    beans.clear();
                    beans.addAll(set);

                    adapter.addAll(beans);
                    adapter.notifyItemRangeInserted(positionStart, beans.size());
                    bindingView.xrvWan.refreshComplete();
                    if (mIsFirst) {
                        mIsFirst = false;
                    }
                } else {
                    if (adapter.getItemCount() == 0) {
                        showError();
                    } else {
                        bindingView.xrvWan.noMoreLoading();
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
