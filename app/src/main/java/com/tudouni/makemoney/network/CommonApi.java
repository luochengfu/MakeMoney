package com.tudouni.makemoney.network;

import android.databinding.ObservableArrayList;

import com.tudouni.makemoney.model.AgentInfo;
import com.tudouni.makemoney.model.AliAuth;
import com.tudouni.makemoney.model.AppConfig;
import com.tudouni.makemoney.model.Banner;
import com.tudouni.makemoney.model.BindInfo;
import com.tudouni.makemoney.model.BindUserBean;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.model.DeleteSyaMsgRequestBean;
import com.tudouni.makemoney.model.FoundTopicBean;
import com.tudouni.makemoney.model.Invite;
import com.tudouni.makemoney.model.LoginBean;
import com.tudouni.makemoney.model.MallAlbumModel;
import com.tudouni.makemoney.model.MallGoodItem;
import com.tudouni.makemoney.model.MessageResponsBean;
import com.tudouni.makemoney.model.NineRecommendBean;
import com.tudouni.makemoney.model.PayBindingInfo;
import com.tudouni.makemoney.model.RecommendTopicBean;
import com.tudouni.makemoney.model.SearchHistory;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.model.Zma;
import com.tudouni.makemoney.utils.upload.UploadInfo;
import com.tudouni.makemoney.widget.versionUpdate.Upinfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
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
    Observable<Result<String>> getVerifCode(@Field("phone") String phone,
                                            @Field("uid") String uid,
                                            @Field("token") String token);

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
    Observable<Result<String>> setPWD(@Field("password") String password,
                                      @Field("uid") String uid,
                                      @Field("token") String token);


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

    @FormUrlEncoded
    @POST(NetConfig.GEN_CERT_URL)
    Observable<Result<Zma>> genCertUrl(@Field("idNumber") String idNumber,
                                       @Field("realname") String realname,
                                       @Field("authFaceType") String authFaceType);

    @FormUrlEncoded
    @POST(NetConfig.ZMXY_CALL)
    Observable<Result<String>> zmxyCall(@Field("idNumber") String idNumber,
                                       @Field("realname") String realname,
                                       @Field("bizNo") String bizNo);

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
    Observable<Result<LoginBean>> threeLoginProcess(@Field("acessToken") String acessToken,
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

    /**
     * 获取钱数
     *
     * @return
     */
    @POST(NetConfig.AGENTINFO)
    Observable<Result<AgentInfo>> getAgentInfo();

    @POST(NetConfig.FOUND_BANNER)
    Observable<Result<List<Banner>>> getFoundBanner();

    @POST(NetConfig.RECOMMEND_TOPIC)
    Observable<Result<List<RecommendTopicBean>>> getRecommendTopic();

    @POST(NetConfig.FOUND_TOPIC)
    Observable<Result<List<FoundTopicBean>>> getFoundTopic();


    @POST(NetConfig.MALL_BANNER)
    Observable<Result<List<MallAlbumModel>>> getMallBannerData();

    /**
     * 获取上传信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(NetConfig.UPLOAD_INFO)
    Observable<Result<UploadInfo>> uploadInfo(@Field("type") String type);

    /**
     * 设置用户信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(NetConfig.SET_INFO)
    Observable<Result<Object>> setUserInfo(@Field("birthday") String birthday, @Field("city") String city, @Field("nickName") String nickName,
                                           @Field("photo") String photo, @Field("sex") String sex, @Field("singtrue") String singtrue);

    /**
     * 获取版本更新信息
     *
     * @return
     */
    @POST(NetConfig.UPINFO)
    Observable<Result<Upinfo>> getVersionUpdataInfo();

//    /**
//     * 我的账本
//     *
//     * @return
//     */
//    @POST("/zzshop/income/profile/{uid}")
//    Observable<Result<AgentInfo>> getMineTicketBook(@Path("uid") String uid);

    /**
     * 我的账本
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/zzshop/income/profile")
    Observable<Result<AgentInfo>> getMineTicketBook(@Field("uid") String uid);


    /**
     * 获取消息列表
     *
     * @param uid
     * @param msgpage  个人系统消息页数
     * @param gmsgpage 全局系统消息页数
     * @return
     */
    @FormUrlEncoded
    @POST("/zzshop/getSysMsg")
    Observable<Result<MessageResponsBean>> getSysMsg(@Field("uid") String uid, @Field("msgpage") int msgpage, @Field("gmsgpage") int gmsgpage);

    /**
     * 是否有未读消息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/zzshop/isThereUnreadMsg")
    Observable<Result<MessageResponsBean>> isThereUnreadMsg(@Field("uid") String uid);

    /**
     * 已读消息上报
     *
     * @param sysmsgtime  个人系统消息最近一条的时间戳，消息体有带time字段，没有则填0
     * @param gsysmsgtime 全局系统消息最近一条的时间戳，消息体有带time字段，没有则填0
     * @return
     */
    @FormUrlEncoded
    @POST("/zzshop/updateMsgReadInfo")
    Observable<Result<Object>> updateMsgReadInfo(@Field("sysmsgtime") long sysmsgtime, @Field("gsysmsgtime") long gsysmsgtime);

    /**
     * 删除消息 zp
     *
     * @return
     */
    @POST("/zzshop/delMsg ")
    Observable<Result<Object>> deleteSysMsg(@Body DeleteSyaMsgRequestBean deleteSyaMsgRequestBean);

//    /**
//     * 删除消息 zp
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("/zzshop/delMsg")
//    Observable<Result<Object>> deleteSysMsg(@Field("uid") String uid, @Field("msgList[]") List<String> msgList);

    @POST("zzshop/gethotcatalog")
    Observable<Result<List<MallAlbumModel>>> getMallAlbumData();

    @FormUrlEncoded
    @POST("zzshop/getrecommendgoods")
    Observable<Result<List<MallGoodItem>>> getRecommendGood(@Field("page") int page, @Field("pageSize") int pageSize);

    @POST("zzshop/getownGoodscatalog")
    Observable<Result<ObservableArrayList<MallAlbumModel>>> getSelfGood();


    @FormUrlEncoded
    @POST("zzshop/auth/invite/info_V2")
    Observable<Result<BindUserBean>> getBindUserInfo(@Field("userCode") String userCode);

    @FormUrlEncoded
    @POST("zzshop/auth/invite/action/addBinding")
    Observable<Result<String>> addBindUser(@Field("userCode") String userCode, @Field("inviterUnionid") String inviterUnionid);

    @FormUrlEncoded
    @POST("zzshop/searchRecord/list")
    Observable<Result<List<SearchHistory>>> loadSearchHistory(@Field("unionid") String unionid);

    @FormUrlEncoded
    @POST("zzshop/searchRecord/save")
    Observable<Result<Object>> saveSearchHistoryToService(@Field("unionid") String unionId, @Field("source") String source, @Field("deviceModel") String model, @Field("content") String content);

    /**
     * 九宫格商品推荐
     *
     * @return
     */
    @POST(NetConfig.NINE_RECOMMEND)
    Observable<Result<List<NineRecommendBean>>> getNineRecommend();
}
