package com.example.jingbin.cloudreader.bean;


import java.util.List;
import java.util.Objects;

/**
 * @author jingbin
 */
public class ComingFilmBean {

    private String code;
    private DataFilmBean data;

    public static class DataFilmBean {
        private List<MovieRecommendBean> recommends;
        private List<MoviecomingsBean> moviecomings;

        public List<MovieRecommendBean> getRecommends() {
            return recommends;
        }

        public void setRecommends(List<MovieRecommendBean> recommends) {
            this.recommends = recommends;
        }

        public List<MoviecomingsBean> getMoviecomings() {
            return moviecomings;
        }

        public void setMoviecomings(List<MoviecomingsBean> moviecomings) {
            this.moviecomings = moviecomings;
        }
    }

    public static class MovieRecommendBean{
        List<MoviecomingsBean> movies;

        public List<MoviecomingsBean> getMovies() {
            return movies;
        }

        public void setMovies(List<MoviecomingsBean> movies) {
            this.movies = movies;
        }
    }

    public DataFilmBean getData() {
        return data;
    }

    public void setData(DataFilmBean data) {
        this.data = data;
    }

    public static class AttentionBean {
        /**
         * actor1 : 乔什·盖德
         * actor2 : 丹尼斯·奎德
         * director : 盖尔·曼库索
         * id : 257987
         * image : http://img5.mtime.cn/mt/2019/05/09/154533.34451761_1280X720X2.jpg
         * isFilter : false
         * isTicket : true
         * isVideo : true
         * locationName : 美国
         * rDay : 17
         * rMonth : 5
         * rYear : 2019
         * releaseDate : 5月17日上映
         * title : 一条狗的使命2
         * type : 冒险 / 喜剧 / 家庭
         * videoCount : 3
         * videos : [{"hightUrl":"","image":"http://img5.mtime.cn/mg/2019/01/16/165549.62375299.jpg","length":42,"title":"一条狗的使命2 回忆杀版预告","url":"http://vfx.mtime.cn/Video/2019/01/16/mp4/190116170107467170.mp4","videoId":73436},{"hightUrl":"","image":"http://img5.mtime.cn/mg/2019/01/30/114104.33664273.jpg","length":169,"title":"一条狗的使命2 正式预告","url":"http://vfx.mtime.cn/Video/2019/01/30/mp4/190130114131977438.mp4","videoId":73617},{"hightUrl":"","image":"http://img5.mtime.cn/mg/2019/01/30/115000.20867247.jpg","length":172,"title":"一条狗的使命2 中字正式预告","url":"http://vfx.mtime.cn/Video/2019/01/30/mp4/190130115053930472.mp4","videoId":73619}]
         * wantedCount : 78
         */

        private String actor1;
        private String actor2;
        private String director;
        private int id;
        private String image;
        private boolean isVideo;
        private String locationName;
        private int rDay;
        private int rMonth;
        private int rYear;
        private String releaseDate;
        private String title;
        private String type;
        private int videoCount;
        private int wantedCount;
        private List<VideosBean> videos;

        public String getActor1() {
            return actor1;
        }

        public void setActor1(String actor1) {
            this.actor1 = actor1;
        }

        public String getActor2() {
            return actor2;
        }

        public void setActor2(String actor2) {
            this.actor2 = actor2;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isIsVideo() {
            return isVideo;
        }

        public void setIsVideo(boolean isVideo) {
            this.isVideo = isVideo;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public int getRDay() {
            return rDay;
        }

        public void setRDay(int rDay) {
            this.rDay = rDay;
        }

        public int getRMonth() {
            return rMonth;
        }

        public void setRMonth(int rMonth) {
            this.rMonth = rMonth;
        }

        public int getRYear() {
            return rYear;
        }

        public void setRYear(int rYear) {
            this.rYear = rYear;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(int videoCount) {
            this.videoCount = videoCount;
        }

        public int getWantedCount() {
            return wantedCount;
        }

        public void setWantedCount(int wantedCount) {
            this.wantedCount = wantedCount;
        }

        public List<VideosBean> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosBean> videos) {
            this.videos = videos;
        }


    }

    public static class MoviecomingsBean {
        /**
         * actor1 : 阿努克·斯戴芬
         * actor2 : 布鲁诺·甘茨
         * director : 阿兰·葛斯彭纳
         * id : 224984
         * image : http://img5.mtime.cn/mt/2019/05/10/174455.88180422_1280X720X2.jpg
         * isFilter : false
         * isTicket : true
         * isVideo : true
         * locationName : 德国
         * rDay : 16
         * rMonth : 5
         * rYear : 2019
         * releaseDate : 5月16日上映
         * title : 海蒂和爷爷
         * type : 家庭 / 儿童
         * videoCount : 3
         * videos : [{"hightUrl":"","image":"http://img5.mtime.cn/mg/2019/05/10/154325.59927342.jpg","length":103,"title":"海蒂和爷爷 \u201c飘零\u201d版预告","url":"http://vfx.mtime.cn/Video/2019/05/10/mp4/190510173319321869.mp4","videoId":74671},{"hightUrl":"","image":"http://img5.mtime.cn/mg/2019/04/25/092522.71052778.jpg","length":111,"title":"海蒂和爷爷 定档预告","url":"http://vfx.mtime.cn/Video/2019/04/25/mp4/190425092604550352.mp4","videoId":74509},{"hightUrl":"","image":"http://img5.mtime.cn/mg/2017/09/21/163748.34327337.jpg","length":86,"title":"海蒂与爷爷 美国预告片","url":"http://vfx.mtime.cn/Video/2017/09/21/mp4/170921163944703745.mp4","videoId":67742}]
         * wantedCount : 184
         */

        private String actor1;
        private String actor2;
        private String director;
        private int id;
        private int movieId;
        private String image;
        private boolean isFilter;
        private boolean isTicket;
        private boolean isVideo;
        private String locationName;
        private int rDay;
        private int rMonth;
        private int rYear;
        private String releaseDate;
        private String title;
        private String type;
        private int videoCount;
        private int wantedCount;
        private List<VideosBean> videos;

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getActor1() {
            return actor1;
        }

        public void setActor1(String actor1) {
            this.actor1 = actor1;
        }

        public String getActor2() {
            return actor2;
        }

        public void setActor2(String actor2) {
            this.actor2 = actor2;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public int getId() {
            return movieId;
        }

        public void setId(int id) {
            this.movieId = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isIsFilter() {
            return isFilter;
        }

        public void setIsFilter(boolean isFilter) {
            this.isFilter = isFilter;
        }

        public boolean isIsTicket() {
            return isTicket;
        }

        public void setIsTicket(boolean isTicket) {
            this.isTicket = isTicket;
        }

        public boolean isIsVideo() {
            return isVideo;
        }

        public void setIsVideo(boolean isVideo) {
            this.isVideo = isVideo;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public int getRDay() {
            return rDay;
        }

        public void setRDay(int rDay) {
            this.rDay = rDay;
        }

        public int getRMonth() {
            return rMonth;
        }

        public void setRMonth(int rMonth) {
            this.rMonth = rMonth;
        }

        public int getRYear() {
            return rYear;
        }

        public void setRYear(int rYear) {
            this.rYear = rYear;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(int videoCount) {
            this.videoCount = videoCount;
        }

        public int getWantedCount() {
            return wantedCount;
        }

        public void setWantedCount(int wantedCount) {
            this.wantedCount = wantedCount;
        }

        public List<VideosBean> getVideos() {
            return videos;
        }

        public void setVideos(List<VideosBean> videos) {
            this.videos = videos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ComingFilmBean.MoviecomingsBean bean = (ComingFilmBean.MoviecomingsBean) o;
            return Objects.equals(movieId, bean.movieId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(movieId);
        }
    }

    public static class VideosBean {
        /**
         * hightUrl :
         * image : http://img5.mtime.cn/mg/2019/01/16/165549.62375299.jpg
         * length : 42
         * title : 一条狗的使命2 回忆杀版预告
         * url : http://vfx.mtime.cn/Video/2019/01/16/mp4/190116170107467170.mp4
         * videoId : 73436
         */

        private String hightUrl;
        private String image;
        private int length;
        private String title;
        private String url;
        private int videoId;

        public String getHightUrl() {
            return hightUrl;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }
    }
}
