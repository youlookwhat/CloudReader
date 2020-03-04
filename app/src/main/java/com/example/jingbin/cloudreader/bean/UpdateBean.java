package com.example.jingbin.cloudreader.bean;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description
 */

public class UpdateBean {


    /**
     * name : 云阅
     * version : 13
     * changelog : 1、[修复] 已选择分类退出App再次进入,type失效的问题 2、[修复] 跳转B站视频显示网页错误的问题
     * updated_at : 1510629404
     * versionShort : 1.8.3
     * build : 13
     * isShow : 1
     * installUrl : http://download.fir.im/v2/app/install/58677918ca87a8490d000395?download_token=d19d3bd555629acaa07e7e5c7e4bee8d&source=update
     * update_url : http://fir.im/cloudreader
     */

    private String name;
    private String version;// 版本号
    private String changelog;// 更新日志
    private String isShow;// 1显示弹框，不然不显示
    private long updated_at;// 更新时间
    private String versionShort;// 版本名称
    private String build;
    private String installUrl;
    private String update_url;

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public long getupdatedAt() {
        return updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getupdateUrl() {
        return update_url;
    }

}
