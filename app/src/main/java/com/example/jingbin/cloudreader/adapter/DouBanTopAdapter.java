package com.example.jingbin.cloudreader.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ItemDoubanTopBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2016/12/10.
 */

public class DouBanTopAdapter extends BaseBindingAdapter<SubjectsBean, ItemDoubanTopBinding> {

    private int width;

    public DouBanTopAdapter(Context context) {
        super(R.layout.item_douban_top);
        int px = DensityUtil.dip2px(context, 36);
        width = (DensityUtil.getDisplayWidth() - px) / 3;
    }

    @Override
    protected void bindView(BaseBindingHolder holder, SubjectsBean bean, ItemDoubanTopBinding binding, int position) {
        binding.setBean(bean);
        DensityUtil.setWidthHeight(binding.ivTopPhoto, width, 0.758f);
        binding.cvTopMovie.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.onClick(bean, binding.ivTopPhoto);
                }
            }
        });
        binding.cvTopMovie.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title = "Top" + (position + 1) + ": " + bean.getTitle();
                DialogBuild.showCustom(v, title, "查看详情", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onClick(bean, binding.ivTopPhoto);
                        }
                    }
                });
                return false;
            }
        });
    }

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(SubjectsBean bean, ImageView imageView);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }
}
