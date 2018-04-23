package com.tudouni.makemoney.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.Constants;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit工具类
 */
public abstract class RetrofitUtils {
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    protected static Subscription subscription;
    protected static Gson gson;

    /**
     * 获取Retrofit对象
     */
    protected static Retrofit getRetrofit() {
        if (null == mRetrofit) {
            if (null == mOkHttpClient) {
                mOkHttpClient = OkHttp3Utils.getOkHttpClient();
            }
            //Retrofit2后使用build设计模式
            mRetrofit = new Retrofit.Builder()
                    //设置服务器路径
                    .baseUrl(Constants.HTTP_URL)
                    //添加转化库，默认是Gson 添加忽略null值
                    .addConverterFactory(GsonConverterFactory.create(buildGson()))
//                    .addConverterFactory(StringConverterFactory.create())
                    //添加回调库，采用RxJava
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //设置使用okhttp网络请求
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    private static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(String.class,new StringDefaultNullAdapter())
                    .registerTypeAdapter(Object.class,new StringDefaultNullAdapter())
                    .create();
        }
        return gson;
    }

    protected static Gson buildGson2() {
        if (gson == null) {
            gson = new GsonBuilder().serializeNulls().create();
        }
        return gson;
    }

    /**
     */
    protected static <T> void setSubscribe(Observable<Result<T>> observable, BaseObserver<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    protected static <T> void setSubscribe(BaseActivity activity, Observable<Result<T>> observable, BaseObserver<T> observer) {
        observable
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
