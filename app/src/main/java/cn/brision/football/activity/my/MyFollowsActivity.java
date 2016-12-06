package cn.brision.football.activity.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.adapter.my.MyFollowsAdaper;
import cn.brision.football.data.MatchAction;
import cn.brision.football.model.BaseCircleInfo;
import cn.brision.football.model.CircleFollowsInfo;

/**
 * Created by wangchengcheng on 16/11/14.
 *
 */
public class MyFollowsActivity extends BaseActivity {


    @Bind(R.id.my_follows)
    RecyclerView followsList;

    private MatchAction matchAction;
    private List<BaseCircleInfo> followsData;
    private MyFollowsAdaper adaper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myfollows);

        ButterKnife.bind(this);

        showToolbar();
        setToolbarTitle(this.getString(R.string.my_follows));
        setToolbarDividerEnable(false);

        matchAction = dataManger.getMatchAction();

        getFollowsData();
    }

    private void getFollowsData() {
        matchAction.circleFollows(new MatchAction.CircleFollowsListener() {
            @Override
            public void circleFollowsData(CircleFollowsInfo mCircleFollowsInfo) {

                if (mCircleFollowsInfo != null) {
                    followsData = mCircleFollowsInfo.getData();

                    filterData(followsData);

                    initAdapter();
                }
            }
        });
    }

    private void initAdapter() {
            adaper = new MyFollowsAdaper(this, null, R.layout.item_my_myfollows);
            adaper.mData = followsData;
            followsList.setLayoutManager(new LinearLayoutManager(this));
            followsList.setAdapter(adaper);
            adaper.notifyDataSetChanged();
    }

    public void filterData(List<BaseCircleInfo> data){
        for (int i = 0; i < data.size(); i++) {
            String type = data.get(i).getType();
            if (Integer.valueOf(type) != 2) {
                data.remove(i);
                --i;
            }
        }

    }


}
