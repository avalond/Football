package cn.brision.football.model;

/**
 * Created by wangchengcheng on 16/10/9.
 */
public class PlayerFollowInfo {

    /**
     * status : true
     * code : 200
     * notice :
     */

    private boolean status;
    private int code;
    private String notice;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
