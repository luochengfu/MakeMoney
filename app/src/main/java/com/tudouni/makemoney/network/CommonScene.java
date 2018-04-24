package com.tudouni.makemoney.network;

import com.tudouni.makemoney.model.AliAuth;
import com.tudouni.makemoney.model.AppConfig;
import com.tudouni.makemoney.model.BindInfo;
import com.tudouni.makemoney.model.Banner;
import com.tudouni.makemoney.model.LoginBean;
import com.tudouni.makemoney.model.PayBindingInfo;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.network.rx.BaseObserver;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class CommonScene extends RetrofitUtils {
    private static final CommonApi commonApi = getRetrofit().create(CommonApi.class);

    public static void getMsgCode(String phone, BaseObserver<String> observer) {
        setSubscribe(commonApi.getMsgCode(phone), observer);
    }

    public static void getVerifCode(String phone, BaseObserver<String> observer) {
        setSubscribe(commonApi.getVerifCode(phone), observer);
    }

    /**
     * 重置密码获取验证码
     */
    public static void getPasswordCode(String phone, BaseObserver<String> observer) {
        setSubscribe(commonApi.getPasswordCode(phone), observer);
    }

    /**
     * 验证码登录
     */
    public static void telCodeLogin(String phone, String code, String model, String brand, BaseObserver<User> observer) {
        setSubscribe(commonApi.telCodeLogin(phone, code, model, brand), observer);
    }

    public static void setPWD(String password, BaseObserver<String> observer) {
        setSubscribe(commonApi.setPWD(password), observer);
    }

    public static void getConfig(BaseObserver<AppConfig> observer) {
        setSubscribe(commonApi.getConfig(), observer);
    }

    public static void getGoodList(BaseObserver<List<Category>> observer){
        setSubscribe(commonApi.getGoodList(),observer);
    }

    /**
     * 绑定手机号
     */
    public static void bindPhone(String phone, String code, String model, String brand, BaseObserver<User> observer) {
        setSubscribe(commonApi.bindPhone(phone,code,model,brand), observer);
    }

    /**
     * 绑定新手机号
     */
    public static void bindPhone2(String phone, String code, String model, String brand, String verifyToken, BaseObserver<User> observer) {
        setSubscribe(commonApi.bindPhone2(phone,code,model,brand,verifyToken), observer);
    }

    /**
     * 解除原手机号
     */
    public static void verifcodeChange(String phone, String code, String model, String brand, BaseObserver<User> observer) {
        setSubscribe(commonApi.verifcodeChange(phone,code,model,brand), observer);
    }

    /**
     * 找回密码
     */
    public static void findPassword(String phone, String code, String model, String brand, BaseObserver<User> observer) {
        setSubscribe(commonApi.findPassword(phone,code,model,brand), observer);
    }

    /**
     * 新用户注册绑定手机号
     */
    public static void bindNewPhone(String phone, String code, String model, String brand,String handleToken, BaseObserver<User> observer) {
        setSubscribe(commonApi.bindNewPhone(phone,code,model,brand,handleToken), observer);
    }

    /**
     * 获取用户数据接口
     */
    public static void getUserInfo(BaseObserver<User> observer) {
        setSubscribe(commonApi.getUserInfo(), observer);
    }

    /**
     * 用户绑定信息
     */
    public static void accountBind(BaseObserver<BindInfo> observer){
        setSubscribe(commonApi.accountBind(), observer);
    }

    /**
     * 是否已经绑定
     */
    public static void payStatus(BaseObserver<PayBindingInfo> observer) {
        setSubscribe(commonApi.payStatus(),observer);
    }

    /**
     * 第三方账号解绑
     */
    public static void unbindThree(String acessToken,String openid,String platform, BaseObserver<String> observer) {
        setSubscribe(commonApi.unbindThree(acessToken,openid,platform),observer);
    }

    /**
     * 第三方账号绑定
     */
    public static void bindAccount(String code,String openid,String platform, BaseObserver<String> observer) {
        setSubscribe(commonApi.bindAccount(code,openid,platform),observer);
    }

    /**
     * 阿里认证
     */
    public static void aliAuth(BaseObserver<AliAuth> observer) {
        setSubscribe(commonApi.aliAuth(),observer);
    }

    /**
     * 修改密码
     */
    public static void verifyPassword(String password, BaseObserver<String> observer) {
        setSubscribe(commonApi.verifyPassword(password),observer);
    }

    /**
     * 密码登录
     */
    public static void passwordLogin(String user, String password,String model, String brand,BaseObserver<User> observer) {
        setSubscribe(commonApi.passwordLogin(user,password,model,brand),observer);
    }

    /**
     * 第三方登录
     */
    public static void threeLoginProcess(String acessToken, String openid,String platform, String birthday,String city, String nickname,
                                         String photo, String sex,String signature, String unionid,String model, String brand,BaseObserver<LoginBean> observer) {
        setSubscribe(commonApi.threeLoginProcess(acessToken,openid,platform,birthday,city,nickname,photo,sex,signature,unionid,model,brand),observer);
    }

    /**
     * 发现获取banner数据
     */
    public static void getFoundBanner(BaseObserver<List<Banner>> observer) {
        setSubscribe(commonApi.getFoundBanner(),observer);
    }

}
