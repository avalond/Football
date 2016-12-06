package cn.brision.football.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import cn.brision.football.adapter.home.HomeAdapter;
import cn.brision.football.data.HomeAction;
import cn.brision.football.fragment.HomeSelection.HomeBannerSection;
import cn.brision.football.model.HomeBanner;
import cn.brision.football.model.HomeHotvideos;
import cn.brision.football.model.HomeLives;
import cn.brision.football.model.HomeTops;
import cn.brision.football.model.HomeUserfollows;
import cn.brision.football.view.HomePtrClassicFrameLayout;
import cn.brision.football.view.banner.BannerEntity;
import cn.brision.football.view.ptr.PtrDefaultHandler;
import cn.brision.football.view.ptr.PtrFrameLayout;
import cn.brision.football.view.ptr.PtrHandler;
import cn.brision.football.view.sectioned.SectionedRecyclerViewAdapter;

/**
 * Created by brision on 16/10/17.
 */
public class HomePageFragment extends BaseFragment {

    @Bind(R.id.main_recyclerView)
    RecyclerView contentRecycler;

    @Bind(R.id.ptr_frame)
    HomePtrClassicFrameLayout mPtrframe;
    private HomeAction homeAction;

    private List<HomeBanner.DataBean> bannerList = new ArrayList<>();;
    private List<HomeLives.DataBean> lives = new ArrayList<>();
    private List<HomeTops.DataBean> tops = new ArrayList<>();
    private List<HomeUserfollows.DataBean> follows = new ArrayList<>();
    private List<HomeHotvideos.DataBean> hotvideos = new ArrayList<>();
    private SectionedRecyclerViewAdapter mSectionedAdapter;

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

        mSectionedAdapter = new SectionedRecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        contentRecycler.setLayoutManager(linearLayoutManager);

        contentRecycler.setAdapter(mSectionedAdapter);
    }

    private PtrHandler mPtrHandler = new PtrHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            clearData();
            getOkhttpData();
        }

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
    };

    @Override
    protected void getOkhttpData() {

        homeAction.banner(new HomeAction.BannerListener() {
            @Override
            public void bannerData(HomeBanner mHomeBanner) {
                bannerList.addAll(mHomeBanner.getData());
                mSectionedAdapter.addSection(new HomeBannerSection(bannerList));
                mSectionedAdapter.notifyDataSetChanged();
            }
        });

        homeAction.lives(new HomeAction.LivesListener() {
            @Override
            public void livesData(HomeLives mHomeLives) {
                lives.addAll(mHomeLives.getData());
            }
        });

        homeAction.tops(new HomeAction.TopsListener() {
            @Override
            public void topsData(HomeTops mHomeTops) {
                tops.addAll(mHomeTops.getData());

            }
        });

        homeAction.userfollows(new HomeAction.UserfollowsListener() {
            @Override
            public void userfollowsData(HomeUserfollows mHomeUserfollows) {
                follows.addAll(mHomeUserfollows.getData());
            }
        });

        homeAction.hotvideos(new HomeAction.HotvideosListener() {
            @Override
            public void hotvideosData(HomeHotvideos mHomeHotvideos) {
                hotvideos.addAll(mHomeHotvideos.getData());
            }
        });
        mPtrframe.refreshComplete();
    }

    /**
     * 设置轮播banners
     */
    private void convertBanner()
    {


    }

    private void clearData(){
        bannerList.clear();
        mSectionedAdapter.removeAllSections();
    }
}
