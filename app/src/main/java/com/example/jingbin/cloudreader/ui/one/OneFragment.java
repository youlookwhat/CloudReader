package com.example.jingbin.cloudreader.ui.one;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.jingbin.cloudreader.MainActivity;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.OneAdapter;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.HotMovieBean;
import com.example.jingbin.cloudreader.databinding.FragmentOneBinding;
import com.example.jingbin.cloudreader.http.HttpUtils;
import com.example.jingbin.cloudreader.http.cache.ACache;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.TimeUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    private View mHeaderView = null;

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

        showLoading();
        aCache = ACache.get(getActivity());
        mHotMovieBean = (HotMovieBean) aCache.getAsObject(Constants.ONE_HOT_MOVIE);
        isPrepared = true;
        DebugUtil.error("---OneFragment   --onActivityCreated");
    }

    /**
     * 懒加载
     */
    @Override
    protected void loadData() {
        DebugUtil.error("------OneFragment---loadData: ");

        if (!isPrepared || !mIsVisible) {
            return;
        }

        // 显示，准备完毕，不是当天，则请求数据（正在请求时避免再次请求）
        String oneData = SPUtils.getString("one_data", "2016-11-26");
        if (!oneData.equals(TimeUtil.getData()) && !mIsLoading) {
            mIsLoading = true;
//            loadHotMovie();
            /**延迟执行防止卡顿*/
            showLoading();
            bindingView.listOne.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadHotMovie();
                }
            }, 1000);
            return;
        }

        if (!isFirst) {
            return;
        }

        showLoading();
        if (mHotMovieBean == null) {
            bindingView.listOne.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadHotMovie();
                }
            }, 1000);
        } else {
            bindingView.listOne.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setAdapter(mHotMovieBean);
                    showContentView();
                }
            }, 1000);
            DebugUtil.error("----缓存: " + oneData);
        }
    }

    private void loadHotMovie() {
        Subscription subscription = HttpUtils.getInstance().getDouBanServer().getHotMovie().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<HotMovieBean>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {

                        if (hotMovieBean != null) {
                            aCache.remove(Constants.ONE_HOT_MOVIE);
                            // 保存12个小时
                            aCache.put(Constants.ONE_HOT_MOVIE, hotMovieBean, 43200);
                            setAdapter(hotMovieBean);
                            // 保存请求的日期
                            SPUtils.putString("one_data", TimeUtil.getData());
                            // 刷新结束
                            mIsLoading = false;
                        }


//                        bindingContentView.listOne.setLayoutManager(new GridLayoutManager(getActivity(), 1));
//                        SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(ScreenUtils.dipToPx(getActivity(),10),ScreenUtils.dipToPx(getActivity(),10),ScreenUtils.dipToPx(getActivity(),10),0);
//                        recyclerview.addItemDecoration(spacesItemDecoration);
//                        recyclerview.setAdapter(filmLiveAdapter);

                        //1 实例化RecyclerView
//                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        //2 为RecyclerView创建布局管理器，这里使用的是LinearLayoutManager，表示里面的Item排列是线性排列
//                        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                        bindingContentView.listOne.setLayoutManager(mLayoutManager);

                        //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
//                        bindingContentView.listOne.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));

                        // GridView
//                        bindingContentView.listOne.setLayoutManager(new GridLayoutManager(getActivity(), 2));

//                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                        bindingContentView.listOne.setLayoutManager(mLayoutManager);
//                        OneAdapter oneAdapter = new OneAdapter();
//                        oneAdapter.addAll(mHotMovieBean.getSubjects());
//                        bindingContentView.listOne.setAdapter(oneAdapter);

//                        oneAdapter.notifyItemInserted(0);addheader
                    }
                });
        addSubscription(subscription);
    }

    private void setAdapter(HotMovieBean hotMovieBean) {

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bindingView.listOne.setLayoutManager(mLayoutManager);
        bindingView.listOne.setPullRefreshEnabled(false);
        bindingView.listOne.setLoadingMoreEnabled(false);
        // 需加，不然滑动不流畅
        bindingView.listOne.setNestedScrollingEnabled(false);
        bindingView.listOne.setHasFixedSize(false);

        if (mHeaderView == null) {
            mHeaderView = View.inflate(getContext(), R.layout.header_item_one, null);
            View llMovieTop = mHeaderView.findViewById(R.id.ll_movie_top);
            llMovieTop.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    DouBanTopActivity.start(v.getContext());
                }
            });
            bindingView.listOne.addHeaderView(mHeaderView);
        }
        OneAdapter oneAdapter = new OneAdapter(activity);
        oneAdapter.addAll(hotMovieBean.getSubjects());
        bindingView.listOne.setAdapter(oneAdapter);
        oneAdapter.notifyDataSetChanged();

        isFirst = false;
    }

    @Override
    protected void onRefresh() {
        loadHotMovie();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--OneFragment   ----onDestroy");
    }
}
