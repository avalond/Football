package cn.brision.football.data.services;

import android.content.Context;
import android.util.Log;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.brision.football.BuildConfig;
import cn.brision.football.data.HomeAction;
import cn.brision.football.data.LoginAction;
import cn.brision.football.data.MatchAction;
import cn.brision.football.utils.PreferencesManager;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by brision on 16/9/7.
 */
public class DataManger {


    private static DataManger instance;
    private String accesstoken;
    private LoginAction loginAction;
    private MatchAction matchAction;
    private HomeAction homeAction;

    private DataManger(Context context) {
        accesstoken = PreferencesManager.getInstance(context).get("Access_token");
        initRepositories();
    }

    public synchronized static DataManger init(Context context) {
        if (instance != null) {
            throw new RuntimeException("DataManager already init!");
        }
        return instance = new DataManger(context.getApplicationContext());
    }

    private void initRepositories() {
        OkHttpClient httpClient = createHttpClient();
        loginAction = new LoginAction(httpClient);
        matchAction = new MatchAction(httpClient);
        homeAction = new HomeAction(httpClient);
    }

    public LoginAction getLoginAction() {
        return loginAction;
    }

    public MatchAction getMatchAction() {
        return matchAction;
    }

    public HomeAction getHomeAction() {
        return homeAction;
    }

    private OkHttpClient createHttpClient() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("AUTHTOKEN", accesstoken);
        Log.d("AUTHTOKEN", accesstoken);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new AccessTokenInterceptor(headers))
                .sslSocketFactory(HttpsTrustManager.getSsLSocketFactory())
                .hostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        Interceptor stethoInterceptor = getStethoInterceptor();
        if (stethoInterceptor != null) {
            builder.addNetworkInterceptor(stethoInterceptor);
        }
        return builder.build();

    }
    private Interceptor getStethoInterceptor() {
        try {
            Class<?> cls = Class.forName("com.facebook.stetho.okhttp3.StethoInterceptor");
            return (Interceptor) cls.newInstance();
        } catch (ClassNotFoundException ignored) {
            ;// ignored
        } catch (InstantiationException ignored) {
            ;// ignored
        } catch (IllegalAccessException ignored) {
            ;// ignored
        }
        return null;
    }
}
