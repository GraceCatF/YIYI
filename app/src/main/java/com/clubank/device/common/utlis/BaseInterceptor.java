package com.clubank.device.common.utlis;

import android.util.ArrayMap;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 基础拦截器
 * Created by fengyq on 2017/2/28.
 */
public class BaseInterceptor implements Interceptor {
    private ArrayMap<String, String> headers;

    public BaseInterceptor(ArrayMap<String, String> headers) {
        this.headers = headers;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder requestBuilder = chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> set = headers.keySet();
            for (String key : set) {
                requestBuilder.addHeader(key, headers.get(key)).build();
            }

        }

        return chain.proceed(requestBuilder.build());
    }
}
