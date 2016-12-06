package cn.brision.football.model;

import java.util.List;

/**
 * Created by brision on 16/11/1.
 */
public class TeamFollowInfo {

    /**
     * status : 200
     * message : follow success
     * data : []
     */

    private int status;
    private String message;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
