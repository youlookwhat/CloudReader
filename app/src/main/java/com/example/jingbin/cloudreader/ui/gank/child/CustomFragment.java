package com.example.jingbin.cloudreader.ui.gank.child;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.GankAndroidAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.FragmentCustomBinding;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.viewmodel.gank.GankViewModel;
import com.example.xrecyclerview.XRecyclerView;

import static com.example.jingbin.cloudreader.app.Constants.GANK_CALA;

/**
 * @author jingbin
 * @data 2018-12-22
 */
public class CustomFragment extends BaseFragment<GankViewModel, FragmentCustomBinding> {

    private String mType = "all";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private BottomSheet.Builder builder = null;
    private GankAndroidAdapter adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initRecyclerView();
        // 准备就绪
        mIsPrepared = true;
    }

    @Override
    public int setContent() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        loadCustomData();
    }

    private void initData() {
        String type = SPUtils.getString(GANK_CALA, "全部");
        if ("全部".equals(type)) {
            mType = "all";
        } else if ("IOS".equals(type)) {
            mType = "iOS";
        } else {
            mType = type;
        }
        viewModel.setType(mType);
    }

    private void initRecyclerView() {
        // 禁止下拉刷新
        bindingView.xrvCustom.setPullRefreshEnabled(false);
        // 去掉刷新头
        bindingView.xrvCustom.clearHeader();
        // 去掉显示动画
        bindingView.xrvCustom.setItemAnimator(null);
        adapter = new GankAndroidAdapter();
        View mHeaderView = View.inflate(getContext(), R.layout.header_item_gank_custom, null);
        bindingView.xrvCustom.addHeaderView(mHeaderView);
        initHeader(mHeaderView);
        bindingView.xrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.xrvCustom.setAdapter(adapter);
        bindingView.xrvCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                page++;
                viewModel.setPage(page);
                loadCustomData();
            }
        });
    }

    private void loadCustomData() {
        viewModel.loadGankData().observe(this, new Observer<GankIoDataBean>() {
            @Override
            public void onChanged(@Nullable GankIoDataBean bean) {
                if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
                    if (viewModel.getPage() == 1) {
                        showContentView();
                        boolean isAll = "全部".equals(SPUtils.getString(GANK_CALA, "全部"));
                        adapter.setAllType(isAll);
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                    }

                    int positionStart = adapter.getItemCount() + 2;
                    adapter.addAll(bean.getResults());
                    adapter.notifyItemRangeInserted(positionStart, bean.getResults().size());
                    bindingView.xrvCustom.refreshComplete();
                    if (mIsFirst) {
                        mIsFirst = false;
                    }
                } else {
                    if (viewModel.getPage() == 1) {
                        showError();
                    } else {
                        bindingView.xrvCustom.noMoreLoading();
                    }
                }
            }
        });
    }

    private void initHeader(View mHeaderView) {
        final TextView txName = (TextView) mHeaderView.findViewById(R.id.tx_name);
        String gankCala = SPUtils.getString(GANK_CALA, "全部");
        txName.setText(gankCala);
        try {
            builder = new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
                    .title("选择分类")
                    .sheet(R.menu.gank_bottomsheet)
                    .listener((dialog, which) -> {
                        switch (which) {
                            case R.id.gank_all:
                                if (isOtherType("全部")) {
                                    changeContent(txName, "全部");
                                }
                                break;
                            case R.id.gank_ios:
                                if (isOtherType("IOS")) {
                                    changeContent(txName, "IOS");
                                }
                                break;
                            case R.id.gank_qian:
                                if (isOtherType("前端")) {
                                    changeContent(txName, "前端");
                                }
                                break;
                            case R.id.gank_app:
                                if (isOtherType("App")) {
                                    changeContent(txName, "App");
                                }
                                break;
                            case R.id.gank_movie:
                                if (isOtherType("休息视频")) {
                                    changeContent(txName, "休息视频");
                                }
                                break;
                            case R.id.gank_resouce:
                                if (isOtherType("拓展资源")) {
                                    changeContent(txName, "拓展资源");
                                }
                                break;
                            default:
                                break;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        View view = mHeaderView.findViewById(R.id.ll_choose_catalogue);
        view.setOnClickListener(v -> {
            if (builder != null) {
                builder.show();
            }
        });
    }

    private void changeContent(TextView textView, String content) {
        if ("全部".equals(content)) {
            textView.setText("全部");
            // 全部传 all
            mType = "all";

        } else if ("IOS".equals(content)) {
            textView.setText("IOS");
            // 这里有严格大小写
            mType = "iOS";

        } else {
            textView.setText(content);
            mType = content;
        }
        // 重置XRecyclerView状态，解决 如出现刷新到底无内容再切换其他类别后，无法上拉加载的情况
        bindingView.xrvCustom.reset();
        viewModel.setType(mType);
        viewModel.setPage(1);
        SPUtils.putString(GANK_CALA, content);
        showLoading();
        loadCustomData();
    }

    private boolean isOtherType(String selectType) {
        String clickText = SPUtils.getString(GANK_CALA, "全部");
        if (clickText.equals(selectType)) {
            ToastUtil.showToast("当前已经是" + selectType + "分类");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 加载失败后点击后的操作
     */
    @Override
    protected void onRefresh() {
        loadCustomData();
    }
}
