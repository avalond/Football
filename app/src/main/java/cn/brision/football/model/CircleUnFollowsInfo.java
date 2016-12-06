package cn.brision.football.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brision on 16/11/8.
 */
public class CircleUnFollowsInfo implements Serializable{


    /**
     * status : 200
     * message :
     * data : [{"title":"中超","content":[{"id":56,"name":"北京国安","type":"team","refId":"933","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},
     * {"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},
     * {"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},
     * {"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},
     * {"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},
     * {"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800}]},
     * {"title":"英超","content":[{"id":59,"name":"曼联","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":60,"name":"利物浦","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800}]},{"title":"西甲","content":[{"id":59,"name":"马竞","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":60,"name":"皇马","type":"team","refId":"5cd75","image":"hhttp://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":60,"name":"皇马","type":"team","refId":"5cd75","image":"hhttp://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":60,"name":"皇马","type":"team","refId":"5cd75","image":"hhttp://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":60,"name":"皇马","type":"team","refId":"5cd75","image":"hhttp://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":60,"name":"皇马","type":"team","refId":"5cd75","image":"hhttp://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":60,"name":"皇马","type":"team","refId":"5cd75","image":"hhttp://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":60,"name":"皇马","type":"team","refId":"5cd75","image":"hhttp://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800}]}]
     */

    private int status;
    private String message;
    /**
     * title : 中超
     * content : [{"id":56,"name":"北京国安","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800},{"id":57,"name":"陕西国力","type":"team","refId":"5cd75","image":"http://reso3.yiihuu.com/img_1329648.jpg","follows":85002,"posts":800}]
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

    public static class DataBean implements Serializable{
        private String title;
        /**
         * id : 56
         * name : 北京国安
         * type : team
         * refId : 5cd75
         * image : http://reso3.yiihuu.com/img_1329648.jpg
         * follows : 85002
         * posts : 800
         */

        private List<BaseCircleInfo> content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<BaseCircleInfo> getContent() {
            return content;
        }

        public void setContent(List<BaseCircleInfo> content) {
            this.content = content;
        }


    }
}
