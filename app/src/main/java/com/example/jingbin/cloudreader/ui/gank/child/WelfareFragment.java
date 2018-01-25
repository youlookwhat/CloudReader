package com.example.jingbin.cloudreader.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WelfareAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.FragmentWelfareBinding;
import com.example.jingbin.cloudreader.view.viewbigimage.ViewBigImageActivity;
import com.example.jingbin.cloudreader.viewmodel.gank.WelfareNavigator;
import com.example.jingbin.cloudreader.viewmodel.gank.WelfareViewModel;
import com.example.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * 福利
 *
 * @author jingbin
 */
public class WelfareFragment extends BaseFragment<FragmentWelfareBinding> implements WelfareNavigator {

    private static final String TAG = "WelfareFragment";
    private WelfareAdapter mWelfareAdapter;
    private boolean isPrepared = false;
    private boolean isFirst = true;
    private ArrayList<String> imgList = new ArrayList<>();
    private ArrayList<String> imgTitleList = new ArrayList<>();
    private WelfareViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new WelfareViewModel(this);
        viewModel.setNavigator(this);

        initRecycleView();
        isPrepared = true;
    }

    private void initRecycleView() {
        bindingView.xrvWelfare.setPullRefreshEnabled(false);
        bindingView.xrvWelfare.clearHeader();
        mWelfareAdapter = new WelfareAdapter();
        //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
        bindingView.xrvWelfare.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        bindingView.xrvWelfare.setAdapter(mWelfareAdapter);
        bindingView.xrvWelfare.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                page++;
                viewModel.setPage(page);
                viewModel.loadWelfareData();
            }
        });
    }

    @Override
    protected void loadData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        viewModel.loadWelfareData();
    }

    private void setAdapter(GankIoDataBean gankIoDataBean) {
        mWelfareAdapter.addAll(gankIoDataBean.getResults());
        mWelfareAdapter.notifyDataSetChanged();

        mWelfareAdapter.setOnItemClickListener((resultsBean, position) -> {
            ViewBigImageActivity.startImageList(getContext(), position, imgList, imgTitleList);
        });

        // 显示成功后就不是第一次了，不再刷新
        isFirst = false;
    }

    @Override
    public int setContent() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void onRefresh() {
        viewModel.loadWelfareData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showLoadSuccessView() {
        showContentView();
    }

    @Override
    public void showAdapterView(GankIoDataBean gankIoDataBean) {
        setAdapter(gankIoDataBean);
    }

    @Override
    public void refreshAdapter(GankIoDataBean gankIoDataBean) {
        bindingView.xrvWelfare.refreshComplete();
        mWelfareAdapter.addAll(gankIoDataBean.getResults());
        mWelfareAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListNoMoreLoading() {
        bindingView.xrvWelfare.noMoreLoading();
    }

    @Override
    public void showLoadFailedView() {
        bindingView.xrvWelfare.refreshComplete();
        if (mWelfareAdapter.getItemCount() == 0) {
            showError();
        }
    }

    @Override
    public void setImageList(ArrayList<String> imgList, ArrayList<String> imgTitleList) {
        this.imgList = imgList;
        this.imgTitleList = imgTitleList;
    }
}
