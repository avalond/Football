package cn.brision.football.model;

/**
 * Created by brision on 16/11/2.
 */
public class HomeCardInfo {

    /**
     * description : 通过eventid获取event 地址 /event/event?eventId=58 method:get 参数 id
     * status : 200
     * message :
     * data : {"id":45,"action":"进球","player":"本泽马","caster":"456","gameClock":982,"team":"德国","description":"本泽马以及那个里疯狂的地方的反馈发到辅导费的负","url":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4","photo":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg","commentsCount":45,"viewCount":558,"date":"2016/8/7"}
     */

    private String description;
    private int status;
    private String message;
    /**
     * id : 45
     * action : 进球
     * player : 本泽马
     * caster : 456
     * gameClock : 982
     * team : 德国
     * description : 本泽马以及那个里疯狂的地方的反馈发到辅导费的负
     * url : http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4
     * photo : http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg
     * commentsCount : 45
     * viewCount : 558
     * date : 2016/8/7
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String action;
        private String player;
        private String caster;
        private int gameClock;
        private String team;
        private String description;
        private String url;
        private String photo;
        private int commentsCount;
        private int viewCount;
        private String date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getPlayer() {
            return player;
        }

        public void setPlayer(String player) {
            this.player = player;
        }

        public String getCaster() {
            return caster;
        }

        public void setCaster(String caster) {
            this.caster = caster;
        }

        public int getGameClock() {
            return gameClock;
        }

        public void setGameClock(int gameClock) {
            this.gameClock = gameClock;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getCommentsCount() {
            return commentsCount;
        }

        public void setCommentsCount(int commentsCount) {
            this.commentsCount = commentsCount;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
