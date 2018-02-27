package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.DuanZiBean;
import com.example.jingbin.cloudreader.databinding.ItemJokeBinding;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.utils.TimeUtil;

/**
 * Created by jingbin on 2016/11/25.
 */

public class JokeAdapter extends BaseRecyclerViewAdapter<DuanZiBean> {

    private Activity activity;

    public JokeAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_joke);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<DuanZiBean, ItemJokeBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final DuanZiBean bean, final int position) {
            if (bean != null) {
                binding.setBean(bean);
                binding.executePendingBindings();
                String time = TimeUtil.formatDataTime(Long.valueOf(bean.getCreateTime() + "000"));
                binding.setTime(time);
                binding.llItemTop.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DialogBuild.showItems(v,bean.getContent());
                        return false;
                    }
                });
            }
        }
    }
}
