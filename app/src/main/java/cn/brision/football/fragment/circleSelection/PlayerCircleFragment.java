package cn.brision.football.fragment.circleSelection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.adapter.data.PlayerCircleAdapter;
import cn.brision.football.data.MatchAction;
import cn.brision.football.fragment.BaseFragment;
import cn.brision.football.fragment.MyFragment;
import cn.brision.football.fragment.circleSelection.CircleItemActivity;
import cn.brision.football.model.PlayerCircleInfo;
import cn.brision.football.utils.Const;
import cn.brision.football.view.loadmore.HaoRecyclerView;
import cn.brision.football.view.loadmore.LoadMoreListener;
import cn.brision.football.view.ptr.PtrClassicFrameLayout;
import cn.brision.football.view.ptr.PtrDefaultHandler;
import cn.brision.football.view.ptr.PtrFrameLayout;
import cn.brision.football.view.ptr.PtrHandler;

/**
 * Created by wangchengcheng on 16/9/27.
 */
public class PlayerCircleFragment extends BaseFragment {

    @Bind(R.id.circler_recyclerview)
    HaoRecyclerView mRecyclerview;
    @Bind(R.id.ptr_frame)
    PtrClassicFrameLayout mPtrframe;

    private MatchAction matchAction;
    private int page = 1;
    private PlayerCircleAdapter playerCircleAdapter;
    private boolean canGetMoreRecipe = true;//是否正在刷新界面，true，允许加载更多
    private boolean loadMoreFail = false;//加载更多是否失败
    private boolean isLoadingMore = false;//是否正在加载更多
    private List<PlayerCircleInfo.DataBean> dataBeen = new ArrayList<>();
    private String subjectId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_player_circle);
        matchAction = dataManger.getMatchAction();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return super.onCreateView(inflater, container, savedInstanceState);
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
        mPtrframe.setEnabled(false);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        playerCircleAdapter = new PlayerCircleAdapter(getActivity());

        mRecyclerview.setAdapter(playerCircleAdapter);

        mRecyclerview.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!canGetMoreRecipe || playerCircleAdapter == null || dataBeen.size() == 0) {
                    mRecyclerview.setCanloadMore(false);
                    mRecyclerview.loadMoreComplete();
                    return;
                }
                if (dataBeen.size() < 20 && dataBeen.size() > 0) {
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

    public void setStatus(boolean isRefersh) {
        mPtrframe.setEnabled(isRefersh);
    }

    @Override
    protected void getOkhttpData() {
        canGetMoreRecipe = false;//刷新整个页面的时候，禁用上滑动加载更多
        subjectId = ((CircleItemActivity) getActivity()).subjectId;
        if (subjectId != null)
            matchAction.playerCiecle(subjectId, page, new MatchAction.PlayerCiecleDataListener() {
                @Override
                public void playerCiecleData(PlayerCircleInfo mPlayerCircleInfo) {
                    if (mPlayerCircleInfo != null) {
                        dataBeen.clear();
                        for (int i = 0; i < mPlayerCircleInfo.getData().size(); i++) {
                            if (mPlayerCircleInfo.getData().get(i).getType().equals("3")) {
                                dataBeen.add(mPlayerCircleInfo.getData().get(i));
                            }
                        }
                        refershData(dataBeen);
                        canGetMoreRecipe = true;
                        mPtrframe.refreshComplete();
                    }
                }
            }, new MatchAction.PlayerCiecleErrorListener() {
                @Override
                public void playerCiecleError() {
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

    private void refershData(List<PlayerCircleInfo.DataBean> dataBeen) {
        if (page == 1) {
            playerCircleAdapter.clearData();
        }
        if (dataBeen != null && dataBeen.size() > 0) {
            if (dataBeen.size() >= 20) {
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
        if (playerCircleAdapter != null) {
            playerCircleAdapter.setData(dataBeen);
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

}
