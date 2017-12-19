package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ItemOneBinding;
import com.example.jingbin.cloudreader.ui.one.OneMovieDetailActivity;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by jingbin on 2016/11/25.
 */

public class OneAdapter extends BaseRecyclerViewAdapter<SubjectsBean> {

    private Activity activity;

    public OneAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_one);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<SubjectsBean, ItemOneBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final SubjectsBean positionData, final int position) {
            if (positionData != null) {
                binding.setSubjectsBean(positionData);
                binding.setCallback(subjectsBean -> OneMovieDetailActivity.start(activity, positionData, binding.ivOnePhoto));
                binding.executePendingBindings();

                // 图片
//                ImgLoadUtil.displayEspImage(positionData.getImages().getLarge(), binding.ivOnePhoto,0);
                // 导演
//                binding.tvOneDirectors.setText(StringFormatUtil.formatName(positionData.getDirectors()));
                // 主演
//                binding.tvOneCasts.setText(StringFormatUtil.formatName(positionData.getCasts()));
                // 类型
//                binding.tvOneGenres.setText("类型：" + StringFormatUtil.formatGenres(positionData.getGenres()));
                // 评分
//                binding.tvOneRatingRate.setText("评分：" + String.valueOf(positionData.getRating().getAverage()));
                // 分割线颜色
//                binding.viewColor.setBackgroundColor(CommonUtils.randomColor());

                ViewHelper.setScaleX(itemView,0.8f);
                ViewHelper.setScaleY(itemView,0.8f);
                ViewPropertyAnimator.animate(itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
                ViewPropertyAnimator.animate(itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

                /*binding.llOneItem.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {

                        OneMovieDetailActivity.start(activity, positionData, binding.ivOnePhoto);

//                        if (position % 2 == 0) {

//                            SlideScrollViewActivity.start(activity, positionData, binding.ivOnePhoto);

//                            MovieDetailActivity.start(activity, positionData, binding.ivOnePhoto);
//                            OneMovieDetailActivity.start(activity, positionData, binding.ivOnePhoto);

//                            TestActivity.start(activity, positionData, binding.ivOnePhoto);
//                            activity.overridePendingTransition(R.anim.push_fade_out, R.anim.push_fade_in);
//                        } else {
//                            SlideScrollViewActivity.start(activity, positionData, binding.ivOnePhoto);
//                            SlideShadeViewActivity.start(activity, positionData, binding.ivOnePhoto);
//                            OneMovieDetailActivity.start(activity, positionData, binding.ivOnePhoto);
//                        }

                        // 这个可以
//                        SlideScrollViewActivity.start(activity, positionData, binding.ivOnePhoto);
//                        TestActivity.start(activity,positionData,binding.ivOnePhoto);
//                        v.getContext().startActivity(new Intent(v.getContext(), SlideScrollViewActivity.class));

//                        SlideShadeViewActivity.start(activity, positionData, binding.ivOnePhoto);

                    }
                });*/
            }
        }
    }
}
