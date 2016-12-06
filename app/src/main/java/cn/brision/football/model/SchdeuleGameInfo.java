package cn.brision.football.model;

/**
 * Created by wangchengcheng on 16/8/26.
 */
public class SchdeuleGameInfo {

    /**
     * status : 200
     * message :
     * data : {"sports":"足球","league":"奥运会女足","season":"2016","round":"金牌赛","date":"2016-08-20","time":"04:30","feed":"tv://","caster":"ligang","home":"瑞典女足","away":"德国女足","score":"1:2","home_score":"1","away_score":"2","home_logo":"http://data.brision.cn/sports/textcaster/logo/57a2afd0bbb1c.jpg","away_logo":"http://data.brision.cn/sports/textcaster/logo/57a2b9f77603a.png"}
     */

    private int status;
    private String message;
    /**
     * sports : 足球
     * league : 奥运会女足
     * season : 2016
     * round : 金牌赛
     * date : 2016-08-20
     * time : 04:30
     * feed : tv://
     * caster : ligang
     * home : 瑞典女足
     * away : 德国女足
     * score : 1:2
     * home_score : 1
     * away_score : 2
     * home_logo : http://data.brision.cn/sports/textcaster/logo/57a2afd0bbb1c.jpg
     * away_logo : http://data.brision.cn/sports/textcaster/logo/57a2b9f77603a.png
     */

    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String sports;
        private String league;
        private String season;
        private String round;
        private String date;
        private String time;
        private String feed;
        private String caster;
        private String home;
        private String away;
        private String score;
        private String home_score;
        private String away_score;
        private String home_logo;
        private String away_logo;

        public String getSports() {
            return sports;
        }

        public void setSports(String sports) {
            this.sports = sports;
        }

        public String getLeague() {
            return league;
        }

        public void setLeague(String league) {
            this.league = league;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
        }

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFeed() {
            return feed;
        }

        public void setFeed(String feed) {
            this.feed = feed;
        }

        public String getCaster() {
            return caster;
        }

        public void setCaster(String caster) {
            this.caster = caster;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getAway() {
            return away;
        }

        public void setAway(String away) {
            this.away = away;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getHome_score() {
            return home_score;
        }

        public void setHome_score(String home_score) {
            this.home_score = home_score;
        }

        public String getAway_score() {
            return away_score;
        }

        public void setAway_score(String away_score) {
            this.away_score = away_score;
        }

        public String getHome_logo() {
            return home_logo;
        }

        public void setHome_logo(String home_logo) {
            this.home_logo = home_logo;
        }

        public String getAway_logo() {
            return away_logo;
        }

        public void setAway_logo(String away_logo) {
            this.away_logo = away_logo;
        }
    }
}
