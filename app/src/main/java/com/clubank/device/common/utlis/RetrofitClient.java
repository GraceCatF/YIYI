package com.clubank.device.common.utlis;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.clubank.device.data.remote.ApiService;
import com.clubank.device.data.remote.model.BaseConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fengyq on 2017/2/28
 */
public class RetrofitClient {
    private final long OKHTTP_CONNECT_TIMEOUT = 6000;
    private final long OKHTTP_READ_TIMEOUT = 6000;
    private final long OKHTTP_WRITE_TIMEOUT = 6000;
    private   ArrayMap<String, String> headers;
    public static ApiService apiService;

    private OkHttpClient okHttpClient;

    public static String baseUrl = BaseConfig.BASE_URL;

    private static Context mContext;

    private static RetrofitClient sNewInstance;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(
                mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }


    public static RetrofitClient getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }
        sNewInstance = new RetrofitClient(context, url);
        return sNewInstance;
    }

    private RetrofitClient(Context context) {

        this(context, null);
    }

    private RetrofitClient(Context context, String url) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        if(null==headers){
            headers=new ArrayMap<>();
            headers.put("Accept","application/json");
          // headers.put("Authorization",BaseConfig.TOKEN);
        }


        okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//出现错误重新连接
                .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor
                        .Level.BODY)) //Log信息
                .addInterceptor(new BaseInterceptor(headers))
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        apiService = retrofit.create(ApiService.class);
    }



    public void  setHeaders(ArrayMap<String, String> headers){
            this.headers=headers;
    }
}
