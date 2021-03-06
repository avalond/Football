package cn.brision.football.model;

import java.util.List;

/**
 * Created by brision on 16/9/14.
 */
public class UserLogout {

    /**
     * status : 200
     * message : Logout sueecess.
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
