package com.example.jingbin.cloudreader.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.databinding.FragmentIosBinding;
import com.example.jingbin.cloudreader.utils.DebugUtil;

public class IosFragment extends BaseFragment<FragmentIosBinding> {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DebugUtil.error("--IosFragment   ----onActivityCreated");
    }

    @Override
    public int setContent() {
        return R.layout.fragment_ios;
    }

    @Override
    protected void loadData() {
        showContentView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--IosFragment   ----onDestroy");
    }
}
