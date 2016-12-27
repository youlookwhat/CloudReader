package com.example.jingbin.cloudreader.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.list.BaseDataBindingHolder;
import com.example.jingbin.cloudreader.base.list.BaseHolder;
import com.example.jingbin.cloudreader.base.list.BaseListAdapter;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.databinding.ItemEverydayOldBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.ImgLoadUtil;

import java.util.List;

/**
 * Created by jingbin on 2016/12/1.
 */

public class EverydayListAdapter extends BaseListAdapter<List<AndroidBean>> {

    private List<AndroidBean> positionTempData;

    @Override
    protected BaseHolder creatHolder(ViewGroup parent, int position) {
        return new ViewHodler(parent.getContext(), R.layout.item_everyday_old);
    }

    class ViewHodler extends BaseDataBindingHolder<List<AndroidBean>, ItemEverydayOldBinding>{

        public ViewHodler(Context context, int layoutID) {
            super(context, layoutID);
        }

        @Override
        public void onBindView(List<AndroidBean> positionData, int position) {
            setAllHide();
            DebugUtil.error("---position: " + position + "--positionData.size():" + positionData.size());
            if (positionData.size() < 1) {
                binding.llAll.setVisibility(View.GONE);
                return;
            }
            positionTempData = positionData;
            switch (position) {
                case 0:
                    binding.tvTitleType.setText("福利");
                    binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_meizi));
                    break;
                case 1:
                    binding.tvTitleType.setText("Android");
                    binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_android));
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

            switch (positionData.size()) {
                case 1:
                    binding.llOnePhoto.setVisibility(View.VISIBLE);
                    binding.llOnePhotoLine.setVisibility(View.GONE);
                    displayImg(position, 0, binding.ivOnePhoto);
                    setDes(binding.tvOnePhotoTitle,0);
//                    ViewGroup.LayoutParams lpBigImage = binding.ivOnePhoto.getLayoutParams();
//                    lpBigImage.width = BaseTools.getWindowWidth(itemView.getContext()) - DensityUtil.dip2px(itemView.getContext(), 20);
//                    lpBigImage.height = lpBigImage.width / 2;
//                    llInfoThreeImage.setLayoutParams(lpBigImage);

                    break;
                case 2:
                    binding.llTwoOne.setVisibility(View.VISIBLE);
                    displayImg(position, 0, binding.ivTwoOneOne);
                    setDes(binding.tvTwoOneOneTitle,0);
                    displayImg(position, 1, binding.ivTwoOneTwo);
                    setDes(binding.tvTwoOneTwoTitle,1);
                    break;
                case 3:
                    binding.llTwoOne.setVisibility(View.VISIBLE);
                    binding.llOnePhoto.setVisibility(View.VISIBLE);
                    displayImg(position, 0, binding.ivTwoOneOne);
                    setDes(binding.tvTwoOneOneTitle,0);
                    displayImg(position, 1, binding.ivTwoOneTwo);
                    setDes(binding.tvTwoOneTwoTitle,1);
                    displayImg(position, 2, binding.ivOnePhoto);
                    setDes(binding.tvOnePhotoTitle,2);
                    break;
                case 4:
                    binding.llTwoOne.setVisibility(View.VISIBLE);
                    binding.llTwoTwo.setVisibility(View.VISIBLE);
                    displayImg(position, 0, binding.ivTwoOneOne);
                    setDes(binding.tvTwoOneOneTitle,0);
                    displayImg(position, 1, binding.ivTwoOneTwo);
                    setDes(binding.tvTwoOneTwoTitle,1);
                    displayImg(position, 2, binding.ivTwoTwoOne);
                    setDes(binding.tvTwoTwoOneTitle,2);
                    displayImg(position, 3, binding.ivTwoTwoTwo);
                    setDes(binding.tvTwoTwoTwoTitle,3);

                    break;
                case 5:
                    binding.llThreeOne.setVisibility(View.VISIBLE);
                    binding.llTwoOne.setVisibility(View.VISIBLE);
                    displayImg(position, 0, binding.ivThreeOneOne);
                    setDes(binding.tvThreeOneOneTitle,0);
                    displayImg(position, 1, binding.ivThreeOneTwo);
                    setDes(binding.tvThreeOneTwoTitle,1);
                    displayImg(position, 2, binding.ivThreeOneThree);
                    setDes(binding.tvThreeOneThreeTitle,2);
                    displayImg(position, 3, binding.ivTwoOneOne);
                    setDes(binding.tvTwoOneOneTitle,3);
                    displayImg(position, 4, binding.ivTwoOneTwo);
                    setDes(binding.tvTwoOneTwoTitle,4);
                    break;
                case 6:
                    binding.llThreeOne.setVisibility(View.VISIBLE);
                    binding.llThreeTwo.setVisibility(View.VISIBLE);
                    displayImg(position, 0, binding.ivThreeOneOne);
                    setDes(binding.tvThreeOneOneTitle,0);
                    displayImg(position, 1, binding.ivThreeOneTwo);
                    setDes(binding.tvThreeOneTwoTitle,1);
                    displayImg(position, 2, binding.ivThreeOneThree);
                    setDes(binding.tvThreeOneThreeTitle,2);
                    displayImg(position, 3, binding.ivThreeTwoOne);
                    setDes(binding.tvThreeTwoOneTitle,3);
                    displayImg(position, 4, binding.ivThreeTwoTwo);
                    setDes(binding.tvThreeTwoTwoTitle,4);
                    displayImg(position, 5, binding.ivThreeTwoThree);
                    setDes(binding.tvThreeTwoThreeTitle,5);
                    break;
                default:
                    binding.llOnePhoto.setVisibility(View.VISIBLE);
                    binding.llOnePhotoLine.setVisibility(View.GONE);
                    displayImg(position, 0, binding.ivOnePhoto);
                    break;
            }
        }

        private void displayImg(int position, int imgPosition, ImageView imageView) {
            if (position == 0) {
                ImgLoadUtil.display(positionTempData.get(imgPosition).getUrl(), binding.ivOnePhoto);
            } else if (position == 1 || position == 2) {
                if (positionTempData != null && positionTempData.get(imgPosition) != null
                        && positionTempData.get(imgPosition).getImages() != null
                        && positionTempData.get(imgPosition).getImages().get(0) != null) {
                    ImgLoadUtil.display(positionTempData.get(imgPosition).getImages().get(0), imageView);
                } else {
                    ImgLoadUtil.display("", imageView);
                }
            } else {
                ImgLoadUtil.display("", imageView);
            }
        }

        private void setDes(TextView textView, int position){
            textView.setText(positionTempData.get(position).getDesc());
        }

        private void setAllHide() {
            binding.llOnePhoto.setVisibility(View.GONE);
            binding.llThreeOne.setVisibility(View.GONE);
            binding.llThreeTwo.setVisibility(View.GONE);
            binding.llTwoOne.setVisibility(View.GONE);
            binding.llTwoTwo.setVisibility(View.GONE);
        }
    }

}
