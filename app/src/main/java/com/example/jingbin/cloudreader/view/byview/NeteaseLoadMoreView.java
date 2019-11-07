package com.example.jingbin.cloudreader.view.byview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.jingbin.cloudreader.R;

import me.jingbin.library.BaseLoadMore;


/**
 * @author jingbin
 */
public class NeteaseLoadMoreView extends LinearLayout implements BaseLoadMore {

    private View viewBottom;
    private boolean isShowLoadingMoreHeight = false;
    private TextView tvNoMore;
    private TextView tvMoreFailed;
    private LinearLayout llMoreLoading;
    private AnimationDrawable mAnimationDrawable;

    public NeteaseLoadMoreView(Context context) {
        super(context);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.load_more_view_netease, this);
        viewBottom = findViewById(R.id.view_bottom);
        llMoreLoading = findViewById(R.id.ll_more_loading);
        tvNoMore = findViewById(R.id.tv_no_more);
        tvMoreFailed = findViewById(R.id.tv_more_failed);
        ImageView mIvProgress = findViewById(R.id.iv_progress);
        mAnimationDrawable = (AnimationDrawable) mIvProgress.getDrawable();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setState(int state) {
        this.setVisibility(View.VISIBLE);

        switch (state) {
            case STATE_LOADING:
                llMoreLoading.setVisibility(VISIBLE);
                tvNoMore.setVisibility(GONE);
                tvMoreFailed.setVisibility(GONE);
                if (!mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.start();
                }
                break;
            case STATE_COMPLETE:
                llMoreLoading.setVisibility(VISIBLE);
                tvNoMore.setVisibility(GONE);
                tvMoreFailed.setVisibility(GONE);
                this.setVisibility(View.GONE);
                if (mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }
                break;
            case STATE_NO_MORE:
                tvNoMore.setVisibility(VISIBLE);
                llMoreLoading.setVisibility(GONE);
                tvMoreFailed.setVisibility(GONE);
                if (mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }
                break;
            case STATE_FAILURE:
                tvMoreFailed.setVisibility(VISIBLE);
                llMoreLoading.setVisibility(GONE);
                tvNoMore.setVisibility(GONE);
                if (mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }
                break;
            default:
                break;
        }
        if (isShowLoadingMoreHeight) {
            viewBottom.setVisibility(View.VISIBLE);
        } else {
            viewBottom.setVisibility(View.GONE);
        }
    }

    /**
     * 为了部分页面底部实现透明效果，这里提高一个底部栏的高度
     * 如果没有可不用理会
     */
    @Override
    public void setLoadingMoreBottomHeight(float heightDp) {
        if (heightDp > 0) {
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, dip2px(heightDp));
            viewBottom.setLayoutParams(lp);
            isShowLoadingMoreHeight = true;
        }
    }

    /**
     * 得到失败的布局，给设置点击重试
     */
    @Override
    public View getFailureView() {
        return tvMoreFailed;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
