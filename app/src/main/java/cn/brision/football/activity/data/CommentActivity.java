package cn.brision.football.activity.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.adapter.data.player.CommentAdapter;
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
import cn.brision.football.view.ptr.PtrClassicFrameLayout;
import cn.brision.football.view.ptr.PtrDefaultHandler;
import cn.brision.football.view.ptr.PtrFrameLayout;
import cn.brision.football.view.ptr.PtrHandler;


/**
 * Created by brision on 16/9/29.
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.circler_recyclerview)
    HaoRecyclerView mRecyclerview;
    @Bind(R.id.ptr_frame)
    PtrClassicFrameLayout mPtrframe;
    @Bind(R.id.text)
    ImageEditText mText;
    private MatchAction matchAction;
    private String eventId;

    private int page = 1;
    private int type ;
    private boolean canGetMoreRecipe = true;//是否正在刷新界面，true，允许加载更多
    private boolean loadMoreFail = false;//加载更多是否失败
    private boolean isLoadingMore = false;//是否正在加载更多
    private CommentAdapter commentAdapter;
    private List<CommentInfo.DataBean> dataBeen = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        matchAction = dataManger.getMatchAction();

        setToolbarDividerEnable(false);
        showToolbar();
        setToolbarTitle(getResources().getString(R.string.comment_title));

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        eventId = extras.getString("eventId");
        type = extras.getInt("type");

        initView();
        getOkhttpData();
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
        commentAdapter = new CommentAdapter(this);
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
            matchAction.playerSendComments(type,eventId,commentMessage,commentMessagepost, new MatchAction.PlayerSendCommentsDataListener() {
                @Override
                public void playerSendComments(BaseInfo mBaseInfo) {
                    if (mBaseInfo!=null && mBaseInfo.getStatus() == 200) {
                        closeInputMethod(mText);
                        ToastUtil.showToast(CommentActivity.this,getResources().getString(R.string.comment_succend), R.drawable.succend);
                        getOkhttpData();
                        mText.setText("");
                    } else {
                        closeInputMethod(mText);
                        ToastUtil.showToast(CommentActivity.this,getResources().getString(R.string.comment_failed), R.drawable.failed);
                    }
                }
            });
            return;
        }
        mText.setText("");
        ToastUtil.showToastOnce(CommentActivity.this,getResources().getString(R.string.comment_faileds));
    }

    private PtrHandler mPtrHandler = new PtrHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            page = 1;
            getOkhttpData();
        }

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
    };


    private void getOkhttpData() {

        matchAction.playercomments(type,eventId, "10", page, new MatchAction.PlayerCieclecommentsDataListener() {
            @Override
            public void playerCieclecomments(CommentInfo mCommentInfo) {
                if(mCommentInfo !=null) {
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


    @Override
    public void onStop() {
        super.onStop();
        if (isLoadingMore && mRecyclerview != null) {
            mPtrframe.refreshComplete();
            mRecyclerview.loadMoreComplete();
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
        openInputMethod(mText);
    }
}
