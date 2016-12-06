package cn.brision.football.activity.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.adapter.my.MyReservationAdapter;
import cn.brision.football.data.MatchAction;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.leancloud.MyUserReservation;
import cn.brision.football.model.LivesInfo;
import cn.brision.football.view.TouchableRecyclerView;

/**
 * Created by wangchengcheng on 16/11/16.
 */
public class MyYuyueActivity extends BaseActivity {

    @Bind(R.id.my_follows)
    TouchableRecyclerView mRecyclerView;
    private List<LivesInfo.DataBean.LivesBean> results = new ArrayList<>();
    private MatchAction matchAction;
    private LivesInfo mLivesInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myfollows);

        ButterKnife.bind(this);
        matchAction = dataManger.getMatchAction();
        showToolbar();
        setToolbarTitle(this.getString(R.string.my_yuyue));
        setToolbarDividerEnable(false);

        initClean();
    }

    private void initClean() {
        MyUser user = (MyUser) MyUser.getCurrentUser();
        AVQuery<MyUserReservation> query = AVObject.getQuery(MyUserReservation.class);
        query.whereEqualTo("user", user);
        query.findInBackground(new FindCallback<MyUserReservation>() {
            @Override
            public void done(List<MyUserReservation> results1, AVException e) {
                if (results1 != null) {
                    for (int i = 0; i < results1.size(); i++) {
                        JSONObject reservation = results1.get(i).getReservation();
                        Gson gson = new Gson();
                        LivesInfo.DataBean.LivesBean livesBean = gson.fromJson(reservation.toString(), LivesInfo.DataBean.LivesBean.class);
                        results.add(livesBean);
                    }
                    initData();
                }
            }
        });
    }

    private void initData() {
        matchAction.dataLives(new MatchAction.DataLivesDataListener() {
            @Override
            public void dataLivesData(LivesInfo mLivesInfo) {
                MyYuyueActivity.this.mLivesInfo = mLivesInfo;
                initRecycler();
            }
        });
    }

    MyReservationAdapter myReservationAdapter;
    private void initRecycler() {
        for (int i = 0; i < results.size(); i++) {
            String id = results.get(i).getId();

            for (int j = 0; j < mLivesInfo.getData().size(); j++) {
                List<LivesInfo.DataBean.LivesBean> lives = mLivesInfo.getData().get(j).getLives();
                for (int k = 0; k < lives.size(); k++) {
                    if (id.equals(lives.get(k).getId()))
                        results.get(i).setLivesStatus(lives.get(k).getLivesStatus());
                }
            }
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myReservationAdapter = new MyReservationAdapter(this, results);
        mRecyclerView.setAdapter(myReservationAdapter);
    }

    public void deleteUser(final int position) {
        AVQuery<MyUserReservation> query = AVObject.getQuery(MyUserReservation.class);
        query.whereEqualTo("gameId", myReservationAdapter.getItem(position).getId());
        query.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
            }
        });
        myReservationAdapter.remove(myReservationAdapter.getItem(position));

    }
}
