package com.tudouni.makemoney.network;

/**
 * Created by ZhangPeng on 2018/4/19.
 */

import android.text.TextUtils;

import com.tudouni.makemoney.BuildConfig;
import com.tudouni.makemoney.utils.base.ACache;

/**
 * 网络配置类
 *
 * @author Storm
 */
public class NetConfig {

    public static final String MSG_CODE = "/zzshop/getVerifCode";
    public static final String TEL_LOGIN = "/zzshop/login";
    public static final String SETPWD = "/zzshop/auth/user/setPwd";
    public static final String CONFIG = "/zzshop/config";
    public static final String BINDPHONE = "/zzshop/auth/user/bindPhone";
    public static final String VERIFCODE_CHANGE = "/zzshop/auth/user/verifyCode";
    public static final String BINDPHONE2 = "/zzshop/auth/user/bindPhone_V2";
    public static final String BIND_NEW_PHONE = "/zzshop/login/thirdPartySNS/bindingPhone";
    public static final String VERIFCODE = "/auth/user/getVerifCode";
    public static final String PASSWORD_MSG_CODE = "/zzshop/user/getVerifCode";
    public static final String GETUSERINFO = "/zzshop/auth/user/info";
    public static final String ACCOUNT_BIND = "/zzshop/auth/user/bindStatus";
    public static final String PAY_STATUS = "/zzshop/auth/pay/status";
    public static final String UNBUNDTHREE = "/zzshop/auth/user/unBindThird";
    public static final String BINDACCCOUNT = "/zzshop/auth/user/bindAccount";
    public static final String ALIAUTH = "/auth/alipay/info";

    public static final String VERIFPWD = "/zzshop/auth/user/verifPwd";
    public static final String USERNAME_PASSWOED_LOGIN = "/zzshop/loginPwd";
    public static final String GOOD_LIST = "/zzshop/goodsCategory/list";
    public static final String INVITEINFO = "/zzshop/auth/invite/info";
    public static final String INVITEINFO_V2 = "/zzshop/auth/invite/info_V2";//查询邀请人的个人信息（简略信息）
    public static final String BINDING_INVITE = "/zzshop/auth/invite/action/addBinding";//绑定邀请
    public static final String AGENTINFO = "/zzshop/auth/user/agentInfo";//商城收益
    public static final String THIRD_LOGIN_PROCESS = "/zzshop/login/thirdPartySNS/loginWithProcess";
    public static final String FOUND_BANNER = "/zzshop/discovery/getbanner";
    public static final String MALL_BANNER = "zzshop/getindexbanner";

    public static final String UPLOAD_INFO = "/zzshop/auth/upAuth";
    public static final String SET_INFO = "/zzshop/auth/user/setInfo";
    public static final String UPINFO = "/zzshop/config/getUpgradeInfo";  //版本升级信息

    public static final String RECOMMEND_TOPIC = "/zzshop/discovery/getRecommendTopic";
    public static final String FOUND_TOPIC = "/zzshop/discovery/getTopic";



    /**
     * 网络环境名称数组
     */
    public static final String[] ENVIRONMENT_NAMES = {"生产", "预发布"};

    /**
     * 直播api基础地址数组
     */
    private static final String[] BASE_URLS = {"https://zhanapi.tudouni.doubozhibo.com", "http://118.31.9.209:8080"};

    /**
     * 商城H5基础地址数组
     */
    private static final String[] BASE_MALL_URLS = {"https://mall.tudouni.doubozhibo.com", "http://rc.doubozhibo.com"};

    /**
     * 土豆泥H5基础地址数组
     */
    private static final String[] BASE_TUDOUNI_H5_URLS = {"https://h5.tudouni.doubozhibo.com/shoplite/",
            "http://dev-sbzhibo-h5.oss-cn-hangzhou.aliyuncs.com/shoplite/"};

    /**
     * 土豆泥订单相关H5地址数据
     */
    private static final String[] BASE_H5_ORDER_URLS = {"http://h5.mall.doubozhibo.com/#/", "http://rc.doubozhibo.com/#/"};

    /**
     * 土豆泥活动规则跳转地址数组
     */
    private static final String[] BASE_ACTIVITY_RULE_URLS = {"https://wap.tudouni.doubozhibo.com", "https://waptest.tudouni.doubozhibo.com"};

    /**
     * 商城买家api基础地址数组 修改uat 改成了 rc！！！！
     */
    private static final String[] BASE_BUYER_URLS = {"http://buyerapi.mall.doubozhibo.com", "http://buyer.rc.doubozhibo.com"};

    /**
     * 商城卖家后台地址api
     */
    private static final String[] BASE_SALE_URLS = {"http://seller.rc.doubozhibo.com/api", "http://seller.rc.doubozhibo.com/api"};

    /**
     * 遊戲后台地址api  zp
     */
    private static final String[] BASE_GAME_URLS = {"https://wap.tudouni.doubozhibo.com", "http://47.96.29.209:8080"};

    /**
     * 融云key数组
     */
    private static final String[] RONG_CLOUD_APP_KEYS = {"n19jmcy5ndgn9", "8brlm7uf8j6x3"};

    /**
     * 商城首页
     */
    private static final String[] SHOP_MAIN_URL = {"http://uat-static-shop.tudouni.doubozhibo.com", "http://uat-static-shop.tudouni.doubozhibo.com"};

    /**
     * 网络环境-默认
     */
    private static final int ENVIRONMENT_DEFAULT = BuildConfig.DEBUG ? 1 : 0;

    private static final String[] SHOPPING_BASE_URLS = {"http://uat-static-shop.tudouni.doubozhibo.com", "http://uat-static-shop.tudouni.doubozhibo.com"};


    /**
     * 获取网络环境index
     */
    public static int getEnvironment() {
        if ("release".equals(BuildConfig.BUILD_TYPE)) {
            return 0;
        }

        int environment = ENVIRONMENT_DEFAULT;
        if (!TextUtils.isEmpty(ACache.getInstance().getAsString("NetEnvironment"))) {
            environment = Integer.valueOf(ACache.getInstance().getAsString("NetEnvironment"));
        }
        return environment;
    }

    /**
     * 设置网络环境
     */
    public static void setEnvironment(int environment) {
        ACache.getInstance().put("NetEnvironment", String.valueOf(environment));
    }

    /**
     * 获取网络环境name
     */
    public static String getEnvironmentName() {
        return ENVIRONMENT_NAMES[getEnvironment()];
    }

    public static String getShoppingMailUrl() {
        return SHOPPING_BASE_URLS[getEnvironment()];
    }

    public static String getActiviryRuleUrl() {
        return BASE_ACTIVITY_RULE_URLS[getEnvironment()];
    }

    public static String getShopMainUrl() {
        return SHOP_MAIN_URL[getEnvironment()];
    }


    /**
     * 获取服务端api的基础地址，包含scheme和域名部分
     */
    public static String getBaseUrl() {
        return BASE_URLS[getEnvironment()];
    }

    /**
     * 获取商城H5的基础地址，包含scheme和域名部分
     */
    public static String getBaseMallUrl() {
        return BASE_MALL_URLS[getEnvironment()];
    }

    /**
     * 获取订单相关H5根地址
     */
    public static String getOrderH5Url() {
        return BASE_H5_ORDER_URLS[getEnvironment()];
    }

    /**
     * 获取商城买家api的基础地址，包含scheme和域名部分
     */
    public static String getBaseBuyerUrl() {
        return BASE_BUYER_URLS[getEnvironment()];
    }

    /**
     * 获取商城卖家api基础地址
     */
    public static String getBaseSaleUrl() {
        return BASE_SALE_URLS[getEnvironment()];
    }

    /**
     * 获取商城卖家api基础地址
     */
    public static String getBaseGameUrl() {
        return BASE_GAME_URLS[getEnvironment()];
    }

    /**
     * 土豆泥H5基础地址，包含scheme和域名部分
     */
    public static String getBaseTuDouNiH5Url() {
        return BASE_TUDOUNI_H5_URLS[getEnvironment()];
    }

    /**
     * 获取融云app key
     */
    public static String getRongIMAppKey() {
        return RONG_CLOUD_APP_KEYS[getEnvironment()];
    }

    /**
     * 神策
     */
    public static String getSensorServerURL() {
        return "release".equals(BuildConfig.BUILD_TYPE) ?
                "http://118.31.67.231:8106/sa?project=production" : "http://118.31.67.231:8106/sa?project=default";
    }

    /**
     * 神策
     */
    public static String getSensorConfigureUrl() {
        return "release".equals(BuildConfig.BUILD_TYPE) ?
                "http://118.31.67.231:8106/config/?project=production" : "http://118.31.67.231:8106/config/?project=default";
    }

    /**
     * BguTags key数组
     */
    private static final String[] DUG_TAGS_APP_KEYS = {"2b3eab9096d40ca20ffd564ee1d1876c", "fc98ac531a6ace787e81c2e2091ce521"};

    /**
     * 获取BguTags app key
     */
    public static String getBugTagsAppKey() {
        return DUG_TAGS_APP_KEYS[getEnvironment()];
    }

}
