package cn.brision.football.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brision on 16/9/27.
 */
public class PlayerCircleInfo implements Serializable{
    /**
     * status : 200
     * message :
     * data : [{"postId":"57ea253595cc9","createdAt":"2016-09-27T15:52:21+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":6,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"57ea249846e14","createdAt":"2016-09-27T15:49:44+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":6,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"57ea23450d5cb","createdAt":"2016-09-27T15:44:05+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":9,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"57ea2066692fe","createdAt":"2016-09-27T15:31:50+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":11,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"57ea1fd79d5ca","createdAt":"2016-09-27T15:29:27+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":12,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"57ea1f7cefc08","createdAt":"2016-09-27T15:27:57+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":12,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"57ea1e5e45bfd","createdAt":"2016-09-27T15:23:10+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":13,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"57e8c214da55c","createdAt":"2016-09-26T14:37:08+08:00","type":"1","cover":"","text":"漂亮","viewCount":27,"shareCount":"0","poster":{"avatar":"","nick":"188****9878","openId":"p_Q-G5tfn_"},"commentsCount":0,"comments":[],"image":[],"video":[],"event":[]},{"postId":"57e87e11e9574","createdAt":"2016-09-26T09:46:57+08:00","type":"1","cover":"","text":"很好看刘皎刘皎刘皎刘皎","viewCount":41,"shareCount":"0","poster":{"avatar":"","nick":"188****9878","openId":"p_Q-G5tfn_"},"commentsCount":1,"comments":[{"commentId":"228","message":"啊啊啊啊啊","createdAt":"2016-09-26T09:47:11+08:00","timestamp":1474854431,"poster":{"avatar":"","nick":"188****9878","openId":"p_Q-G5tfn_"}}],"image":[],"video":[],"event":[]},{"postId":"57de6837335af","createdAt":"2016-09-18T18:11:03+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":45,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"57de6555771b2","createdAt":"2016-09-18T17:58:45+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":45,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"57de643177017","createdAt":"2016-09-18T17:53:54+08:00","type":"2","cover":"http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","text":"2016-07-21Test","viewCount":45,"shareCount":"0","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"},"commentsCount":0,"comments":[],"image":["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"],"video":[],"event":[]},{"postId":"5787324735399","createdAt":"2016-07-08T04:11:31+08:00","type":"3","cover":"","text":"格里兹曼 (法国) 破门得分","viewCount":140,"shareCount":"0","poster":{"avatar":"http://public.brision.cn/share/image2/brisionbot002.png","nick":"小圈机器人1号","openId":"Kd6SqVnVEa"},"commentsCount":1,"comments":[{"commentId":"229","message":"好好","createdAt":"2016-09-27T16:00:03+08:00","timestamp":1474963203,"poster":{"avatar":"","nick":"188****9878","openId":"p_Q-G5tfn_"}}],"image":[],"video":["http://data.brision.cn/sports/tasks/1557871951577ebc2d925bb/event.orig.mp4"],"event":[]},{"postId":"578732474d171","createdAt":"2016-07-08T03:46:40+08:00","type":"3","cover":"","text":"格里兹曼 (法国) 破门得分","viewCount":140,"shareCount":"0","poster":{"avatar":"http://public.brision.cn/share/image2/brisionbot002.png","nick":"小圈机器人1号","openId":"Kd6SqVnVEa"},"commentsCount":0,"comments":[],"image":[],"video":["http://data.brision.cn/sports/tasks/1433472199577ec5a314e8c/event.orig.mp4"],"event":[]},{"postId":"578732387bc77","createdAt":"2016-07-04T03:44:04+08:00","type":"3","cover":"","text":"格里兹曼 (法国) 破门得分","viewCount":152,"shareCount":"0","poster":{"avatar":"http://public.brision.cn/share/image2/brisionbot002.png","nick":"小圈机器人1号","openId":"Kd6SqVnVEa"},"commentsCount":0,"comments":[],"image":[],"video":["http://data.brision.cn/sports/tasks/75820436757796bbb2b853/event.orig.mp4"],"event":[]},{"postId":"5787322a57b4d","createdAt":"2016-06-26T10:00:46+08:00","type":"3","cover":"","text":"格里兹曼 (法国) 破门得分","viewCount":137,"shareCount":"0","poster":{"avatar":"http://public.brision.cn/share/image2/brisionbot002.png","nick":"小圈机器人1号","openId":"Kd6SqVnVEa"},"commentsCount":0,"comments":[],"image":[],"video":["http://data.brision.cn/sports/tasks/861531861576fe4f1714c9/event.orig.mp4"],"event":[]},{"postId":"5787322a57574","createdAt":"2016-06-26T09:57:00+08:00","type":"3","cover":"","text":"格里兹曼 (法国) 破门得分","viewCount":137,"shareCount":"0","poster":{"avatar":"http://public.brision.cn/share/image2/brisionbot002.png","nick":"小圈机器人1号","openId":"Kd6SqVnVEa"},"commentsCount":0,"comments":[],"image":[],"video":["http://data.brision.cn/sports/tasks/519976047576fe41840f0b/event.orig.mp4"],"event":[]},{"postId":"578732056077c","createdAt":"2016-06-16T04:29:10+08:00","type":"3","cover":"","text":"格里兹曼 (法国) 破门得分","viewCount":137,"shareCount":"0","poster":{"avatar":"http://public.brision.cn/share/image2/brisionbot002.png","nick":"小圈机器人1号","openId":"Kd6SqVnVEa"},"commentsCount":0,"comments":[],"image":[],"video":["http://data.brision.cn/sports/tasks/5510374625761fb3cf3318/event.orig.mp4"],"event":[]}]
     */
    //https://api.brision.cn/webservice/api/subjects/636/posts?page=1
    private int status;
    private String message;
    /**
     * postId : 57ea253595cc9
     * createdAt : 2016-09-27T15:52:21+08:00
     * type : 2
     * cover : http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png
     * text : 2016-07-21Test
     * viewCount : 6
     * shareCount : 0
     * poster : {"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"}
     * commentsCount : 0
     * comments : []
     * image : ["http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png","http://upload.brision.cn/576BA486-73E8-4578-9D85-C840936A9E43.png"]
     * video : []
     * event : []
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
        private String postId;
        private String createdAt;
        private String type;
        private String cover;
        private String text;
        private int viewCount;
        private String shareCount;
        /**
         * avatar : http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png
         * nick : Matt
         * openId : jIfJDqMtWZ
         */

        private PosterBean poster;
        private int commentsCount;
        private List<PlayerCommentsInfo> comments;
        private List<String> image;
        private List<?> video;
        private List<?> event;

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public String getShareCount() {
            return shareCount;
        }

        public void setShareCount(String shareCount) {
            this.shareCount = shareCount;
        }

        public PosterBean getPoster() {
            return poster;
        }

        public void setPoster(PosterBean poster) {
            this.poster = poster;
        }

        public int getCommentsCount() {
            return commentsCount;
        }

        public void setCommentsCount(int commentsCount) {
            this.commentsCount = commentsCount;
        }

        public List<PlayerCommentsInfo> getComments() {
            return comments;
        }

        public void setComments(List<PlayerCommentsInfo> comments) {
            this.comments = comments;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }

        public List<?> getVideo() {
            return video;
        }

        public void setVideo(List<?> video) {
            this.video = video;
        }

        public List<?> getEvent() {
            return event;
        }

        public void setEvent(List<?> event) {
            this.event = event;
        }

        public static class PosterBean implements Serializable{
            private String avatar;
            private String nick;
            private String openId;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }
        }
    }

}
