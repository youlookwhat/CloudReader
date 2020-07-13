package com.example.jingbin.cloudreader.ui.gank.child;

import androidx.lifecycle.Observer;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.GankAndroidAdapter;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.databinding.FragmentAndroidBinding;
import com.example.jingbin.cloudreader.utils.RefreshHelper;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.viewmodel.gank.GankViewModel;

import me.jingbin.library.ByRecyclerView;

import static com.example.jingbin.cloudreader.app.Constants.GANK_TYPE;

/**
 * @author jingbin
 * @data 2018-12-22
 */
public class CustomFragment extends BaseFragment<GankViewModel, FragmentAndroidBinding> {

    private String mType = "All";
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
        return R.layout.fragment_android;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        showLoading();
        loadCustomData();
        mIsFirst = false;
    }

    private void initData() {
        String type = SPUtils.getString(GANK_TYPE, "全部");
        setSelectType(type);
        viewModel.setType(mType);
    }

    private void initRecyclerView() {
        adapter = new GankAndroidAdapter();
        View mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_item_gank_custom, (ViewGroup) bindingView.xrvAndroid.getParent(), false);
        bindingView.xrvAndroid.addHeaderView(mHeaderView);
        initHeader(mHeaderView);
        RefreshHelper.initLinear(bindingView.xrvAndroid, false);
        bindingView.xrvAndroid.setAdapter(adapter);
        bindingView.xrvAndroid.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
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
        viewModel.loadGankData().observe(getViewLifecycleOwner(), new Observer<GankIoDataBean>() {
            @Override
            public void onChanged(@Nullable GankIoDataBean bean) {
                bindingView.xrvAndroid.setStateViewEnabled(false);
                bindingView.xrvAndroid.setLoadMoreEnabled(true);
                if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
                    if (viewModel.getPage() == 1) {
                        showContentView();
                        boolean isAll = "全部".equals(SPUtils.getString(GANK_TYPE, "全部"));
                        adapter.setAllType(isAll);
                        adapter.setNewData(bean.getResults());
                    } else {
                        adapter.addData(bean.getResults());
                        bindingView.xrvAndroid.loadMoreComplete();
                    }
                } else {
                    if (viewModel.getPage() == 1) {
                        showContentView();
                        bindingView.xrvAndroid.setStateView(R.layout.layout_loading_empty);
                        bindingView.xrvAndroid.setLoadMoreEnabled(false);
                        adapter.setNewData(null);
                    } else {
                        bindingView.xrvAndroid.loadMoreEnd();
                    }
                }
            }
        });
    }

    private void initHeader(View mHeaderView) {
        final TextView txName = (TextView) mHeaderView.findViewById(R.id.tx_name);
        String gankType = SPUtils.getString(GANK_TYPE, "全部");
        txName.setText(gankType);
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
                            case R.id.gank_flutter:
                                if (isOtherType("Flutter")) {
                                    changeContent(txName, "Flutter");
                                }
                                break;
                            case R.id.gank_ios:
                                if (isOtherType("iOS")) {
                                    changeContent(txName, "iOS");
                                }
                                break;
                            case R.id.gank_qian:
                                if (isOtherType("前端")) {
                                    changeContent(txName, "前端");
                                }
                                break;
                            case R.id.gank_backend:
                                if (isOtherType("后端")) {
                                    changeContent(txName, "后端");
                                }
                                break;
                            case R.id.gank_app:
                                if (isOtherType("App")) {
                                    changeContent(txName, "App");
                                }
                                break;
                            case R.id.gank_resouce:
                                if (isOtherType("推荐")) {
                                    changeContent(txName, "推荐");
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
        textView.setText(content);
        setSelectType(content);
        viewModel.setType(mType);
        viewModel.setPage(1);
        SPUtils.putString(GANK_TYPE, content);
        showLoading();
        loadCustomData();
    }

    private void setSelectType(String type) {
        switch (type) {
            case "全部":
                mType = "All";
                break;
            case "iOS":
                mType = "iOS";
                break;
            case "Flutter":
                mType = "Flutter";
                break;
            case "App":
                mType = "app";
                break;
            case "前端":
                mType = "frontend";
                break;
            case "后端":
                mType = "backend";
                break;
            case "推荐":
                mType = "promote";
                break;
            default:
                mType = type;
                break;
        }
    }

    private boolean isOtherType(String selectType) {
        String clickText = SPUtils.getString(GANK_TYPE, "全部");
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
