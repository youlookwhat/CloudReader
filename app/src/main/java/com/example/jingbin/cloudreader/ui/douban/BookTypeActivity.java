package com.example.jingbin.cloudreader.ui.douban;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.BookTypeAdapter;
import com.example.jingbin.cloudreader.databinding.ActivityBookTypeBinding;
import com.example.jingbin.cloudreader.http.api.BookApiUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;

import java.util.ArrayList;
import java.util.Arrays;

import me.jingbin.bymvvm.base.BaseActivity;
import me.jingbin.bymvvm.base.NoViewModel;
import me.jingbin.library.decoration.SpacesItemDecoration;

/**
 * @author jingbin
 */
public class BookTypeActivity extends BaseActivity<NoViewModel, ActivityBookTypeBinding> {

    private BookTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_type);
        setTitle("选择分类");
        initRefreshView();
        initData();
    }

    private void initData() {
        bindingView.recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> types = new ArrayList<>();
                types.addAll(Arrays.asList(BookApiUtils.TagTitles));
                types.addAll(Arrays.asList(BookApiUtils.HomeTag));
                types.addAll(Arrays.asList(BookApiUtils.LiterTag));
                types.addAll(Arrays.asList(BookApiUtils.CoderTag));
                types.addAll(Arrays.asList(BookApiUtils.PopularTag));
                types.addAll(Arrays.asList(BookApiUtils.CultureTag));
                types.addAll(Arrays.asList(BookApiUtils.LifeTag));
                types.addAll(Arrays.asList(BookApiUtils.FinancialTag));
                mAdapter.addData(types);
                mAdapter.notifyDataSetChanged();
                showContentView();
            }
        }, 100);
    }

    private void initRefreshView() {
        String type = getIntent().getStringExtra("type");
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 4);
        bindingView.recyclerView.setLayoutManager(mLayoutManager);
        bindingView.recyclerView.setItemAnimator(null);
        mAdapter = new BookTypeAdapter();
        mAdapter.setType(type);
        bindingView.recyclerView.addItemDecoration(new SpacesItemDecoration(this, SpacesItemDecoration.VERTICAL));
        bindingView.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnSelectListener(new BookTypeAdapter.OnSelectListener() {
            @Override
            public void onSelected(String type) {
                DebugUtil.error(type);
                Intent intent = new Intent();
                intent.putExtra("type", type);
                setResult(10, intent);
                finish();
            }
        });
    }


    /**
     * Fragment开启Activity携带返回值
     */
    public static void start(Fragment fragment, String type) {
        Intent intent = new Intent(fragment.getActivity(), BookTypeActivity.class);
        intent.putExtra("type", type);
        fragment.startActivityForResult(intent, 520);
    }

}
