package cn.brision.football.activity.data;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tedcoder.wkvideoplayer.model.Video;
import com.android.tedcoder.wkvideoplayer.model.VideoUrl;
import com.android.tedcoder.wkvideoplayer.util.DensityUtil;
import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.adapter.data.schdeule.ExpListViewAdapterWithCheckbox;
import cn.brision.football.adapter.data.schdeule.SchdeuleActivityAdapter;
import cn.brision.football.data.MatchAction;
import cn.brision.football.fragment.HomeSelection.ScheduleFifterSection;
import cn.brision.football.model.FifterStatusInfo;
import cn.brision.football.model.ScheduleGameEvent;
import cn.brision.football.model.SchdeuleGameInfo;
import cn.brision.football.utils.GlideUtils;
import cn.brision.football.utils.SystemBarHelper;
import cn.brision.football.utils.ToastUtil;
import cn.brision.football.view.ptr.PtrClassicFrameLayout;
import cn.brision.football.view.sectioned.SectionedRecyclerViewAdapter;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by wangchengcheng on 16/8/25.
 * 赛程视频播放页面
 */
public class ScheduleActivity extends BaseActivity implements SchdeuleActivityAdapter.oneKeyShareListener {

    //    @Bind(R.id.schdeul_home)
//    TextView teamHome;
//    @Bind(R.id.schdeul_away)
//    TextView teamAway;
//    @Bind(R.id.schdeul_score)
//    TextView teamScore;
//    @Bind(R.id.schdeul_league)
//    TextView teamLeague;
//    @Bind(R.id.schdeul_time)
//    TextView time;
    @Bind(R.id.viewcount)
    TextView viewCount;
    @Bind(R.id.schdeul_back)
    ImageView back;
    @Bind(R.id.schdeule_listview)
    ListView schdeuleContent;
    @Bind(R.id.video_player_item_1)
    SuperVideoPlayer mSuperVideoPlayer;
    @Bind(R.id.fifter_view)
    LinearLayout fifterView;
    @Bind(R.id.expandlistview)
    RecyclerView mRecyclerView;
    @Bind(R.id.ptr_frame)
    PtrClassicFrameLayout mPtrframe;
    @Bind(R.id.bar_gride)
    View bar_gride;
    @Bind(R.id.bar_height)
    View bar_height;


    private final String SHARE_WAB = "http://public.brision.cn/share/playevent.html?id=";
    //每次过滤都要
    private List<ScheduleGameEvent.DataBean> data = new ArrayList<>();
    //过滤完的数据集合
    private List<ScheduleGameEvent.DataBean> statusData = new ArrayList<>();
    public ArrayList<String> strings = new ArrayList<>();
    //    private ExpListViewAdapterWithCheckbox fifterAdapter;
    public MatchAction matchAction;
    private SchdeuleGameInfo mSchdeuleGameInfo;
    private SectionedRecyclerViewAdapter viewAdapter;
    //子item的数据集合
    private List<String> actionList = new ArrayList<>();
    private List<String> playerList = new ArrayList<>();
    private List<String> matchList = new ArrayList<>();
    private HashMap<String, List<String>> hashMap = new HashMap<>();
    //每个事件的对象
    private ScheduleFifterSection scheduleFifterSection;
    private ScheduleFifterSection scheduleFifterSection1;
    private ScheduleFifterSection scheduleFifterSection2;

    private List<FifterStatusInfo> statusInfos = new ArrayList<>();
    private int anInt;
    private int newInt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schdeule);
        ButterKnife.bind(this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            // 激活状态栏
//            tintManager.setStatusBarTintEnabled(true);
//            // enable navigation bar tint 激活导航栏
////            tintManager.setNavigationBarTintEnabled(true);
////            //设置系统栏设置颜色
////            tintManager.setTintColor(R.color.black);
//            //给状态栏设置颜色
//            tintManager.setStatusBarTintResource(R.color.black);
//            //Apply the specified drawable or color resource to the system navigation bar.
////            //给导航栏设置资源
////            tintManager.setNavigationBarTintResource(R.color.green);
//        }
        hideToolbar();
//        showToolbar();
        initStatusBar();
        //初始化ShareSDK
        ShareSDK.initSDK(this, "16e712c25c40e");

        //初始化视频播放器
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        //初始化controller
        mSuperVideoPlayer.setAutoHideController(true);

        matchAction = dataManger.getMatchAction();


    }

    public void initStatusBar() {
//        setSupportActionBar(mToolbar);
//        ActionBar supportActionBar = getSupportActionBar();
//        if (supportActionBar != null)
//            supportActionBar.setDisplayHomeAsUpEnabled(true);

        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, bar_gride);
        SystemBarHelper.setHeightAndPadding1(this, bar_height);
//        SystemBarHelper.setHeightAndPadding(this, mToolbar);

    }

    /**
     * 设置过滤器数据
     */
    public void setFifterList(List<ScheduleGameEvent.DataBean> data, List<ScheduleGameEvent.DataBean> originalData) {
        this.data.clear();
        this.statusData.clear();

        this.data.addAll(data);
        this.statusData.addAll(data);
//        expandlistview.setGroupIndicator(null);

        ArrayList<String> parent = new ArrayList<>();
        parent.add(getString(R.string.schdeule_team));
        parent.add(getString(R.string.event));
        parent.add(getString(R.string.player));


        for (int i = 0; i < originalData.size(); i++) {
            if (!actionList.contains(originalData.get(i).getAction())) {
                actionList.add(originalData.get(i).getAction());
            }
        }

        for (int i = 0; i < originalData.size(); i++) {
            if (!playerList.contains(originalData.get(i).getPlayer())) {
                playerList.add(originalData.get(i).getPlayer());
            }
        }

        for (int i = 0; i < originalData.size(); i++) {
            if (!matchList.contains(originalData.get(i).getTeam())) {
                matchList.add(originalData.get(i).getTeam());
            }
        }

        hashMap.put(parent.get(0), matchList);
        hashMap.put(parent.get(1), actionList);
        hashMap.put(parent.get(2), playerList);

        viewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {

                switch (viewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;

                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 3;

                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(viewAdapter);

        scheduleFifterSection = new ScheduleFifterSection(this, parent.get(0), hashMap);
        viewAdapter.addSection(scheduleFifterSection);
        viewAdapter.notifyDataSetChanged();
        scheduleFifterSection1 = new ScheduleFifterSection(this, parent.get(1), hashMap);
        viewAdapter.addSection(scheduleFifterSection1);
        viewAdapter.notifyDataSetChanged();
        scheduleFifterSection2 = new ScheduleFifterSection(this, parent.get(2), hashMap);
        viewAdapter.addSection(scheduleFifterSection2);
        viewAdapter.notifyDataSetChanged();

        saveCheck(hashMap, parent);
//        fifterAdapter =new ExpListViewAdapterWithCheckbox(this, parent, hashMap, data);
//        expandlistview.setAdapter(fifterAdapter);
    }

    /**
     * 获取头部数据
     */
    public void getTitleData(String gameId) {

        matchAction.schdeuleTitle(gameId, new MatchAction.SchdeuleTitleDataListener() {
            @Override
            public void schdeuleTitleData(SchdeuleGameInfo mSchdeuleGameInfo) {
                if (mSchdeuleGameInfo != null)
                ScheduleActivity.this.mSchdeuleGameInfo = mSchdeuleGameInfo;
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(newInt!=0) {
//            mSuperVideoPlayer.awayPlay(newInt);
//            newInt = 0;
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        anInt = mSuperVideoPlayer.onPausePlay();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        newInt = anInt;
//        anInt = 0;
//        mSuperVideoPlayer.mSuperVideoView.stopPlayback();
//    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mSuperVideoPlayer.goOnPlay();
    }

    public SchdeuleActivityAdapter adapter;

    /**
     * 下载完列表数据之后设置适配器
     */
    public void setContentListView() {
        adapter = new SchdeuleActivityAdapter(this);
        if (dataBeen.size() != 0) {
            adapter.setList(dataBeen, 0);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.head_schdeule_list, null);

        TextView teamHome = (TextView) view.findViewById(R.id.schdeul_home);
        TextView teamAway = (TextView) view.findViewById(R.id.schdeul_away);
        TextView teamScore = (TextView) view.findViewById(R.id.schdeul_score);
        TextView teamLeague = (TextView) view.findViewById(R.id.schdeul_league);
        TextView time = (TextView) view.findViewById(R.id.schdeul_time);
        ImageView homeLogo = (ImageView) view.findViewById(R.id.schdeul_home_logo);
        ImageView awayLogo = (ImageView) view.findViewById(R.id.schdeul_away_logo);

        teamHome.setText(mSchdeuleGameInfo.getData().getHome());
        teamAway.setText(mSchdeuleGameInfo.getData().getAway());
        teamScore.setText(mSchdeuleGameInfo.getData().getScore());
        String string = mSchdeuleGameInfo.getData().getLeague() + " "
                + mSchdeuleGameInfo.getData().getRound() + " "
                + mSchdeuleGameInfo.getData().getHome() + " VS "
                + mSchdeuleGameInfo.getData().getAway();
        teamLeague.setText(string);
        String t = mSchdeuleGameInfo.getData().getDate() + " "
                + mSchdeuleGameInfo.getData().getTime();
        time.setText(t);
        GlideUtils.get(this).getImage(mSchdeuleGameInfo.getData().getHome_logo(),homeLogo,R.anim.fade_in);
        GlideUtils.get(this).getImage(mSchdeuleGameInfo.getData().getAway_logo(),awayLogo,R.anim.fade_in);

        schdeuleContent.addHeaderView(view);

        schdeuleContent.setAdapter(adapter);

        schdeuleContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    mSuperVideoPlayer.loadMultipleVideo(videoArrayList, 0, 0, 0, i - 1);
                    viewCount.setText(Html.fromHtml(dataBeen.get(i - 1).getViewCount() + "<font color=#808080><b>次播放</b></font>"));
                    pos = i - 1;

                    adapter.setList(dataBeen, i - 1);
                }
            }
        });

    }

    @OnClick(R.id.commentscount)
    public void commentClick(View view) {
        Intent intent1 = new Intent(this, CommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("eventId", shareData != null ? shareData.getId() : "");
        bundle.putInt("type", 0);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }

    @OnClick(R.id.sharecount)
    public void shareClick(View view) {
        showShare(shareData);
    }

    @OnClick(R.id.schdeul_back)
    public void backOnclick(View view) {
        this.finish();
    }

    @OnClick(R.id.schdeul_selector)
    public void filterClick(View view) {
        fifterView.setVisibility(View.VISIBLE);
        //在打开过滤器按钮的时候将原来存储的所有还原过滤器的数据清空
        statusInfos.clear();
    }

    public List<ScheduleGameEvent.DataBean> dataBeen = new ArrayList<>();

    /**
     * 选择过滤却未点击确定,进行还原item选择的状态
     *
     * @param view
     */
    @OnClick(R.id.dismiss_fifter)
    public void missViewClick(View view) {
        fifterView.setVisibility(View.INVISIBLE);
        if (statusInfos != null && scheduleFifterSection != null) {
            scheduleFifterSection.initStatus(statusInfos);
            viewAdapter.notifyDataSetChanged();
            scheduleFifterSection1.initStatus(statusInfos);
            viewAdapter.notifyDataSetChanged();
            scheduleFifterSection2.initStatus(statusInfos);
            viewAdapter.notifyDataSetChanged();
            for (int i = 0; i < statusInfos.size(); i++) {
                mAllStates.get(statusInfos.get(i).getTitle())[statusInfos.get(i).getPos()] = statusInfos.get(i).getChecked() ? false : true;
            }
        }

    }

    //点击确定以后适配改变后的数据,过滤器之后刷新数据
    @OnClick(R.id.sure_fifter)
    public void dissViewClick(View view) {
        if (scheduleFifterSection != null) {
            getNewData();
            fifterView.setVisibility(View.INVISIBLE);
            if (statusData != null) {
                dataBeen = statusData;
                adapter.setList(this.dataBeen, 0);
                adapter.notifyDataSetChanged();
                strings.clear();
                for (int i = 0; i < this.dataBeen.size(); i++) {
                    strings.add(this.dataBeen.get(i).getUrl());
                }
                pos = 0;
                initVideo();

                if (strings.size() == 0) {
                    mSuperVideoPlayer.close();
                }
            }
        }

    }

    private HashMap<String, boolean[]> mAllStates = new HashMap<>();

    /**
     * 为了操作集合 在activity中设置每个item的默认状态
     *
     * @param listDataChild
     * @param parent
     */
    private void saveCheck(HashMap<String, List<String>> listDataChild, ArrayList<String> parent) {
        for (int i = 0; i < parent.size(); i++) {
            boolean[] booleen = new boolean[listDataChild.get(parent.get(i)).size()];
            for (int j = 0; j < listDataChild.get(parent.get(i)).size(); j++) {
                booleen[j] = false;
            }
            mAllStates.put(parent.get(i), booleen);
        }
    }

    /**
     * 获得过滤器状态发生改变的item的 pos title isChecked
     *
     * @param pos       在集合中的位置
     * @param title     item对应的hashmap的key
     * @param isChecked item被选择的状态
     */
    public void getNewStatus(int pos, String title, boolean isChecked) {
        this.statusData.clear();
        this.statusData.addAll(data);
        mAllStates.get(title)[pos] = isChecked;

        //已modul的形式把item的状态存起来,已供选择了过滤却没有点击确定的时候数据的还原操作
        FifterStatusInfo fifterStatusInfo = new FifterStatusInfo();
        fifterStatusInfo.setChecked(isChecked);
        fifterStatusInfo.setPos(pos);
        fifterStatusInfo.setTitle(title);
        statusInfos.add(fifterStatusInfo);
    }

    /*
     *获得过滤后的新数据
     */
    private void getNewData() {

        ArrayList<String> parent = new ArrayList<>();
        parent.add(getString(R.string.schdeule_team));
        parent.add(getString(R.string.event));
        parent.add(getString(R.string.player));

        for (int k = 0; k < parent.size(); k++) {
            boolean[] booleen = mAllStates.get(parent.get(k));
            for (int i = 0; i < booleen.length; i++) {
                if (booleen[i]) {
                    for (int j = 0; j < statusData.size(); j++) {
                        switch (parent.get(k)) {
                            case "事件":
                                if (statusData.get(j).getAction().contains(hashMap.get(parent.get(k)).get(i))) {
                                    statusData.remove(j);
                                    j = j - 1;
                                }
                                break;
                            case "球员":
                                if (statusData.get(j).getPlayer().contains(hashMap.get(parent.get(k)).get(i))) {
                                    statusData.remove(j);
                                    j = j - 1;
                                }
                                break;
                            case "球队":
                                if (statusData.get(j).getTeam().contains(hashMap.get(parent.get(k)).get(i))) {
                                    statusData.remove(j);
                                    j = j - 1;
                                }
                                break;
                        }
                    }
                }

            }
        }
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
                viewCount.setText(Html.fromHtml(data.get(pos).getViewCount() + "<font color=#808080><b>次播放</b></font>"));
                adapter.setIndex(pos);
            } else {
                ToastUtil.showToastOnce(ScheduleActivity.this, getString(R.string.videoplayover));
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

            //横屏状态的时候还原statusbar的状态和高度
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
            if (fifterView.getVisibility() == View.VISIBLE) {
                dissViewClick(findViewById(R.id.fifter_view));
                fifterView.setVisibility(View.GONE);
            } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                this.finish();
            }
        }
        return false;
    }

    private void showShare(final ScheduleGameEvent.DataBean data) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
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
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
            oks.setTitle(mSchdeuleGameInfo.getData().getLeague()
                    + mSchdeuleGameInfo.getData().getRound() + getResources().getString(R.string.share_basetitle));

            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            oks.setTitleUrl(SHARE_WAB + data.getId());
            String url = data.getUrl();
            String imageUrl = url.substring(0, url.length() - 4);

            // text是分享文本，所有平台都需要这个字段
            oks.setText(mSchdeuleGameInfo.getData().getHome() +
                    " " + "VS" + " " + mSchdeuleGameInfo.getData().getAway() + " " + data.getDescription());
            oks.setImageUrl(imageUrl + "1.jpg");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(SHARE_WAB + data.getId());

            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                @Override
                public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                    if (SinaWeibo.NAME.equals(platform.getName())) {
                        String text = mSchdeuleGameInfo.getData().getLeague()
                                + mSchdeuleGameInfo.getData().getRound() + getResources().getString(R.string.share_basetitle)
                                + "\n" + mSchdeuleGameInfo.getData().getHome()
                                + " " + "VS" + " " + mSchdeuleGameInfo.getData().getAway() + " " + data.getDescription()
                                + "\n" + SHARE_WAB + data.getId();
                        paramsToShare.setText(text);
                    }
                    if ("ShortMessage".equals(platform.getName())) {
                        String url = null;
                        paramsToShare.setImageUrl(url);
                        String text = mSchdeuleGameInfo.getData().getLeague()
                                + mSchdeuleGameInfo.getData().getRound() + getResources().getString(R.string.share_basetitle)
                                + "\n" + mSchdeuleGameInfo.getData().getHome()
                                + " " + "VS" + " " + mSchdeuleGameInfo.getData().getAway() + " " + data.getDescription()
                                + "\n" + SHARE_WAB + data.getId();
                        paramsToShare.setText(text);
                    }
                }
            });
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
//            oks.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//            oks.setSiteUrl("http://sharesdk.cn");
            // 启动分享GUI
            oks.show(this);
        }

    }

    private ScheduleGameEvent.DataBean shareData;

    @Override
    public void share(ScheduleGameEvent.DataBean data) {
        this.shareData = data;
    }
}
