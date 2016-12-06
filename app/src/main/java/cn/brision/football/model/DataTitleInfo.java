package cn.brision.football.model;

import java.util.List;

/**
 * Created by wangchengcheng on 16/8/10.
 */
public class DataTitleInfo {


    /**
     * status : 200
     * message :
     * data : [{"id":"70","league":"奥运会女足","rank":"2"},{"id":"69","league":"奥运会男足","rank":"2"},{"id":"59","league":"欧洲杯","rank":"1"},{"id":"55","league":"世界杯","rank":"1"},{"id":"64","league":"法甲","rank":"0"},{"id":"63","league":"西甲","rank":"0"},{"id":"62","league":"意甲","rank":"0"},{"id":"61","league":"德甲","rank":"0"},{"id":"56","league":"中超联赛","rank":"0"},{"id":"58","league":"英超","rank":"0"},{"id":"71","league":"世界杯12强赛","rank":"0"}]
     */

    private int status;
    private String message;
    /**
     * id : 70
     * league : 奥运会女足
     * rank : 2
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
        private String rank;

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

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }
    }
}
