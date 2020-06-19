package com.example.jingbin.cloudreader.adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeItemBean;
import com.example.jingbin.cloudreader.bean.wanandroid.WxarticleItemBean;
import com.example.jingbin.cloudreader.ui.wan.child.CategoryDetailActivity;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DataUtil;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.view.ThinBoldSpan;
import com.google.android.flexbox.FlexboxLayout;

import java.util.LinkedList;
import java.util.Queue;

import me.jingbin.library.adapter.BaseByViewHolder;
import me.jingbin.library.adapter.BaseRecyclerAdapter;

/**
 * Update by jingbin on 2020/1/03.
 */

public class TreeAdapter extends BaseRecyclerAdapter<TreeItemBean> {

    private LayoutInflater mInflater = null;
    private Queue<TextView> mFlexItemTextViewCaches = new LinkedList<>();
    private boolean isSelect = false;
    private int selectedPosition = -1;

    public TreeAdapter() {
        super(R.layout.item_tree);
    }

    @Override
    protected void bindView(BaseByViewHolder<TreeItemBean> holder, TreeItemBean dataBean, int position) {
        if (dataBean != null) {
            TextView tvTreeTitle = holder.getView(R.id.tv_tree_title);
            FlexboxLayout flTree = holder.getView(R.id.fl_tree);
            String name = DataUtil.getHtmlString(dataBean.getName());
            if (isSelect) {
                flTree.setVisibility(View.GONE);
                if (selectedPosition == position) {
                    name = name + "     ★★★";
                    tvTreeTitle.setTextColor(CommonUtils.getColor(R.color.colorTheme));
                } else {
                    tvTreeTitle.setTextColor(CommonUtils.getColor(R.color.colorContent));
                }
            } else {
                tvTreeTitle.setTextColor(CommonUtils.getColor(R.color.colorContent));
                flTree.setVisibility(View.VISIBLE);
                for (int i = 0; i < dataBean.getChildren().size(); i++) {
                    WxarticleItemBean childItem = dataBean.getChildren().get(i);
                    TextView child = createOrGetCacheFlexItemTextView(flTree);
                    child.setText(DataUtil.getHtmlString(childItem.getName()));
                    child.setOnClickListener(v -> CategoryDetailActivity.start(v.getContext(), childItem.getId(), dataBean));
                    flTree.addView(child);
                }
            }
            tvTreeTitle.setText(ThinBoldSpan.getDefaultSpanString(tvTreeTitle.getContext(), name));
        }
    }

    /**
     * 复用需要有相同的BaseByViewHolder，且HeaderView部分获取不到FlexboxLayout，需要容错
     */
    @Override
    public void onViewRecycled(@NonNull BaseByViewHolder<TreeItemBean> holder) {
        super.onViewRecycled(holder);
        FlexboxLayout fbl = holder.getView(R.id.fl_tree);
        if (fbl != null) {
            for (int i = 0; i < fbl.getChildCount(); i++) {
                mFlexItemTextViewCaches.offer((TextView) fbl.getChildAt(i));
            }
            fbl.removeAllViews();
        }
    }

    private TextView createOrGetCacheFlexItemTextView(FlexboxLayout fbl) {
        TextView tv = mFlexItemTextViewCaches.poll();
        if (tv != null) {
            return tv;
        }
        if (mInflater == null) {
            mInflater = LayoutInflater.from(fbl.getContext());
        }
        return (TextView) mInflater.inflate(R.layout.layout_tree_tag, fbl, false);
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
        if (isSelect) {
            selectedPosition = SPUtils.getInt(Constants.FIND_POSITION, -1);
        }
    }

    public boolean isSelect() {
        return isSelect;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
