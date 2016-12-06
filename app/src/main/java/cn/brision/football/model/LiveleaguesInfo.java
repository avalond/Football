package cn.brision.football.model;

import java.util.List;

/**
 * Created by wangchengcheng on 16/11/7.
 */
public class LiveleaguesInfo {

    /**
     * status : 200
     * message :
     * data : [{"id":"63","league":"西甲","image":"http://reso3.yiihuu.com/img_1329648.jpg"},{"id":"62","league":"意甲","image":"http://a3.att.hudong.com/82/67/01300000637327125747677577435.jpg"},{"id":"64","league":"法甲","image":""},{"id":"56","league":"中超联赛","image":""},{"id":"72","league":"咸阳民间足球","image":""}]
     */

    private int status;
    private String message;
    /**
     * id : 63
     * league : 西甲
     * image : http://reso3.yiihuu.com/img_1329648.jpg
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
        private String image;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
