package cn.brision.football.model;

import java.io.Serializable;

/**
 * Created by brision on 16/11/14.
 */
public class BaseCircleInfo implements Serializable{
    /**
     * id : 4
     * title : 德国队
     * image : http://data.brision.cn/sports/textcaster/logo/56793680afad7.png
     * type : 2
     * teamId : 693
     * participates : 7
     * notesCount : 40
     * createdAt : 2016-04-18T12:54:49+08:00
     * isFollow : true
     */

    private String id;
    private String title;
    private String image;
    private String type;
    private String teamId;
    private String playerId;
    private String participates;
    private String notesCount;
    private String createdAt;
    private boolean isFollow;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getParticipates() {
        return participates;
    }

    public void setParticipates(String participates) {
        this.participates = participates;
    }

    public String getNotesCount() {
        return notesCount;
    }

    public void setNotesCount(String notesCount) {
        this.notesCount = notesCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

}
