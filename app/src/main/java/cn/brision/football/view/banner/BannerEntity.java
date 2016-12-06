package cn.brision.football.view.banner;

import java.util.List;

/**
 * Created by hcc on 16/8/24 21:37
 * 100332338@qq.com
 * <p>
 * Banner模型类
 */
public class BannerEntity
{

//    public String title;
//
//
//    public String img;
//
//
//    public String link;





    /**
     * description : 首页 的 banner api type为类型 1 事件 2 帖子 3网页 地址 /home/banner method:get
     * status : 200
     * message :
     * data : [{"id":45,"title":"本泽马以及那个里疯狂的地方的反馈发到辅导费的负担负担本泽马以及那个里疯狂的地方的反馈发到辅导费的负担负担本泽马以及那个里疯狂的地方的反馈发到辅导费的负担负担","imgpath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpgpppp","videopath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4","linkurl":"","type":1},{"id":45,"title":"本泽马以及那个本泽马以及那个里疯狂的地方的反馈发到辅导费的负担负担里疯狂的地方的反馈发到辅导费的负担负担","imgpath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg","videopath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4","linkurl":"","type":1},{"id":45,"title":"本泽马以及那个","imgpath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg","videopath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4","linkurl":"","type":1},{"id":45,"title":"本泽马以及那个里疯狂的地方的反馈发到辅导费的负担负担","imgpath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg","videopath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4","linkurl":"","type":1},{"id":45,"title":"本泽马以及那个里疯狂的地方的反馈发到辅导费的负担负担","imgpath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpg","videopath":"http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4","linkurl":"","type":1}]
     */

    private String description;
    private int status;
    private String message;
    /**
     * id : 45
     * title : 本泽马以及那个里疯狂的地方的反馈发到辅导费的负担负担本泽马以及那个里疯狂的地方的反馈发到辅导费的负担负担本泽马以及那个里疯狂的地方的反馈发到辅导费的负担负担
     * imgpath : http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig1.jpgpppp
     * videopath : http://data.brision.cn/sports/tasks/129755163257bbcfeedffae/event.orig.mp4
     * linkurl :
     * type : 1
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
        private String imgpath;
        private String videopath;
        private String linkurl;
        private int type;

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

        public String getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }


}
