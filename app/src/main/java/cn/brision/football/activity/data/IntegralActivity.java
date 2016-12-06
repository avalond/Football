package cn.brision.football.activity.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.activity.logins.LoginActivity;
import cn.brision.football.adapter.data.integral.TeamPlayerAdapter;
import cn.brision.football.adapter.data.integral.TeamScheuleAdapter;
import cn.brision.football.adapter.expandRecyclerviewadapter.StickyRecyclerHeadersDecoration;
import cn.brision.football.data.MatchAction;
import cn.brision.football.model.IntegralTeam;
import cn.brision.football.model.PlayerCiecleSubjectIdInfo;
import cn.brision.football.model.TeamFollowInfo;
import cn.brision.football.model.TeamPlayer;
import cn.brision.football.model.TeamSchedule;
import cn.brision.football.utils.DisplayUtils;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.SystemBarHelper;
import cn.brision.football.view.CustomProgressDialog;


/**
 * Created by wangchengcheng on 16/9/2.
 * 球队信息页面
 */
public class IntegralActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.team_logo)
    ImageView teamLogo;
    @Bind(R.id.team_name)
    TextView teamName;
    @Bind(R.id.lineview)
    LinearLayout line;
    @Bind(R.id.text2)
    TextView schdeuleText;
    @Bind(R.id.text3)
    TextView playerText;
    @Bind(R.id.recycler2)
    RecyclerView schdeuleContent;
    @Bind(R.id.recycler3)
    RecyclerView playerContent;
    @Bind(R.id.relativeLayout2)
    RelativeLayout mRelativeLayout;
    @Bind(R.id.player_guanzhu)
    TextView mFollow;

    private String teamId;
    private String seasonId;
    private int lineWidth = 0;//初始化进度条的宽度
    private MatchAction matchAction;
    private List<TeamSchedule.DataBean> scheduleData;//球队--赛程部分的数据集合
    private List<TeamSchedule.DataBean> mSchedule = new ArrayList<>();
    private TeamScheuleAdapter teamScheuleAdapter;
    private List<TeamPlayer.DataBean> playerData;//球队--球员列表部分的数据集合
    private List<TeamPlayer.DataBean> mPlayer = new ArrayList<>();
    private TeamPlayerAdapter playerAdapter;
    private CustomProgressDialog customProgressDialog;
    public String subjectId;
    private IntegralTeam.DataBean titleData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_integral);
        ButterKnife.bind(this);
        matchAction = dataManger.getMatchAction();

        hideToolbar();
        initBar();

        getIntentData();
        getSubjectId();
        getTeamData();
        getScheuleData();
        getPlayerData();

        mFollow.setOnClickListener(this);
    }

    private boolean isLoging() {
        PreferencesManager pm = PreferencesManager.getInstance(this);
        String accessToken = pm.get("Access_token", "");

        return accessToken.length() > 0;
    }

    private void initBar() {
        //初始化statusbar
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mRelativeLayout);

        //初始化dialog
        customProgressDialog = new CustomProgressDialog(this, R.style.ProgressHUD);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //设置进度条的父容器的宽度,为屏幕宽度的一半
        int screenWidth = DisplayUtils.getScreenWidth(this);
        lineWidth = screenWidth / 2;

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) line.getLayoutParams();
        params.width = lineWidth;
        line.setLayoutParams(params);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        teamId = intent.getStringExtra("teamId");
        seasonId = intent.getStringExtra("seasonId");
    }


    @Override
    public void onClick(View v) {
        if (!isLoging()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {

            if (subjectId != null && titleData != null) {
                mFollow.setEnabled(false);
                if (!titleData.isIsFollowed()) {
                    follow();
                } else {
                    unFollow();
                }
            } else {
                customProgressDialog.setStyle(IntegralActivity.this, true, false, null, CustomProgressDialog.DEFEAT);
                customProgressDialog.show();
            }
        }
    }

    private void unFollow() {
        dismissDialog();
        customProgressDialog.setStyle(this, true, false, null, CustomProgressDialog.LOADING_UN);
        customProgressDialog.show();
        matchAction.teamUnfollow(subjectId, new MatchAction.TeamUnfollowListener() {
            @Override
            public void teamUnfollowData(TeamFollowInfo mTeamFollowInfo) {
                if (mTeamFollowInfo.getStatus() == 200) {
                    customProgressDialog.setStyle(IntegralActivity.this, true, false, null, CustomProgressDialog.FAIL);
                    setFollowText(R.string.follow, R.mipmap.unfollow);
                    getTeamData();
                } else {
                    customProgressDialog.setStyle(IntegralActivity.this, true, false, null, CustomProgressDialog.MISSFAIL);
                }
            }
        });
    }

    private void follow() {
        dismissDialog();
        customProgressDialog.setStyle(this, true, false, null, CustomProgressDialog.LOADING_SUB);
        customProgressDialog.show();
        matchAction.teamFollow(subjectId, new MatchAction.TeamFollowListener() {
            @Override
            public void teamFollowData(TeamFollowInfo mTeamFollowInfo) {
                if (mTeamFollowInfo.getStatus() == 200) {
                    customProgressDialog.setStyle(IntegralActivity.this, true, false, null, CustomProgressDialog.SUCCESS);
                    setFollowText(R.string.followed, R.mipmap.follow);
                    getTeamData();
                } else {
                    customProgressDialog.setStyle(IntegralActivity.this, true, false, null, CustomProgressDialog.DEFEAT);
                }

            }
        });
    }

    private void setFollowText(int stringId, int mipmapId) {
        mFollow.setText(getResources().getString(stringId));
        mFollow.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(mipmapId), null, null, null);
    }


    private void dismissDialog() {
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
        }
    }

    private void getTeamData() {
        matchAction.integralTitle(teamId, new MatchAction.IntegralTitleDataListener() {
            @Override
            public void integralTitleData(IntegralTeam mIntegralTeam) {
                titleData = mIntegralTeam.getData();
                teamName.setText(mIntegralTeam.getData().getName());
                GlideUtils.get(IntegralActivity.this).getImage(mIntegralTeam.getData().getLogo(),
                        getResources().getDrawable(R.drawable.defaultlogo), teamLogo, R.anim.fade_in);
                if (!mIntegralTeam.getData().isIsFollowed()) {
                    setFollowText(R.string.follow, R.mipmap.unfollow);
                } else {
                    setFollowText(R.string.followed, R.mipmap.follow);
                }
                mFollow.setEnabled(true);
            }
        });

    }

    private void getSubjectId() {
        matchAction.teamIntegralSubjectId(teamId, new MatchAction.TeamIntegralSubjectIdDataListener() {
            @Override
            public void teamIntegralSubjectIdData(PlayerCiecleSubjectIdInfo mPlayerCiecleSubjectIdInfo) {
                if (mPlayerCiecleSubjectIdInfo != null) {
                    subjectId = mPlayerCiecleSubjectIdInfo.getData().getSubjectId();
                }
            }
        });
    }

    private void getScheuleData() {
        matchAction.teamSchedule(teamId, seasonId, "1", new MatchAction.TeamScheduleDataListener() {
            @Override
            public void teamScheduleData(TeamSchedule mTeamSchedule) {
                scheduleData = mTeamSchedule.getData();

                setScheuleData(scheduleData);
                initScheuleAdapter();
            }
        });
    }

    private void getPlayerData() {
        matchAction.teamPlayer(teamId, new MatchAction.TeamPlayerDataListener() {
            @Override
            public void teamPlayerData(TeamPlayer mTeamPlayer) {
                playerData = mTeamPlayer.getData();

                PlayerPositionCompare compare = new PlayerPositionCompare();
                Collections.sort(playerData, compare);

                setPlayerData(playerData);
                initPlayerAdapter();
            }


        });
    }

    private void initPlayerAdapter() {
        if (playerAdapter == null) {
            playerAdapter = new TeamPlayerAdapter(this);
            playerAdapter.addData(mPlayer);
            int orientation = LinearLayoutManager.VERTICAL;
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this, orientation, false);
            playerContent.setLayoutManager(layoutManager);

            playerContent.setAdapter(playerAdapter);

            final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(playerAdapter);
            playerContent.addItemDecoration(headersDecor);
            playerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    headersDecor.invalidateHeaders();
                }
            });
        } else {
            playerAdapter.addData(mPlayer);
        }
    }

    private void setPlayerData(List<TeamPlayer.DataBean> data) {
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                TeamPlayer.DataBean dataBean = new TeamPlayer.DataBean();
                dataBean.setId(data.get(i).getId());
                dataBean.setName(data.get(i).getName());
                dataBean.setNumber(data.get(i).getNumber());
                dataBean.setPosition(data.get(i).getPosition());
                dataBean.setPhoto(data.get(i).getPhoto());
                mPlayer.add(dataBean);
            }
        }
    }

    private void initScheuleAdapter() {
        if (teamScheuleAdapter == null) {
            teamScheuleAdapter = new TeamScheuleAdapter(this);
            teamScheuleAdapter.addData(mSchedule);
            int orientation = LinearLayoutManager.VERTICAL;
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this, orientation, false);
            schdeuleContent.setLayoutManager(layoutManager);

            schdeuleContent.setAdapter(teamScheuleAdapter);

            final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(teamScheuleAdapter);
            schdeuleContent.addItemDecoration(headersDecor);
            teamScheuleAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    headersDecor.invalidateHeaders();
                }
            });
        } else {
            teamScheuleAdapter.addData(mSchedule);
        }
    }

    private void setScheuleData(List<TeamSchedule.DataBean> data) {
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                TeamSchedule.DataBean dataBean = new TeamSchedule.DataBean();
                dataBean.setId(data.get(i).getId());
                dataBean.setDate(data.get(i).getDate());
                dataBean.setTime(data.get(i).getTime());
                dataBean.setCaster(data.get(i).getCaster());
                dataBean.setHome(data.get(i).getHome());
                dataBean.setAway(data.get(i).getAway());
                dataBean.setScore(data.get(i).getScore());
                dataBean.setStatus(data.get(i).getStatus());
                dataBean.setHome_id(data.get(i).getHome_id());
                dataBean.setHome_logo(data.get(i).getHome_logo());
                dataBean.setAway_id(data.get(i).getAway_id());
                dataBean.setAway_logo(data.get(i).getAway_logo());
                dataBean.setDatetime(data.get(i).getDatetime());
                mSchedule.add(dataBean);
            }
        }
    }

    @OnClick(R.id.integral_back)
    public void backClick(View view) {
        this.finish();
    }

    @OnClick(R.id.text2)
    public void schdeuleOnClick(View view) {
        resetUi();
        schdeuleText.setTextColor(this.getResources().getColor(R.color.yellow_01));
        schdeuleContent.setVisibility(View.VISIBLE);
        startMove(lineWidth, 0);
        playerText.setEnabled(true);
    }

    @OnClick(R.id.text3)
    public void playerOnClick(View view) {
        resetUi();
        playerText.setTextColor(this.getResources().getColor(R.color.yellow_01));
        playerContent.setVisibility(View.VISIBLE);
        startMove(0, lineWidth);
        schdeuleText.setEnabled(true);
    }

    /**
     * tab切换时,进度条的水平移动
     *
     * @param formX 开始位置
     * @param toX   结束位置
     */
    private void startMove(int formX, int toX) {
        Animation animation = new TranslateAnimation(formX, toX, 0, 0);
        animation.setDuration(200);
        animation.setInterpolator(new AccelerateInterpolator());//加速器
        animation.setFillAfter(true);//停在结束位置
        line.startAnimation(animation);
    }

    private void resetUi() {
        schdeuleText.setTextColor(this.getResources().getColor(R.color.gray_128));
        playerText.setTextColor(this.getResources().getColor(R.color.gray_128));

        playerText.setEnabled(false);
        schdeuleText.setEnabled(false);

        schdeuleContent.setVisibility(View.GONE);
        playerContent.setVisibility(View.GONE);
    }


    public class PlayerPositionCompare implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            TeamPlayer.DataBean m = (TeamPlayer.DataBean) o1;
            TeamPlayer.DataBean n = (TeamPlayer.DataBean) o2;
            int flag = m.getPosition().compareTo(n.getPosition());
            return flag;
        }
    }
}
