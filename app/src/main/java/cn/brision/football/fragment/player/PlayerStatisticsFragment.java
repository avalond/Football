package cn.brision.football.fragment.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.activity.data.PlayerActivity;
import cn.brision.football.adapter.data.player.PlayerStatisticsAdapter;
import cn.brision.football.data.MatchAction;
import cn.brision.football.fragment.BaseFragment;
import cn.brision.football.model.PlayerStatisticsInfo;

/**
 * Created by wangchengcheng on 16/9/27.
 */
public class PlayerStatisticsFragment extends BaseFragment {

    @Bind(R.id.player_statistics)
    RecyclerView statisticsListView;

    private MatchAction matchAction;
    private List<PlayerStatisticsInfo.DataBean.DataBean1> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_player_statistics);
        matchAction = dataManger.getMatchAction();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getOkhttpData() {
        matchAction.playerStatistics(((PlayerActivity) getActivity()).player, new MatchAction.PlayerStatisticsDataListener() {
            @Override
            public void playerStatisticsData(PlayerStatisticsInfo mPlayerStatisticsInfo) {
                if (mPlayerStatisticsInfo != null) {
                    data = mPlayerStatisticsInfo.getData().getData();
                    PlayerStatisticsAdapter adapter = new PlayerStatisticsAdapter(getActivity(), data, R.layout.item_player_statistics);
                    if (((PlayerActivity) getActivity()).player != null)
                        adapter.setPlayerId(((PlayerActivity) getActivity()).player);
                    statisticsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    statisticsListView.setAdapter(adapter);
                }
            }
        });


    }
}
