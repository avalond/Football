package cn.brision.football.activity.lives;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.adapter.lives.LiveLeaguesAdapter;
import cn.brision.football.adapter.lives.LiveNotAdapter;
import cn.brision.football.data.MatchAction;
import cn.brision.football.eventbus.RefreshLiveLeagues;
import cn.brision.football.eventbus.RefreshLiveNotLeagues;
import cn.brision.football.model.LiveleaguesInfo;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.view.MyRecyclerView;

/**
 * Created by wangchengcheng on 16/11/4.
 *
 */
public class LivesSelectorActivity extends BaseActivity {

    @Bind(R.id.already_custom)
    MyRecyclerView already;
    @Bind(R.id.not_customized)
    MyRecyclerView not;

    private MatchAction action;
    private List<LiveleaguesInfo.DataBean> data = new ArrayList<>();
    private List<LiveleaguesInfo.DataBean> data0 = new ArrayList<>();
    private LiveLeaguesAdapter alreadyAdapter;
    private LiveNotAdapter notAdapter;
    private List<LiveleaguesInfo.DataBean> notData = new ArrayList<>();
    private PreferencesManager preferencesManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lives_selector);

        initToolbar();

        action = dataManger.getMatchAction();

        getAlreadyData();

        EventBus.getDefault().register(this);

        preferencesManager = PreferencesManager.getInstance(this);

    }

    private void initToolbar() {
        showToolbar();
        setToolbarTitle("赛事定制");
        setToolbarDividerEnable(false);
    }

    private void getAlreadyData() {
        action.liveleagues(new MatchAction.LiveLeaguesListener() {
            @Override
            public void liveLeagues(LiveleaguesInfo mLiveleaguesInfo) {
                if (mLiveleaguesInfo != null) {
                    data0.clear();
                    data0.addAll(mLiveleaguesInfo.getData());
                    data = mLiveleaguesInfo.getData();

                    initAdapter(data);

                    initNotAdapter(notData);
                }
            }
        });
    }

    private void initAdapter(List<LiveleaguesInfo.DataBean> data) {
        if (preferencesManager.isContain("dingYue")) {
            List<LiveleaguesInfo.DataBean> a = getPreferenceDingYueData("dingYue");
            data.clear();
            data.addAll(a);
        }

        if (alreadyAdapter == null) {
            alreadyAdapter = new LiveLeaguesAdapter(this, null, R.layout.item_live_leagues);
            alreadyAdapter.addNot(notData, preferencesManager);
            alreadyAdapter.mData = data;
            already.setLayoutManager(new GridLayoutManager(this, 3));
            already.setAdapter(alreadyAdapter);
            alreadyAdapter.notifyDataSetChanged();
        } else {
            alreadyAdapter.mData = data;
            alreadyAdapter.notifyDataSetChanged();
        }
    }

    private void initNotAdapter(List<LiveleaguesInfo.DataBean> notData) {
        if (preferencesManager.isContain("noDingYue")) {
            List<LiveleaguesInfo.DataBean> n = getPreferenceDingYueData("noDingYue");
            notData.clear();
            notData.addAll(n);
        }

        if (notAdapter == null) {
            notAdapter = new LiveNotAdapter(this, null, R.layout.item_live_leagues);
            notAdapter.addData(data, preferencesManager);
            notAdapter.mData = notData;
            not.setLayoutManager(new GridLayoutManager(this, 3));
            not.setAdapter(notAdapter);
            notAdapter.notifyDataSetChanged();
        } else {
            notAdapter.mData = notData;
            notAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取Preferences中所存储的 (已定制/未定制)  的赛事集合
     *
     * @param tab 存储的 (已定制/未定制) 集合标记
     * @return (读取定制的储存状态)     从Preferences中读取的Set<String>集合转还成List<LiveleaguesInfo.DataBean>
     */
    public List<LiveleaguesInfo.DataBean> getPreferenceDingYueData(String tab) {

        Set<String> set = preferencesManager.get(tab, new HashSet<String>());

        List<LiveleaguesInfo.DataBean> a = new ArrayList<>();

        for (int i = 0; i < data0.size(); i++) {
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(data0.get(i).getLeague())) {
                    a.add(data0.get(i));
                    break;
                }
            }
        }
        return a;
    }

    @Subscribe
    public void onEvent(RefreshLiveNotLeagues event) {
        notData = event.data;

        initNotAdapter(notData);
    }

    @Subscribe
    public void onEvent(RefreshLiveLeagues event) {
        data = event.data;

        initAdapter(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
