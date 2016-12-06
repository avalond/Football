package cn.brision.football.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangchengcheng on 16/10/10.
 */
public class HomeLives implements Serializable{

    /**
     * description : 首页 的 热门直播 模型为直播页面的模型 地址 /home/lives method : get
     * status : 200
     * message :
     * data : [{"roundId":980,"home":{"image":"http://data.brision.cn/sports/textcaster/logo/5677f1a58b1b2.png","teamId":34,"teamName":"广州恒大第一梯队队"},"away":{"image":"http://data.brision.cn/sports/textcaster/logo/5677f1a58b1b2.png","teamId":34,"teamName":"北京国安地安们队"},"startAt":"2016-09-30T19:35:00+08:00","score":"0-1","livesStatus":1},{"roundId":984,"home":{"image":"http://data.brision.cn/sports/textcaster/logo/5677f1a58b1b2.png","teamId":34,"teamName":"广州恒大"},"away":{"image":"http://data.brision.cn/sports/textcaster/logo/5677f1a58b1b2.png","teamId":34,"teamName":"北京国安"},"startAt":"2016-09-30T19:35:00+08:00","score":"0-1","livesStatus":0}]
     */

    private String description;
    private int status;
    private String message;
    /**
     * roundId : 980
     * home : {"image":"http://data.brision.cn/sports/textcaster/logo/5677f1a58b1b2.png","teamId":34,"teamName":"广州恒大第一梯队队"}
     * away : {"image":"http://data.brision.cn/sports/textcaster/logo/5677f1a58b1b2.png","teamId":34,"teamName":"北京国安地安们队"}
     * startAt : 2016-09-30T19:35:00+08:00
     * score : 0-1
     * livesStatus : 1
     */

    private List<DataBean> data;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public static class DataBean implements Serializable{
        private int roundId;
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        /**
         * image : http://data.brision.cn/sports/textcaster/logo/5677f1a58b1b2.png
         * teamId : 34
         * teamName : 广州恒大第一梯队队
         */

        private HomeBean home;
        /**
         * image : http://data.brision.cn/sports/textcaster/logo/5677f1a58b1b2.png
         * teamId : 34
         * teamName : 北京国安地安们队
         */

        private AwayBean away;
        private String startAt;
        private String score;
        private int livesStatus;

        public int getRoundId() {
            return roundId;
        }

        public void setRoundId(int roundId) {
            this.roundId = roundId;
        }

        public HomeBean getHome() {
            return home;
        }

        public void setHome(HomeBean home) {
            this.home = home;
        }

        public AwayBean getAway() {
            return away;
        }

        public void setAway(AwayBean away) {
            this.away = away;
        }

        public String getStartAt() {
            return startAt;
        }

        public void setStartAt(String startAt) {
            this.startAt = startAt;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getLivesStatus() {
            return livesStatus;
        }

        public void setLivesStatus(int livesStatus) {
            this.livesStatus = livesStatus;
        }

        public static class HomeBean implements Serializable{
            private String image;
            private int teamId;
            private String teamName;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getTeamId() {
                return teamId;
            }

            public void setTeamId(int teamId) {
                this.teamId = teamId;
            }

            public String getTeamName() {
                return teamName;
            }

            public void setTeamName(String teamName) {
                this.teamName = teamName;
            }
        }

        public static class AwayBean implements Serializable{
            private String image;
            private int teamId;
            private String teamName;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getTeamId() {
                return teamId;
            }

            public void setTeamId(int teamId) {
                this.teamId = teamId;
            }

            public String getTeamName() {
                return teamName;
            }

            public void setTeamName(String teamName) {
                this.teamName = teamName;
            }
        }
    }
}
