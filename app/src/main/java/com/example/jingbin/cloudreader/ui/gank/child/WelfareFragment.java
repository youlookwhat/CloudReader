package com.example.jingbin.cloudreader.ui.gank.child;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.jingbin.cloudreader.R;

import me.jingbin.bymvvm.base.BaseFragment;

import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.FragmentWelfareBinding;
import com.example.jingbin.cloudreader.databinding.ItemWelfareBinding;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.view.viewbigimage.ViewBigImageActivity;
import com.example.jingbin.cloudreader.viewmodel.gank.WelfareViewModel;

import java.util.ArrayList;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;
import me.jingbin.library.ByRecyclerView;
import me.jingbin.library.view.OnItemFilterClickListener;

/**
 * 福利
 *
 * @author jingbin
 */
public class WelfareFragment extends BaseFragment<WelfareViewModel, FragmentWelfareBinding> {

    private static final String TAG = "WelfareFragment";
    private boolean isPrepared = false;
    private boolean isFirst = true;
    private ArrayList<String> imgList = new ArrayList<>();
    private ArrayList<String> imgTitleList = new ArrayList<>();
    private BaseBindingAdapter<GankIoDataBean.ResultBean, ItemWelfareBinding> mWelfareAdapter;

    @Override
    public int setContent() {
        return R.layout.fragment_welfare;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecycleView();
        isPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        showLoading();
        loadWelfareData();
        isFirst = false;
    }

    private void initRecycleView() {
        int width = (DensityUtil.getDisplayWidth() - 36) / 2;
        mWelfareAdapter = new BaseBindingAdapter<GankIoDataBean.ResultBean, ItemWelfareBinding>(R.layout.item_welfare) {
            @Override
            protected void bindView(BaseBindingHolder holder, GankIoDataBean.ResultBean bean, ItemWelfareBinding binding, int position) {
                DensityUtil.setWidthHeight(binding.ivWelfare, width, 852 / 1280f);
                binding.setBean(bean);
            }
        };
        RefreshHelper.initStaggeredGrid(bindingView.xrvWelfare, 2, 12);
        bindingView.xrvWelfare.setAdapter(mWelfareAdapter);
        bindingView.xrvWelfare.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                page++;
                viewModel.setPage(page);
                loadWelfareData();
            }
        }, 300);
        bindingView.xrvWelfare.setOnItemClickListener(new OnItemFilterClickListener() {
            @Override
            public void onSingleClick(View v, int position) {
                ViewBigImageActivity.startImageList(getContext(), position, imgList, imgTitleList);
            }
        });
        viewModel.getImageAndTitle().observe(getViewLifecycleOwner(), new Observer<ArrayList<ArrayList<String>>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ArrayList<String>> arrayLists) {
                if (arrayLists != null && arrayLists.size() == 2) {
                    imgList.addAll(arrayLists.get(0));
                    imgTitleList.addAll(arrayLists.get(1));
                }
            }
        });
    }

    private void loadWelfareData() {
        viewModel.loadWelfareData().observe(this, new Observer<GankIoDataBean>() {
            @Override
            public void onChanged(@Nullable GankIoDataBean bean) {
                if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
                    // +1 是因为本身的rv带有一个刷新头布局
                    if (viewModel.getPage() == 1) {
                        showContentView();
                        mWelfareAdapter.clear();
                        mWelfareAdapter.setNewData(bean.getResults());
                    } else {
                        mWelfareAdapter.addData(bean.getResults());
                    }
                    bindingView.xrvWelfare.loadMoreComplete();
                } else {
                    bindingView.xrvWelfare.loadMoreComplete();
                    if (mWelfareAdapter.getItemCount() == 0) {
                        showError();
                    } else {
                        bindingView.xrvWelfare.loadMoreEnd();
                    }
                }
            }
        });
    }

    @Override
    protected void onRefresh() {
        loadWelfareData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
