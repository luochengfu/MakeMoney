package com.tudouni.makemoney.network;

import com.tudouni.makemoney.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public interface CommonApi
{
    @FormUrlEncoded
    @POST(NetConfig.MSG_CODE)
    Observable<Result<String>> getMsgCode(@Field("phone") String phone);


    @FormUrlEncoded
    @POST(NetConfig.TEL_LOGIN)
    Observable<Result<User>> telCodeLogin(@Field("phone") String phone,
                                          @Field("code") String code,
                                          @Field("model") String model,
                                          @Field("brand") String brand);

    @FormUrlEncoded
    @POST(NetConfig.SETPWD)
    Observable<Result<String>> setPWD(@Field("password") String password);
}
