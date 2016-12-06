package cn.brision.football.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.brision.football.R;
import cn.brision.football.adapter.home.HomeAdapter;
import cn.brision.football.data.HomeAction;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.leancloud.MyUserReservation;
import cn.brision.football.model.HomeBanner;
import cn.brision.football.model.HomeHotvideos;
import cn.brision.football.model.HomeLives;
import cn.brision.football.model.HomeTops;
import cn.brision.football.model.HomeUserfollows;
import cn.brision.football.view.HomePtrClassicFrameLayout;
import cn.brision.football.view.ptr.PtrDefaultHandler;
import cn.brision.football.view.ptr.PtrFrameLayout;
import cn.brision.football.view.ptr.PtrHandler;

/**
 * Created by wangchengcheng on 16/8/2.
 * 圈子页面
 */
public class MomentsFragment extends BaseFragment {

    @Bind(R.id.main_recyclerView)
    RecyclerView contentRecycler;

    @Bind(R.id.ptr_frame)
    HomePtrClassicFrameLayout mPtrframe;

    private HomeAction homeAction;

    private List<HomeBanner.DataBean> banner;
    private List<HomeLives.DataBean> lives;
    private List<HomeTops.DataBean> tops;
    private List<HomeUserfollows.DataBean> follows;
    private List<HomeHotvideos.DataBean> hotvideos;
    private HomeAdapter homeAdapter;
    private List<HomeLives.DataBean> results = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_moments);
        homeAction = dataManger.getHomeAction();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
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

        homeAdapter = new HomeAdapter(getActivity());
        contentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initClean();
    }

    @Override
    protected void getOkhttpData() {
        homeAction.banner(new HomeAction.BannerListener() {
            @Override
            public void bannerData(HomeBanner mHomeBanner) {
                if (mHomeBanner != null) {
                    banner = mHomeBanner.getData();
                    homeAdapter.addBannerData(banner);
                    contentRecycler.setAdapter(homeAdapter);
                }
            }
        });

        homeAction.lives(new HomeAction.LivesListener() {
            @Override
            public void livesData(HomeLives mHomeLives) {
                if (mHomeLives != null) {
                    lives = mHomeLives.getData();
                    homeAdapter.addLivesData(lives);
                    contentRecycler.setAdapter(homeAdapter);
                }
            }
        });

        homeAction.tops(new HomeAction.TopsListener() {
            @Override
            public void topsData(HomeTops mHomeTops) {
                if (mHomeTops != null) {
                    tops = mHomeTops.getData();
                    homeAdapter.addTopsData(tops);
                    contentRecycler.setAdapter(homeAdapter);
                }
            }
        });

        homeAction.userfollows(new HomeAction.UserfollowsListener() {
            @Override
            public void userfollowsData(HomeUserfollows mHomeUserfollows) {
                if (mHomeUserfollows != null) {
                    follows = mHomeUserfollows.getData();
                    homeAdapter.addTopsFollowsData(follows);
                    contentRecycler.setAdapter(homeAdapter);
                }
            }
        });

        homeAction.hotvideos(new HomeAction.HotvideosListener() {
            @Override
            public void hotvideosData(HomeHotvideos mHomeHotvideos) {
                if (mHomeHotvideos != null) {
                    hotvideos = mHomeHotvideos.getData();
                    homeAdapter.addTopsHotvdeosData(hotvideos);
                    contentRecycler.setAdapter(homeAdapter);
                }
            }
        });
        mPtrframe.refreshComplete();

    }

    private PtrHandler mPtrHandler = new PtrHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            initClean();
            getOkhttpData();
        }

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
    };

    private void initClean() {
        MyUser user = (MyUser) MyUser.getCurrentUser();
        AVQuery<MyUserReservation> query = AVObject.getQuery(MyUserReservation.class);
        query.whereEqualTo("user", user);
        query.findInBackground(new FindCallback<MyUserReservation>() {
            @Override
            public void done(List<MyUserReservation> results1, AVException e) {
                if (results1 != null) {
                    for (int i = 0; i < results1.size(); i++) {
                        JSONObject reservation = results1.get(i).getReservation();
                        Gson gson = new Gson();
                        HomeLives.DataBean livesBean = gson.fromJson(reservation.toString(), HomeLives.DataBean.class);
                        results.add(livesBean);
                    }
                    homeAdapter.addYuYueLivesData(results);
                    contentRecycler.setAdapter(homeAdapter);
                }
            }
        });
    }
}
