package com.example.jingbin.cloudreader.view;

/**
 * @author jingbin
 * @data 2019/1/19
 * @description
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 可以取消底部分割线的ItemDecoration
 * copied from DividerItemDecoration
 * mIsShowBottomDivider  false 不显示底部分割线
 * mIsShowFirstDivider   false 不显示第一个item的分割线
 * mIsShowSecondDivider  false 不显示第二个item的分割线
 *
 * @author Ethan Lee
 */
public class MyDividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private static final String TAG = "DividerItem";
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private boolean mIsShowBottomDivider;
    private boolean mIsShowFirstDivider;
    private boolean mIsShowSecondDivider;
    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;
    private final Rect mBounds = new Rect();

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link android.support.v7.widget.LinearLayoutManager}.
     *
     * @param context             Current context, it will be used to access resources.
     * @param orientation         Divider orientation. Should be {@link #HORIZONTAL} or
     *                            {@link #VERTICAL}.
     * @param isShowBottomDivider true show bottom divider false not show bottom divider
     */
    public MyDividerItemDecoration(Context context, int orientation, boolean isShowBottomDivider) {
        this(context, orientation, isShowBottomDivider, true, true);
    }

    public MyDividerItemDecoration(Context context, int orientation,
                                   boolean isShowBottomDivider, boolean isShowFirstDivider, boolean isShowSecondDivider) {
        mIsShowBottomDivider = isShowBottomDivider;
        mIsShowFirstDivider = isShowFirstDivider;
        mIsShowSecondDivider = isShowSecondDivider;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this "
                    + "DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        a.recycle();
        setOrientation(orientation);
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent, state);
        } else {
            drawHorizontal(c, parent, state);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        canvas.save();
        final int left;
        final int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        final int lastPosition = state.getItemCount() - 1;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int childRealPosition = parent.getChildAdapterPosition(child);
            // mIsShowFirstDivider false绘制第一个view的divider
            if (childRealPosition == 0 && !mIsShowFirstDivider) {
                continue;
            }
            // mIsShowSecondDivider false绘制第二个view的divider
            if (childRealPosition == 1 && !mIsShowSecondDivider) {
                continue;
            }
            // mIsShowBottomDivider false的时候不绘制最后一个view的divider
            if (mIsShowBottomDivider || childRealPosition < lastPosition) {
                parent.getDecoratedBoundsWithMargins(child, mBounds);
                final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                final int top = bottom - mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        canvas.save();
        final int top;
        final int bottom;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        final int lastPosition = state.getItemCount() - 1;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int childRealPosition = parent.getChildAdapterPosition(child);
            // mIsShowFirstDivider false绘制第一个view的divider
            if (childRealPosition == 0 && !mIsShowFirstDivider) {
                continue;
            }
            // mIsShowSecondDivider false绘制第二个view的divider
            if (childRealPosition == 1 && !mIsShowSecondDivider) {
                continue;
            }
            //mIsShowBottomDivider false的时候不绘制最后一个view的divider
            if (mIsShowBottomDivider || childRealPosition < lastPosition) {
                parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
                final int right = mBounds.right + Math.round(child.getTranslationX());
                final int left = right - mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == VERTICAL) {
            //parent.getChildCount() 不能拿到item的总数
            //https://stackoverflow.com/questions/29666598/android-recyclerview-finding-out-first
            // -and-last-view-on-itemdecoration
            int lastPosition = state.getItemCount() - 1;
            int position = parent.getChildAdapterPosition(view);
            if (mIsShowBottomDivider || position < lastPosition) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, 0, 0);
            }
        } else {
            int lastPosition = state.getItemCount() - 1;
            int position = parent.getChildAdapterPosition(view);
            if (mIsShowBottomDivider || position < lastPosition) {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }
}
