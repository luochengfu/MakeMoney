package com.tudouni.makemoney.network;

import android.databinding.ObservableArrayList;

import com.tudouni.makemoney.activity.BaseActivity;
import com.tudouni.makemoney.model.AgentInfo;
import com.tudouni.makemoney.model.AliAuth;
import com.tudouni.makemoney.model.AppConfig;
import com.tudouni.makemoney.model.Banner;
import com.tudouni.makemoney.model.BindInfo;
import com.tudouni.makemoney.model.BindPayInfo;
import com.tudouni.makemoney.model.BindUserBean;
import com.tudouni.makemoney.model.Category;
import com.tudouni.makemoney.model.DeleteSyaMsgRequestBean;
import com.tudouni.makemoney.model.EarningsRank;
import com.tudouni.makemoney.model.FoundTopicBean;
import com.tudouni.makemoney.model.Invite;
import com.tudouni.makemoney.model.LoginBean;
import com.tudouni.makemoney.model.MallAlbumModel;
import com.tudouni.makemoney.model.MallGoodItem;
import com.tudouni.makemoney.model.MessageResponsBean;
import com.tudouni.makemoney.model.MyEarnings;
import com.tudouni.makemoney.model.NineRecommendBean;
import com.tudouni.makemoney.model.PayBindingInfo;
import com.tudouni.makemoney.model.RecommendTopicBean;
import com.tudouni.makemoney.model.RequestNineRecommendShareBean;
import com.tudouni.makemoney.model.SavingsProfile;
import com.tudouni.makemoney.model.SearchHistory;
import com.tudouni.makemoney.model.User;
import com.tudouni.makemoney.model.Withdraw;
import com.tudouni.makemoney.model.Zma;
import com.tudouni.makemoney.myApplication.MyApplication;
import com.tudouni.makemoney.network.rx.BaseObserver;
import com.tudouni.makemoney.utils.upload.UploadInfo;
import com.tudouni.makemoney.widget.versionUpdate.Upinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class CommonScene extends RetrofitUtils {
    private static final CommonApi commonApi = getRetrofit().create(CommonApi.class);

    public static void getMsgCode(String phone, BaseObserver<String> observer) {
        setSubscribe(commonApi.getMsgCode(phone), observer);
    }

    public static void getVerifCode(String phone, String uid, String token, BaseObserver<String> observer) {
        setSubscribe(commonApi.getVerifCode(phone, uid, token), observer);
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

    public static void setPWD(String password, String uid, String token, BaseObserver<String> observer) {
        setSubscribe(commonApi.setPWD(password, uid, token), observer);
    }

    public static void getConfig(BaseObserver<AppConfig> observer) {
        setSubscribe(commonApi.getConfig(), observer);
    }

    public static void getGoodList(BaseObserver<List<Category>> observer) {
        setSubscribe(commonApi.getGoodList(), observer);
    }

    /**
     * 绑定手机号
     */
    public static void bindPhone(String phone, String code, String model, String brand, BaseObserver<User> observer) {
        setSubscribe(commonApi.bindPhone(phone, code, model, brand), observer);
    }

    /**
     * 绑定新手机号
     */
    public static void bindPhone2(String phone, String code, String model, String brand, String verifyToken, BaseObserver<User> observer) {
        setSubscribe(commonApi.bindPhone2(phone, code, model, brand, verifyToken), observer);
    }

    /**
     * 解除原手机号
     */
    public static void verifcodeChange(String phone, String code, String model, String brand, BaseObserver<User> observer) {
        setSubscribe(commonApi.verifcodeChange(phone, code, model, brand), observer);
    }

    /**
     * 找回密码
     */
    public static void findPassword(String phone, String code, String model, String brand, BaseObserver<User> observer) {
        setSubscribe(commonApi.findPassword(phone, code, model, brand), observer);
    }

    /**
     * 新用户注册绑定手机号
     */
    public static void bindNewPhone(String phone, String code, String model, String brand, String handleToken, BaseObserver<User> observer) {
        setSubscribe(commonApi.bindNewPhone(phone, code, model, brand, handleToken), observer);
    }

    /**
     * 芝麻认证（调芝麻接口前）
     */
    public static void genCertUrl(String idNumber, String realname, String authFaceType, BaseObserver<Zma> observer) {
        setSubscribe(commonApi.genCertUrl(idNumber, realname, authFaceType), observer);
    }

    /**
     * 芝麻认证（调芝麻接口后）
     */
    public static void zmxyCall(String idNumber, String realname, String bizNo, BaseObserver<String> observer) {
        setSubscribe(commonApi.zmxyCall(idNumber, realname, bizNo), observer);
    }

    /**
     * 人工认证
     */
    public static void setAuthInfo(String realname, String idNumber, String frontPhoto, String backPhoto, BaseObserver<String> observer) {
        setSubscribe(commonApi.setAuthInfo(realname, idNumber, frontPhoto, backPhoto), observer);
    }

    /**
     * 获取阿里提现手续费率
     */
    public static void payRate(BaseObserver<String> observer) {
        setSubscribe(commonApi.payRate("100", "alipay"), observer);
    }

    /**
     * 提现
     */
    public static void payCash(String money, String phone, String code, BaseObserver<String> observer) {
        setSubscribe(commonApi.payCash("alipay", money, phone, code), observer);
    }

    /**
     * 获取用户提现列表
     */
    public static void incomeHistory(String page, BaseObserver<List<Withdraw>> observer) {
        setSubscribe(commonApi.incomeHistory(page, "20"), observer);
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
    public static void accountBind(BaseObserver<BindInfo> observer) {
        setSubscribe(commonApi.accountBind(), observer);
    }

    /**
     * 是否已经绑定
     */
    public static void payStatus(BaseObserver<PayBindingInfo> observer) {
        setSubscribe(commonApi.payStatus(), observer);
    }

    /**
     * 第三方账号解绑
     */
    public static void unbindThree(String acessToken, String openid, String platform, BaseObserver<String> observer) {
        setSubscribe(commonApi.unbindThree(acessToken, openid, platform), observer);
    }

    /**
     * 第三方账号绑定
     */
    public static void bindAccount(String code, String openid, String platform, BaseObserver<String> observer) {
        setSubscribe(commonApi.bindAccount(code, openid, platform), observer);
    }

    /**
     * 阿里认证
     */
    public static void aliAuth(BaseObserver<AliAuth> observer) {
        setSubscribe(commonApi.aliAuth(), observer);
    }

    /**
     * 支付宝保存授权
     */
    public static void bindAlipay(String authCode, BaseObserver<String> observer) {
        setSubscribe(commonApi.bindAlipay(authCode), observer);
    }

    /**
     * 修改密码
     */
    public static void verifyPassword(String password, BaseObserver<String> observer) {
        setSubscribe(commonApi.verifyPassword(password), observer);
    }

    /**
     * 密码登录
     */
    public static void passwordLogin(String user, String password, String model, String brand, BaseObserver<User> observer) {
        setSubscribe(commonApi.passwordLogin(user, password, model, brand), observer);
    }

    /**
     * 第三方登录
     */
    public static void threeLoginProcess(String acessToken, String openid, String platform, String birthday, String city, String nickname,
                                         String photo, String sex, String signature, String unionid, String model, String brand, BaseObserver<LoginBean> observer) {
        setSubscribe(commonApi.threeLoginProcess(acessToken, openid, platform, birthday, city, nickname, photo, sex, signature, unionid, model, brand), observer);
    }

    /**
     * 查询绑定人信息
     *
     * @param observer
     */
    public static void getBind(BaseObserver<Invite> observer) {
        setSubscribe(commonApi.getBindata(), observer);
    }

    /**
     * 查询邀请人的个人信息（简略信息）
     * <p>
     * zhangpeng
     *
     * @param inviterUnionid 待查询的邀请人的unionid
     * @param userId         待查询的邀请人的userId（注：不是土豆号、是数据库uid）
     * @param userCode       待查询的邀请人的土豆号
     * @return
     */
    public static void getInviteInfo(String inviterUnionid, String userId, String userCode, BaseObserver<Invite> observer) {
        setSubscribe(commonApi.getInviteInfo(inviterUnionid, userId, userCode), observer);
    }


    /**
     * @param inviterUnionid 待绑定的邀请人的unionind
     * @param userCode       待绑定的要亲人的土豆号
     * @param observer
     */
    public static void addBinding(String inviterUnionid, String userCode, BaseObserver<Result> observer) {
        setSubscribe(commonApi.addBinding(inviterUnionid, userCode), observer);
    }

    /**
     * 商城收益
     *
     * @param observer
     */
    public static void getAgentInfo(BaseObserver<AgentInfo> observer) {
        setSubscribe(commonApi.getAgentInfo(), observer);
    }

    /**
     * 发现获取banner数据
     */
    public static void getFoundBanner(BaseObserver<List<Banner>> observer) {
        setSubscribe(commonApi.getFoundBanner(), observer);
    }

    /**
     * 获取上传信息
     */
    public static void uploadInfo(String type, BaseObserver<UploadInfo> observer) {
        Map<String, String> para1 = new HashMap<>();
        para1.put("type", type);
        setSubscribe(commonApi.uploadInfo(type), observer);
    }

    /**
     * 获取上传信息
     */
    public static void setUserInfo(String birthday, String city, String nickName, String photo, String sex, String singtrue, BaseObserver<Object> observer) {
        setSubscribe(commonApi.setUserInfo(birthday, city, nickName, photo, sex, singtrue), observer);
    }

    /**
     * 版本更新
     */
    public static void getVersionUpdataInfo(BaseObserver<Upinfo> observer) {
        setSubscribe(commonApi.getVersionUpdataInfo(), observer);
    }

    /**
     * 商城首页Banner
     */
    public static void getMallBanner(BaseObserver<List<MallAlbumModel>> observer) {
        setSubscribe(commonApi.getMallBannerData(), observer);
    }


    /**
     * 商城首页专题
     */
    public static void getMallAlbumData(BaseObserver<List<MallAlbumModel>> observer) {
        setSubscribe(commonApi.getMallAlbumData(), observer);
    }

    /**
     * 商城首页推荐
     */
    public static void getRecommendGood(BaseObserver<List<MallGoodItem>> observer, int page, int pageSize) {
        setSubscribe(commonApi.getRecommendGood(page, pageSize), observer);
    }

    /**
     * 自营分类
     */
    public static void getSelfGood(BaseObserver<ObservableArrayList<MallAlbumModel>> observer) {
        setSubscribe(commonApi.getSelfGood(), observer);
    }

    /**
     * 我的账本
     */
    public static void getMineTicketBook(BaseObserver<AgentInfo> observer) {
        if (MyApplication.getLoginUser() == null) return;
        setSubscribe(commonApi.getMineTicketBook(MyApplication.getLoginUser().getUid()), observer);
    }

    /**
     * 发现获取发现底部数据
     */

    public static void getRecommendTopic(BaseObserver<List<RecommendTopicBean>> observer) {
        setSubscribe(commonApi.getRecommendTopic(), observer);
    }


    /**
     * zp
     * 九宫格商品推荐
     */

    public static void getNineRecommend(BaseObserver<List<NineRecommendBean>> observer) {
        setSubscribe(commonApi.getNineRecommend(), observer);
    }

    /**
     * zp
     * 九宫格商品推荐 分享的图片地址
     */

    public static void getNineRecommendShareData(RequestNineRecommendShareBean requestNineRecommendShareBean, BaseObserver<List<String>> observer) {
        if (requestNineRecommendShareBean == null) return;
        setSubscribe(commonApi.getNineRecommendShareData(requestNineRecommendShareBean), observer);
    }

    /**
     * 发现获取topic
     */
    public static void getFoundTopic(BaseObserver<List<FoundTopicBean>> observer) {
        setSubscribe(commonApi.getFoundTopic(), observer);
    }

    /**
     * 获取消息列表
     */
    public static void getSysMsg(String uid, int msgpage, int gmsgpage, BaseObserver<MessageResponsBean> observer) {
        setSubscribe(commonApi.getSysMsg(uid, msgpage, gmsgpage), observer);
    }

    /**
     * 是否有未读消息
     *
     * @param observer
     */
    public static void isThereUnreadMsg(BaseObserver<MessageResponsBean> observer) {
        if (MyApplication.getLoginUser() == null) return;
        setSubscribe(commonApi.isThereUnreadMsg(MyApplication.getLoginUser().getUid()), observer);
    }

    /**
     * 获取消息列表
     */
    public static void updateMsgReadInfo(long sysmsgtime, long gsysmsgtime, BaseObserver<Object> observer) {
        setSubscribe(commonApi.updateMsgReadInfo(sysmsgtime, gsysmsgtime), observer);
    }

    /**
     * 删除消息
     */
    public static void deleteSysMsg(DeleteSyaMsgRequestBean deleteSyaMsgRequestBean, BaseObserver<Object> observer) {
        setSubscribe(commonApi.deleteSysMsg(deleteSyaMsgRequestBean), observer);
    }

    /**
     * 删除消息
     */
//    public static void deleteSysMsgNew(String uid, ArrayList<String> deleteSyaMsgRequestBean, BaseObserver<Object> observer) {
//        setSubscribe(commonApi.deleteSysMsg(uid, deleteSyaMsgRequestBean), observer);
//    }


    /**
     * 邀请绑定用户数据
     */
    public static void getBindUserInfo(String userCode, BaseObserver<BindUserBean> observer) {
        setSubscribe(commonApi.getBindUserInfo(userCode), observer);
    }

    /**
     * 绑定操作
     */
    public static void addBindUser(String userCode, String inviterUnionid, BaseObserver<String> observer) {
        setSubscribe(commonApi.addBindUser(userCode, inviterUnionid), observer);
    }


    public static void loadSearchHistory(String unionId, BaseObserver<List<SearchHistory>> observer) {
        setSubscribe(commonApi.loadSearchHistory(unionId), observer);
    }

    public static void saveSearchHistoryToService(String unionId, String source, String deviceModel, String content, BaseObserver<Object> observer) {
        setSubscribe(commonApi.saveSearchHistoryToService(unionId, source, deviceModel, content), observer);
    }

    public static void loadIncomeProfile(String uid, BaseObserver<MyEarnings> observer) {
        setSubscribe(commonApi.loadIncomeProfile(uid), observer);
    }

    public static void loadEarningsRank(int top, BaseObserver<List<EarningsRank>> observer) {
        setSubscribe(commonApi.loadEarningsRank(top), observer);
    }

    public static void loadSavingsRank(int top, BaseObserver<List<EarningsRank>> observer) {
        setSubscribe(commonApi.loadSavingsRank(top), observer);
    }

    public static void loadSavingsProfile(String uid, BaseObserver<SavingsProfile> observer){
        setSubscribe(commonApi.loadSavingsProfile(uid),observer);
    }

    public static void checkBindAlipay(String uid, BaseObserver<BindPayInfo> observer){
        setSubscribe(commonApi.checkBindAlipay(uid,MyApplication.getLoginUser().getToken()),observer);
    }






}
