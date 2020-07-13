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
import com.example.jingbin.cloudreader.adapter.EverydayAdapter;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.databinding.FragmentEverydayBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemEverydayBinding;
import me.jingbin.bymvvm.rxbus.RxBus;
import me.jingbin.bymvvm.rxbus.RxBusBaseMessage;
import com.example.jingbin.cloudreader.app.RxCodeConstants;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.GlideUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.gank.EverydayViewModel;

import java.util.ArrayList;

import me.jingbin.sbanner.config.OnBannerClickListener;
import me.jingbin.sbanner.holder.SBannerViewHolder;
import me.jingbin.sbanner.holder.HolderCreator;

import static com.example.jingbin.cloudreader.viewmodel.gank.EverydayViewModel.getTodayTime;

/**
 * 每日推荐
 * 更新逻辑：判断是否是第二天(相对于2016-11-26)
 * 是：判断是否是大于12：30
 * *****     |是：刷新当天数据
 * *****     |否：使用缓存：|无：请求前一天数据,直到请求到数据为止
 * **********             |有：使用缓存
 * 否：使用缓存 ： |无：请求今天数据
 * **********    |有：使用缓存
 */
public class EverydayFragment extends BaseFragment<EverydayViewModel, FragmentEverydayBinding> {

    private HeaderItemEverydayBinding mHeaderBinding;
    private EverydayAdapter mEverydayAdapter;
    private boolean mIsPrepared = false;
    private RotateAnimation animation;
    private boolean isLoadBanner;

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
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    @Override
    protected void loadData() {
        if (mIsPrepared && isLoadBanner) {
            onResume();
        }
        if (!mIsVisible || !mIsPrepared) {
            return;
        }
        bindingView.recyclerView.postDelayed(() -> viewModel.loadData(), 150);
    }

    private void initRecyclerView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.header_item_everyday, null, false);
        mEverydayAdapter = new EverydayAdapter();
        bindingView.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bindingView.recyclerView.setLoadMoreEnabled(false);
        bindingView.recyclerView.setHasFixedSize(false);
        bindingView.recyclerView.addHeaderView(mHeaderBinding.getRoot());
        bindingView.recyclerView.addFooterView(R.layout.footer_item_everyday);
        bindingView.recyclerView.setAdapter(mEverydayAdapter);

        // 显示日期,去掉第一位的"0"
        String day = getTodayTime().get(2);
        mHeaderBinding.includeEveryday.tvDailyText.setText(day.indexOf("0") == 0 ? day.replace("0", "") : day);
        mHeaderBinding.includeEveryday.ibXiandu.setOnClickListener(listener);
        mHeaderBinding.includeEveryday.ibWanAndroid.setOnClickListener(listener);
        mHeaderBinding.includeEveryday.ibMovieHot.setOnClickListener(listener);
        mHeaderBinding.includeEveryday.flEveryday.setOnClickListener(listener);
        DensityUtil.setWidthHeight(mHeaderBinding.banner, DensityUtil.getDisplayWidth(), 2.5f);

        onObserveViewModel();
    }

    private void onObserveViewModel() {
        viewModel.getShowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                showRotaLoading(aBoolean);
            }
        });
        viewModel.getBannerData().observe(this, new Observer<EverydayViewModel.BannerDataBean>() {
            @Override
            public void onChanged(@Nullable EverydayViewModel.BannerDataBean bean) {
                if (bean != null && bean.getImageUrls() != null && bean.getImageUrls().size() > 0) {
                    mHeaderBinding.banner
                            .setAutoPlay(true)
                            .setOffscreenPageLimit(bean.getImageUrls().size())
                            .setPages(bean.getImageUrls(), new HolderCreator<SBannerViewHolder>() {
                                @Override
                                public SBannerViewHolder createViewHolder() {
                                    return new SBannerViewHolder<String>() {
                                        private ImageView imageView;

                                        @Override
                                        public View createView(Context context) {
                                            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_wanandroid, null);
                                            imageView = (ImageView) view.findViewById(R.id.iv_banner);
                                            return view;
                                        }

                                        @Override
                                        public void onBind(Context context, int position, String data) {
                                            DensityUtil.setWidthHeight(imageView, DensityUtil.getDisplayWidth(), 2.5f);
                                            GlideUtil.displayEspImage(data, imageView, 3);
                                        }
                                    };
                                }
                            }).start();
                    mHeaderBinding.banner.setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void onBannerClick(int position) {
                            if (bean.getList() != null
                                    && bean.getList().size() > 0
                                    && !TextUtils.isEmpty(bean.getList().get(position).getCode())
                                    && bean.getList().get(position).getCode().startsWith("http")) {
                                WebViewActivity.loadUrl(getContext(), bean.getList().get(position).getCode(), "加载中...");
                            }
                        }
                    });
                    isLoadBanner = true;
                }
            }
        });
        viewModel.getContentData().observe(this, new Observer<ArrayList<ArrayList<AndroidBean>>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ArrayList<AndroidBean>> lists) {
                if (lists != null && lists.size() > 0) {
                    mEverydayAdapter.setNewData(lists);
                } else {
                    showError();
                }
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
