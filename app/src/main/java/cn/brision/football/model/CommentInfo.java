package cn.brision.football.model;

import java.util.List;

/**
 * Created by brision on 16/9/30.
 */
public class CommentInfo {

    /**
     * status : 200
     * message :
     * data : [{"commentId":"199","message":"11测试text1","createdAt":"2016-08-09T10:17:22+08:00","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"}},{"commentId":"234","message":"11测试text1","createdAt":"2016-09-30T09:58:00+08:00","poster":{"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"}}]
     */

    private int status;
    private String message;
    /**
     * commentId : 199
     * message : 11测试text1
     * createdAt : 2016-08-09T10:17:22+08:00
     * poster : {"avatar":"http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png","nick":"Matt","openId":"jIfJDqMtWZ"}
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
        private String commentId;
        private String message;
        private String createdAt;
        /**
         * avatar : http://file-you06.oss-cn-beijing.aliyuncs.com/avatar.png
         * nick : Matt
         * openId : jIfJDqMtWZ
         */

        private PosterBean poster;

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public PosterBean getPoster() {
            return poster;
        }

        public void setPoster(PosterBean poster) {
            this.poster = poster;
        }

        public static class PosterBean {
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
