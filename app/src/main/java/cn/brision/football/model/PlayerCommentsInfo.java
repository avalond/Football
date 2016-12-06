package cn.brision.football.model;

import java.io.Serializable;

/**
 * Created by brision on 16/9/29.
 */
public class PlayerCommentsInfo implements Serializable{

    /**
     * commentId : 179
     * message : ˊ_>ˋ
     * createdAt : 2016-08-03T10:29:24+08:00
     * timestamp : 1470191364
     * poster : {"avatar":"","nick":"159****7967","openId":"gdEUiSaG5z"}
     */

    private String commentId;
    private String message;
    private String createdAt;
    private int timestamp;
    /**
     * avatar :
     * nick : 159****7967
     * openId : gdEUiSaG5z
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

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public PosterBean getPoster() {
        return poster;
    }

    public void setPoster(PosterBean poster) {
        this.poster = poster;
    }

    public static class PosterBean implements Serializable{
        private String avatar;
        private String nick;
        private String openId;
//        public boolean isViComment = false;

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
