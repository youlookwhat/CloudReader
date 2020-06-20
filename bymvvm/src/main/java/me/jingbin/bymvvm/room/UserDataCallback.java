package me.jingbin.bymvvm.room;

/**
 * @author jingbin
 * @data 2018/4/19
 * @Description
 */

public interface UserDataCallback {

    /**
     * 返回数据为null
     */
    void onDataNotAvailable();

    /**
     * @param bean 返回数据
     */
    void getData(User bean);
}
