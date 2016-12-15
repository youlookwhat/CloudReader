package com.example.jingbin.cloudreader.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ActivityMovieHeaderBinding;
import com.example.jingbin.cloudreader.view.statusbar.StatusBarUtil;

import java.util.List;


/**
 * Created by jingbin on 16/11/29.
 * <p>
 * 布局记得删掉
 * R.layout.header_layout  原来的图片头部布局
 * item_layout
 */

public class MovieDetailPersonAdapter extends RecyclerView.Adapter {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CONTENT = 1;
    private List<String> mData;
    private ImageView mHeaderView;
    private SubjectsBean subjectsBean;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_detail, null));
        } else {
            return new HeaderViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.activity_movie_header, null, false).getRoot());
        }
    }

    public ImageView getHeaderView() {
        return mHeaderView;
    }

    public void setData(List<String> data, SubjectsBean subjectsBean) {
        mData = data;
        this.subjectsBean = subjectsBean;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CONTENT) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.mTextView.setText(mData.get(position - 1));
        } else {
            Log.i("zhouwei", "初始化。。。。。");
            mHeaderView = ((HeaderViewHolder) holder).headerImage;
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 1 : 1 + mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_CONTENT;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView headerImage;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ActivityMovieHeaderBinding binding = DataBindingUtil.getBinding(itemView);
            // 绑定数据
            binding.setSubjectsBean(subjectsBean);
//            ImgLoadUtil.displayGaussian(itemView.getContext(), subjectsBean.getImages().getLarge(), binding.imgItemBg);
//            ImgLoadUtil.getInstance().displayEspImage(itemView.getContext(), subjectsBean.getImages().getLarge(), binding.ivOnePhoto);

            // 删掉图片的下面三个状态栏的高度
            if (binding.imgItemBg != null) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.imgItemBg.getLayoutParams();
                layoutParams.setMargins(0, -3 * StatusBarUtil.getStatusBarHeight(itemView.getContext()), 0, 0);
            }
        }
    }
}
