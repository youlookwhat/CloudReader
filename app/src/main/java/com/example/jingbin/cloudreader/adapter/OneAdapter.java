package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.moviechild.SubjectsBean;
import com.example.jingbin.cloudreader.databinding.ItemOneBinding;
import com.example.jingbin.cloudreader.ui.douban.OneMovieDetailActivity;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2016/11/25.
 */

public class OneAdapter extends BaseBindingAdapter<SubjectsBean, ItemOneBinding> {

    private Activity activity;

    public OneAdapter(Activity activity) {
        super(R.layout.item_one);
        this.activity = activity;
    }

    @Override
    protected void bindView(BaseBindingHolder holder, SubjectsBean positionData, ItemOneBinding binding, int position) {
        if (positionData != null) {
            binding.setSubjectsBean(positionData);
            binding.llOneItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    OneMovieDetailActivity.start(activity, positionData, binding.ivOnePhoto);
                }
            });

            // 图片
//                ImageLoadUtil.displayEspImage(positionData.getImages().getLarge(), binding.ivOnePhoto,0);
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

            ViewHelper.setScaleX(binding.getRoot(), 0.8f);
            ViewHelper.setScaleY(binding.getRoot(), 0.8f);
            ViewPropertyAnimator.animate(binding.getRoot()).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(binding.getRoot()).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

                /*binding.llOneItem.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {

                        OneMovieDetailActivity.start(activity, positionData, binding.ivOnePhoto);

//                        if (position % 2 == 0) {

//                            SlideScrollViewActivity.start(activity, positionData, binding.ivOnePhoto);

//                            MovieDetailActivity.start(activity, positionData, binding.ivOnePhoto);
//                            OneMovieDetailActivity.start(activity, positionData, binding.ivOnePhoto);

//                            activity.overridePendingTransition(R.anim.push_fade_out, R.anim.push_fade_in);
//                        } else {
//                            SlideScrollViewActivity.start(activity, positionData, binding.ivOnePhoto);
//                            SlideShadeViewActivity.start(activity, positionData, binding.ivOnePhoto);
//                            OneMovieDetailActivity.start(activity, positionData, binding.ivOnePhoto);
//                        }

                        // 这个可以
//                        SlideScrollViewActivity.start(activity, positionData, binding.ivOnePhoto);
//                        v.getContext().startActivity(new Intent(v.getContext(), SlideScrollViewActivity.class));

//                        SlideShadeViewActivity.start(activity, positionData, binding.ivOnePhoto);

                    }
                });*/
        }
    }
}
