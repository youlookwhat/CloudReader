package com.example.jingbin.cloudreader.bean;

public class FilmDetailBean {


    public static class BoxOfficeBean {
        /**
         * movieId : 218090
         * ranking : 2
         * todayBox : 624073437
         * todayBoxDes : 624.07
         * todayBoxDesUnit : 今日实时(万)
         * totalBox : 404496826397
         * totalBoxDes : 40.45
         * totalBoxUnit : 累计票房(亿)
         */

        private int movieId;
        private int ranking;
        private long todayBox;
        private String todayBoxDes;
        private String todayBoxDesUnit;
        private long totalBox;
        private String totalBoxDes;
        private String totalBoxUnit;

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public long getTodayBox() {
            return todayBox;
        }

        public void setTodayBox(long todayBox) {
            this.todayBox = todayBox;
        }

        public String getTodayBoxDes() {
            return todayBoxDes;
        }

        public void setTodayBoxDes(String todayBoxDes) {
            this.todayBoxDes = todayBoxDes;
        }

        public String getTodayBoxDesUnit() {
            return todayBoxDesUnit;
        }

        public void setTodayBoxDesUnit(String todayBoxDesUnit) {
            this.todayBoxDesUnit = todayBoxDesUnit;
        }

        public long getTotalBox() {
            return totalBox;
        }

        public void setTotalBox(long totalBox) {
            this.totalBox = totalBox;
        }

        public String getTotalBoxDes() {
            return totalBoxDes;
        }

        public void setTotalBoxDes(String totalBoxDes) {
            this.totalBoxDes = totalBoxDes;
        }

        public String getTotalBoxUnit() {
            return totalBoxUnit;
        }

        public void setTotalBoxUnit(String totalBoxUnit) {
            this.totalBoxUnit = totalBoxUnit;
        }
    }

}
