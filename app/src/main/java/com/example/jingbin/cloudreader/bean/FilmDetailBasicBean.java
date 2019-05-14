package com.example.jingbin.cloudreader.bean;

import android.text.TextUtils;

import java.util.List;

public class FilmDetailBasicBean {


    /**
     * image : http://img5.mtime.cn/mt/2019/05/06/105806.73235069_1280X720X2.jpg
     * titleCn : 大侦探皮卡丘
     * titleEn : Pokémon Detective Pikachu
     * rating : 7.1
     * scoreCount : 622
     * year : 2019
     * content : 蒂姆·古德曼（贾斯提斯·史密斯饰）为寻找下落不明的父亲来到莱姆市，意外与父亲的前宝可梦搭档大侦探皮卡丘（瑞安·雷诺兹 配音）相遇，并惊讶地发现自己是唯一能听懂皮卡丘说话的人类，他们决定组队踏上揭开真相的刺激冒险之路。探案过程中他们邂逅了各式各样的宝可梦，并意外发现了一个足以毁灭整个宝可梦宇宙的惊天阴谋。
     * type : ["动作","冒险","喜剧","家庭","悬疑","科幻"]
     * runTime : 104分钟
     * url : http://movie.mtime.com/235701/
     * wapUrl : http://movie.wap.mtime.com/235701/
     * isEggHunt : false
     * commonSpecial : 萌贱皮卡丘开启宝可梦世界
     * hotRanking : -1
     * weekBoxOffice :
     * totalBoxOffice :
     * weekBoxOfficeUnit :
     * totalBoxOfficeUnit :
     * director : {"directorId":905363,"directorName":"罗伯·莱特曼","directorNameEn":"Rob Letterman","directorImg":"http://img31.mtime.cn/ph/2014/02/22/194342.54873239_1280X720X2.jpg"}
     * actorList : [{"actorId":913605,"actor":"瑞安·雷诺兹","actorEn":"Ryan Reynolds","actorImg":"http://img31.mtime.cn/ph/2014/03/14/153701.71287349_1280X720X2.jpg","roleName":"皮卡丘（英文配音）","roleImg":""},{"actorId":2134445,"actor":"贾斯提斯·史密斯","actorEn":"Justice Smith","actorImg":"http://img5.mtime.cn/ph/2017/11/17/095257.42234868_1280X720X2.jpg","roleName":"蒂姆","roleImg":""}]
     * isTicket : true
     * showCinemaCount : 29
     * showtimeCount : 37
     * showDay : 1557388800
     * style : {"ifLeadPage":0,"leadUrl":"","leadImag":"http://img2.mtime.cn/mg/.jpg"}
     * is3D : true
     * isIMAX : false
     * isIMAX3D : true
     * isDMAX : true
     * festivals : []
     * awards : []
     * totalWinAward : 0
     * totalNominateAward : 0
     * directors : ["罗伯·莱特曼"]
     * actors : ["瑞安·雷诺兹","贾斯提斯·史密斯"]
     * release : {"location":"中国","date":"2019-5-10"}
     * imageCount : 192
     * images : ["http://img5.mtime.cn/pi/2019/04/21/125413.30584658_1280X720X2.jpg","http://img5.mtime.cn/pi/2019/04/21/125412.58243939_1280X720X2.jpg","http://img5.mtime.cn/pi/2019/04/21/125412.43291961_1280X720X2.jpg","http://img5.mtime.cn/pi/2019/04/21/125411.48030810_1280X720X2.jpg"]
     * video : http://vfx.mtime.cn/Video/2019/04/03/mp4/190403103412401676.mp4
     * videoId : 74237
     * videoCount : 3
     * videos : [{"url":"http://vfx.mtime.cn/Video/2019/04/03/mp4/190403103412401676.mp4","image":"http://img5.mtime.cn/mg/2019/04/03/103730.84578651.jpg","length":69,"title":"大侦探皮卡丘 定档预告","videoId":74237},{"url":"http://vfx.mtime.cn/Video/2019/02/27/mp4/190227082529746178.mp4","image":"http://img5.mtime.cn/mg/2019/02/27/074948.35506199.jpg","length":143,"title":"大侦探皮卡丘 中文正式预告","videoId":73889},{"url":"http://vfx.mtime.cn/Video/2018/11/13/mp4/181113120437163423.mp4","image":"http://img5.mtime.cn/mg/2018/11/13/072514.91662976.jpg","length":142,"title":"精灵宝可梦：大侦探皮卡丘 中文预告片","videoId":72617}]
     * personCount : 80
     */

    private String image;
    private String titleCn;
    private String titleEn;
    private String rating;
    private String scoreCount;
    private String year;
    private String content;
    private String runTime;
    private String url;
    private String wapUrl;
    private boolean isEggHunt;
    private String commonSpecial;
    private int hotRanking;
    private String weekBoxOffice;
    private String totalBoxOffice;
    private String weekBoxOfficeUnit;
    private String totalBoxOfficeUnit;
    private DirectorBean director;
    private boolean isTicket;
    private int showCinemaCount;
    private int showtimeCount;
    private int showDay;
    private StyleBean style;
    private boolean is3D;
    private boolean isIMAX;
    private boolean isIMAX3D;
    private boolean isDMAX;
    private int totalWinAward;
    private int totalNominateAward;
    private ReleaseBean release;
    private int imageCount;
    private String video;
    private int videoId;
    private int videoCount;
    private int personCount;
    private List<String> type;
    private List<ActorListBean> actorList;
    private List<String> directors;
    private List<String> actors;
    private List<String> images;
    private List<VideosBean> videos;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitleCn() {
        return titleCn;
    }

    public void setTitleCn(String titleCn) {
        this.titleCn = titleCn;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(String scoreCount) {
        this.scoreCount = scoreCount;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
    }

    public boolean isIsEggHunt() {
        return isEggHunt;
    }

    public void setIsEggHunt(boolean isEggHunt) {
        this.isEggHunt = isEggHunt;
    }

    public String getCommonSpecial() {
        return commonSpecial;
    }

    public void setCommonSpecial(String commonSpecial) {
        this.commonSpecial = commonSpecial;
    }

    public int getHotRanking() {
        return hotRanking;
    }

    public void setHotRanking(int hotRanking) {
        this.hotRanking = hotRanking;
    }

    public String getWeekBoxOffice() {
        return weekBoxOffice;
    }

    public void setWeekBoxOffice(String weekBoxOffice) {
        this.weekBoxOffice = weekBoxOffice;
    }

    public String getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public void setTotalBoxOffice(String totalBoxOffice) {
        this.totalBoxOffice = totalBoxOffice;
    }

    public String getWeekBoxOfficeUnit() {
        return weekBoxOfficeUnit;
    }

    public void setWeekBoxOfficeUnit(String weekBoxOfficeUnit) {
        this.weekBoxOfficeUnit = weekBoxOfficeUnit;
    }

    public String getTotalBoxOfficeUnit() {
        return totalBoxOfficeUnit;
    }

    public void setTotalBoxOfficeUnit(String totalBoxOfficeUnit) {
        this.totalBoxOfficeUnit = totalBoxOfficeUnit;
    }

    public DirectorBean getDirector() {
        return director;
    }

    public void setDirector(DirectorBean director) {
        this.director = director;
    }

    public boolean isIsTicket() {
        return isTicket;
    }

    public void setIsTicket(boolean isTicket) {
        this.isTicket = isTicket;
    }

    public int getShowCinemaCount() {
        return showCinemaCount;
    }

    public void setShowCinemaCount(int showCinemaCount) {
        this.showCinemaCount = showCinemaCount;
    }

    public int getShowtimeCount() {
        return showtimeCount;
    }

    public void setShowtimeCount(int showtimeCount) {
        this.showtimeCount = showtimeCount;
    }

    public int getShowDay() {
        return showDay;
    }

    public void setShowDay(int showDay) {
        this.showDay = showDay;
    }

    public StyleBean getStyle() {
        return style;
    }

    public void setStyle(StyleBean style) {
        this.style = style;
    }

    public boolean isIs3D() {
        return is3D;
    }

    public void setIs3D(boolean is3D) {
        this.is3D = is3D;
    }

    public boolean isIsIMAX() {
        return isIMAX;
    }

    public void setIsIMAX(boolean isIMAX) {
        this.isIMAX = isIMAX;
    }

    public boolean isIsIMAX3D() {
        return isIMAX3D;
    }

    public void setIsIMAX3D(boolean isIMAX3D) {
        this.isIMAX3D = isIMAX3D;
    }

    public boolean isIsDMAX() {
        return isDMAX;
    }

    public void setIsDMAX(boolean isDMAX) {
        this.isDMAX = isDMAX;
    }

    public int getTotalWinAward() {
        return totalWinAward;
    }

    public void setTotalWinAward(int totalWinAward) {
        this.totalWinAward = totalWinAward;
    }

    public int getTotalNominateAward() {
        return totalNominateAward;
    }

    public void setTotalNominateAward(int totalNominateAward) {
        this.totalNominateAward = totalNominateAward;
    }

    public ReleaseBean getRelease() {
        return release;
    }

    public void setRelease(ReleaseBean release) {
        this.release = release;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<ActorListBean> getActorList() {
        return actorList;
    }

    public void setActorList(List<ActorListBean> actorList) {
        this.actorList = actorList;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    public static class DirectorBean {
        /**
         * directorId : 905363
         * directorName : 罗伯·莱特曼
         * directorNameEn : Rob Letterman
         * directorImg : http://img31.mtime.cn/ph/2014/02/22/194342.54873239_1280X720X2.jpg
         */

        private int directorId;
        private String directorName;
        private String directorNameEn;
        private String directorImg;

        public int getDirectorId() {
            return directorId;
        }

        public void setDirectorId(int directorId) {
            this.directorId = directorId;
        }

        public String getDirectorName() {
            return directorName;
        }

        public void setDirectorName(String directorName) {
            this.directorName = directorName;
        }

        public String getDirectorNameEn() {
            return directorNameEn;
        }

        public void setDirectorNameEn(String directorNameEn) {
            this.directorNameEn = directorNameEn;
        }

        public String getDirectorImg() {
            return directorImg;
        }

        public void setDirectorImg(String directorImg) {
            this.directorImg = directorImg;
        }
    }

    public static class StyleBean {
        /**
         * ifLeadPage : 0
         * leadUrl :
         * leadImag : http://img2.mtime.cn/mg/.jpg
         */

        private int ifLeadPage;
        private String leadUrl;
        private String leadImag;

        public int getIfLeadPage() {
            return ifLeadPage;
        }

        public void setIfLeadPage(int ifLeadPage) {
            this.ifLeadPage = ifLeadPage;
        }

        public String getLeadUrl() {
            return leadUrl;
        }

        public void setLeadUrl(String leadUrl) {
            this.leadUrl = leadUrl;
        }

        public String getLeadImag() {
            return leadImag;
        }

        public void setLeadImag(String leadImag) {
            this.leadImag = leadImag;
        }
    }

    public static class ReleaseBean {
        /**
         * location : 中国
         * date : 2019-5-10
         */

        private String location;
        private String date;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class ActorListBean {
        /**
         * actorId : 913605
         * actor : 瑞安·雷诺兹
         * actorEn : Ryan Reynolds
         * actorImg : http://img31.mtime.cn/ph/2014/03/14/153701.71287349_1280X720X2.jpg
         * roleName : 皮卡丘（英文配音）
         * roleImg :
         */

        private int actorId;
        private String actor;
        private String actorEn;
        private String actorImg;
        private String roleName;
        private String roleImg;

        public int getActorId() {
            return actorId;
        }

        public void setActorId(int actorId) {
            this.actorId = actorId;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getActorEn() {
            return actorEn;
        }

        public void setActorEn(String actorEn) {
            this.actorEn = actorEn;
        }

        public String getActorImg() {
            return actorImg;
        }

        public void setActorImg(String actorImg) {
            this.actorImg = actorImg;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleImg() {
            return roleImg;
        }

        public void setRoleImg(String roleImg) {
            this.roleImg = roleImg;
        }
    }

    public static class VideosBean {
        /**
         * url : http://vfx.mtime.cn/Video/2019/04/03/mp4/190403103412401676.mp4
         * image : http://img5.mtime.cn/mg/2019/04/03/103730.84578651.jpg
         * length : 69
         * title : 大侦探皮卡丘 定档预告
         * videoId : 74237
         */

        private String url;
        private String image;
        private int length;
        private String title;
        private int videoId;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }
    }
}
