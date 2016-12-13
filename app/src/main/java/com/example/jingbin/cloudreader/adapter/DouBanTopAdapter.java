package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ItemDoubanTopBinding;
import com.example.jingbin.cloudreader.ui.one.OneMovieDetailActivity;
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    View view = View.inflate(v.getContext(), R.layout.title_douban_top, null);
                    TextView titleTop = (TextView) view.findViewById(R.id.title_top);
                    titleTop.setText("Top" + (position + 1) + ": " + bean.getTitle());
                    builder.setCustomTitle(view);
                    builder.setPositiveButton("查看详情", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            OneMovieDetailActivity.start(activity, bean, binding.ivTopPhoto);
                        }
                    });
                    builder.show();
                    return false;
                }
            });
        }
    }
}
