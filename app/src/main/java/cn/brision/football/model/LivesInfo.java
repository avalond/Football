package cn.brision.football.model;

import com.avos.avoscloud.AVObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangchengcheng on 16/8/8.
 */
public class LivesInfo implements Serializable {


    /**
     * status : 200
     * message :
     * data : [{"leagueId":61,"league":"德甲","lives":[{"id":"5450","roundId":"989","home":{"teamId":"852","teamName":"沙尔克04","image":"http://data.brision.cn/sports/textcaster/logo/56d4399999c7c.png"},"away":{"teamId":"842","teamName":"拜仁","image":"http://data.brision.cn/sports/textcaster/logo/56d40a9cc8c8c.png"},"score":"0-0","startAt":"2016-09-10T02:30:00+08:00","livesStatus":0},{"id":"5455","roundId":"989","home":{"teamId":0,"teamName":"","image":""},"away":{"teamId":"851","teamName":"科隆","image":"http://data.brision.cn/sports/textcaster/logo/56d436a09af48.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0},{"id":"5454","roundId":"989","home":{"teamId":"1076","teamName":"弗赖堡","image":"http://data.brision.cn/sports/textcaster/logo/57c161c9ae477.jpg"},"away":{"teamId":"850","teamName":"门兴","image":"http://data.brision.cn/sports/textcaster/logo/56d4344c1295b.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0},{"id":"5452","roundId":"989","home":{"teamId":"855","teamName":"因戈尔施塔特","image":"http://data.brision.cn/sports/textcaster/logo/56d43f713b686.png"},"away":{"teamId":"841","teamName":"柏林赫塔","image":"http://data.brision.cn/sports/textcaster/logo/56d40716690e6.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0},{"id":"5451","roundId":"989","home":{"teamId":"848","teamName":"勒沃库森","image":"http://data.brision.cn/sports/textcaster/logo/56d43154f24ea.png"},"away":{"teamId":"845","teamName":"汉堡","image":"http://data.brision.cn/sports/textcaster/logo/56d42a04329d9.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0},{"id":"5453","roundId":"989","home":{"teamId":"844","teamName":"达姆施塔特","image":"http://data.brision.cn/sports/textcaster/logo/56d40cf5a2f32.png"},"away":{"teamId":"856","teamName":"法兰克福","image":"http://data.brision.cn/sports/textcaster/logo/56d440880d2f5.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0}]},{"leagueId":58,"league":"英超","lives":[{"id":"5781","roundId":"953","home":{"teamId":"829","teamName":"曼联","image":"http://data.brision.cn/sports/textcaster/logo/56cc0991355db.png"},"away":{"teamId":"839","teamName":"曼城","image":"http://data.brision.cn/sports/textcaster/logo/56cc3329b9c6b.png"},"score":"0-0","startAt":"2016-09-10T19:30:00+08:00","livesStatus":0},{"id":"5787","roundId":"953","home":{"teamId":"828","teamName":"桑德兰","image":"http://data.brision.cn/sports/textcaster/logo/56cc0bc7e2efc.png"},"away":{"teamId":"825","teamName":"埃弗顿","image":"http://data.brision.cn/sports/textcaster/logo/56cc0ab431c85.png"},"score":"0-0","startAt":"2016-09-10T22:00:00+08:00","livesStatus":0},{"id":"5788","roundId":"953","home":{"teamId":"838","teamName":"西汉姆","image":"http://data.brision.cn/sports/textcaster/logo/56cc154aacfe0.png"},"away":{"teamId":"826","teamName":"沃特福德","image":"http://data.brision.cn/sports/textcaster/logo/56cc0ada4ce5e.png"},"score":"0-0","startAt":"2016-09-10T22:00:00+08:00","livesStatus":0},{"id":"5786","roundId":"953","home":{"teamId":"832","teamName":"斯托克城","image":"http://data.brision.cn/sports/textcaster/logo/56cc14b9d2a0e.png"},"away":{"teamId":"837","teamName":"热刺","image":"http://data.brision.cn/sports/textcaster/logo/56cc151aa7f52.png"},"score":"0-0","startAt":"2016-09-10T22:00:00+08:00","livesStatus":0},{"id":"5784","roundId":"953","home":{"teamId":"1055","teamName":"伯恩利","image":"http://data.brision.cn/sports/textcaster/logo/57a4a47314e1a.jpg"},"away":{"teamId":"1057","teamName":"胡尔城","image":"http://data.brision.cn/sports/textcaster/logo/57a4a5bd6f1f6.jpg"},"score":"0-0","startAt":"2016-09-10T22:00:00+08:00","livesStatus":0},{"id":"5782","roundId":"953","home":{"teamId":"737","teamName":"阿森纳","image":"http://data.brision.cn/sports/textcaster/logo/568b2801c67e7.png"},"away":{"teamId":"1059","teamName":"南安普顿","image":"http://data.brision.cn/sports/textcaster/logo/57a4a5ee91f7f.jpg"},"score":"0-0","startAt":"2016-09-10T22:00:00+08:00","livesStatus":0},{"id":"5783","roundId":"953","home":{"teamId":"821","teamName":"伯恩茅斯","image":"http://data.brision.cn/sports/textcaster/logo/56cc09dca046c.png"},"away":{"teamId":"1058","teamName":"西布朗","image":"http://data.brision.cn/sports/textcaster/logo/57a4a5d60a5e0.jpg"},"score":"0-0","startAt":"2016-09-10T22:00:00+08:00","livesStatus":0},{"id":"5785","roundId":"953","home":{"teamId":"1074","teamName":"米堡","image":"http://data.brision.cn/sports/textcaster/logo/57bea53bc5f70.png"},"away":{"teamId":"833","teamName":"水晶宫","image":"http://data.brision.cn/sports/textcaster/logo/56cc14d45f2e1.png"},"score":"0-0","startAt":"2016-09-10T22:00:00+08:00","livesStatus":0}]},{"leagueId":64,"league":"法甲","lives":[{"id":"6172","roundId":"1026","home":{"teamId":"914","teamName":"圣日耳曼","image":"http://data.brision.cn/sports/textcaster/logo/56f5007e49225.jpg"},"away":{"teamId":"920","teamName":"圣埃蒂安","image":"http://data.brision.cn/sports/textcaster/logo/56f50681db4f9.jpg"},"score":"0-0","startAt":"2016-09-10T02:00:00+08:00","livesStatus":0},{"id":"6169","roundId":"1026","home":{"teamId":"917","teamName":"里昂","image":"http://data.brision.cn/sports/textcaster/logo/56f503bf42308.jpg"},"away":{"teamId":"927","teamName":"波尔多","image":"http://data.brision.cn/sports/textcaster/logo/56f6b63c4d123.jpg"},"score":"0-0","startAt":"2016-09-10T02:00:00+08:00","livesStatus":0}]},{"leagueId":71,"league":"世界杯12强赛","lives":[{"id":"6995","roundId":"1115","home":{"teamId":"1085","teamName":"中国","image":"http://data.brision.cn/sports/textcaster/logo/57c7f0c8ca8f3.png"},"away":{"teamId":"1087","teamName":"伊朗","image":"http://data.brision.cn/sports/textcaster/logo/57c8e756e168a.png"},"score":"0-0","startAt":"2016-09-06T19:35:00+08:00","livesStatus":2},{"id":"6999","roundId":"1115","home":{"teamId":"1092","teamName":"叙利亚","image":"http://data.brision.cn/sports/textcaster/logo/57c93f1edb0d4.jpg"},"away":{"teamId":"1086","teamName":"韩国","image":"http://data.brision.cn/sports/textcaster/logo/57c7f5e451e6c.png"},"score":"0-0","startAt":"2016-09-06T20:00:00+08:00","livesStatus":2},{"id":"7000","roundId":"1115","home":{"teamId":"1088","teamName":"卡塔尔","image":"http://data.brision.cn/sports/textcaster/logo/57c8f59cae18d.PNG"},"away":{"teamId":"1091","teamName":"乌兹别克","image":"http://data.brision.cn/sports/textcaster/logo/57c93a36c6fbb.jpg"},"score":"0-1","startAt":"2016-09-07T00:00:00+08:00","livesStatus":2}]}]
     */

    private int status;
    private String message;
    /**
     * leagueId : 61
     * league : 德甲
     * lives : [{"id":"5450","roundId":"989","home":{"teamId":"852","teamName":"沙尔克04","image":"http://data.brision.cn/sports/textcaster/logo/56d4399999c7c.png"},"away":{"teamId":"842","teamName":"拜仁","image":"http://data.brision.cn/sports/textcaster/logo/56d40a9cc8c8c.png"},"score":"0-0","startAt":"2016-09-10T02:30:00+08:00","livesStatus":0},{"id":"5455","roundId":"989","home":{"teamId":0,"teamName":"","image":""},"away":{"teamId":"851","teamName":"科隆","image":"http://data.brision.cn/sports/textcaster/logo/56d436a09af48.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0},{"id":"5454","roundId":"989","home":{"teamId":"1076","teamName":"弗赖堡","image":"http://data.brision.cn/sports/textcaster/logo/57c161c9ae477.jpg"},"away":{"teamId":"850","teamName":"门兴","image":"http://data.brision.cn/sports/textcaster/logo/56d4344c1295b.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0},{"id":"5452","roundId":"989","home":{"teamId":"855","teamName":"因戈尔施塔特","image":"http://data.brision.cn/sports/textcaster/logo/56d43f713b686.png"},"away":{"teamId":"841","teamName":"柏林赫塔","image":"http://data.brision.cn/sports/textcaster/logo/56d40716690e6.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0},{"id":"5451","roundId":"989","home":{"teamId":"848","teamName":"勒沃库森","image":"http://data.brision.cn/sports/textcaster/logo/56d43154f24ea.png"},"away":{"teamId":"845","teamName":"汉堡","image":"http://data.brision.cn/sports/textcaster/logo/56d42a04329d9.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0},{"id":"5453","roundId":"989","home":{"teamId":"844","teamName":"达姆施塔特","image":"http://data.brision.cn/sports/textcaster/logo/56d40cf5a2f32.png"},"away":{"teamId":"856","teamName":"法兰克福","image":"http://data.brision.cn/sports/textcaster/logo/56d440880d2f5.png"},"score":"0-0","startAt":"2016-09-10T21:30:00+08:00","livesStatus":0}]
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

    public static class DataBean implements Serializable {
        private int leagueId;
        private String league;
        /**
         * id : 5450
         * roundId : 989
         * home : {"teamId":"852","teamName":"沙尔克04","image":"http://data.brision.cn/sports/textcaster/logo/56d4399999c7c.png"}
         * away : {"teamId":"842","teamName":"拜仁","image":"http://data.brision.cn/sports/textcaster/logo/56d40a9cc8c8c.png"}
         * score : 0-0
         * startAt : 2016-09-10T02:30:00+08:00
         * livesStatus : 0
         */

        private List<LivesBean> lives;

        public int getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(int leagueId) {
            this.leagueId = leagueId;
        }

        public String getLeague() {
            return league;
        }

        public void setLeague(String league) {
            this.league = league;
        }

        public List<LivesBean> getLives() {
            return lives;
        }

        public void setLives(List<LivesBean> lives) {
            this.lives = lives;
        }

        public static class LivesBean implements Serializable {
            private String id;
            private String roundId;
            /**
             * teamId : 852
             * teamName : 沙尔克04
             * image : http://data.brision.cn/sports/textcaster/logo/56d4399999c7c.png
             */

            private HomeBean home;
            /**
             * teamId : 842
             * teamName : 拜仁
             * image : http://data.brision.cn/sports/textcaster/logo/56d40a9cc8c8c.png
             */

            private AwayBean away;
            private String score;
            private String startAt;
            private int livesStatus;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRoundId() {
                return roundId;
            }

            public void setRoundId(String roundId) {
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

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getStartAt() {
                return startAt;
            }

            public void setStartAt(String startAt) {
                this.startAt = startAt;
            }

            public int getLivesStatus() {
                return livesStatus;
            }

            public void setLivesStatus(int livesStatus) {
                this.livesStatus = livesStatus;
            }

            public static class HomeBean implements Serializable {
                private String teamId;
                private String teamName;
                private String image;

                public String getTeamId() {
                    return teamId;
                }

                public void setTeamId(String teamId) {
                    this.teamId = teamId;
                }

                public String getTeamName() {
                    return teamName;
                }

                public void setTeamName(String teamName) {
                    this.teamName = teamName;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }
            }

            public static class AwayBean implements Serializable {
                private String teamId;
                private String teamName;
                private String image;

                public String getTeamId() {
                    return teamId;
                }

                public void setTeamId(String teamId) {
                    this.teamId = teamId;
                }

                public String getTeamName() {
                    return teamName;
                }

                public void setTeamName(String teamName) {
                    this.teamName = teamName;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }
            }
        }
    }
}
