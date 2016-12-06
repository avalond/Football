package cn.brision.football.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brision on 16/11/8.
 */
public class CircleFollowsInfo implements Serializable{


    /**
     * status : 200
     * message :
     * data : [{"id":56,"name":"北京国安","type":"team","refId":"5cd55","image":"http://brison.com/XXXX.png","follows":8508,"posts":800},{"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://brison.com/XXXX.png","follows":8503,"posts":800},{"id":57,"name":"皇马","type":"team","refId":"5cd75","image":"http://brison.com/XXXX.png","follows":89002,"posts":800},{"id":57,"name":"巴萨","type":"team","refId":"5cd75","image":"http://brison.com/XXXX.png","follows":59632,"posts":800}]
     */

    private int status;
    private String message;
    /**
     * id : 56
     * name : 北京国安
     * type : team
     * refId : 5cd55
     * image : http://brison.com/XXXX.png
     * follows : 8508
     * posts : 800
     */

    private List<BaseCircleInfo> data;

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

    public List<BaseCircleInfo> getData() {
        return data;
    }

    public void setData(List<BaseCircleInfo> data) {
        this.data = data;
    }


}
