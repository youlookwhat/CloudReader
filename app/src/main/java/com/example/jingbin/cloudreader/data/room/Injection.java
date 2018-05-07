package com.example.jingbin.cloudreader.data.room;

import com.example.jingbin.cloudreader.utils.AppExecutors;

/**
 * @author jingbin
 * @data 2018/4/19
 * @Description
 */

public class Injection {

    public static WaitDataBaseSource get() {
        WaitDataBase database = WaitDataBase.getDatabase();
        return WaitDataBaseSource.getInstance(new AppExecutors(), database.waitDao());
    }

}
