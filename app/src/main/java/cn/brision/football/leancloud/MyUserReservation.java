package cn.brision.football.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import org.json.JSONObject;
import java.io.Serializable;

import cn.brision.football.model.LivesInfo;

/**
 * Created by brision on 16/11/28.
 */
@AVClassName("UserReservation")
public class MyUserReservation extends AVObject {


    public MyUserReservation(){

    }

    public String getGameId() {
        return getString("gameId");
    }

    public void setGameId(String gameId) {
        put("gameId",gameId);
    }

    public MyUser getUser() {
        return getAVObject("user");
    }

    public void setUser(MyUser user) {
        put("user",user);
    }

    public JSONObject getReservation() {
        return getJSONObject("reservation");
    }

    public void setReservation(JSONObject reservation) {
        put("reservation",reservation);
    }
}