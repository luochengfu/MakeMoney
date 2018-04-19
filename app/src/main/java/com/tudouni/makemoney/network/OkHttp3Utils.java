package com.tudouni.makemoney.network;

import android.util.Log;


import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.utils.base.BaseViewHelper;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 类描述：封装一个OkHttp3的获取类
 * Created by YSL on 2016/8/3 0003.
 */
public class OkHttp3Utils {

    private static OkHttpClient mOkHttpClient;
    //设置缓存目录
    private static File cacheDirectory = new File(BaseViewHelper.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "CacheFile");
    //设置缓存目录大小 10M
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);
    private static List<Interceptor> interceptors;

    public static void setInterceptors(List<Interceptor> items) {
        interceptors = items;
    }

    private static HttpLoggingInterceptor loggingInterceptor;

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {
        if (loggingInterceptor == null) {
            loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    if (BuildConfig.BUILD_TYPE.equals("debug"))
                        Log.i("RetrofitLog", "retrofitBack = " + message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        if (null == mOkHttpClient) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (interceptors != null)
                builder.interceptors().addAll(interceptors);

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = builder
                    .addInterceptor(new LogInterceptor())
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .cache(cache)
                    .build();

        }
        return mOkHttpClient;
    }

}
