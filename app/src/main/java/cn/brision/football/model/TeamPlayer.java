package cn.brision.football.model;

import java.util.List;

/**
 * Created by wangchengcheng on 16/11/1.
 */
public class TeamPlayer {

    /**
     * status : 200
     * message :
     * data : [{"id":"21458","name":"芭芭拉","number":"1","position":"前锋","photo":"images/ico_delete.png"},{"id":"21459","name":"艾琳","number":null,"position":"守门员","photo":null},{"id":"21460","name":"莫妮卡","number":"3","position":"前锋","photo":"images/ico_delete.png"},{"id":"21463","name":"弗米加","number":"8","position":"前锋","photo":"images/ico_delete.png"},{"id":"21464","name":"艾丽卡","number":"13","position":"前锋","photo":"images/ico_delete.png"},{"id":"21614","name":"塔伊萨\t","number":"5","position":"前锋","photo":"images/ico_delete.png"},{"id":"21616","name":"塔米尔斯","number":"6","position":"前锋","photo":"images/ico_delete.png"},{"id":"21617","name":"安德萨-阿尔维斯","number":"9","position":"前锋","photo":"images/ico_delete.png"},{"id":"21618","name":"马尔塔","number":"10","position":"前锋","photo":"images/ico_delete.png"},{"id":"21619","name":"克里斯琴","number":"11","position":"前锋","photo":"images/ico_delete.png"},{"id":"21620","name":"比阿特丽斯","number":"16","position":"前锋","photo":"images/ico_delete.png"},{"id":"21621","name":"德伯拉","number":"7","position":"前锋","photo":"images/ico_delete.png"},{"id":"21622","name":"波利娜","number":"12","position":"前锋","photo":"images/ico_delete.png"},{"id":"21623","name":"艾丽卡","number":null,"position":"教练","photo":null},{"id":"21624","name":"布吕纳","number":"14","position":"前锋","photo":"images/ico_delete.png"},{"id":"21625","name":"拉奎尔-费尔南德斯","number":"15","position":"前锋","photo":"images/ico_delete.png"},{"id":"21626","name":"安德瑞莎","number":"17","position":"前锋","photo":"images/ico_delete.png"},{"id":"21627","name":"阿莱","number":"18","position":"前锋","photo":"images/ico_delete.png"},{"id":"21816","name":"菲比安娜","number":"2","position":"前锋","photo":"images/ico_delete.png"},{"id":"22390","name":"4\t拉法勒","number":null,"position":"前锋","photo":null},{"id":"22392","name":"4\t拉法勒","number":null,"position":"前锋","photo":null},{"id":"22393","name":"4\t拉法勒","number":null,"position":"前锋","photo":null}]
     */

    private int status;
    private String message;
    /**
     * id : 21458
     * name : 芭芭拉
     * number : 1
     * position : 前锋
     * photo : images/ico_delete.png
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
        private String id;
        private String name;
        private String number;
        private String position;
        private String photo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
