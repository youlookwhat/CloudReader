package com.example.jingbin.cloudreader.data.local;

import java.util.List;

/**
 * @author jingbin
 * @data 2018/4/19
 * @Description
 */

public interface WaitLoadCallback {
    void onDataNotAvailable();

    void getAll(List<Wait> waitList);
}
