package cn.brision.football.data.services;

import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {
    private final Map<String, String> mHeaderMap = new ConcurrentHashMap<>();

    public AccessTokenInterceptor(Map<String, String> headers) {
        this.mHeaderMap.putAll(headers);
    }

    public void updateToken(String token) {
        this.mHeaderMap.put("AUTHTOKEN", token == null ? "" : token);
    }

    public Map<String, String> getHeaders(){
        return mHeaderMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request newRequest = request.newBuilder().headers(Headers.of(mHeaderMap)).build();

        return chain.proceed(newRequest);
    }
}
