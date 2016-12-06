package cn.brision.football.fragment.circleSelection;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.activity.logins.LoginActivity;
import cn.brision.football.data.MatchAction;
import cn.brision.football.model.BaseCircleInfo;
import cn.brision.football.model.CircleFollowService;
import cn.brision.football.model.PlayerFollowInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.SystemBarHelper;
import cn.brision.football.view.CustomProgressDialog;

/**
 * Created by brision on 16/11/10.
 */
public class CircleItemActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.ll_fragment)
    LinearLayout ll;
    @Bind(R.id.toolbar_view)
    LinearLayout toolbar_view;
    @Bind(R.id.view_miss)
    View mView;
    @Bind(R.id.app_layout)
    AppBarLayout toolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.player_guanzhu)
    TextView followView;
    @Bind(R.id.follows_counts)
    TextView followsCounts;
    @Bind(R.id.post_counts)
    TextView postCounts;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.title_logo)
    ImageView logo;


    public String subjectId;
    public boolean isRefersh = false;
    private PlayerCircleFragment playerCircleFragment;
    private CustomProgressDialog progressDialog;
    private MatchAction matchAction;
    private BaseCircleInfo circleFollowsInfo;
    private CircleFollowService.DataBean data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_itemteam);
        ButterKnife.bind(this);
        hideToolbar();
        setToobar();
        matchAction = dataManger.getMatchAction();

        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mView);

        initTitle();
        initData();
        progressDialog = new CustomProgressDialog(this, R.style.ProgressHUD);
        initView();

        followView.setOnClickListener(this);
    }

    public void initTitle() {
        Intent intent = getIntent();
        circleFollowsInfo = (BaseCircleInfo) intent.getSerializableExtra("BaseCircleInfo");
        subjectId = circleFollowsInfo.getId();

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        playerCircleFragment = new PlayerCircleFragment();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll_fragment, playerCircleFragment);
        fragmentTransaction.commit();

        //变化toobar颜色
        toolbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int measuredHeight = toolbar.getMeasuredHeight();

                if (measuredHeight > Math.abs(verticalOffset) && verticalOffset != 0) {
                    setToobar();
                    settate(false);
                } else if (verticalOffset == 0) {
                    setToobar();
                    settate(true);
                } else {
                    initToobarColor();
                    settate(false);
                }
            }
        });

        toolbarTitle.setText(circleFollowsInfo.getTitle());
        followsCounts.setText(circleFollowsInfo.getParticipates());
        postCounts.setText(circleFollowsInfo.getNotesCount());
        GlideUtils.get(this).getImage(circleFollowsInfo.getImage(), getResources().getDrawable(R.mipmap.logo), logo, R.anim.fade_in);
    }

    public void settate(boolean isRefersh) {
        this.isRefersh = isRefersh;
        playerCircleFragment.setStatus(this.isRefersh);
    }

    public void initToobarColor() {
        toolbar_view.setBackgroundColor(getResources().getColor(R.color.title_bg));
    }

    public void setToobar() {
        toolbar_view.setBackgroundColor(getResources().getColor(R.color.transparent_30));
    }

    @OnClick(R.id.toolbar_left_btn)
    public void backAction(View view) {
        finish();
    }

    private void initData() {
        matchAction.circleUnFollowsService(circleFollowsInfo.getTeamId(), new MatchAction.CircleUnFollowsServiceListener() {

            @Override
            public void circleUnFollowsServiceData(CircleFollowService mCircleFollowService) {
                if (mCircleFollowService!=null) {
                    data = mCircleFollowService.getData();
                    if (data.isIsFollowed()) {
                        setFollowText(R.string.followed, R.mipmap.follow);
                    } else {
                        setFollowText(R.string.follow, R.mipmap.unfollow);
                    }
                    followView.setEnabled(true);
                }
            }
        });
    }

    private boolean isLoging() {
        PreferencesManager pm = PreferencesManager.getInstance(this);
        String accessToken = pm.get("Access_token", "");

        return accessToken.length() > 0;
    }

    @Override
    public void onClick(View v) {
        if (!isLoging()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            if (data != null && subjectId != null) {
                followView.setEnabled(false);
                if (!data.isIsFollowed()) {
                    follow();
                } else {
                    unFollow();
                }
            } else {
                progressDialog.setStyle(CircleItemActivity.this, true, false, null, CustomProgressDialog.DEFEAT);
                progressDialog.show();
            }
        }
    }

    private void unFollow() {
        dismissDialog();
        progressDialog.setStyle(this, true, false, null, CustomProgressDialog.LOADING_UN);
        progressDialog.show();
        matchAction.playerUnfollow(subjectId, new MatchAction.PlayerUnfollowListener() {
            @Override
            public void playerUnfollowData(PlayerFollowInfo mPlayerFollowInfo) {
                if (mPlayerFollowInfo.getCode() == 200) {
                    progressDialog.setStyle(CircleItemActivity.this, true, false, null, CustomProgressDialog.FAIL);
                    setFollowText(R.string.follow, R.mipmap.unfollow);
                    initData();
                } else {
                    progressDialog.setStyle(CircleItemActivity.this, true, false, null, CustomProgressDialog.MISSFAIL);
                }
            }
        });
    }

    private void follow() {
        dismissDialog();
        progressDialog.setStyle(this, true, false, null, CustomProgressDialog.LOADING_SUB);
        progressDialog.show();
        matchAction.playerFollow(subjectId, new MatchAction.PlayerFollowListener() {
            @Override
            public void playerFollowData(PlayerFollowInfo mPlayerFollowInfo) {
                if (mPlayerFollowInfo.getCode() == 200) {
                    progressDialog.setStyle(CircleItemActivity.this, true, false, null, CustomProgressDialog.SUCCESS);
                    setFollowText(R.string.followed, R.mipmap.follow);
                    initData();
                } else {
                    progressDialog.setStyle(CircleItemActivity.this, true, false, null, CustomProgressDialog.DEFEAT);
                }

            }
        });
    }

    private void setFollowText(int stringId, int mipmapId) {
        followView.setText(getResources().getString(stringId));
        followView.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(mipmapId), null, null, null);
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
