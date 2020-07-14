package com.example.jingbin.cloudreader.ui.gank.child;

import android.animation.ValueAnimator;

import androidx.lifecycle.Observer;

import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.GankAndroidAdapter;

import me.jingbin.bymvvm.base.BaseFragment;

import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.FragmentEverydayBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemEverydayBinding;

import me.jingbin.bymvvm.rxbus.RxBus;
import me.jingbin.bymvvm.rxbus.RxBusBaseMessage;

import com.example.jingbin.cloudreader.app.RxCodeConstants;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.GlideUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.view.viewbigimage.ViewBigImageActivity;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.gank.GankHomeViewModel;

import me.jingbin.library.skeleton.ByRVItemSkeletonScreen;
import me.jingbin.library.skeleton.BySkeleton;
import me.jingbin.sbanner.holder.HolderCreator;
import me.jingbin.sbanner.holder.SBannerViewHolder;

import static com.example.jingbin.cloudreader.viewmodel.gank.EverydayViewModel.getTodayTime;

/**
 * 干货首页
 *
 * @author jingbin
 */
public class GankHomeFragment extends BaseFragment<GankHomeViewModel, FragmentEverydayBinding> {

    private HeaderItemEverydayBinding mHeaderBinding;
    private GankAndroidAdapter mAdapter;
    private boolean mIsPrepared = false;
    private boolean mIsFirst = true;
    private RotateAnimation animation;
    private boolean isLoadBanner;
    private ByRVItemSkeletonScreen skeletonScreen;

    @Override
    public int setContent() {
        return R.layout.fragment_everyday;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showContentView();
        initAnimation();
        initRecyclerView();

        mIsPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (mIsPrepared && isLoadBanner) {
            onResume();
        }
        if (!mIsVisible || !mIsPrepared || !mIsFirst) {
            return;
        }
        bindingView.recyclerView.postDelayed(() -> viewModel.loadData(), 150);
        mIsFirst = false;
    }

    private void initRecyclerView() {
        mAdapter = new GankAndroidAdapter();
        mAdapter.setAllType(true);
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.header_item_everyday, null, false);
        bindingView.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bindingView.recyclerView.setLoadMoreEnabled(true);
        bindingView.recyclerView.setHasFixedSize(false);
        bindingView.recyclerView.addHeaderView(mHeaderBinding.getRoot());
//        bindingView.recyclerView.setAdapter(mAdapter);

        // 显示日期,去掉第一位的"0"
        String day = getTodayTime().get(2);
        mHeaderBinding.includeEveryday.tvDailyText.setText(day.indexOf("0") == 0 ? day.replace("0", "") : day);
        mHeaderBinding.includeEveryday.ibXiandu.setOnClickListener(listener);
        mHeaderBinding.includeEveryday.ibWanAndroid.setOnClickListener(listener);
        mHeaderBinding.includeEveryday.ibMovieHot.setOnClickListener(listener);
        mHeaderBinding.includeEveryday.flEveryday.setOnClickListener(listener);
        DensityUtil.setWidthHeight(mHeaderBinding.banner, DensityUtil.getDisplayWidth(), 2.2f);

        onObserveViewModel();
        showItemSkeleton();
    }

    /**
     * 骨架图
     */
    private void showItemSkeleton() {
        skeletonScreen = BySkeleton
                .bindItem(bindingView.recyclerView)
                .adapter(mAdapter)// 必须设置adapter，且在此之前不要设置adapter
                .shimmer(false)// 是否有动画
                .load(R.layout.item_android_skeleton)// item骨架图
                .angle(30)// 微光角度
                .frozen(false) // 是否不可滑动
                .duration(1500)// 微光一次显示时间
                .count(10)// item个数
                .show();
    }

    private void onObserveViewModel() {
        viewModel.getShowLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                showRotaLoading(aBoolean);
            }
        });
        viewModel.getBannerData().observe(getViewLifecycleOwner(), new Observer<GankIoDataBean>() {
            @Override
            public void onChanged(@Nullable GankIoDataBean bean) {
                if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
                    mHeaderBinding.banner
                            .setDelayTime(4000)
                            .setAutoPlay(true)
                            .setOffscreenPageLimit(bean.getResults().size())
                            .setPages(bean.getResults(), new HolderCreator<SBannerViewHolder>() {
                                @Override
                                public SBannerViewHolder createViewHolder() {
                                    return new SBannerViewHolder<GankIoDataBean.ResultBean>() {
                                        private ImageView imageView;

                                        @Override
                                        public View createView(Context context) {
                                            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_wanandroid, null);
                                            imageView = (ImageView) view.findViewById(R.id.iv_banner);
                                            return view;
                                        }

                                        @Override
                                        public void onBind(Context context, int position, GankIoDataBean.ResultBean data) {
                                            DensityUtil.setWidthHeight(imageView, DensityUtil.getDisplayWidth(), 2.2f);
                                            GlideUtil.displayEspImage(data.getImage(), imageView, 3);
                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (!TextUtils.isEmpty(bean.getResults().get(position).getUrl())
                                                            && bean.getResults().get(position).getUrl().startsWith("http")) {
                                                        WebViewActivity.loadUrl(getContext(), bean.getResults().get(position).getUrl(), "干货集中营");
                                                    }
                                                }
                                            });
                                            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                                                @Override
                                                public boolean onLongClick(View v) {
                                                    ViewBigImageActivity.start(v.getContext(), data.getImage(), data.getTitle());
                                                    return true;
                                                }
                                            });
                                        }
                                    };
                                }
                            }).start();
                    isLoadBanner = true;
                }
            }
        });
        viewModel.getContentData().observe(getViewLifecycleOwner(), new Observer<GankIoDataBean>() {
            @Override
            public void onChanged(@Nullable GankIoDataBean bean) {
                bindingView.ivLoading.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
                            skeletonScreen.hide();
                            mAdapter.setNewData(bean.getResults());
                            bindingView.recyclerView.loadMoreEnd();
                        } else {
                            showError();
                        }
                    }
                }, 1000);
            }
        });
    }

    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            switch (v.getId()) {
                case R.id.ib_xiandu:
                    WebViewActivity.loadUrl(v.getContext(), getString(R.string.string_url_gank), "干货集中营");
                    break;
                case R.id.ib_wan_android:
                    WebViewActivity.loadUrl(v.getContext(), getString(R.string.string_url_wanandroid), "玩Android");
                    break;
                case R.id.ib_movie_hot:
                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE_TO_ONE, new RxBusBaseMessage());
                    break;
                case R.id.fl_everyday:
                    WebViewActivity.loadUrl(v.getContext(), getString(R.string.string_url_trending), "Trending");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        // 失去焦点，否则RecyclerView第一个item会回到顶部
        bindingView.recyclerView.setFocusable(false);
        if (isLoadBanner) {
            mHeaderBinding.banner.startAutoPlay();
        }
    }

    @Override
    protected void onInvisible() {
        if (mIsPrepared && isLoadBanner) {
            onPause();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 不可见时轮播图停止滚动
        if (isLoadBanner) {
            mHeaderBinding.banner.stopAutoPlay();
        }
    }

    private void initAnimation() {
        bindingView.llLoading.setVisibility(View.VISIBLE);
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());//不停顿
        animation.setRepeatMode(ValueAnimator.RESTART);//重新从头执行
        animation.setRepeatCount(ValueAnimator.INFINITE);//设置重复次数
        bindingView.ivLoading.setAnimation(animation);
        animation.startNow();
    }

    /**
     * 显示旋转动画
     */
    private void showRotaLoading(Boolean isLoading) {
        if (isLoading != null && isLoading) {
            bindingView.llLoading.setVisibility(View.VISIBLE);
            bindingView.recyclerView.setVisibility(View.GONE);
            animation.startNow();
        } else {
            bindingView.llLoading.setVisibility(View.GONE);
            bindingView.recyclerView.setVisibility(View.VISIBLE);
            animation.cancel();
        }
    }

    @Override
    protected void onRefresh() {
        showContentView();
        showRotaLoading(true);
        viewModel.loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHeaderBinding.banner.stopAutoPlay();
    }
}
