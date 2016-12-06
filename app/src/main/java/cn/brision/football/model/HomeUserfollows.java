package cn.brision.football.model;

import java.util.List;

/**
 * Created by wangchengcheng on 16/10/10.
 */
public class HomeUserfollows {


    /**
     * description : 首页 的 关注 视频 地址 /home/userfollows method:get
     * status : 200
     * message :
     * data : [{"id":"dfd2df55","title":"本周十大进球","creatAt":"2016-09-25T21:00:00+08:00","imgpath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg","videopath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4","participates":55,"notesCount":8},{"id":"dfd2df55","title":"本周十大助攻","creatAt":"2016-09-25T12:00:00+08:00","imgpath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg","videopath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4","participates":55333,"notesCount":8974},{"id":"dfd2df55","title":"本周十大瞬间","creatAt":"2016-09-25T09:00:00+08:00","imgpath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg","videopath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4","participates":55453,"notesCount":8974}]
     */

    private String description;
    private int status;
    private String message;
    /**
     * id : dfd2df55
     * title : 本周十大进球
     * creatAt : 2016-09-25T21:00:00+08:00
     * imgpath : http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg
     * videopath : http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4
     * participates : 55
     * notesCount : 8
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

    public static class DataBean {
        private String id;
        private String title;
        private String creatAt;
        private String imgpath;
        private String videopath;
        private int participates;
        private int notesCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreatAt() {
            return creatAt;
        }

        public void setCreatAt(String creatAt) {
            this.creatAt = creatAt;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public String getVideopath() {
            return videopath;
        }

        public void setVideopath(String videopath) {
            this.videopath = videopath;
        }

        public int getParticipates() {
            return participates;
        }

        public void setParticipates(int participates) {
            this.participates = participates;
        }

        public int getNotesCount() {
            return notesCount;
        }

        public void setNotesCount(int notesCount) {
            this.notesCount = notesCount;
        }
    }
}
