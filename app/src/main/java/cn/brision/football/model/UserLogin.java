package cn.brision.football.model;

/**
 * Created by wangchengcheng on 16/8/9.
 */
public class UserLogin {

    /**
     * status : true
     * code : 200
     * notice :
     * access_token : d0636a6b5f2e9a840b42a752d0503f98
     * openid : p_Q-G5tfn_
     * authData : {"uid":"p_Q-G5tfn_","access_token":"d0636a6b5f2e9a840b42a752d0503f98"}
     */

    private boolean status;
    private int code;
    private String notice;
    private String access_token;
    private String openid;
    /**
     * uid : p_Q-G5tfn_
     * access_token : d0636a6b5f2e9a840b42a752d0503f98
     */

    private AuthDataBean authData;

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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public AuthDataBean getAuthData() {
        return authData;
    }

    public void setAuthData(AuthDataBean authData) {
        this.authData = authData;
    }

    public static class AuthDataBean {
        private String uid;
        private String access_token;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }
}
