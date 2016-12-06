package cn.brision.football.model;

/**
 * Created by wangchengcheng on 16/9/2.
 */
public class IntegralTeam {

    /**
     * status : 200
     * message :
     * data : {"id":"1022","name":"巴西女足","logo":"http://data.brision.cn/sports/textcaster/logo/56773fc504e95.","founding":"","place":"","home":"","phone":"","email":"","rewards":"","isFollowed":false}
     */

    private int status;
    private String message;
    /**
     * id : 1022
     * name : 巴西女足
     * logo : http://data.brision.cn/sports/textcaster/logo/56773fc504e95.
     * founding :
     * place :
     * home :
     * phone :
     * email :
     * rewards :
     * isFollowed : false
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
        private String id;
        private String name;
        private String logo;
        private String founding;
        private String place;
        private String home;
        private String phone;
        private String email;
        private String rewards;
        private boolean isFollowed;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getFounding() {
            return founding;
        }

        public void setFounding(String founding) {
            this.founding = founding;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRewards() {
            return rewards;
        }

        public void setRewards(String rewards) {
            this.rewards = rewards;
        }

        public boolean isIsFollowed() {
            return isFollowed;
        }

        public void setIsFollowed(boolean isFollowed) {
            this.isFollowed = isFollowed;
        }
    }
}
