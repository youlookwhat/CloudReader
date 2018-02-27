package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ItemDoubanTopBinding;
import com.example.jingbin.cloudreader.ui.one.OneMovieDetailActivity;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;

/**
 * Created by jingbin on 2016/12/10.
 */

public class DouBanTopAdapter extends BaseRecyclerViewAdapter<SubjectsBean> {


    private Activity activity;

    public DouBanTopAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_douban_top);
    }

    class ViewHolder extends BaseRecyclerViewHolder<SubjectsBean, ItemDoubanTopBinding> {

        ViewHolder(ViewGroup parent, int layout) {
            super(parent, layout);
        }

        @Override
        public void onBindViewHolder(final SubjectsBean bean, final int position) {
            binding.setBean(bean);
            /**
             * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
             */
            binding.executePendingBindings();
            binding.llItemTop.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    OneMovieDetailActivity.start(activity, bean, binding.ivTopPhoto);
                }
            });
            binding.llItemTop.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String title = "Top" + (position + 1) + ": " + bean.getTitle();
                    DialogBuild.show(v, title, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            OneMovieDetailActivity.start(activity, bean, binding.ivTopPhoto);
                        }
                    });
                    return false;
                }
            });
        }
    }
}
