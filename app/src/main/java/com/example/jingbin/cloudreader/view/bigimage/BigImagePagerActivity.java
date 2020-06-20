package com.example.jingbin.cloudreader.view.bigimage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.SharedElementCallback;
import androidx.viewpager.widget.PagerAdapter;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;
import com.example.jingbin.cloudreader.R;
import me.jingbin.bymvvm.rxbus.RxBus;
import com.example.jingbin.cloudreader.app.RxCodeConstants;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017/10/2.
 *
 * @author ice
 * @GitHub https://github.com/XunMengWinter
 */

public class BigImagePagerActivity extends AppCompatActivity {

    public static final String KEY_IMAGE_URLS = "image_urls";
    public static final String KEY_ENTER_INDEX = "enter_index";
    public static Integer sExitIndex;

    private List<String> mImageUrls;
    private int mEnterIndex;

    private ViewPager mViewPager;
    private SparseArray<PhotoView> mPhotoViewMap;

    public static void startThis(final AppCompatActivity activity, List<View> imageViews, List<String> imageUrls, int enterIndex) {
        Intent intent = new Intent(activity, BigImagePagerActivity.class);
        intent.putStringArrayListExtra(KEY_IMAGE_URLS, (ArrayList<String>) imageUrls);
        intent.putExtra(KEY_ENTER_INDEX, enterIndex);

        ActivityOptionsCompat optionsCompat
                = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageViews.get(enterIndex), imageUrls.get(enterIndex));

        try {
            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            activity.startActivity(intent);
        }

        ActivityCompat.setExitSharedElementCallback(activity, new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                /* 这个方法会调用两次，一次进入前，一次回来前。 */
                if (sExitIndex == null) {
                    return;
                }

                int exitIndex = sExitIndex;
                sExitIndex = null;

                if (exitIndex != enterIndex
                        && imageViews.size() > exitIndex
                        && imageUrls.size() > exitIndex) {
                    names.clear();
                    sharedElements.clear();
                    View view = imageViews.get(exitIndex);
                    String transitName = imageUrls.get(exitIndex);
                    if (view == null) {
                        activity.setExitSharedElementCallback((SharedElementCallback) null);
                        return;
                    }
                    names.add(transitName);
                    sharedElements.put(transitName, view);
                }
                activity.setExitSharedElementCallback((SharedElementCallback) null);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_big_image);

        mViewPager = (ViewPager) findViewById(R.id.very_image_viewpager);

        //延迟动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        mEnterIndex = getIntent().getIntExtra(KEY_ENTER_INDEX, 0);
        mImageUrls = getIntent().getStringArrayListExtra(KEY_IMAGE_URLS);
        if (mImageUrls == null) {
            return;
        }


        mPhotoViewMap = new SparseArray<>();
        //                imageView.setLayoutParams(new ViewGroup.LayoutParams(
        //                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //                    View view = getLayoutInflater().inflate(R.layout.dialog_save_image, null);
        //
        //                    AlertDialog alertDialog = new AlertDialog.Builder(BigImagePagerActivity.this)
        //                            .setView(view)
        //                            .create();
        //
        //                    TextView textView = (TextView) view.findViewById(R.id.save_image_tv);
        //                    textView.setOnClickListener(v1 -> {
        //                        saveImage(pv.getDrawable(), imageUrl);
        //                        alertDialog.dismiss();
        //                    });
        //                    alertDialog.show();
        PagerAdapter mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageUrls.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view.equals(object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(container.getContext());
//                imageView.setLayoutParams(new ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                String imageUrl = mImageUrls.get(position);
                Glide.with(BigImagePagerActivity.this).load(imageUrl).into(photoView);

                if (mPhotoViewMap.get(position) != null) {
                    mPhotoViewMap.remove(position);
                }
                mPhotoViewMap.put(position, photoView);

                photoView.setOnViewTapListener((view, x, y) -> {
                    onBackPressed();
                });

                photoView.setOnLongClickListener(v -> {
                    PhotoView pv = (PhotoView) v;
                    if (pv.getDrawable() == null) {
                        return false;
                    }

//                    View view = getLayoutInflater().inflate(R.layout.dialog_save_image, null);
//
//                    AlertDialog alertDialog = new AlertDialog.Builder(BigImagePagerActivity.this)
//                            .setView(view)
//                            .create();
//
//                    TextView textView = (TextView) view.findViewById(R.id.save_image_tv);
//                    textView.setOnClickListener(v1 -> {
//                        saveImage(pv.getDrawable(), imageUrl);
//                        alertDialog.dismiss();
//                    });


//                    alertDialog.show();
                    return true;
                });

                if (position == mEnterIndex) {
                    ViewCompat.setTransitionName(photoView, mImageUrls.get(position));
                    setStartPostTransition(photoView);
                }


                container.addView(photoView);
                return photoView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                if (mPhotoViewMap.get(position) != null) {
                    mPhotoViewMap.remove(position);
                }
                container.removeView((View) object);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mEnterIndex);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                RxBus.getDefault().post(RxCodeConstants.JUMP_CURRENT_POSITION, i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void finishAfterTransition() {
        sExitIndex = mViewPager.getCurrentItem();
        if (sExitIndex != mEnterIndex) {
//            RxBus.getDefault().post(RxCodeConstants.JUMP_CURRENT_POSITION,sExitIndex);
//            Intent data = new Intent();
//            data.putExtra("sExitIndex", sExitIndex);
//            setResult(RESULT_OK, data);
            setSharedElementCallback(sExitIndex, mPhotoViewMap.get(sExitIndex));
        }
        super.finishAfterTransition();
    }

    private void setSharedElementCallback(final int exitIndex, final View sharedView) {
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                String transitName = mImageUrls.get(exitIndex);
                names.clear();
                sharedElements.clear();
                names.add(transitName);
                sharedElements.put(transitName, sharedView);
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    public void setStartPostTransition(final View sharedView) {
        sharedView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedView.getViewTreeObserver().removeOnPreDrawListener(this);
                        //延迟结束，启用动画
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startPostponedEnterTransition();
                        }
                        return false;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
