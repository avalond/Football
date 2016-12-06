package cn.brision.football.data;

import cn.brision.football.BuildConfig;
import cn.brision.football.data.services.AbsRemoteAction;
import cn.brision.football.data.services.HomeService;
import cn.brision.football.model.HomeBanner;
import cn.brision.football.model.HomeCardInfo;
import cn.brision.football.model.HomeHotvideos;
import cn.brision.football.model.HomeLives;
import cn.brision.football.model.HomeTops;
import cn.brision.football.model.HomeUserfollows;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangchengcheng on 16/10/10.
 */
public class HomeAction extends AbsRemoteAction {


    private final HomeService homeService;
    private final static Boolean DEBUG = BuildConfig.API_ENV;

    public HomeAction(OkHttpClient httpClient) {
        super(httpClient);
        if (DEBUG)
            homeService = createService(HomeService.DEBUG_BASE_RUL, HomeService.class);
        else
            homeService = createService(HomeService.PRODUCT_BASE_RUL, HomeService.class);
    }

    /**
     * 头部轮播
     *
     * @param mBannerListener
     */
    public void banner(final BannerListener mBannerListener) {
        Call<HomeBanner> call = homeService.banner();
        call.enqueue(new Callback<HomeBanner>() {
            @Override
            public void onResponse(Call<HomeBanner> call, Response<HomeBanner> response) {
                mBannerListener.bannerData(response.body());
            }

            @Override
            public void onFailure(Call<HomeBanner> call, Throwable t) {

            }
        });
    }

    public interface BannerListener {
        void bannerData(HomeBanner mHomeBanner);
    }

    /**
     * 热门比赛
     *
     * @param mLivesListener
     */
    public void lives(final LivesListener mLivesListener) {
        Call<HomeLives> call = homeService.lives();
        call.enqueue(new Callback<HomeLives>() {
            @Override
            public void onResponse(Call<HomeLives> call, Response<HomeLives> response) {
                mLivesListener.livesData(response.body());
            }

            @Override
            public void onFailure(Call<HomeLives> call, Throwable t) {

            }
        });
    }

    public interface LivesListener {
        void livesData(HomeLives mHomeLives);
    }

    /**
     * 十佳球
     *
     * @param mTopsListener
     */
    public void tops(final TopsListener mTopsListener) {
        Call<HomeTops> call = homeService.tops();
        call.enqueue(new Callback<HomeTops>() {
            @Override
            public void onResponse(Call<HomeTops> call, Response<HomeTops> response) {
                mTopsListener.topsData(response.body());
            }

            @Override
            public void onFailure(Call<HomeTops> call, Throwable t) {

            }
        });
    }

    public interface TopsListener {
        void topsData(HomeTops mHomeTops);
    }

    /**
     * 关注
     *
     * @param mUserfollowsListener
     */
    public void userfollows(final UserfollowsListener mUserfollowsListener) {
        Call<HomeUserfollows> call = homeService.userfollows();
        call.enqueue(new Callback<HomeUserfollows>() {
            @Override
            public void onResponse(Call<HomeUserfollows> call, Response<HomeUserfollows> response) {
                mUserfollowsListener.userfollowsData(response.body());
            }

            @Override
            public void onFailure(Call<HomeUserfollows> call, Throwable t) {

            }
        });
    }

    public interface UserfollowsListener {
        void userfollowsData(HomeUserfollows mHomeUserfollows);
    }

    /**
     * 热门视频
     *
     * @param mHotvideosListener
     */
    public void hotvideos(final HotvideosListener mHotvideosListener) {
        Call<HomeHotvideos> call = homeService.hotvideos();
        call.enqueue(new Callback<HomeHotvideos>() {
            @Override
            public void onResponse(Call<HomeHotvideos> call, Response<HomeHotvideos> response) {
                mHotvideosListener.hotvideosData(response.body());
            }

            @Override
            public void onFailure(Call<HomeHotvideos> call, Throwable t) {

            }
        });
    }

    public interface HotvideosListener {
        void hotvideosData(HomeHotvideos mHomeHotvideos);
    }

    /**
     * 进关注item的帖子页面
     *
     * @param mCardPageListener
     */
    public void cardPage(String eventId,final CardPageListener mCardPageListener) {
        Call<HomeCardInfo> call = homeService.cardPage(eventId);
        call.enqueue(new Callback<HomeCardInfo>() {
            @Override
            public void onResponse(Call<HomeCardInfo> call, Response<HomeCardInfo> response) {
                mCardPageListener.cardPageData(response.body());
            }

            @Override
            public void onFailure(Call<HomeCardInfo> call, Throwable t) {

            }
        });
    }

    public interface CardPageListener {
        void cardPageData(HomeCardInfo mHomeCardInfo);
    }
}
