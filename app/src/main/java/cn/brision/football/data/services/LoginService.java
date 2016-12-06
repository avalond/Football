package cn.brision.football.data.services;

import cn.brision.football.model.UserLogin;
import cn.brision.football.model.UserLogout;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by brision on 16/9/6.
 */
public interface LoginService {

    String PRODUCT_BASE_RUL = "https://" + BaseService.PRODUCT_HOST;
    String DEBUG_BASE_RUL = "http://" + BaseService.DEBUG_HOST;
    @GET("/webservice/api/register/sms.php")
    Call<UserLogin> login(@Query("tel") String phone,
                          @Query("code") String code,
                          @Query("type") String type);

    @GET("/webservice/api/user/logout")
    Call<UserLogout> logout();
}
