package cn.brision.football.fragment;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.MainActivity;
import cn.brision.football.activity.lives.LivesSelectorActivity;
import cn.brision.football.adapter.expandRecyclerviewadapter.StickyRecyclerHeadersDecoration;
import cn.brision.football.adapter.lives.LivesAdapter;
import cn.brision.football.data.MatchAction;
import cn.brision.football.data.services.DataManger;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.leancloud.MyUserReservation;
import cn.brision.football.model.LivesInfo;
import cn.brision.football.utils.DimensionPixelUtil;
import cn.brision.football.utils.DisplayUtils;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.TimeturnUtils;
import cn.brision.football.view.ptr.PtrClassicFrameLayout;
import cn.brision.football.view.ptr.PtrDefaultHandler;
import cn.brision.football.view.ptr.PtrFrameLayout;
import cn.brision.football.view.ptr.PtrHandler;
import cn.brision.football.view.ptr.indicator.PtrIndicator;

public class LivesFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.live_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.lives_title)
    LinearLayout livesTitle;
    @Bind(R.id.lives_horizontalscrollview)
    HorizontalScrollView scrollView;
    @Bind(R.id.ptr_frame)
    PtrClassicFrameLayout mPtrframe;
    @Bind(R.id.live_point)
    LinearLayout mPoint;
    @Bind(R.id.totop)
    ImageView btn;

    private LayoutInflater mLayoutInflater;
    private View mRootView;

    private List<LivesInfo.DataBean> list = null;
    private View lineView;
    private TextView titleText;
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<LivesInfo.DataBean.LivesBean> allLives = new ArrayList<>();
    private List<LivesInfo.DataBean.LivesBean> mLives = new ArrayList<>();
    private List<LivesInfo.DataBean.LivesBean> results = new ArrayList<>();
    private MatchAction matchAction;
    private LivesAdapter mAdapter;
    private PtrIndicator mPtrIndicator;
    private List<String> a = new ArrayList<>();//赛事筛选存储的选择title数据
    private PreferencesManager preferencesManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManger dataManger = ((MainActivity) getActivity()).getApp().getDataManger();
        matchAction = dataManger.getMatchAction();
        mPtrIndicator = new PtrIndicator();
        preferencesManager = PreferencesManager.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflater = LayoutInflater.from(getActivity());
        mRootView = mLayoutInflater.inflate(R.layout.fragment_lives, null);
        ButterKnife.bind(this, mRootView);
        initPtrframe();
        return mRootView;
    }

    public PtrClassicFrameLayout isPtrframe() {
        if (mPtrframe == null) {
            return null;
        }
        return mPtrframe;
    }

    private void initPtrframe() {
        mPtrframe.setLastUpdateTimeRelateObject(this);
        mPtrframe.setPtrHandler(mPtrHandler);
        mPtrframe.setResistance(1.7f);
        mPtrframe.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrframe.setDurationToClose(200);
        mPtrframe.setDurationToCloseHeader(1000);
        mPtrframe.setPullToRefresh(false);

        //HorizontalScrollView开启动画效果
        scrollView.setSmoothScrollingEnabled(true);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == recyclerView.SCROLL_STATE_IDLE) {
                    btn.setVisibility(View.VISIBLE);
                } else {
                    btn.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onResume() {
        a.clear();
        Set<String> set = preferencesManager.get("dingYue", new HashSet<String>());
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            a.add(iterator.next());
        }
//        initClean();
//        initData();
        super.onResume();
        mPtrframe.autoRefresh((int) DimensionPixelUtil.dip2px(getActivity(), 60));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initClean();
//        initData();
    }

    private PtrHandler mPtrHandler = new PtrHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            initClean();
            initData();
        }

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
    };


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            mPtrframe.refreshComplete();
        }
    }

    @OnClick(R.id.lives_selector)
    public void selectorClick(View v) {
        Intent intent = new Intent(getActivity(), LivesSelectorActivity.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.totop)
    public void toTop(View view) {
        mRecyclerView.smoothScrollToPosition(0);
    }

    private void initClean() {
        MyUser user = (MyUser) MyUser.getCurrentUser();
        AVQuery<MyUserReservation> query = AVObject.getQuery(MyUserReservation.class);
        query.whereEqualTo("user", user);
        query.findInBackground(new FindCallback<MyUserReservation>() {
            @Override
            public void done(List<MyUserReservation> results1, AVException e) {
                if (results1 != null) {
                    results.clear();
                    for (int i = 0; i < results1.size(); i++) {
                        JSONObject reservation = results1.get(i).getReservation();
                        Gson gson = new Gson();
                        LivesInfo.DataBean.LivesBean livesBean = gson.fromJson(reservation.toString(), LivesInfo.DataBean.LivesBean.class);
                        results.add(livesBean);
                    }
                    initAdapter();
                    mAdapter.addYuYueData(results);
                }
            }
        });
    }


    private void initData() {
        matchAction.dataLives(new MatchAction.DataLivesDataListener() {
            @Override
            public void dataLivesData(LivesInfo mLivesInfo) {
                if (getActivity() != null) {
                    mPtrframe.refreshComplete();
                    titleList.clear();
                    allLives.clear();
                    livesTitle.removeAllViews();

                    list = mLivesInfo.getData();
                    titleList.add(0, "全部");

                    for (int i = 0; i < list.size(); i++) {
                        allLives.addAll(list.get(i).getLives());
                    }

                    if (a.size() == 0) {
                        for (int i = 0; i < list.size(); i++) {
                            titleList.add((list.get(i)).getLeague());
                        }
                    } else {
                        for (int i = 0; i < a.size(); i++) {
                            titleList.add(a.get(i));
                        }
                    }

                    TimeturnUtils utils = new TimeturnUtils();
                    Collections.sort(allLives, utils);

                    for (int i = 0; i < titleList.size(); i++) {
                        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.title_data_item, null);
                        titleText = (TextView) mView.findViewById(R.id.data_title_text);

                        //设置每个title_item的宽度
                        setTitleItemWidth(titleText);

                        lineView = mView.findViewById(R.id.data_title_line);
                        titleText.setOnClickListener(LivesFragment.this);
                        titleText.setText(titleList.get(i));

                        titleText.setTag(i);
                        livesTitle.addView(mView);
                        if (i != 0) {
                            lineView.setVisibility(View.GONE);
                        }
                    }
                    if (num != 0) {
                        View agoLine = livesTitle.getChildAt(0).findViewById(R.id.data_title_line);
                        agoLine.setVisibility(View.GONE);
                        if (num < titleList.size()) {
                            View currentLine = livesTitle.getChildAt(num).findViewById(R.id.data_title_line);
                            currentLine.setVisibility(View.VISIBLE);
                        } else {
                            agoLine.setVisibility(View.VISIBLE);
                            setUI(allLives);
                            scrollView.smoothScrollTo(0, 0);
                        }
                    } else {
                        setUI(allLives);
                    }
                }
            }
        });
    }

    private void setUI(ArrayList<LivesInfo.DataBean.LivesBean> data) {
        seperateLists(data);
        initAdapter();
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new LivesAdapter(getActivity());
            mAdapter.addData(mLives);
            int orientation = LinearLayoutManager.VERTICAL;
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), orientation, false);
            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setAdapter(mAdapter);

            /**
             * 分组
             */
            final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
            mRecyclerView.addItemDecoration(headersDecor);
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    headersDecor.invalidateHeaders();
                }
            });
        } else {
            mAdapter.addData(mLives);
        }
    }

    private void seperateLists(ArrayList<LivesInfo.DataBean.LivesBean> data) {
        mLives.clear();
        if (data != null && data.size() > 0) {
            mPoint.setVisibility(View.GONE);
            for (int i = 0; i < data.size(); i++) {
                LivesInfo.DataBean.LivesBean livesBean = new LivesInfo.DataBean.LivesBean();
                livesBean.setId(data.get(i).getId());
                livesBean.setRoundId(data.get(i).getRoundId());
                livesBean.setStartAt(data.get(i).getStartAt());
                livesBean.setAway(data.get(i).getAway());
                livesBean.setHome(data.get(i).getHome());
                livesBean.setScore(data.get(i).getScore());
                livesBean.setLivesStatus(data.get(i).getLivesStatus());
                mLives.add(livesBean);
            }
        } else {
            mPoint.setVisibility(View.VISIBLE);
        }
    }

    private int num = 0;//记录上一次点击提示线的位置

    @Override
    public void onClick(View view) {
        mPtrframe.refreshComplete();
        for (int i = 0; i < titleList.size(); i++) {

            int tag = (int) view.getTag();
            if (tag == i) {
                //设置title部分的滑动选择提示条的位置和状态
                int leftLength = livesTitle.getChildAt(i).getLeft();
                int screenWith = getResources().getDisplayMetrics().widthPixels;
                int width = livesTitle.getChildAt(i).getWidth();
                int scrollLength = leftLength - screenWith / 2 + width / 2;
                scrollView.smoothScrollTo(scrollLength, 0);

                View currentLine = livesTitle.getChildAt(i).findViewById(R.id.data_title_line);
                currentLine.setVisibility(View.VISIBLE);
                if (num < titleList.size()) {
                    View agoLine = livesTitle.getChildAt(num).findViewById(R.id.data_title_line);
                    if (num != i) {
                        agoLine.setVisibility(View.GONE);
                    }
                } else {
                    View first = livesTitle.getChildAt(0).findViewById(R.id.data_title_line);
                    first.setVisibility(View.VISIBLE);
                    setUI(allLives);
                    scrollView.smoothScrollTo(0, 0);
                }

                num = i;
                if (i != 0) {
                    TextView tv = (TextView) livesTitle.getChildAt(i).findViewById(R.id.data_title_text);
                    String text = (String) tv.getText();
                    View first = livesTitle.getChildAt(0).findViewById(R.id.data_title_line);
                    first.setVisibility(View.GONE);
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).getLeague().equals(text)) {
                            setUI((ArrayList<LivesInfo.DataBean.LivesBean>) list.get(j).getLives());
                            break;
                        } else {
                            setUI(null);
                        }
                    }
                } else {
                    setUI(allLives);
                }
            }
        }
    }

    private void setTitleItemWidth(final View view) {
        int screenWidth = DisplayUtils.getScreenWidth(getActivity());
        int width = screenWidth / Math.min(titleList.size(), 4);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();

        params.width = width;
        view.setLayoutParams(params);
    }
}

