package com.example.jingbin.cloudreader.viewmodel.gank;

import java.util.ArrayList;

/**
 * @author jingbin
 * @data 2018/1/17
 * @Description
 */

public interface WelfareNavigator extends BigAndroidNavigator {

    /**
     * 更新请求到的图片的全部数据
     */
    void setImageList(ArrayList<String> list, ArrayList<String> imgList);
}
