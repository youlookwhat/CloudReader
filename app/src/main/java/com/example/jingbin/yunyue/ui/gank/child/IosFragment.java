package com.example.jingbin.yunyue.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.jingbin.yunyue.R;
import com.example.jingbin.yunyue.base.BaseFragment;
import com.example.jingbin.yunyue.databinding.FragmentIosBinding;
import com.example.jingbin.yunyue.utils.DebugUtil;

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
