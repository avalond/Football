package cn.brision.football.activity.data;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.data.MatchAction;
import cn.brision.football.model.ScheduleGameEvent;

/**
 * Created by brision on 16/9/27.
 */
public class MatchActivity extends ScheduleActivity {

    private String gameId;
    private List<ScheduleGameEvent.DataBean> data = new ArrayList<>();
    private List<ScheduleGameEvent.DataBean> originalData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        gameId = intent.getStringExtra("GameID");
        mPtrframe.setEnabled(false);
        getTitleData(gameId);
        getContentListViewData(gameId);
    }

    /**
     * 下载播放列表数据
     */
    public void getContentListViewData(String gameId) {
        matchAction.schdeuleContent(gameId, new MatchAction.SchdeuleContentDataListener() {
            @Override
            public void schdeuleContentData(ScheduleGameEvent mScheduleGameEvent) {
                if (mScheduleGameEvent != null) {
                    if (mScheduleGameEvent.getData().size() != 0) {
                        data = mScheduleGameEvent.getData();
                        originalData = mScheduleGameEvent.getData();

                        for (int i = 0; i < data.size(); i++) {
                            strings.add(data.get(i).getUrl());
                        }
                        viewCount.setText(Html.fromHtml(data.get(0).getViewCount() + "<font color=#808080><b>次播放</b></font>"));
                    }
                    initVideo();
                    if (dataBeen.size() != 0) {
                        dataBeen.clear();
                        dataBeen.addAll(data);
                        adapter.setList(dataBeen, 0);
                        return;
                    }
                    dataBeen.addAll(data);
                    setContentListView();
                    setFifterList(data, originalData);
                }
            }
        });
        initVideo();
    }

    @OnClick(R.id.schdeul_back)
    public void backOnclick(View view) {
        this.finish();
    }
}
