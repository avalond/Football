package cn.brision.football.data.services;

import cn.brision.football.model.HomeBanner;
import cn.brision.football.model.HomeCardInfo;
import cn.brision.football.model.HomeHotvideos;
import cn.brision.football.model.HomeLives;
import cn.brision.football.model.HomeTops;
import cn.brision.football.model.HomeUserfollows;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wangchengcheng on 16/10/10.
 */
public interface HomeService {
    String PRODUCT_BASE_RUL = "https://" + BaseService.PRODUCT_HOST;
    String DEBUG_BASE_RUL = "http://" + BaseService.DEBUG_HOST;

    @GET("/home/banner")
    Call<HomeBanner> banner();

    @GET("/home/lives")
    Call<HomeLives> lives();

    @GET("/home/tops")
    Call<HomeTops> tops();

    @GET("/home/userfollows")
    Call<HomeUserfollows> userfollows();

    @GET("/home/hotvideos")
    Call<HomeHotvideos> hotvideos();


    @GET("event/event")
    Call<HomeCardInfo> cardPage(@Query("eventId")String eventId);



}
