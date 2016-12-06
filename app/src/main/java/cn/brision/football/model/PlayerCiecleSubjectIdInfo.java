package cn.brision.football.model;

/**
 * Created by brision on 16/9/27.
 */
public class PlayerCiecleSubjectIdInfo {


    /**
     * status : 200
     * message :
     * data : {"subjectId":"661"}
     */

    private int status;
    private String message;
    /**
     * subjectId : 661
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String subjectId;

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }
    }
}
