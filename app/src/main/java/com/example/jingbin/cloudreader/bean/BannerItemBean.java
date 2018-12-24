package com.example.jingbin.cloudreader.bean;

import java.io.Serializable;

/**
 * Created by jingbin on 2018/12/23.
 */

public class BannerItemBean implements Serializable {

    private String randpic;
    private String code;
    private int type;
    private String randpic_desc;

    public String getRandpic() {
        return randpic;
    }

    public String getCode() {
        return code;
    }

    public int getType() {
        return type;
    }

    public String getRandpic_desc() {
        return randpic_desc;
    }
}
