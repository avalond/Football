package cn.brision.football.model;

import java.util.List;

/**
 * Created by wangchengcheng on 16/8/12.
 */
public class DataSeasonInfo {
    /**
     * status : 200
     * message :
     * data : [{"id":"189","league":"奥运会女足","season":"2016","roundCount":7,"finish":"0"}]
     */

    private int status;
    private String message;
    /**
     * id : 189
     * league : 奥运会女足
     * season : 2016
     * roundCount : 7
     * finish : 0
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
        private String league;
        private String season;
        private int roundCount;
        private String finish;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getRoundCount() {
            return roundCount;
        }

        public void setRoundCount(int roundCount) {
            this.roundCount = roundCount;
        }

        public String getFinish() {
            return finish;
        }

        public void setFinish(String finish) {
            this.finish = finish;
        }
    }

    /**
     * id : 150
     * league : 中超联赛
     * season : 2015
     * roundCount : 30
     * finish : 0
     */
//
//    private String id;
//    private String league;
//    private String season;
//    private int roundCount;
//    private String finish;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getLeague() {
//        return league;
//    }
//
//    public void setLeague(String league) {
//        this.league = league;
//    }
//
//    public String getSeason() {
//        return season;
//    }
//
//    public void setSeason(String season) {
//        this.season = season;
//    }
//
//    public int getRoundCount() {
//        return roundCount;
//    }
//
//    public void setRoundCount(int roundCount) {
//        this.roundCount = roundCount;
//    }
//
//    public String getFinish() {
//        return finish;
//    }
//
//    public void setFinish(String finish) {
//        this.finish = finish;
//    }
//
//    @Override
//    public String toString() {
//        return "DataSeasonInfo{" +
//                "id='" + id + '\'' +
//                ", league='" + league + '\'' +
//                ", season='" + season + '\'' +
//                ", roundCount=" + roundCount +
//                ", finish='" + finish + '\'' +
//                '}';
//    }



}
