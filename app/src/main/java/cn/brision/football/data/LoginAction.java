package cn.brision.football.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.avos.avoscloud.AVUser;

import cn.brision.football.BuildConfig;
import cn.brision.football.R;
import cn.brision.football.activity.logins.LoginActivity;
import cn.brision.football.activity.logins.LogoutActivity;
import cn.brision.football.commen.NotificationReceiver;
import cn.brision.football.data.services.AbsRemoteAction;
import cn.brision.football.data.services.AccessTokenInterceptor;
import cn.brision.football.data.services.LoginService;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.model.UserLogin;
import cn.brision.football.model.UserLogout;
import cn.brision.football.utils.Const;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.ToastUtil;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by brision on 16/9/6.
 */
public class LoginAction extends AbsRemoteAction {


    private final LoginService mloginService;
    public static final boolean DEBUG = BuildConfig.API_ENV;

    public LoginAction(OkHttpClient httpClient) {
        super(httpClient);
        if (DEBUG)
            mloginService = (LoginService) createService(LoginService.DEBUG_BASE_RUL, LoginService.class);
        else
            mloginService = (LoginService) createService(LoginService.PRODUCT_BASE_RUL, LoginService.class);
    }

    private void updateToken(String token) {
        OkHttpClient httpClient = getHttpClient();
        for (Interceptor interceptor : httpClient.interceptors()) {
            if (interceptor instanceof AccessTokenInterceptor) {
                ((AccessTokenInterceptor) interceptor).updateToken(token);
                break;
            }
        }
    }


    public void login(String tel, String code, final LoginDataListener mLoginDataListener) {
        Call<UserLogin> call = mloginService.login(tel, code, "android");
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                mLoginDataListener.loginData(response.body());
                if (response.body() != null)
                    updateToken(response.body().getAccess_token());
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
            }
        });
    }

    public interface LoginDataListener {
        void loginData(UserLogin mUserLogin);
    }

    public void logout(final LogoutActivity context) {
        Call<UserLogout> call = mloginService.logout();
        call.enqueue(new Callback<UserLogout>() {
            @Override
            public void onResponse(Call<UserLogout> call, Response<UserLogout> response) {
                if (response != null) {
                    if (200 == response.body().getStatus()) {
                        PreferencesManager instance = PreferencesManager.getInstance(context);
                        instance.clearAll();
                        instance.put("Access_token","");
                        instance.put("user_number", "");
                        updateToken("");
                        sentLoginStateChangeBroadcast(context);
                        MyUser.logOut();
                        ((LogoutActivity) context).finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserLogout> call, Throwable t) {
                ToastUtil.showToastOnce(context, context.getString(R.string.logout_error));
            }
        });
    }
    /**
     * 登陆状态变更的广播
     */
    private void sentLoginStateChangeBroadcast(Context context){
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction(Const.LOGIN_STATE_CHANGE);
        context.sendBroadcast(intent);   //发送广播
        Intent intent1 = new Intent();
        intent1.setAction(Const.LOGIN_STATE_CHANGE);
        context.sendBroadcast(intent1);   //发送广播
    }
}
