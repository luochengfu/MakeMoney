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

    public static final String MSG_CODE = "/getVerifCode";
    public static final String TEL_LOGIN = "/login";
    public static final String SETPWD = "/auth/user/setPwd";
    public static final String CONFIG = "/config";
    public static final String GOOD_LIST = "/shop/goodsCategory/list";

    /**
     * 网络环境名称数组
     */
    public static final String[] ENVIRONMENT_NAMES = {"生产", "预发布"};

    /**
     * 直播api基础地址数组
     */
    private static final String[] BASE_URLS = {"https://api.tudouni.doubozhibo.com", "http://118.31.9.209:8080"};

    /**
     * 商城H5基础地址数组
     */
    private static final String[] BASE_MALL_URLS = {"https://mall.tudouni.doubozhibo.com", "http://rc.doubozhibo.com"};

    /**
     * 土豆泥H5基础地址数组
     */
    private static final String[] BASE_TUDOUNI_H5_URLS = {"https://h5.tudouni.doubozhibo.com",
            "http://dev-sbzhibo-h5.oss-cn-hangzhou.aliyuncs.com"};

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
