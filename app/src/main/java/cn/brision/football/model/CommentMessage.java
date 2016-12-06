package cn.brision.football.model;

/**
 * Created by brision on 16/9/30.
 */
public class CommentMessage {
    private String eventId;
    private String text;

    public String geteventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
