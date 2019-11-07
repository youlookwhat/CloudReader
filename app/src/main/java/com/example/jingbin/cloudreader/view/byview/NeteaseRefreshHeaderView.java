package com.example.jingbin.cloudreader.view.byview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;

import me.jingbin.library.BaseRefreshHeader;

/**
 * @author jingbin
 */
public class NeteaseRefreshHeaderView extends LinearLayout implements BaseRefreshHeader {

    private static final int ROTATE_ANIM_DURATION = 180;

    private TextView tvRefreshTip;
    private ImageView mIvArrow;
    private ImageView mProgress;
    private LinearLayout mContainer;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private int mState = STATE_NORMAL;
    private int mMeasuredHeight;
    private AnimationDrawable mAnimationDrawable;

    public NeteaseRefreshHeaderView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.refresh_view_netease, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        mIvArrow = (ImageView) mContainer.findViewById(R.id.iv_arrow);
        mProgress = (ImageView) mContainer.findViewById(R.id.iv_progress);
        tvRefreshTip = (TextView) mContainer.findViewById(R.id.tv_refresh_tip);
        mAnimationDrawable = (AnimationDrawable) mProgress.getDrawable();
        measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);

        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) {
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public void setState(int state) {
        if (state == mState) {
            return;
        }

        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        tvRefreshTip.setVisibility(VISIBLE);
        if (state == STATE_REFRESHING) {
            // show progress
            mIvArrow.clearAnimation();
            mIvArrow.setVisibility(View.INVISIBLE);
            mProgress.setVisibility(View.VISIBLE);
            smoothScrollTo(mMeasuredHeight);
        } else if (state == STATE_DONE) {
            mIvArrow.setVisibility(View.INVISIBLE);
            mProgress.setVisibility(View.INVISIBLE);
            tvRefreshTip.setVisibility(INVISIBLE);
        } else {
            // show arrow image
            mIvArrow.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.INVISIBLE);
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_RELEASE_TO_REFRESH) {
                    mIvArrow.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mIvArrow.clearAnimation();
                }
                tvRefreshTip.setText(R.string.custom_header_hint_normal);
                break;
            case STATE_RELEASE_TO_REFRESH:
                mIvArrow.clearAnimation();
                mIvArrow.startAnimation(mRotateUpAnim);
                tvRefreshTip.setText(R.string.custom_header_hint_release);
                break;
            case STATE_REFRESHING:
                tvRefreshTip.setText(R.string.custom_refreshing);
                if (!mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.start();
                }
                break;
            case STATE_DONE:
                tvRefreshTip.setText(R.string.custom_refresh_done);
                break;
            default:
        }
        mState = state;
    }


    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;

        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        int destHeight = 0;
        if (mState == STATE_REFRESHING) {
            // 处理刷新中时，让其定位到 destHeight 高度
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;
    }

    @Override
    public void refreshComplete() {
        setState(STATE_DONE);
        setState(STATE_NORMAL);
        smoothScrollTo(0);
    }

    private void smoothScrollTo(final int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (destHeight == 0) {
                    setState(STATE_NORMAL);
                }
            }
        });
        animator.start();
    }

    private void setVisibleHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    @Override
    public int getVisibleHeight() {
        return mContainer.getHeight();
    }

    @Override
    public int getState() {
        return mState;
    }
}
