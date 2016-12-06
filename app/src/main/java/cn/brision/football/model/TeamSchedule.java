package cn.brision.football.model;

import java.util.List;

/**
 * Created by wangchengcheng on 16/10/31.
 */
public class TeamSchedule {

    /**
     * status : 200
     * message :
     * data : [{"id":"6515","date":"2016-08-04","time":"03:00","caster":"ligang","home":"巴西女足","away":"中国女足","score":"3:0","status":"Finished","home_id":"1022","home_logo":"http://data.brision.cn/sports/textcaster/logo/56773fc504e95.","away_id":"1023","away_logo":"http://data.brision.cn/sports/textcaster/logo/579af6a99e7ad.png","datetime":"2016-08-04T03:00:00+08:00"},{"id":"6569","date":"2016-08-07","time":"09:00","caster":"ligang","home":"巴西女足","away":"瑞典女足","score":"5:1","status":"Finished","home_id":"1022","home_logo":"http://data.brision.cn/sports/textcaster/logo/56773fc504e95.","away_id":"1035","away_logo":"http://data.brision.cn/sports/textcaster/logo/57a2afd0bbb1c.jpg","datetime":"2016-08-07T09:00:00+08:00"},{"id":"6546","date":"2016-08-10","time":"09:00","caster":"和风211949","home":"南非女足","away":"巴西女足","score":"0:0","status":"Finished","away_id":"1022","away_logo":"http://data.brision.cn/sports/textcaster/logo/56773fc504e95.","home_id":"1021","home_logo":"http://data.brision.cn/sports/textcaster/logo/57a1d122846d6.png","datetime":"2016-08-10T09:00:00+08:00"},{"id":"6964","date":"2016-08-13","time":"09:00","caster":"ligang","home":"巴西女足","away":"澳大利亚女足","score":"7:6","status":"Finished","home_id":"1022","home_logo":"http://data.brision.cn/sports/textcaster/logo/56773fc504e95.","away_id":"1037","away_logo":"http://data.brision.cn/sports/textcaster/logo/57a2b442a08a6.png","datetime":"2016-08-13T09:00:00+08:00"},{"id":"6980","date":"2016-08-17","time":"00:00","caster":"ligang","home":"巴西女足","away":"瑞典女足","score":"4:3","status":"Finished","home_id":"1022","home_logo":"http://data.brision.cn/sports/textcaster/logo/56773fc504e95.","away_id":"1035","away_logo":"http://data.brision.cn/sports/textcaster/logo/57a2afd0bbb1c.jpg","datetime":"2016-08-17T00:00:00+08:00"},{"id":"6983","date":"2016-08-20","time":"00:00","caster":"ligang","home":"巴西女足","away":"加拿大女足","score":"1:2","status":"Finished","home_id":"1022","home_logo":"http://data.brision.cn/sports/textcaster/logo/56773fc504e95.","away_id":"1036","away_logo":"http://data.brision.cn/sports/textcaster/logo/57a2b214c9f27.png","datetime":"2016-08-20T00:00:00+08:00"}]
     */

    private int status;
    private String message;
    /**
     * id : 6515
     * date : 2016-08-04
     * time : 03:00
     * caster : ligang
     * home : 巴西女足
     * away : 中国女足
     * score : 3:0
     * status : Finished
     * home_id : 1022
     * home_logo : http://data.brision.cn/sports/textcaster/logo/56773fc504e95.
     * away_id : 1023
     * away_logo : http://data.brision.cn/sports/textcaster/logo/579af6a99e7ad.png
     * datetime : 2016-08-04T03:00:00+08:00
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String date;
        private String time;
        private String caster;
        private String home;
        private String away;
        private String score;
        private String status;
        private String home_id;
        private String home_logo;
        private String away_id;
        private String away_logo;
        private String datetime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHome_id() {
            return home_id;
        }

        public void setHome_id(String home_id) {
            this.home_id = home_id;
        }

        public String getHome_logo() {
            return home_logo;
        }

        public void setHome_logo(String home_logo) {
            this.home_logo = home_logo;
        }

        public String getAway_id() {
            return away_id;
        }

        public void setAway_id(String away_id) {
            this.away_id = away_id;
        }

        public String getAway_logo() {
            return away_logo;
        }

        public void setAway_logo(String away_logo) {
            this.away_logo = away_logo;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }
}
