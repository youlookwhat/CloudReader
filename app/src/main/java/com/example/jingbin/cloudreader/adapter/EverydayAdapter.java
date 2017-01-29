package com.example.jingbin.cloudreader.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.databinding.ItemEverydayOneBinding;
import com.example.jingbin.cloudreader.databinding.ItemEverydayThreeBinding;
import com.example.jingbin.cloudreader.databinding.ItemEverydayTitleBinding;
import com.example.jingbin.cloudreader.databinding.ItemEverydayTwoBinding;
import com.example.jingbin.cloudreader.http.rx.RxBus;
import com.example.jingbin.cloudreader.http.rx.RxCodeConstants;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.ImgLoadUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

import java.util.List;

/**
 * Created by jingbin on 2016/12/27.
 */

public class EverydayAdapter extends BaseRecyclerViewAdapter<List<AndroidBean>> {

    private static final int TYPE_TITLE = 1; // title
    private static final int TYPE_ONE = 2;// 一张图
    private static final int TYPE_TWO = 3;// 二张图
    private static final int TYPE_THREE = 4;// 三张图

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(getData().get(position).get(0).getType_title())) {
            return TYPE_TITLE;
        } else if (getData().get(position).size() == 1) {
            return TYPE_ONE;
        } else if (getData().get(position).size() == 2) {
            return TYPE_TWO;
        } else if (getData().get(position).size() == 3) {
            return TYPE_THREE;
        }
        return super.getItemViewType(position);
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                return new TitleHolder(parent, R.layout.item_everyday_title);
            case TYPE_ONE:
                return new OneHolder(parent, R.layout.item_everyday_one);
            case TYPE_TWO:
                return new TwoHolder(parent, R.layout.item_everyday_two);
            default:
                return new ThreeHolder(parent, R.layout.item_everyday_three);
        }
    }

    private class TitleHolder extends BaseRecyclerViewHolder<List<AndroidBean>, ItemEverydayTitleBinding> {

        TitleHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, final int position) {
            int index = 0;
            String title = object.get(0).getType_title();
            binding.tvTitleType.setText(title);
            if ("Android".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_android));
                index = 0;
            } else if ("福利".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_meizi));
                index = 1;
            } else if ("IOS".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_ios));
                index = 2;
            } else if ("休息视频".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_movie));
                index = 2;
            } else if ("拓展资源".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_source));
                index = 2;
            } else if ("瞎推荐".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_xia));
                index = 2;
            } else if ("前端".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_qian));
                index = 2;
            } else if ("App".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_app));
                index = 2;
            }

            if (position != 0) {
                binding.viewLine.setVisibility(View.VISIBLE);
            } else {
                binding.viewLine.setVisibility(View.GONE);
            }

            final int finalIndex = index;
            binding.llTitleMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE, finalIndex);
                }
            });
        }
    }

    private class OneHolder extends BaseRecyclerViewHolder<List<AndroidBean>, ItemEverydayOneBinding> {

        OneHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final List<AndroidBean> object, int position) {
            if ("福利".equals(object.get(0).getType())) {
                binding.tvOnePhotoTitle.setVisibility(View.GONE);
                binding.ivOnePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                ImgLoadUtil.displayEspImage(object.get(0).getUrl(), binding.ivOnePhoto, 1);
                Glide.with(binding.ivOnePhoto.getContext())
                        .load(object.get(0).getUrl())
                        .crossFade(1500)
                        .placeholder(R.drawable.img_two_bi_one)
                        .error(R.drawable.img_two_bi_one)
                        .into(binding.ivOnePhoto);

            } else {
                binding.tvOnePhotoTitle.setVisibility(View.VISIBLE);
                setDes(object, 0, binding.tvOnePhotoTitle);
                displayRandomImg(1, 0, binding.ivOnePhoto, object);
            }
            setOnClick(binding.llOnePhoto, object.get(0));
        }
    }

    private class TwoHolder extends BaseRecyclerViewHolder<List<AndroidBean>, ItemEverydayTwoBinding> {

        TwoHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, int position) {
            displayRandomImg(2, 0, binding.ivTwoOneOne, object);
            displayRandomImg(2, 1, binding.ivTwoOneTwo, object);
            setDes(object, 0, binding.tvTwoOneOneTitle);
            setDes(object, 1, binding.tvTwoOneTwoTitle);
            setOnClick(binding.llTwoOneOne, object.get(0));
            setOnClick(binding.llTwoOneTwo, object.get(1));
        }
    }

    private class ThreeHolder extends BaseRecyclerViewHolder<List<AndroidBean>, ItemEverydayThreeBinding> {

        ThreeHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, int position) {
            displayRandomImg(3, 0, binding.ivThreeOneOne, object);
            displayRandomImg(3, 1, binding.ivThreeOneTwo, object);
            displayRandomImg(3, 2, binding.ivThreeOneThree, object);
            setOnClick(binding.llThreeOneOne, object.get(0));
            setOnClick(binding.llThreeOneTwo, object.get(1));
            setOnClick(binding.llThreeOneThree, object.get(2));
            setDes(object, 0, binding.tvThreeOneOneTitle);
            setDes(object, 1, binding.tvThreeOneTwoTitle);
            setDes(object, 2, binding.tvThreeOneThreeTitle);
        }
    }

    private void setDes(List<AndroidBean> object, int position, TextView textView) {
        textView.setText(object.get(position).getDesc());
    }

    private void displayRandomImg(int imgNumber, int position, ImageView imageView, List<AndroidBean> object) {
//        DebugUtil.error("-----Image_url: "+object.get(position).getImage_url());
        ImgLoadUtil.displayRandom(imgNumber, object.get(position).getImage_url(), imageView);
    }


    private void setOnClick(final LinearLayout linearLayout, final AndroidBean bean) {
        linearLayout.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), bean.getUrl(), "加载中...");
            }
        });

        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = View.inflate(v.getContext(), R.layout.title_douban_top, null);
                TextView titleTop = (TextView) view.findViewById(R.id.title_top);
                titleTop.setTextSize(14);
                String title = TextUtils.isEmpty(bean.getType()) ? bean.getDesc() : bean.getType() + "：  " + bean.getDesc();
                titleTop.setText(title);
                builder.setCustomTitle(view);
                builder.setPositiveButton("查看详情", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WebViewActivity.loadUrl(linearLayout.getContext(), bean.getUrl(), "加载中...");
                    }
                });
                builder.show();
                return false;
            }
        });

    }
}
