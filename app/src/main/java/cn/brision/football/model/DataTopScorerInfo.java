package cn.brision.football.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangchengcheng on 16/8/17.
 */
public class DataTopScorerInfo {

    private int status;
    private String message;
    /**
     * number : 15
     * teamId : 729
     * team : 天津泰达
     * playerId : 14184
     * id : 14184
     * player : 巴尔克斯
     * photo : http://data.brision.cn/sports/textcaster/photo/567bc8060ac69.jpg
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
        private String number;
        private String teamId;
        private String team;
        private String playerId;
        private String id;
        private String player;
        private String photo;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlayer() {
            return player;
        }

        public void setPlayer(String player) {
            this.player = player;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }

}
