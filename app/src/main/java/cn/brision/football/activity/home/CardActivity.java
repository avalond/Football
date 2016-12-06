package cn.brision.football.activity.home;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tedcoder.wkvideoplayer.model.Video;
import com.android.tedcoder.wkvideoplayer.model.VideoUrl;
import com.android.tedcoder.wkvideoplayer.util.DensityUtil;
import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.adapter.expandRecyclerviewadapter.DividerDecoration;
import cn.brision.football.adapter.home.CardAdapter;
import cn.brision.football.data.HomeAction;
import cn.brision.football.data.MatchAction;
import cn.brision.football.model.BaseInfo;
import cn.brision.football.model.CommentInfo;
import cn.brision.football.model.CommentMessage;
import cn.brision.football.model.CommentMessagepost;
import cn.brision.football.model.HomeCardInfo;
import cn.brision.football.model.PlayerCircleInfo;
import cn.brision.football.model.ScheduleGameEvent;
import cn.brision.football.utils.BitmapUtils;
import cn.brision.football.utils.SystemBarHelper;
import cn.brision.football.utils.ToastUtil;
import cn.brision.football.view.ImageEditText;
import cn.brision.football.view.loadmore.HaoRecyclerView;
import cn.brision.football.view.loadmore.LoadMoreListener;
import cn.brision.football.view.ptr.PtrClassicFrameLayout;
import cn.brision.football.view.ptr.PtrDefaultHandler;
import cn.brision.football.view.ptr.PtrFrameLayout;
import cn.brision.football.view.ptr.PtrHandler;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by brision on 16/11/2.
 */
public class CardActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.video_player_item_1)
    SuperVideoPlayer mSuperVideoPlayer;
    @Bind(R.id.schdeul_back)
    ImageView back;
    @Bind(R.id.circler_recyclerview)
    HaoRecyclerView mRecyclerview;
    @Bind(R.id.ptr_frame)
    PtrClassicFrameLayout mPtrframe;
    @Bind(R.id.commenttext_miss)
    LinearLayout mLinearLayout;
    @Bind(R.id.bar_height)
    View bar_height;
    @Bind(R.id.text)
    ImageEditText mText;
    @Bind(R.id.viewcount)
    TextView mViewCount;


    private final String SHARE_WAB = "http://public.brision.cn/share/playevent.html?id=";
    private ArrayList<String> strings = new ArrayList<>();
    private String eventId;
    private HomeAction homeAction;
    private CardAdapter commentAdapter;
    private MatchAction matchAction;

    private int page = 1;
    private boolean loadMoreFail = false;//加载更多是否失败
    private boolean isLoadingMore = false;//是否正在加载更多
    private boolean canGetMoreRecipe = true;//是否正在刷新界面，true，允许加载更多
    private List<CommentInfo.DataBean> dataBeen = new ArrayList<>();

    private HomeCardInfo mmHomeCardInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_card);
        hideToolbar();

        //初始化视频播放器
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        //初始化controller
        mSuperVideoPlayer.setAutoHideController(true);

        homeAction = dataManger.getHomeAction();
        matchAction = dataManger.getMatchAction();
        eventId = getIntent().getStringExtra("eventId");

        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding1(this, bar_height);

        initView();
        getTitleData();
        getData();

        mText.setOnClickListener(this);
    }

    private void initView() {
        mPtrframe.setLastUpdateTimeRelateObject(this);
        mPtrframe.setPtrHandler(mPtrHandler);
        mPtrframe.setResistance(1.7f);
        mPtrframe.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrframe.setDurationToClose(200);
        mPtrframe.setDurationToCloseHeader(1000);
        mPtrframe.setPullToRefresh(false);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        commentAdapter = new CardAdapter(this);
        mRecyclerview.setAdapter(commentAdapter);
//        mRecyclerview.addItemDecoration(new DividerDecoration(this));
        mRecyclerview.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!canGetMoreRecipe || commentAdapter == null || dataBeen.size() == 0) {
                    mRecyclerview.setCanloadMore(false);
                    mRecyclerview.loadMoreComplete();
                    return;
                }
                if (dataBeen.size() < 10 && dataBeen.size() > 0) {
                    mRecyclerview.loadMoreEnd();
                }

                isLoadingMore = true;
                mRecyclerview.setCanloadMore(true);
                page++;
                getData();
            }
        });
        mRecyclerview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (loadMoreFail) {
                    loadMoreFail = false;
                    isLoadingMore = true;//开始加载更多
                    mRecyclerview.setloadFail();
                    mRecyclerview.setCanloadMore(true);
                    page++;
                    getData();
                }
            }
        });
    }

    private PtrHandler mPtrHandler = new PtrHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            page = 1;
            getData();
        }

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
    };

    private void getTitleData() {
        homeAction.cardPage(eventId, new HomeAction.CardPageListener() {
            @Override
            public void cardPageData(HomeCardInfo mHomeCardInfo) {
                if (mHomeCardInfo != null) {
                    mmHomeCardInfo = mHomeCardInfo;
                    strings.add(mHomeCardInfo.getData().getUrl());
                    ArrayList<HomeCardInfo> list = new ArrayList<>();
                    list.add(mHomeCardInfo);
                    commentAdapter.setHeadData(list);
                    mViewCount.setText(mHomeCardInfo.getData().getViewCount()+"次");
                    initVideo();
                }
            }
        });
    }

    private void getData() {
        matchAction.playercomments(0,eventId, "10", page, new MatchAction.PlayerCieclecommentsDataListener() {
            @Override
            public void playerCieclecomments(CommentInfo mCommentInfo) {
                if (mCommentInfo != null) {
                    dataBeen.clear();
                    List<CommentInfo.DataBean> data = mCommentInfo.getData();
                    dataBeen.addAll(data);
                    refershData(dataBeen);
                    canGetMoreRecipe = true;
                    mPtrframe.refreshComplete();
                }

            }
        }, new MatchAction.PlayercommentsErrorListener() {
            @Override
            public void playercommentsError() {
                if (1 == page) {
                    isLoadingMore = false;
                    mPtrframe.refreshComplete();
                } else {
                    loadMoreFail = true;
                    mRecyclerview.loadFail();
                }
            }
        });
    }

    private void refershData(List<CommentInfo.DataBean> dataBeen) {
        if (page == 1) {
            commentAdapter.clearData();
        }
        if (dataBeen != null && dataBeen.size() > 0) {
            if (dataBeen.size() >= 10) {
                mRecyclerview.setCanloadMore(true);
                mRecyclerview.loadMoreComplete();
            } else {
                mRecyclerview.setCanloadMore(false);
                mRecyclerview.loadMoreEnd();
            }
        } else {
            mRecyclerview.setCanloadMore(false);
            mRecyclerview.loadMoreEnd();
        }
        if (commentAdapter != null) {
            commentAdapter.setData(dataBeen);
        }
    }

    @OnClick(R.id.comment_send)
    public void toSend(View view) {
        String s = mText.getText().toString();
        if (!s.trim().equals("")) {
            CommentMessage commentMessage = new CommentMessage();
            CommentMessagepost commentMessagepost = new CommentMessagepost();
            commentMessage.setEventId(eventId);
            commentMessage.setText(s);
            commentMessagepost.setEventId(eventId);
            commentMessagepost.setText(s);
            matchAction.playerSendComments(1,eventId,commentMessage,commentMessagepost, new MatchAction.PlayerSendCommentsDataListener() {
                @Override
                public void playerSendComments(BaseInfo mBaseInfo) {
                    if (mBaseInfo != null && mBaseInfo.getStatus() == 200) {
                        closeInputMethod(mText);
                        ToastUtil.showToast(CardActivity.this,getResources().getString(R.string.comment_succend), R.drawable.succend);
                        getData();
                        mText.setText("");
                    } else {
                        closeInputMethod(mText);
                        ToastUtil.showToast(CardActivity.this,getResources().getString(R.string.comment_failed), R.drawable.failed);
                    }
                }
            });
            return;
        }
        mText.setText("");
        ToastUtil.showToastOnce(CardActivity.this,getResources().getString(R.string.comment_faileds));
    }

    @OnClick(R.id.schdeul_back)
    public void backOnclick(View view) {
        this.finish();
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
        ArrayList<Video> videoArrayList = new ArrayList<>();
        videoArrayList.add(video);

        mSuperVideoPlayer.loadMultipleVideo(videoArrayList, 0, 0, 0, 0);
    }

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
            mLinearLayout.setVisibility(View.GONE);
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
            mLinearLayout.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
        }
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
            Toast.makeText(CardActivity.this, getString(R.string.videoplayover), Toast.LENGTH_SHORT).show();
        }
    };

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

    @Override
    public void onStop() {
        super.onStop();
        if (isLoadingMore && mRecyclerview != null) {
            mPtrframe.refreshComplete();
            mRecyclerview.loadMoreComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoPlayCallback.onCloseVideo();
    }

    @OnClick(R.id.sharecount)
    public void shareClick(View view) {
        showShare(mmHomeCardInfo);
    }

    private void showShare(final HomeCardInfo data) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = null;
        if (oks == null) {
            oks = new OnekeyShare();
        }
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
            oks.setTitle(getResources().getString(R.string.share_basetitlevideo));

            // titleUrl是标题的网络链接，QQ和QQ空间等使用
            oks.setTitleUrl(SHARE_WAB + data.getData().getId());

            // text是分享文本，所有平台都需要这个字段
            oks.setText(data.getData().getDescription());
//            oks.setImageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
            oks.setImageUrl(data.getData().getPhoto());
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(SHARE_WAB + data.getData().getId());
            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                @Override
                public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                    if (SinaWeibo.NAME.equals(platform.getName())) {
                        String text = data.getData().getDescription()
                                + "\n" + SHARE_WAB + data.getData().getId();
                        paramsToShare.setText(text);
                    }
                    if (WechatMoments.NAME.equals(platform.getName())) {
                        paramsToShare.setTitle(getResources().getString(R.string.share_basetitlevideo) + " " + data.getData().getDescription());
                    }
                    if ("ShortMessage".equals(platform.getName())) {
                        String text = getResources().getString(R.string.share_basetitlevideo) + " " + data.getData().getDescription()
                                + "\n" + SHARE_WAB + data.getData().getId();
                        paramsToShare.setImageUrl(null);
                        paramsToShare.setText(text);
                    }
                }
            });
            oks.show(this);
        }
    }

    /**
     * 显示系统键盘
     *
     * @param editText
     */
    public void openInputMethod(final ImageEditText editText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) editText.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 8);
            }
        }, 200);
    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public void closeInputMethod(ImageEditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int[] scrcoords = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (y < v.getTop() || y > v.getBottom()) {//x < v.getLeft() || x > v.getRight() ||
                closeInputMethod(mText);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {

    }


}
