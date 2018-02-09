package com.example.jingbin.cloudreader.ui.one;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.MainActivity;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.OneAdapter;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.databinding.FragmentOneBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemOneBinding;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.TimeUtil;
import com.example.jingbin.cloudreader.viewmodel.movie.OneViewModel;

public class OneFragment extends BaseFragment<FragmentOneBinding> {

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
    private OneViewModel oneViewModel;

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
        showContentView();
        initRecyclerView();
        aCache = ACache.get(getActivity());
        mHotMovieBean = (HotMovieBean) aCache.getAsObject(Constants.ONE_HOT_MOVIE);
        oneViewModel = ViewModelProviders.of(this).get(OneViewModel.class);
        isPrepared = true;
    }

    public static void showQsbk(){

    }

    /**
     * 懒加载
     * 从此页面新开activity界面返回此页面 不会走这里
     */
    @Override
    protected void loadData() {
//        DebugUtil.error("------OneFragment---loadData: ");

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
            if (mIsLoading) {
                return;
            }
            if (!isFirst) {
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
                }, 150);
            }
        }

    }

    private void loadHotMovie() {
        oneViewModel.getHotMovie().observe(this, hotMovieBean -> {
            showContentView();
            if (hotMovieBean != null && hotMovieBean.getSubjects() != null) {
                setAdapter(hotMovieBean);

                aCache.remove(Constants.ONE_HOT_MOVIE);
                aCache.put(Constants.ONE_HOT_MOVIE, hotMovieBean);
                // 保存请求的日期
                SPUtils.putString("one_data", TimeUtil.getData());
                // 刷新结束
                mIsLoading = false;
            } else {
                if (mHotMovieBean != null) {
                    setAdapter(mHotMovieBean);
                } else {
                    if (oneAdapter.getItemCount() == 0) {
                        showError();
                    }
                }
            }
        });
    }

    private void setAdapter(HotMovieBean hotMovieBean) {
        oneAdapter.clear();
        oneAdapter.addAll(hotMovieBean.getSubjects());
        bindingView.listOne.setAdapter(oneAdapter);
        oneAdapter.notifyDataSetChanged();

        isFirst = false;
    }

    private void initRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.listOne.setLayoutManager(mLayoutManager);
        // 加上这两行代码，下拉出提示才不会产生出现刷新头的bug，不加拉不下来
        bindingView.listOne.setPullRefreshEnabled(false);
        bindingView.listOne.clearHeader();
        bindingView.listOne.setLoadingMoreEnabled(false);
        // 需加，不然滑动不流畅
        bindingView.listOne.setNestedScrollingEnabled(false);
        bindingView.listOne.setHasFixedSize(false);
        HeaderItemOneBinding oneBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_one, null, false);
        oneBinding.setView(this);
        bindingView.listOne.addHeaderView(oneBinding.getRoot());
        oneAdapter = new OneAdapter(activity);
    }

    /**
     * 延迟执行，避免卡顿
     * 加同步锁，避免重复加载
     */
    private void postDelayLoad() {
        synchronized (this) {
            if (!mIsLoading) {
                mIsLoading = true;
                bindingView.listOne.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadHotMovie();
                    }
                }, 150);
            }
        }
    }

    public void headerClick() {
        DoubanTopActivity.start(activity);
    }

    @Override
    protected void onRefresh() {
        loadHotMovie();
    }

    /**
     * 从此页面新开activity界面返回此页面 走这里
     */
    @Override
    public void onResume() {
        super.onResume();
    }
}
