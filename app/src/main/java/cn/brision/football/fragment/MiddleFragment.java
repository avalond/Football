package cn.brision.football.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.data.MatchAction;
import cn.brision.football.fragment.circleSelection.CircleFollowSelection;
import cn.brision.football.fragment.circleSelection.CircleUnfollowsSelection;
import cn.brision.football.model.BaseCircleInfo;
import cn.brision.football.model.CircleFollowsInfo;
import cn.brision.football.model.CircleUnFollowsInfo;
import cn.brision.football.utils.Const;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.view.ptr.PtrClassicFrameLayout;
import cn.brision.football.view.ptr.PtrDefaultHandler;
import cn.brision.football.view.ptr.PtrFrameLayout;
import cn.brision.football.view.ptr.PtrHandler;
import cn.brision.football.view.sectioned.SectionedRecyclerViewAdapter;

/**
 * Created by brision on 16/10/12.
 */
public class MiddleFragment extends BaseFragment {


    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.ptr_frame1)
    PtrClassicFrameLayout mPtrframe;

    private MatchAction matchAction;
    private boolean isRefes = false;
    private MyLoginStatusReceiver mReceiver;

    List<BaseCircleInfo> followsData = new ArrayList<>();
    private List<CircleUnFollowsInfo.DataBean> unfollowsData = new ArrayList<>();
    private SectionedRecyclerViewAdapter sectionedRecyclerViewAdapter;
    private CircleFollowSelection circleFollowSelection;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_middle);
        matchAction = dataManger.getMatchAction();
        onRegisterReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private PtrHandler mPtrHandler = new PtrHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
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

    @Override
    protected void initView() {

        mPtrframe.setLastUpdateTimeRelateObject(this);
        mPtrframe.setPtrHandler(mPtrHandler);
        mPtrframe.setResistance(1.7f);
        mPtrframe.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrframe.setDurationToClose(200);
        mPtrframe.setDurationToCloseHeader(1000);
        mPtrframe.setPullToRefresh(false);

        sectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (sectionedRecyclerViewAdapter.getSectionItemViewType(position)) {

                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 4;

                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 4;

                    default:
                        return 1;
                }
            }
        });

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(sectionedRecyclerViewAdapter);

        circleFollowSelection = new CircleFollowSelection(getActivity(), sectionedRecyclerViewAdapter);
        circleFollowSelection.setData(followsData);
        sectionedRecyclerViewAdapter.addSection(circleFollowSelection);
    }

    @Override
    protected void getOkhttpData() {
        matchAction.circleUnFollows(new MatchAction.CircleUnFollowsListener() {
            @Override
            public void circleUnFollowsData(CircleUnFollowsInfo mCircleUnFollowsInfo) {
                if (mCircleUnFollowsInfo != null) {
                    unfollowsData.clear();
                    unfollowsData.addAll(mCircleUnFollowsInfo.getData());
                    if (getActivity() != null)
                        isRefes = false;
                    initSelections();
                }
            }
        });
    }

    private void initData() {
        matchAction.circleFollows(new MatchAction.CircleFollowsListener() {
            @Override
            public void circleFollowsData(CircleFollowsInfo mCircleFollowsInfo) {
                if (mCircleFollowsInfo != null) {
                    mPtrframe.refreshComplete();
                    followsData.clear();
                    followsData.addAll(mCircleFollowsInfo.getData());
                    if (getActivity() != null)
                        initSelections();
                }
            }
        });
    }

    private void initSelections() {
        for (int i = 0; i < followsData.size(); i++) {
            String type = followsData.get(i).getType();
            if (Integer.valueOf(type) != 2) {
                followsData.remove(i);
                --i;
            }
        }

        circleFollowSelection.setData(followsData);

        for (int i = 0; i < unfollowsData.size(); i++) {
            if (unfollowsData.get(i).getContent() != null) {
                List<BaseCircleInfo> content = unfollowsData.get(i).getContent();
                for (int j = 0; j < content.size(); j++) {
                    String type = content.get(j).getType();
                    if (Integer.valueOf(type) != 2) {
                        content.remove(j);
                        --j;
                    }
                }
            }
        }

        if (!isRefes) {
            for (int i = 0; i < unfollowsData.size(); i++) {
                CircleUnfollowsSelection circleUnfollowsSelection = new CircleUnfollowsSelection(getActivity(), sectionedRecyclerViewAdapter);
                circleUnfollowsSelection.setData(unfollowsData.get(i));
                sectionedRecyclerViewAdapter.addSection(circleUnfollowsSelection);
            }
        }
        isRefes = true;
    }

    class MyLoginStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Const.LOGIN_STATE_CHANGE)) {
                    if (!PreferencesManager.getInstance(getActivity()).get("Access_token").equals(""))
                        initData();
                    else {
                    followsData.clear();
                    circleFollowSelection.setData(followsData);
                }
            }
        }
    }

    private void onRegisterReceiver() {
        if (mReceiver == null) {
            mReceiver = new MyLoginStatusReceiver();
            IntentFilter wxIntentFilter = new IntentFilter();
            wxIntentFilter.addAction(Const.LOGIN_STATE_CHANGE);
            mContext.registerReceiver(mReceiver, wxIntentFilter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(mReceiver);
    }

}
