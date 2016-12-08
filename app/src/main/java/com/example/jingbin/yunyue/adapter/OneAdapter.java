package com.example.jingbin.yunyue.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.yunyue.MainActivity;
import com.example.jingbin.yunyue.R;
import com.example.jingbin.yunyue.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.yunyue.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.yunyue.bean.moviechild.SubjectsBean;
import com.example.jingbin.yunyue.databinding.ItemOneBinding;
import com.example.jingbin.yunyue.ui.one.SlideScrollViewActivity;
import com.example.jingbin.yunyue.ui.one.SlideShadeViewActivity;
import com.example.jingbin.yunyue.utils.CommonUtils;
import com.example.jingbin.yunyue.utils.ImgLoadUtil;
import com.example.jingbin.yunyue.utils.PerfectClickListener;
import com.example.jingbin.yunyue.utils.StringFormatUtil;

/**
 * Created by jingbin on 2016/11/25.
 */

public class OneAdapter extends BaseRecyclerViewAdapter<SubjectsBean> {

    private Activity activity;

    public OneAdapter(MainActivity activity) {
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
                // 图片
                ImgLoadUtil.displayEspImage(positionData.getImages().getLarge(), binding.ivOnePhoto,0);
                // 导演
                binding.tvOneDirectors.setText(StringFormatUtil.formatName(positionData.getDirectors()));
                // 主演
                binding.tvOneCasts.setText(StringFormatUtil.formatName(positionData.getCasts()));
                // 类型
                binding.tvOneGenres.setText("类型：" + StringFormatUtil.formatGenres(positionData.getGenres()));
                // 评分
                binding.tvOneRatingRate.setText("评分：" + String.valueOf(positionData.getRating().getAverage()));
                // 分割线颜色
                binding.viewColor.setBackgroundColor(CommonUtils.randomColor());

                binding.llOneItem.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        if (position % 2 == 0) {

                            SlideScrollViewActivity.start(activity, positionData, binding.ivOnePhoto);

//                            TestActivity.start(activity, positionData, binding.ivOnePhoto);
//                            activity.overridePendingTransition(R.anim.push_fade_out, R.anim.push_fade_in);
                        } else {
//                            SlideScrollViewActivity.start(activity, positionData, binding.ivOnePhoto);
                            SlideShadeViewActivity.start(activity, positionData, binding.ivOnePhoto);
                        }

                        // 这个可以
//                        SlideScrollViewActivity.start(activity, positionData, binding.ivOnePhoto);
//                        TestActivity.start(activity,positionData,binding.ivOnePhoto);
//                        v.getContext().startActivity(new Intent(v.getContext(), SlideScrollViewActivity.class));

//                        SlideShadeViewActivity.start(activity, positionData, binding.ivOnePhoto);

                    }
                });
            }
        }
    }
}
