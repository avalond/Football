package cn.brision.football.leancloud;

import com.avos.avoscloud.AVUser;

/**
 * Created by brision on 16/11/23.
 */

public class MyUser extends AVUser {

    public void setNickName(String name) {
        this.put("nick", name);
    }

    public String getNickName() {
        return this.getString("nick");
    }

    public void setAvatar(String avatar) {
        this.put("avatar", avatar);
    }

    public String getAvatar() {
        return this.getString("avatar");
    }
}
