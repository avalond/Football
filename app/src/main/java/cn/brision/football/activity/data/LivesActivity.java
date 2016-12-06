package cn.brision.football.activity.data;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.data.MatchAction;
import cn.brision.football.model.ScheduleGameEvent;
import cn.brision.football.view.ptr.PtrDefaultHandler;
import cn.brision.football.view.ptr.PtrFrameLayout;
import cn.brision.football.view.ptr.PtrHandler;

/**
 * Created by brision on 16/9/27.
 */
public class LivesActivity extends ScheduleActivity {

    private MyCountTime mTimer;
    private final static int ALL_TIME = 60001;
    private final static int INTERVAL_TIME = 1000;
    private String gameId;
    private List<ScheduleGameEvent.DataBean> data = new ArrayList<>();
    private List<ScheduleGameEvent.DataBean> originalData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        gameId = intent.getStringExtra("GameID");

        mTimer = new MyCountTime(ALL_TIME, INTERVAL_TIME);
        initPtrframe();
        mTimer.start();

        getTitleData(gameId);
        getContentListViewData(gameId);
    }

    /**
     * 初始化刷新属性
     */
    private void initPtrframe() {
        mPtrframe.setLastUpdateTimeRelateObject(this);
        mPtrframe.setPtrHandler(mPtrHandler);
        mPtrframe.setResistance(1.7f);
        mPtrframe.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrframe.setDurationToClose(200);
        mPtrframe.setDurationToCloseHeader(1000);
        mPtrframe.setPullToRefresh(false);
    }

    /**
     * 刷新数据
     */
    private PtrHandler mPtrHandler = new PtrHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            getContentListViewData(gameId);
            mTimer.cancel();
            mTimer.start();
        }

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
    };

    /**
     * 下载播放列表数据
     */
    public void getContentListViewData(String gameId) {
        matchAction.schdeuleContent(gameId, new MatchAction.SchdeuleContentDataListener() {
            @Override
            public void schdeuleContentData(ScheduleGameEvent mScheduleGameEvent) {
                if (mScheduleGameEvent != null) {
                    if (mScheduleGameEvent.getData().size() != 0) {
                        data = getStatusData(mScheduleGameEvent.getData());
                        originalData = getStatusData(mScheduleGameEvent.getData());
                        mPtrframe.refreshComplete();
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

    /**
     * 直播页面的倒序
     *
     * @param list
     * @return
     */
    private List<ScheduleGameEvent.DataBean> getStatusData(List<ScheduleGameEvent.DataBean> list) {
        List<ScheduleGameEvent.DataBean> newList = new ArrayList<>();
        for (int i = list.size() - 1; i < list.size() && i >= 0; i--) {
            newList.add(list.get(i));
        }
        return newList;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPtrframe.refreshComplete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    class MyCountTime extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            mTimer.start();
            getContentListViewData(gameId);
        }
    }

}
