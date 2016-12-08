package com.example.jingbin.cloudreader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by CZH on 2015/9/1.
 * 发现页面的ScrollView，用来写ActionBar渐变
 */
public class DiscoverScrollView extends ScrollView{

    private String tag = "DiscoverScrollView";
    private CallBack_ScrollChanged callBack_scrollChanged;

    public DiscoverScrollView(Context context) {
        super(context);
    }

    public DiscoverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscoverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (callBack_scrollChanged != null){
            callBack_scrollChanged.onScrollChanged(t);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setCallBack_scrollChanged(CallBack_ScrollChanged callBack_scrollChanged) {
        this.callBack_scrollChanged = callBack_scrollChanged;
    }

}
