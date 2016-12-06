package cn.brision.football.model;

/**
 * Created by wangchengcheng on 16/9/27.
 */
public class PlayerTitleInfo {

    /**
     * status : 200
     * message :
     * data : {"id":"21998","name":"布里","number":"17","photo":"images/ico_delete.png","nation":"中国","height":"0","birthday":"1970-01-01","weight":"0","foot":"左脚","position":"前锋","rewards":"","age":0,"isFollowed":false}
     */

    private int status;
    private String message;
    /**
     * id : 21998
     * name : 布里
     * number : 17
     * photo : images/ico_delete.png
     * nation : 中国
     * height : 0
     * birthday : 1970-01-01
     * weight : 0
     * foot : 左脚
     * position : 前锋
     * rewards :
     * age : 0
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
        private String number;
        private String photo;
        private String nation;
        private String height;
        private String birthday;
        private String weight;
        private String foot;
        private String position;
        private String rewards;
        private int age;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getFoot() {
            return foot;
        }

        public void setFoot(String foot) {
            this.foot = foot;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getRewards() {
            return rewards;
        }

        public void setRewards(String rewards) {
            this.rewards = rewards;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isIsFollowed() {
            return isFollowed;
        }

        public void setIsFollowed(boolean isFollowed) {
            this.isFollowed = isFollowed;
        }
    }
}
