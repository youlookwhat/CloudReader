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
     * installUrl : http://download.fir.im/v2/app/install/58677918ca87a8490d000395?download_token=d19d3bd555629acaa07e7e5c7e4bee8d&source=update
     * install_url : http://download.fir.im/v2/app/install/58677918ca87a8490d000395?download_token=d19d3bd555629acaa07e7e5c7e4bee8d&source=update
     * direct_install_url : http://download.fir.im/v2/app/install/58677918ca87a8490d000395?download_token=d19d3bd555629acaa07e7e5c7e4bee8d&source=update
     * update_url : http://fir.im/cloudreader
     * binary : {"fsize":6214117}
     */

    private String name;
    private String version;
    private String changelog;
    private long updated_at;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String direct_install_url;
    private String update_url;
    private BinaryBean binary;

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

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
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

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }

    public static class BinaryBean {
        /**
         * fsize : 6214117
         */

        private int fsize;

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }
    }
}
