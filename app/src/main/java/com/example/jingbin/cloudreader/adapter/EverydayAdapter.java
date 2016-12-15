package com.example.jingbin.cloudreader.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.databinding.ItemEverydayBinding;
import com.example.jingbin.cloudreader.http.rx.RxBus;
import com.example.jingbin.cloudreader.http.rx.RxCodeConstants;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.ImgLoadUtil;

import java.util.List;

/**
 * Created by jingbin on 2016/11/30.
 */

public class EverydayAdapter extends BaseRecyclerViewAdapter<List<AndroidBean>> {

    private List<AndroidBean> positionTempData;

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodler(parent, R.layout.item_everyday);
    }

    private class ViewHodler extends BaseRecyclerViewHolder<List<AndroidBean>, ItemEverydayBinding> {

        ViewHodler(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> positionData, int position) {
            if (positionData.size() > 0) {
                binding.llAll.setVisibility(View.VISIBLE);
                setAllHide();
                DebugUtil.error("---position: " + position + "--positionData.size():" + positionData.size());
                positionTempData = positionData;
                switch (position) {
                    case 0:
                        binding.tvTitleType.setText("Android");
                        binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_android));
                        break;
                    case 1:
                        binding.tvTitleType.setText("福利");
                        binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_meizi));
                        break;
                    case 2:
                        binding.tvTitleType.setText("IOS");
                        binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_ios));
                        break;
                    case 3:
                        binding.tvTitleType.setText("拓展资源");
                        binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_source));
                        break;
                    case 4:
                        binding.tvTitleType.setText("瞎推荐");
                        binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_xia));
                        break;
                    case 5:
                        binding.tvTitleType.setText("休息视频");
                        binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_movie));
                        break;
                    case 6:
                        binding.tvTitleType.setText("前端");
                        binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_qian));
                        break;
                    case 7:
                        binding.tvTitleType.setText("App");
                        binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_app));
                        break;
                }

                setOnclick(position);
                switch (positionData.size()) {
                    case 1:
                        binding.llOnePhoto.setVisibility(View.VISIBLE);
                        binding.llOnePhotoLine.setVisibility(View.GONE);
                        displayRandomImg(1, 0, position, binding.ivOnePhoto);

//                        displayImg(position, 0, binding.ivOnePhoto);
                        if (position == 1) {
                            binding.tvOnePhotoTitle.setVisibility(View.GONE);
                        } else {
                            binding.tvOnePhotoTitle.setVisibility(View.VISIBLE);
                            setDes(0, binding.tvOnePhotoTitle);
                        }
                        break;
                    case 2:
                        binding.llTwoOne.setVisibility(View.VISIBLE);
                        displayRandomImg(2, 0, position, binding.ivTwoOneOne);
                        displayRandomImg(2, 1, position, binding.ivTwoOneTwo);

//                        displayImg(position, 0, binding.ivTwoOneOne);
//                        displayImg(position, 1, binding.ivTwoOneTwo);
                        setDes(0, binding.tvTwoOneOneTitle);
                        setDes(1, binding.tvTwoOneTwoTitle);
                        break;
                    case 3:
                        binding.llTwoOne.setVisibility(View.VISIBLE);
                        binding.llOnePhoto.setVisibility(View.VISIBLE);
                        displayRandomImg(3, 0, position, binding.ivTwoOneOne);
                        displayRandomImg(3, 1, position, binding.ivTwoOneTwo);
                        displayRandomImg(3, 2, position, binding.ivOnePhoto);

//                        displayImg(position, 0, binding.ivTwoOneOne);
//                        displayImg(position, 1, binding.ivTwoOneTwo);
//                        displayImg(position, 2, binding.ivOnePhoto);
                        setDes(0, binding.tvTwoOneOneTitle);
                        setDes(1, binding.tvTwoOneTwoTitle);
                        setDes(2, binding.tvOnePhotoTitle);
                        break;
                    case 4:
                        binding.llTwoOne.setVisibility(View.VISIBLE);
                        binding.llTwoTwo.setVisibility(View.VISIBLE);
                        displayRandomImg(4, 0, position, binding.ivTwoOneOne);
                        displayRandomImg(4, 1, position, binding.ivTwoOneTwo);
                        displayRandomImg(4, 2, position, binding.ivTwoTwoOne);
                        displayRandomImg(4, 3, position, binding.ivTwoTwoTwo);

//                        displayImg(position, 0, binding.ivTwoOneOne);
//                        displayImg(position, 1, binding.ivTwoOneTwo);
//                        displayImg(position, 2, binding.ivTwoTwoOne);
//                        displayImg(position, 3, binding.ivTwoTwoTwo);
                        setDes(0, binding.tvTwoOneOneTitle);
                        setDes(1, binding.tvTwoOneTwoTitle);
                        setDes(2, binding.tvTwoTwoOneTitle);
                        setDes(3, binding.tvTwoTwoTwoTitle);

                        break;
                    case 5:
                        binding.llThreeOne.setVisibility(View.VISIBLE);
                        binding.llTwoOne.setVisibility(View.VISIBLE);
                        displayRandomImg(5, 0, position, binding.ivThreeOneOne);
                        displayRandomImg(5, 1, position, binding.ivThreeOneTwo);
                        displayRandomImg(5, 2, position, binding.ivThreeOneThree);
                        displayRandomImg(5, 3, position, binding.ivTwoOneOne);
                        displayRandomImg(5, 4, position, binding.ivTwoOneTwo);

//                        displayImg(position, 0, binding.ivThreeOneOne);
//                        displayImg(position, 1, binding.ivThreeOneTwo);
//                        displayImg(position, 2, binding.ivThreeOneThree);
//                        displayImg(position, 3, binding.ivTwoOneOne);
//                        displayImg(position, 4, binding.ivTwoOneTwo);
                        setDes(0, binding.tvThreeOneOneTitle);
                        setDes(1, binding.tvThreeOneTwoTitle);
                        setDes(2, binding.tvThreeOneThreeTitle);
                        setDes(3, binding.tvTwoOneOneTitle);
                        setDes(4, binding.tvTwoOneTwoTitle);
                        break;
                    case 6:
                        binding.llThreeOne.setVisibility(View.VISIBLE);
                        binding.llThreeTwo.setVisibility(View.VISIBLE);
                        // "6":6图情况；后面的是显示随机图片
                        displayRandomImg(6, 0, position, binding.ivThreeOneOne);
                        displayRandomImg(6, 1, position, binding.ivThreeOneTwo);
                        displayRandomImg(6, 2, position, binding.ivThreeOneThree);
                        displayRandomImg(6, 3, position, binding.ivThreeTwoOne);
                        displayRandomImg(6, 4, position, binding.ivThreeTwoTwo);
                        displayRandomImg(6, 5, position, binding.ivThreeTwoThree);

//                        displayImg(position, 0, binding.ivThreeOneOne);
//                        displayImg(position, 1, binding.ivThreeOneTwo);
//                        displayImg(position, 2, binding.ivThreeOneThree);
//                        displayImg(position, 3, binding.ivThreeTwoOne);
//                        displayImg(position, 4, binding.ivThreeTwoTwo);
//                        displayImg(position, 5, binding.ivThreeTwoThree);
                        setDes(0, binding.tvThreeOneOneTitle);
                        setDes(1, binding.tvThreeOneTwoTitle);
                        setDes(2, binding.tvThreeOneThreeTitle);
                        setDes(3, binding.tvThreeTwoOneTitle);
                        setDes(4, binding.tvThreeTwoTwoTitle);
                        setDes(5, binding.tvThreeTwoThreeTitle);
                        break;
                    default:
                        binding.llThreeOne.setVisibility(View.VISIBLE);
                        binding.llThreeTwo.setVisibility(View.VISIBLE);
                        // "6":6图情况；后面的是显示随机图片
                        displayRandomImg(1, 6, position, binding.ivThreeOneOne);
                        displayRandomImg(1, 6, position, binding.ivThreeOneTwo);
                        displayRandomImg(1, 6, position, binding.ivThreeOneThree);
                        displayRandomImg(1, 6, position, binding.ivThreeTwoOne);
                        displayRandomImg(1, 6, position, binding.ivThreeTwoTwo);
                        displayRandomImg(1, 6, position, binding.ivThreeTwoThree);
                        setDes(0, binding.tvThreeOneOneTitle);
                        setDes(1, binding.tvThreeOneTwoTitle);
                        setDes(2, binding.tvThreeOneThreeTitle);
                        setDes(3, binding.tvThreeTwoOneTitle);
                        setDes(4, binding.tvThreeTwoTwoTitle);
                        setDes(5, binding.tvThreeTwoThreeTitle);
                        break;
                }
            } else {
                binding.llAll.setVisibility(View.GONE);
                setAllHide();
                binding.rlTitle.setVisibility(View.GONE);
                binding.llSix.setVisibility(View.GONE);
                binding.viewLine.setVisibility(View.GONE);
            }
        }

        private void displayRandomImg(int imgNumber, int position, int itemPosition, ImageView imageView) {
            ImgLoadUtil.displayRandom(imgNumber, position, itemPosition, imageView);
        }


        private void displayImg(int position, int imgPosition, ImageView imageView) {


            switch (position) {
                case 0:// android
//                    ImgLoadUtil.displayRandom(imgNumber, position, imageView);
                    break;
                case 1://福利
                    ImgLoadUtil.display(positionTempData.get(imgPosition).getUrl(), binding.ivOnePhoto);
                    break;
                case 2://ios
                    break;

            }

            if (position == 1) {
                ImgLoadUtil.display(positionTempData.get(imgPosition).getUrl(), binding.ivOnePhoto);
            } else if (position == 0 || position == 2) {
                if (positionTempData != null && positionTempData.get(imgPosition) != null
                        && positionTempData.get(imgPosition).getImages() != null
                        && positionTempData.get(imgPosition).getImages().get(0) != null) {
//                    ImgLoadUtil.display(positionTempData.get(imgPosition).getImages().get(0), imageView);
//                    ImgLoadUtil.display("", imageView);
//                    ImgLoadUtil.displayRandom(imgNumber, position, imageView);
                } else {
//                    ImgLoadUtil.display("", imageView);
//                    ImgLoadUtil.displayRandom(imgNumber, position, imageView);
                }
            } else {
                ImgLoadUtil.display("", imageView);
            }
        }

        private void setDes(int position, TextView textView) {
            textView.setText(positionTempData.get(position).getDesc());
        }

        private void setAllHide() {
            binding.llOnePhoto.setVisibility(View.GONE);
            binding.llThreeOne.setVisibility(View.GONE);
            binding.llThreeTwo.setVisibility(View.GONE);
            binding.llTwoOne.setVisibility(View.GONE);
            binding.llTwoTwo.setVisibility(View.GONE);
        }

        private void setOnclick(final int position) {
            binding.llTitleMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position < 3) {
                        RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE, position);
                    }
                }
            });
        }
    }

}
