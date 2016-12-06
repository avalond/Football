package cn.brision.football.activity.data;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.tedcoder.wkvideoplayer.model.Video;
import com.android.tedcoder.wkvideoplayer.model.VideoUrl;
import com.android.tedcoder.wkvideoplayer.util.DensityUtil;
import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.adapter.data.player.PlayerEventAdapter;
import cn.brision.football.data.MatchAction;
import cn.brision.football.model.PlayerEventInfo;
import cn.brision.football.utils.SystemBarHelper;
import cn.brision.football.utils.ToastUtil;
import cn.brision.football.view.ptr.PtrClassicFrameLayout;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by wangchengcheng on 16/9/28.
 * 球员信息统计的视频播放页面
 */
public class PlayerStatisticsVideoActivity extends BaseActivity implements PlayerEventAdapter.oneKeyShareListener {


    @Bind(R.id.viewcount)
    TextView viewCount;
    @Bind(R.id.schdeule_listview)
    ListView schdeuleContent;
    @Bind(R.id.schdeul_back)
    ImageView back;
    @Bind(R.id.video_player_item_1)
    SuperVideoPlayer mSuperVideoPlayer;
    @Bind(R.id.ptr_frame)
    PtrClassicFrameLayout mPtrframe;
    @Bind(R.id.bar_gride)
    View bar_gride;
    @Bind(R.id.bar_height)
    View bar_height;

    private String seasonId;
    private String playerId;
    private String eventType;
    private String theme;

    private final String SHARE_WAB = "http://public.brision.cn/share/playevent.html?id=";
    public ArrayList<String> strings = new ArrayList<>();
    public MatchAction matchAction;
    private List<PlayerEventInfo.DataBean> data = new ArrayList<>();
    private PlayerEventAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideo);
        ButterKnife.bind(this);

        ShareSDK.initSDK(this, "16e712c25c40e");

        //初始化视频播放器
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        //初始化controller
        mSuperVideoPlayer.setAutoHideController(true);
        matchAction = dataManger.getMatchAction();

        getIntentData();
        mPtrframe.setEnabled(false);

        hideToolbar();
        initStatusBar();

        getContentListViewData(playerId, seasonId, eventType);
    }

    public void initStatusBar()
    {
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this,bar_gride);
        SystemBarHelper.setHeightAndPadding1(this,bar_height);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        seasonId = intent.getStringExtra("SeasonId");
        playerId = intent.getStringExtra("playerId");
        eventType = intent.getStringExtra("eventType");
        theme = intent.getStringExtra("theme");
    }

    public List<PlayerEventInfo.DataBean> dataBeen = new ArrayList<>();

    private void getContentListViewData(String id, String seasonId, String eventType) {
        matchAction.playerEvent(id, seasonId, eventType, new MatchAction.PlayerEventDataListener() {
            @Override
            public void playerEventData(PlayerEventInfo mPlayerEventInfo) {
                if (mPlayerEventInfo.getData().size() != 0) {
                    data = mPlayerEventInfo.getData();

                    for (int i = 0; i < data.size(); i++) {
                        strings.add(data.get(i).getUrl());
                    }
                    viewCount.setText(data.get(0).getViewCount() + getString(R.string.playcount));
//                    commentCount.setText(getString(R.string.comment) + data.get(0).getCommentsCount() + getString(R.string.brackets));
                }
                initVideo();
                if (dataBeen.size() != 0) {
                    dataBeen.clear();
                    dataBeen.addAll(data);
                    adapter.setList(dataBeen, 0);
                    return;
                }
                dataBeen.addAll(data);
                setListView();
            }
        });
        initVideo();
    }

    private void setListView() {
        adapter = new PlayerEventAdapter(this);
        if (dataBeen.size() != 0) {
            adapter.setList(dataBeen, 0);
        }

        View view = LayoutInflater.from(this).inflate(R.layout.head_player_statistics, null);

        TextView head = (TextView) view.findViewById(R.id.player_head);

        head.setText(theme);

        schdeuleContent.addHeaderView(view);

        schdeuleContent.setAdapter(adapter);

        schdeuleContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    mSuperVideoPlayer.loadMultipleVideo(videoArrayList, 0, 0, 0, i - 1);
                    String s = dataBeen.get(i - 1).getViewCount() + getString(R.string.playcount);
                    viewCount.setText(s);
//                commentCount.setText(getString(R.string.comment) + dataBeen.get(i).getCommentsCount() + getString(R.string.brackets));
                    pos = i - 1;
                    adapter.setList(dataBeen, i - 1);
                }
            }
        });
    }

    @OnClick(R.id.schdeul_back)
    public void backOnclick(View view) {
        this.finish();
    }

    @OnClick(R.id.commentscount)
    public void commentClick(View view) {
        Intent intent1 = new Intent(this, CommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("eventId", shareData.getId());
        bundle.putInt("type", 0);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }

    @OnClick(R.id.sharecount)
    public void shareClick(View view) {
        showShare(shareData);
    }

    /**
     * 设置视频播放的路径和参数
     */
    public void initVideo() {
        Video video = new Video();
        VideoUrl videoUrl1 = new VideoUrl();
        videoUrl1.setFormatName("720P");
        videoUrl1.setFormatUrls(strings);
        ArrayList<VideoUrl> arrayList1 = new ArrayList<>();
        arrayList1.add(videoUrl1);
        video.setVideoUrl(arrayList1);
        videoArrayList = new ArrayList<>();
        videoArrayList.add(video);

        mSuperVideoPlayer.loadMultipleVideo(videoArrayList, 0, 0, 0, 0);
    }

    /**
     * SuperVideoPlayer的回调
     */
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        @Override
        public void onCloseVideo() {
            mSuperVideoPlayer.close();
            resetPageToPortrait();
        }

        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
            }
        }

        @Override
        public void onPlayFinish() {
            ArrayList<String> formatUrls = videoArrayList.get(0).getVideoUrl().get(0).getFormatUrls();
            if (videoArrayList != null && formatUrls.size() - 1 > pos) {
                pos++;
                mSuperVideoPlayer.loadMultipleVideo(videoArrayList, 0, 0, 0, pos);
                schdeuleContent.smoothScrollToPositionFromTop(pos, 60);
                String s = data.get(pos).getViewCount() + getString(R.string.playcount);
                viewCount.setText(s);
//                commentCount.setText(getString(R.string.comment) + data.get(pos).getCommentsCount() + getString(R.string.brackets));

                adapter.setIndex(pos);
            } else {
                ToastUtil.showToastOnce(PlayerStatisticsVideoActivity.this, getString(R.string.videoplayover));
            }
        }
    };
    private ArrayList<Video> videoArrayList;
    private int pos = 0;

    /**
     * 旋转屏幕之后回调
     *
     * @param newConfig newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (null == mSuperVideoPlayer) return;
        /***
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//横屏播放
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mSuperVideoPlayer.getLayoutParams().height = (int) width;
            mSuperVideoPlayer.getLayoutParams().width = (int) height;
            SystemBarHelper.immersiveStatusBarReduction(this);
            back.setVisibility(View.GONE);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();//竖屏播放
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 202f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
            //设置StatusBar透明
            SystemBarHelper.immersiveStatusBar(this);
            back.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoPlayCallback.onCloseVideo();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                this.finish();
            }
        }
        return false;
    }

    private void showShare(final PlayerEventInfo.DataBean data) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();

        if (!ShareSDK.getPlatform(Wechat.NAME).isClientValid()) {
            oks.addHiddenPlatform(Wechat.NAME);
            oks.addHiddenPlatform(WechatMoments.NAME);
        }

        if (!ShareSDK.getPlatform(QQ.NAME).isClientValid()) {
            oks.addHiddenPlatform(QQ.NAME);
        }

        if (!ShareSDK.getPlatform(SinaWeibo.NAME).isClientValid()) {
            oks.addHiddenPlatform(SinaWeibo.NAME);
        }

        if (data != null) {
            oks.setTitle(getResources().getString(R.string.share_basetitle));

            oks.setTitleUrl(SHARE_WAB + data.getId());

            String url = data.getUrl();
            String imageUrl = url.substring(0, url.length() - 4);
            oks.setImageUrl(imageUrl + "1.jpg");

            oks.setText(data.getDescription());

            oks.setUrl(SHARE_WAB + data.getId());

            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                @Override
                public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                    if (SinaWeibo.NAME.equals(platform.getName())) {
                        String text = data.getDescription() + " " + data.getUrl();
                        paramsToShare.setText(text);
                    }
                    if ("ShortMessage".equals(platform.getName())) {
                        paramsToShare.setImageUrl(null);
                        String text = data.getDescription() + " " + data.getUrl();
                        paramsToShare.setText(text);
                    }
                }
            });
            oks.show(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mSuperVideoPlayer.goOnPlay();
    }

    private PlayerEventInfo.DataBean shareData;

    @Override
    public void share(PlayerEventInfo.DataBean data) {
        this.shareData = data;
    }
}
