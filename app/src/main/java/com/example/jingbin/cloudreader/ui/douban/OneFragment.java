package com.example.jingbin.cloudreader.ui.douban;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.OneAdapter;
import com.example.jingbin.cloudreader.app.Constants;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.databinding.FragmentOneBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemOneBinding;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.ui.MainActivity;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.TimeUtil;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.viewmodel.movie.OneViewModel;

import me.jingbin.library.ByRecyclerView;

/**
 * @author jingbin
 */
public class OneFragment extends BaseFragment<OneViewModel, FragmentOneBinding> {

    // 初始化完成后加载数据
    private boolean isPrepared = false;
    // 第一次显示时加载数据，第二次不显示
    private boolean isFirst = true;
    // 是否正在刷新（用于刷新数据时返回页面不再刷新）
    private boolean mIsLoading = false;
    private ACache aCache;
    private MainActivity activity;
    private HotMovieBean mHotMovieBean;
    private OneAdapter oneAdapter;
    private HeaderItemOneBinding oneBinding;

    @Override
    public int setContent() {
        return R.layout.fragment_one;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();
        aCache = ACache.get(getActivity());
        mHotMovieBean = (HotMovieBean) aCache.getAsObject(Constants.ONE_HOT_MOVIE);
        isPrepared = true;
        loadData();
    }

    private void initRecyclerView() {
        oneBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_one, null, false);
        oneBinding.setView(this);
        bindingView.listOne.setLayoutManager(new LinearLayoutManager(activity));
        bindingView.listOne.setItemAnimator(null);
        bindingView.listOne.addHeaderView(oneBinding.getRoot());
        oneAdapter = new OneAdapter(activity);
        bindingView.listOne.setAdapter(oneAdapter);
        bindingView.listOne.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (oneBinding.tlMovie.getSelectedTabPosition() == 1) {
                    viewModel.handleNextStart();
                    loadComingSoonMovie();
                } else {
                    bindingView.listOne.loadMoreEnd();
                }
            }
        });
        oneBinding.tlMovie.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = oneBinding.tlMovie.getSelectedTabPosition();
                if (tabPosition == 0) {
                    viewModel.setStart(0);
                    bindingView.listOne.setRefreshing(false);
                    loadHotMovie();
                } else {
                    viewModel.setStart(0);
                    bindingView.listOne.setRefreshing(false);
                    loadComingSoonMovie();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 懒加载
     * 从此页面新开activity界面返回此页面 不会走这里
     */
    @Override
    protected void loadData() {
        if (!isPrepared || !mIsVisible) {
            return;
        }

        // 显示，准备完毕，不是当天，则请求数据（正在请求时避免再次请求）
        String oneData = SPUtils.getString("one_data", "2016-11-26");

        if (!oneData.equals(TimeUtil.getData()) && !mIsLoading) {
            showLoading();
            /**延迟执行防止卡顿*/
            postDelayLoad();
        } else {
            // 为了正在刷新时不执行这部分
            if (mIsLoading || !isFirst) {
                return;
            }

            showLoading();
            if (mHotMovieBean == null && !mIsLoading) {
                postDelayLoad();
            } else {
                bindingView.listOne.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (this) {
                            setAdapter(mHotMovieBean);
                            showContentView();
                        }
                    }
                }, 300);
            }
        }

    }

    private void loadHotMovie() {
        viewModel.getHotMovie().observe(this, this::showContent);
    }

    private void loadComingSoonMovie() {
        viewModel.getComingSoon().observe(this, this::showContent);
    }

    private void showContent(HotMovieBean movieBean) {
        showContentView();
        if (movieBean != null && movieBean.getSubjects() != null && movieBean.getSubjects().size() > 0) {
            if (oneBinding.tlMovie.getSelectedTabPosition() == 0) {
                setAdapter(movieBean);
                aCache.remove(Constants.ONE_HOT_MOVIE);
                aCache.put(Constants.ONE_HOT_MOVIE, movieBean);
                // 保存请求的日期
                SPUtils.putString("one_data", TimeUtil.getData());
                // 刷新结束
                mIsLoading = false;
            } else {
                if (viewModel.getStart() == 0) {
                    oneAdapter.clear();
                    oneAdapter.notifyDataSetChanged();
                }
                // +2 一个刷新头布局 一个自己新增的头布局
                int positionStart = oneAdapter.getItemCount() + 1;
                oneAdapter.addAll(movieBean.getSubjects());
                oneAdapter.notifyItemRangeInserted(positionStart, movieBean.getSubjects().size());
                bindingView.listOne.loadMoreComplete();
            }
        } else {
            bindingView.listOne.loadMoreComplete();
            if (oneBinding.tlMovie.getSelectedTabPosition() == 0) {
                if (mHotMovieBean != null) {
                    setAdapter(mHotMovieBean);
                } else if (oneAdapter.getItemCount() == 0) {
                    showError();
                }
            } else {
                if (viewModel.getStart() == 0) {
                    ToastUtil.showToastLong("没有即将上映的电影数据~");
                    oneBinding.tlMovie.setScrollPosition(0, 0, true);
                    oneBinding.tlMovie.getTabAt(0).select();
                }
                if (oneAdapter.getItemCount() == 0) {
                    showError();
                } else {
                    bindingView.listOne.loadMoreEnd();
                }
            }
        }
    }

    private void setAdapter(HotMovieBean hotMovieBean) {
        oneAdapter.clear();
        oneAdapter.addAll(hotMovieBean.getSubjects());
        oneAdapter.notifyDataSetChanged();
        bindingView.listOne.loadMoreComplete();
        if (isFirst) {
            isFirst = false;
        }
    }

    /**
     * 延迟执行，避免卡顿
     * 加同步锁，避免重复加载
     */
    private void postDelayLoad() {
        synchronized (this) {
            if (!mIsLoading) {
                mIsLoading = true;
                bindingView.listOne.postDelayed(this::onRefresh, 150);
            }
        }
    }

    public void headerClick() {
        DoubanTopActivity.start(activity);
    }

    @Override
    protected void onRefresh() {
        if (oneBinding.tlMovie.getSelectedTabPosition() == 0) {
            loadHotMovie();
        } else {
            loadComingSoonMovie();
        }
    }

    /**
     * 从此页面新开activity界面返回此页面 走这里
     */
    @Override
    public void onResume() {
        super.onResume();
    }
}
