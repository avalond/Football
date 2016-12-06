package cn.brision.football.model;

/**
 * Created by brision on 16/9/30.
 */
public class CommentMessagepost {
    private String postId;
    private String text;

    public String geteventId() {
        return postId;
    }

    public void setEventId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
