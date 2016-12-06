package cn.brision.football.activity.data;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tedcoder.wkvideoplayer.model.Video;
import com.android.tedcoder.wkvideoplayer.model.VideoUrl;
import com.android.tedcoder.wkvideoplayer.util.DensityUtil;
import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.adapter.circle.CircleItemAdapter;
import cn.brision.football.adapter.expandRecyclerviewadapter.DividerDecoration;
import cn.brision.football.data.MatchAction;
import cn.brision.football.model.BaseInfo;
import cn.brision.football.model.CommentInfo;
import cn.brision.football.model.CommentMessage;
import cn.brision.football.model.CommentMessagepost;
import cn.brision.football.utils.ToastUtil;
import cn.brision.football.view.ImageEditText;
import cn.brision.football.view.loadmore.HaoRecyclerView;
import cn.brision.football.view.loadmore.LoadMoreListener;

/**
 * Created by brision on 16/9/29.
 */
public class CircleVideoActivity extends BaseActivity {

    @Bind(R.id.video_player)
    SuperVideoPlayer mSuperVideoPlayer;
    @Bind(R.id.circler_recyclerview)
    HaoRecyclerView mRecyclerview;
    @Bind(R.id.text)
    ImageEditText mText;
    @Bind(R.id.commenttext_miss)
    LinearLayout mLinearLayout;
    @Bind(R.id.miss_ll)
    RelativeLayout miss_ll;
    @Bind(R.id.ll_one)
    View ll_one;
    @Bind(R.id.ll_two)
    View ll_two;

    private ArrayList<String> strings = new ArrayList<>();
    private ArrayList<Video> videoArrayList;
    private CircleItemAdapter commentAdapter;


    private int page = 1;
    private boolean loadMoreFail = false;//加载更多是否失败
    private boolean isLoadingMore = false;//是否正在加载更多
    private boolean canGetMoreRecipe = true;//是否正在刷新界面，true，允许加载更多
    private MatchAction matchAction;
    private List<CommentInfo.DataBean> dataBeen = new ArrayList<>();
    private String postId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circlevideo);
        ButterKnife.bind(this);

        matchAction = dataManger.getMatchAction();
        showToolbar();
        setToolbarTitle("帖子详情");
        setToolbarDividerEnable(false);
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("VIDEO_URL");
        postId = intent.getStringExtra("postId");

        strings.add(videoUrl);
        //初始化视频播放器
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        //初始化controller
        mSuperVideoPlayer.setAutoHideController(true);
        initView();
        initVideo();
        getOkhttpData();
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


    private void initView() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        commentAdapter = new CircleItemAdapter(this);
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
                getOkhttpData();
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
                    getOkhttpData();
                }
            }
        });
    }


    private void getOkhttpData() {
        matchAction.playercomments(1,postId, "10", page, new MatchAction.PlayerCieclecommentsDataListener() {
            @Override
            public void playerCieclecomments(CommentInfo mCommentInfo) {
                if (mCommentInfo != null) {
                    dataBeen.clear();
                    List<CommentInfo.DataBean> data = mCommentInfo.getData();
                    dataBeen.addAll(data);
                    refershData(dataBeen);
                    canGetMoreRecipe = true;
                }
            }
        }, new MatchAction.PlayercommentsErrorListener() {
            @Override
            public void playercommentsError() {
                if (1 == page) {
                    isLoadingMore = false;
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
            commentMessage.setEventId(postId);
            commentMessage.setText(s);
            commentMessagepost.setEventId(postId);
            commentMessagepost.setText(s);
            matchAction.playerSendComments(1,postId,commentMessage,commentMessagepost, new MatchAction.PlayerSendCommentsDataListener() {
                @Override
                public void playerSendComments(BaseInfo mBaseInfo) {
                    if (mBaseInfo != null && mBaseInfo.getStatus() == 200) {
                        closeInputMethod(mText);
                        ToastUtil.showToast(CircleVideoActivity.this, getResources().getString(R.string.comment_succend), R.drawable.succend);
//                        getData();
                        mText.setText("");
                    } else {
                        closeInputMethod(mText);
                        ToastUtil.showToast(CircleVideoActivity.this, getResources().getString(R.string.comment_failed), R.drawable.failed);
                    }
                }
            });
            return;
        }
        mText.setText("");
        ToastUtil.showToastOnce(CircleVideoActivity.this, getResources().getString(R.string.comment_faileds));
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
            Toast.makeText(CircleVideoActivity.this, getString(R.string.videoplayover), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if (isLoadingMore && mRecyclerview != null) {
            mRecyclerview.loadMoreComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoPlayCallback.onCloseVideo();
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
            mSuperVideoPlayer.setPadding(0,0,0,0);

            mLinearLayout.setVisibility(View.GONE);
            miss_ll.setVisibility(View.GONE);
            ll_one.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);
            hideToolbar();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();//竖屏播放
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 190f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
            mLinearLayout.setVisibility(View.VISIBLE);
            miss_ll.setVisibility(View.VISIBLE);
            ll_one.setVisibility(View.VISIBLE);
            ll_two.setVisibility(View.VISIBLE);
            showToolbar();
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
}
