package com.example.jingbin.cloudreader.ui.book.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.databinding.FragmentBookCustomBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;

public class BookCustomFragment extends BaseFragment<FragmentBookCustomBinding> {

    private static final String TYPE = "param1";
    private String mType = "综合";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;

    @Override
    public int setContent() {
        return R.layout.fragment_book_custom;
    }

    public static BookCustomFragment newInstance(String param1) {
        BookCustomFragment fragment = new BookCustomFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        bindingView.srlBook.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.srlBook.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DebugUtil.error("-----onRefresh");
//                listTag= Arrays.asList(BookApiUtils.getApiTag(position));
//                String tag=BookApiUtils.getRandomTAG(listTag);
//                doubanBookPresenter.searchBookByTag(BookReadingFragment.this,tag,false);
                bindingView.srlBook.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (bindingView.srlBook != null) {
                            bindingView.srlBook.setRefreshing(false);
                            DebugUtil.error("-----run");
                        }
                    }
                }, 2000);
            }
        });

        // 准备就绪
        mIsPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlBook.setRefreshing(true);
        loadCustomData();
        DebugUtil.error("-----setRefreshing");
        mIsFirst = false;

//        if (mAndroidBean != null
//                && mAndroidBean.getResults() != null
//                && mAndroidBean.getResults().size() > 0) {
//            showContentView();
//            setAdapter(mAndroidBean);
//        } else {
//            loadAndroidData();
//        }
    }

    private void loadCustomData() {

//        HttpUtils.getInstance().getDouBanServer().
    }

    @Override
    protected void onRefresh() {
        bindingView.srlBook.setRefreshing(true);
        loadCustomData();
    }
}
