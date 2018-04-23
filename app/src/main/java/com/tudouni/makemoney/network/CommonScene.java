package com.tudouni.makemoney.network;

import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.network.rx.BaseObserver;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class CommonScene extends RetrofitUtils
{
    private static final CommonApi commonApi = getRetrofit().create(CommonApi.class);

    public static void getMsgCode(String phone, BaseObserver<String> observer) {
        setSubscribe(commonApi.getMsgCode(phone), observer);
    }

    public static void telCodeLogin(String phone, String code, String model, String brand, BaseObserver<User> observer) {
        setSubscribe(commonApi.telCodeLogin(phone,code,model,brand), observer);
    }

    public static void setPWD(String password, BaseObserver<String> observer) {
        setSubscribe(commonApi.setPWD(password), observer);
    }
}
