package com.duzzi.sdk.core.http;


import com.duzzi.sdk.core.constant.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 文件名: HttpHelper
 * 描    述: [该类的简要描述]
 * 创建人: duzzi
 * 创建时间: 2018/10/25
 */
public class HttpHelper {
    private static HttpHelper sHttpHelper;
    private static ApiService apiService;
    private static OkHttpClient sHttpClient;


    private Retrofit mWanAndroidRetrofit;
    private HttpHelper() {
    }

    private static OkHttpClient getOkHttpClient() {
        if (sHttpClient == null) {
            synchronized (HttpHelper.class) {
                if (sHttpClient == null) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    sHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(Constants.DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                            .readTimeout(Constants.DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(Constants.DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                            .addInterceptor(interceptor)
                            .build();
                }
            }
        }
        return sHttpClient;
    }

    public static HttpHelper getInstance() {
        if (sHttpHelper == null) {
            synchronized (HttpHelper.class) {
                if (sHttpHelper == null) {
                    sHttpHelper = new HttpHelper();
                }
            }
        }
        return sHttpHelper;
    }

    public ApiService getService() {
        if (apiService == null) {
            apiService = getWanAndroidRetrofit().create(ApiService.class);
        }
        return apiService;
    }

    private Retrofit getWanAndroidRetrofit() {
        if (mWanAndroidRetrofit == null) {
            mWanAndroidRetrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return mWanAndroidRetrofit;
    }
}
