package com.tudouni.makemoney.network;

import com.tudouni.makemoney.model.AliAuth;
import com.tudouni.makemoney.model.AppConfig;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.model.BindInfo;
import com.tudouni.makemoney.model.Invite;
import com.tudouni.makemoney.model.PayBindingInfo;
import com.tudouni.makemoney.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public interface CommonApi {
    /**
     * APP启动配置接口
     *
     * @return
     */
    @POST(NetConfig.CONFIG)
    Observable<Result<AppConfig>> getConfig();

    @FormUrlEncoded
    @POST(NetConfig.MSG_CODE)
    Observable<Result<String>> getMsgCode(@Field("phone") String phone);

    @FormUrlEncoded
    @POST(NetConfig.VERIFCODE)
    Observable<Result<String>> getVerifCode(@Field("phone") String phone);

    @FormUrlEncoded
    @POST(NetConfig.PASSWORD_MSG_CODE)
    Observable<Result<String>> getPasswordCode(@Field("phone") String phone);


    @FormUrlEncoded
    @POST(NetConfig.TEL_LOGIN)
    Observable<Result<User>> telCodeLogin(@Field("phone") String phone,
                                          @Field("code") String code,
                                          @Field("model") String model,
                                          @Field("brand") String brand);

    @FormUrlEncoded
    @POST(NetConfig.SETPWD)
    Observable<Result<String>> setPWD(@Field("password") String password);


    @POST(NetConfig.GOOD_LIST)
    Observable<Result<List<Category>>> getGoodList();


    @FormUrlEncoded
    @POST(NetConfig.BINDPHONE)
    Observable<Result<User>> bindPhone(@Field("phone") String phone,
                                       @Field("code") String code,
                                       @Field("model") String model,
                                       @Field("brand") String brand);

    @FormUrlEncoded
    @POST(NetConfig.BINDPHONE2)
    Observable<Result<User>> bindPhone2(@Field("phone") String phone,
                                        @Field("code") String code,
                                        @Field("model") String model,
                                        @Field("brand") String brand,
                                        @Field("verifyToken") String verifyToken);

    @FormUrlEncoded
    @POST(NetConfig.VERIFCODE_CHANGE)
    Observable<Result<User>> verifcodeChange(@Field("phone") String phone,
                                             @Field("code") String code,
                                             @Field("model") String model,
                                             @Field("brand") String brand);

    @FormUrlEncoded
    @POST(NetConfig.TEL_LOGIN)
    Observable<Result<User>> findPassword(@Field("phone") String phone,
                                          @Field("code") String code,
                                          @Field("model") String model,
                                          @Field("brand") String brand);

    @FormUrlEncoded
    @POST(NetConfig.BIND_NEW_PHONE)
    Observable<Result<User>> bindNewPhone(@Field("phone") String phone,
                                          @Field("code") String code,
                                          @Field("model") String model,
                                          @Field("brand") String brand,
                                          @Field("handleToken") String handleToken);

    @POST(NetConfig.GETUSERINFO)
    Observable<Result<User>> getUserInfo();

    @POST(NetConfig.ACCOUNT_BIND)
    Observable<Result<BindInfo>> accountBind();

    @POST(NetConfig.PAY_STATUS)
    Observable<Result<PayBindingInfo>> payStatus();

    @FormUrlEncoded
    @POST(NetConfig.UNBUNDTHREE)
    Observable<Result<String>> unbindThree(@Field("acessToken") String acessToken,
                                           @Field("openid") String openid,
                                           @Field("platform") String platform);

    @FormUrlEncoded
    @POST(NetConfig.BINDACCCOUNT)
    Observable<Result<String>> bindAccount(@Field("code") String code,
                                           @Field("openid") String openid,
                                           @Field("platform") String platform);

    @POST(NetConfig.ALIAUTH)
    Observable<Result<AliAuth>> aliAuth();

    @FormUrlEncoded
    @POST(NetConfig.VERIFPWD)
    Observable<Result<String>> verifyPassword(@Field("password") String password);

    @FormUrlEncoded
    @POST(NetConfig.USERNAME_PASSWOED_LOGIN)
    Observable<Result<User>> passwordLogin(@Field("user") String user,
                                           @Field("password") String password,
                                           @Field("model") String model,
                                           @Field("brand") String brand);

    @FormUrlEncoded
    @POST(NetConfig.THIRD_LOGIN_PROCESS)
    Observable<Result<User>> threeLoginProcess(@Field("acessToken") String acessToken,
                                               @Field("openid") String openid,
                                               @Field("platform") String platform,
                                               @Field("birthday") String birthday,
                                               @Field("city") String city,
                                               @Field("nickname") String nickname,
                                               @Field("photo") String photo,
                                               @Field("sex") String sex,
                                               @Field("signature") String signature,
                                               @Field("unionid") String unionid,
                                               @Field("model") String model,
                                               @Field("brand") String brand);


    @POST(NetConfig.INVITEINFO)
    Observable<Result<Invite>> getBindata();

    /**
     * 查询邀请人的个人信息（简略信息）
     *
     * @param inviterUnionid 待查询的邀请人的unionid
     * @param userId         待查询的邀请人的userId（注：不是土豆号、是数据库uid）
     * @param userCode       待查询的邀请人的土豆号
     * @return
     */
    @FormUrlEncoded
    @POST(NetConfig.INVITEINFO_V2)
    Observable<Result<Invite>> getInviteInfo(@Field("inviterUnionid") String inviterUnionid, @Field("userId") String userId, @Field("userCode") String userCode);

    /**
     * 绑定邀请
     *
     * @param unionid
     * @param userCode
     * @return
     */
    @FormUrlEncoded
    @POST(NetConfig.BINDING_INVITE)
    Observable<Result<Result>> addBinding(@Field("inviterUnionid") String unionid, @Field("userCode") String userCode);
}
