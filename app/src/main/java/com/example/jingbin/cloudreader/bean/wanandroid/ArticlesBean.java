package com.example.jingbin.cloudreader.bean.wanandroid;

/**
 * @author jingbin
 * @data 2018/10/8
 * @description 完安卓item bean
 */

public class ArticlesBean {

    /**
     * apkLink : http://www.wanandroid.com/blogimgs/e8faab6b-ecb1-4bc2-af96-f7e5039032b3.apk
     * author : GcsSloop
     * chapterId : 294
     * chapterName : 完整项目
     * collect : false
     * courseId : 13
     * desc : Diycode 社区客户端，可以更方便的在手机上查看社区信息。应用采用了数据多级缓存，并且实现了离线浏览(访问过一次的页面会被缓存下来，没有网络也可查看)，相比于网页版，在一定程度上可以减少在手机上访问的流量消耗。由于目前功能尚未完善，还存在一些已知或未知的bug，所以当前版本仅为 beta 测试版。
     * envelopePic : http://www.wanandroid.com/blogimgs/8876bcc1-7d12-4443-bf95-3f9a698685a6.png
     * id : 2241
     * link : http://www.wanandroid.com/blog/show/2033
     * niceDate : 2018-01-29
     * origin :
     * projectLink : https://github.com/GcsSloop/diycode
     * publishTime : 1517236491000
     * title : 【开源完整项目】diycode客户端
     * visible : 1
     * zan : 0
     */

    private String apkLink;
    private String author;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String envelopePic;
    private int id;
    private int originId = -1;// 收藏文章列表里面的原始文章id
    private String link;
    private String niceDate;
    private String origin;
    private String projectLink;
    private long publishTime;
    private String title;
    private int visible;
    private int zan;
    private boolean fresh;
    private boolean isShowImage = true;
    // 分类name
    private String navigationName;
    // 可能没有author 有 shareUser
    private String shareUser;

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public String getNavigationName() {
        return navigationName;
    }

    public void setNavigationName(String navigationName) {
        this.navigationName = navigationName;
    }

    public boolean isShowImage() {
        return isShowImage;
    }

    public void setShowImage(boolean showImage) {
        isShowImage = showImage;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}
