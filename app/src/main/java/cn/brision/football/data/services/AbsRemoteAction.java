package cn.brision.football.data.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brision on 16/9/6.
 */
public abstract class AbsRemoteAction {

    OkHttpClient httpClient;

    public AbsRemoteAction(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected <T> T createService(String url, final Class<T> service) {
        return new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(service);
    }
    public OkHttpClient getHttpClient() {
        return httpClient;
    }

}
