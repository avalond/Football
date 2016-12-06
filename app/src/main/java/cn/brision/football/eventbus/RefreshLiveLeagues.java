package cn.brision.football.eventbus;

import java.util.ArrayList;
import java.util.List;

import cn.brision.football.model.LiveleaguesInfo;

/**
 * Created by wangchengcheng on 16/11/7.
 */
public class RefreshLiveLeagues {
    public List<LiveleaguesInfo.DataBean> data = new ArrayList<>();

    public RefreshLiveLeagues(List<LiveleaguesInfo.DataBean> data) {
        this.data = data;
    }
}
