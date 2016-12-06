package cn.brision.football.activity.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.data.MatchAction;
import cn.brision.football.fragment.player.PlayerStatisticsFragment;
import cn.brision.football.model.PlayerCiecleSubjectIdInfo;
import cn.brision.football.model.PlayerFollowInfo;
import cn.brision.football.model.PlayerTitleInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.SystemBarHelper;
import cn.brision.football.view.CustomProgressDialog;
import cn.brision.football.view.roundedimageview.RoundedImageView;


/**
 * Created by wangchengcheng on 16/9/26.
 * 球员信息页面
 */
public class PlayerActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.player_guanzhu)
    TextView followView;
    @Bind(R.id.player_post)
    TextView postView;
    @Bind(R.id.player_logo)
    RoundedImageView playerLogo;
    @Bind(R.id.player_name)
    TextView playerName;
    @Bind(R.id.player_number)
    TextView playerNumber;
    @Bind(R.id.player_age)
    TextView playerAge;
    //    @Bind(R.id.player_quanzi)
//    TextView circleTab;
//    @Bind(R.id.player_tongji)
//    TextView statisticsTab;

//    public DataTopScorerInfo.DataBean player;
    public String player;
    private MatchAction matchAction;
    private FragmentManager fragmentManager;
    private PlayerStatisticsFragment statisticsFragment;
    //    private PlayerCircleFragment circleFragment;
    public String subjectId;
    private PlayerTitleInfo.DataBean titleData;
    private CustomProgressDialog progressDialog;
    private View viewById;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbarHeight(R.dimen.action_bar_bigheight);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        showToolbar();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        showDefaultFragment(transaction);

        Intent intent = getIntent();
//        player = (DataTopScorerInfo.DataBean) intent.getSerializableExtra("player");
        player = intent.getStringExtra("player");
        matchAction = dataManger.getMatchAction();

        getSubjectId();
        loadPlayerTitleData();
        //弹出关注的 对话框
        progressDialog = new CustomProgressDialog(this, R.style.ProgressHUD);

        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding1(this, viewById);
//        SystemBarHelper.forceFitsSystemWindows(mToolbar);
        followView.setOnClickListener(this);
    }

    @Override
    protected View createToolbarView(LayoutInflater inflater, ViewGroup container) {
        View toolbarView = inflater.inflate(R.layout.toolbar_player, null);
        viewById = toolbarView.findViewById(R.id.miss_bar);
        return toolbarView;
    }

    private void showDefaultFragment(FragmentTransaction transaction) {
//        clickTab.setBackgroundResource(R.color.gray_240);
//        unClickTab.setBackgroundResource(R.color.white);

        if (statisticsFragment == null) {
            statisticsFragment = new PlayerStatisticsFragment();
            transaction.add(R.id.player_content, statisticsFragment);
        } else {
            transaction.show(statisticsFragment);
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
//        if (circleFragment != null) {
//            transaction.hide(circleFragment);
//        }
        if (statisticsFragment != null) {
            transaction.hide(statisticsFragment);
        }
    }

    private void loadPlayerTitleData() {
        matchAction.playerTitle(player, new MatchAction.PlayerTitleDataListener() {
            @Override
            public void playerTitleData(PlayerTitleInfo mPlayerTitleInfo) {
                if (mPlayerTitleInfo != null) {
                    titleData = mPlayerTitleInfo.getData();
                    playerName.setText(titleData.getName());
                    String number = titleData.getPosition() + " / " + titleData.getNumber() + "号/ "+titleData.getNation() + " / " + titleData.getAge() + "岁";
                    playerNumber.setText(number);
                    String height = titleData.getBirthday()+" / "+titleData.getHeight() + "cm / " + titleData.getWeight() + "kg";
                    playerAge.setText(height);
                    GlideUtils.get(PlayerActivity.this).getImage(titleData.getPhoto(), getResources().getDrawable(R.mipmap.player), playerLogo, R.anim.fade_in);
                    if (titleData.isIsFollowed()) {
                        setFollowText(R.string.followed,R.mipmap.follow);
                    } else {
                        setFollowText(R.string.follow,R.mipmap.unfollow);
                    }
                    followView.setEnabled(true);
                }
            }
        });
    }

    private void getSubjectId() {
        matchAction.playerCiecleSubjectId(player, new MatchAction.PlayerCiecleSubjectIdDataListener() {
            @Override
            public void playerCiecleSubjectIdData(PlayerCiecleSubjectIdInfo mPlayerCiecleSubjectIdInfo) {
                if (mPlayerCiecleSubjectIdInfo != null) {
                    subjectId = mPlayerCiecleSubjectIdInfo.getData().getSubjectId();
                }
            }
        });
    }

//    @OnClick(R.id.player_quanzi)
//    public void circleClick(View view) {
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        hideFragment(transaction);
//
//        circleTab.setBackgroundResource(R.color.gray_240);
//        statisticsTab.setBackgroundResource(R.color.white);
//
////        if (circleFragment == null) {
////            circleFragment = new PlayerCircleFragment();
////            transaction.add(R.id.player_content, circleFragment);
////        } else {
////            transaction.show(circleFragment);
////        }
//        transaction.commit();
//    }

//    @OnClick(R.id.player_tongji)
//    public void statisticsClick(View view) {
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        hideFragment(transaction);
//        showDefaultFragment(transaction);
//    }

    @OnClick(R.id.player_back)
    public void backClick(View view) {
        this.finish();
    }

    @Override
    public void onClick(View v) {
        if (subjectId!=null && titleData != null) {
            followView.setEnabled(false);
            if (!titleData.isIsFollowed()) {
                follow();
            } else {
                unFollow();
            }
        }
        else {
            progressDialog.setStyle(PlayerActivity.this, true, false, null, CustomProgressDialog.DEFEAT);
            progressDialog.show();
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
                    progressDialog.setStyle(PlayerActivity.this, true, false, null, CustomProgressDialog.FAIL);
                    setFollowText(R.string.follow,R.mipmap.unfollow);
                    loadPlayerTitleData();
                } else {
                    progressDialog.setStyle(PlayerActivity.this, true, false, null, CustomProgressDialog.MISSFAIL);
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
                    progressDialog.setStyle(PlayerActivity.this, true, false, null, CustomProgressDialog.SUCCESS);
                    setFollowText(R.string.followed,R.mipmap.follow);
                    loadPlayerTitleData();
                } else {
                    progressDialog.setStyle(PlayerActivity.this, true, false, null, CustomProgressDialog.DEFEAT);
                }

            }
        });
    }

    private void setFollowText(int stringId,int mipmapId){
        followView.setText(getResources().getString(stringId));
        followView.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(mipmapId), null, null, null);
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
